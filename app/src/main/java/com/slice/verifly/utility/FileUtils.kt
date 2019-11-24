package com.slice.verifly.utility

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.slice.verifly.BuildConfig
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    // constants

    private const val TAG = "AppUtils"

    private const val EXTERNAL_STORAGE_AUTHORITY = "com.android.externalstorage.documents"
    private const val DOWNLOADS_AUTHORITY = "com.android.providers.downloads.documents"
    private const val MEDIA_STORAGE_AUTHORITY = "com.android.providers.media.documents"
    private const val GOOGLE_PHOTOS_AUTHORITY = "com.google.android.apps.photos.content"

    const val IMAGE = "image"
    const val VIDEO = "video"
    const val PDF = "pdf"

    // Public operational usages

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file)
    }

    fun getRealPath(context: Context, uri: Uri): String? {
        return if (Build.VERSION.SDK_INT < 19) {
            getRealPathForAPILevel11to18(context, uri)
        } else {
            getRealPathForAPILevel19(context, uri)
        }
    }

    fun getFileSizeInMb(filePath: String): Int {
        val file = File(filePath)
        val sizeInKb = (file.length() / 1024).toInt()
        return (sizeInKb / 1024)
    }

    fun resolveFile(context: Context, uri: Uri): String? {
        val fileType = context.contentResolver.getType(uri) ?: kotlin.run {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        }
        fileType?.let {
            return when {
                it.toLowerCase(Locale.ENGLISH).contains(IMAGE) -> IMAGE
                it.toLowerCase(Locale.ENGLISH).contains(VIDEO) -> VIDEO
                it.toLowerCase(Locale.ENGLISH).contains(PDF) -> PDF
                else -> return null
            }
        } ?: return null
    }

    fun uploadFile(
        localPath: String,
        uuid: String,
        userId: String,
        key: String,
        i: Int? = null
    ): String? {
        val config = AppUtils.configureCloudinary()
        val cloudinary = Cloudinary(config)
        val timestamp = (System.currentTimeMillis() / 100).toString()
        var publicId = "users/".plus(BuildConfig.ENVIRONMENT).plus("/")
            .plus(uuid + userId + key).plus(timestamp).plus(i)
        if (localPath.toLowerCase(Locale.ENGLISH).contains(".pdf")) {
            publicId += "_PDF"
        }
        try {
            cloudinary.uploader().upload(localPath, ObjectUtils.asMap("public_id", publicId))
        } catch (e: IOException) {
            e.printStackTrace()
            SlicePayLog.info(TAG, "uploadFile: ${e.message}")
            return null
        }
        return cloudinary.url().secure(true).generate(publicId)
    }

    // Private operational usages

    @SuppressLint("NewApi")
    private fun getRealPathForAPILevel11to18(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null
        val cursor = CursorLoader(context, uri, projection, null, null, null).loadInBackground()
        cursor?.let {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }
        return result
    }

    @SuppressLint("NewApi")
    private fun getRealPathForAPILevel19(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            when (uri.authority) {
                EXTERNAL_STORAGE_AUTHORITY -> { // ExternalStorageProvider
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    return if ("primary".equals(type, ignoreCase = true)) { // checking Main Memory
                        if (split.size > 1) {
                            Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                        } else {
                            Environment.getExternalStorageDirectory().toString() + "/"
                        }
                    } else { // checking SD Card
                        "storage" + "/" + docId.replace(":", "/")
                    }
                }

                DOWNLOADS_AUTHORITY -> { // DownloadsProvider
                    val fileName = getFilePath(context, uri)
                    fileName?.let {
                        return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    }
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), id.toLong())
                    return getDataColumn(context, contentUri)
                }

                MEDIA_STORAGE_AUTHORITY -> { // MediaProvider
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    when (type) {
                        "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }

                else -> {
                    return null
                }
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) { // MediaStore (and general)
            if (uri.authority == GOOGLE_PHOTOS_AUTHORITY) { // Return the remote address
                return uri.lastPathSegment
            }
            return getDataColumn(context, uri)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) { // File
            return uri.path
        } else {
            return null
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getFilePath(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        try {
            cursor = context.contentResolver.query(uri, projection, null, null,null)
            cursor?.let {
                if (it.moveToFirst()) {
                    val index = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                    return it.getString(index)
                }
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    @Throws(IllegalArgumentException::class)
    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String? = null,
        selectionArgs: Array<String>? = null
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = uri?.let { context.contentResolver.query(it, projection, selection, selectionArgs,null) }
            cursor?.let {
                if (it.moveToFirst()) {
                    val index = it.getColumnIndexOrThrow(column)
                    return it.getString(index)
                }
            }
        } finally {
            cursor?.close()
        }
        return null
    }
}