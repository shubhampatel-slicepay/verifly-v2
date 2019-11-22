package com.slice.verifly.data.remote.interceptors

import android.text.TextUtils
import com.slice.verifly.utility.AppUtils
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.*
import java.io.IOException

object GzipRequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        if ((!AppUtils.isObjectNotEmpty(req)) or (!TextUtils.isEmpty(req.header("Content-Encoding")))) {
            return chain.proceed(req)
        }

        val compressedReq = req.newBuilder()
            .header("Content-Encoding", "gzip")
            .method(req.method(), forceContentLength(gzip(req.body())))
            .build()

        return chain.proceed(compressedReq)
    }

    private fun forceContentLength(requestBody: RequestBody): RequestBody {
        val buffer = Buffer()
        requestBody.writeTo(buffer)

        return object: RequestBody() {
            override fun contentType(): MediaType? {
                return requestBody.contentType()
            }

            @Throws(IOException::class)
            override fun writeTo(sink: BufferedSink) {
                sink.write(buffer.snapshot())
            }

            override fun contentLength(): Long {
                return buffer.size
            }
        }
    }

    private fun gzip(requestBody: RequestBody?): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType? {
                return requestBody?.contentType()
            }

            @Throws(IOException::class)
            override fun writeTo(sink: BufferedSink) {
                val gzipSink = GzipSink(sink).buffer()
                requestBody?.writeTo(gzipSink)
                gzipSink.close()
            }

            override fun contentLength(): Long {
                return -1
            }
        }
    }
}