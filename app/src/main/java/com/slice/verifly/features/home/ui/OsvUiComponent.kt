package com.slice.verifly.features.home.ui

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.slice.verifly.R
import com.slice.verifly.customview.FileUploaderView
import com.slice.verifly.features.home.base.BaseUiComponent
import com.slice.verifly.features.home.communicator.UiComponentCommunicator
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.models.tasks.UserAddress
import com.slice.verifly.utility.AppUtils
import com.slice.verifly.utility.Constants
import kotlinx.android.synthetic.main.layout_task_form_osv.view.*
import java.util.Date

class OsvUiComponent(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val taskDoc: TaskDocuments,
    isEditable: Boolean = true,
    private val communicator: UiComponentCommunicator?
): BaseUiComponent(context, attrs, defStyleAttr) {

    companion object {
        fun newInstance(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0,
            taskDoc: TaskDocuments,
            isEditable: Boolean = true,
            communicator: UiComponentCommunicator?
        ) = OsvUiComponent(context, attrs, defStyleAttr, taskDoc, isEditable, communicator)

        private var editable = Editable.Factory.getInstance()

        private const val PAN = "pan"
        private const val USER_PHOTO = "userPhoto"
        private const val ADDRESS_PROOF_TYPE = "addressProofType"
        private const val FRONT_ADDRESS_PROOF = "frontAddressProof"
        private const val REAR_ADDRESS_PROOF = "rearAddressProof"

        private const val ADDRESS_PROOF_AADHAR = "aadhaar"
        private const val ADDRESS_PROOF_DL = "driving"
        private const val ADDRESS_PROOF_PASSPORT = "passport"
        private const val ADDRESS_PROOF_VOTER = "voter"

        private const val AADHAR_CARD = "Aadhar Card"
        private const val DL = "Driving License"
        private const val PASSPORT = "Passport"
        private const val VOTER_ID = "Voter ID"
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_task_form_osv, this, true)
        setName(name = taskDoc.data?.userName, isEditable = isEditable)
        setAddress(userAddress = taskDoc.userAddress, isEditable = isEditable)
        setPhone(phone = taskDoc.userPhone, isEditable = isEditable)
        setSelfie(selfieUrl = taskDoc.userSelfie, isEditable = isEditable)
        setDate(date = taskDoc.scheduleDate, isEditable = isEditable)
        setTime(time = taskDoc.scheduleSlot, isEditable = isEditable)
        setPAN(panUrl = taskDoc.data?.documents?.get(PAN), isEditable = isEditable)
        setAddressProof(
            addressProofType = taskDoc.data?.documents?.get(ADDRESS_PROOF_TYPE),
            frontAddressProofUrl = taskDoc.data?.documents?.get(FRONT_ADDRESS_PROOF),
            rearAddressProofUrl = taskDoc.data?.documents?.get(REAR_ADDRESS_PROOF),
            isEditable = isEditable
        )
        setPhotoOfUser(photoOfUserUrl = taskDoc.data?.documents?.get(USER_PHOTO), isEditable = isEditable)
        setComment(comment = taskDoc.data?.comment, isEditable = isEditable)
    }

    // Properties

    private var selfieUploader: FileUploaderView? = null
    private var panUploader: FileUploaderView? = null
    private var frontAddressProofUploader: FileUploaderView? = null
    private var rearAddressProofUploader: FileUploaderView? = null
    private var photoOfUserUploader: FileUploaderView? = null

    private var selfieFilePath: String? = null
    private var panFilePath: String? = null
    private var frontAddressProofFilePath: String? = null
    private var rearAddressProofFilePath: String? = null
    private var photoOfUserFilePath: String? = null

    // Operations

    // name -> mandatory

    private fun setName(name: String?, isEditable: Boolean) {
        tiet_name.text = editable.newEditable(name ?: "-")
    }

    // address -> mandatory

    private fun setAddress(userAddress: UserAddress?, isEditable: Boolean) {
        val address = userAddress?.let {
            it.line1?:""
                .plus(", ").plus(it.line2?:"")
                .plus(", ").plus(it.city?:"")
                .plus(", ").plus(it.pincode?:"")
        }
        tiet_address.text = editable.newEditable(address ?: "-")
    }

    // phone -> mandatory

    private fun setPhone(phone: String?, isEditable: Boolean) {
        tiet_phone.text = editable.newEditable(phone ?: "-")
    }

    // selfie -> optional

    private fun setSelfie(selfieUrl: String?, isEditable: Boolean) {
        if (isEditable) {
            showSelfie()
            selfieUploader?.isEnabled = true
            selfieUploader?.applyListener(Constants.OSV_SELFIE_MEDIA_REQ_CODE)
        } else {
            selfieUrl?.let {
                showSelfie()
                selfieUploader?.loadImage(it)
                selfieUploader?.isEnabled = false
            } ?: hideSelfie()
        }
    }

    private fun hideSelfie() {
        cl_selfie_container.visibility = View.GONE
    }

    private fun showSelfie() {
        cl_selfie_container.visibility = View.VISIBLE
        selfieUploader = FileUploaderView(context)
        ll_selfie.addView(selfieUploader)
    }

    // date -> mandatory

    private fun setDate(date: Date?, isEditable: Boolean) {
        val formattedDate = date?.let {
            AppUtils.formatDate(it)
        }
        tiet_date.text = editable.newEditable(formattedDate ?: "-")
    }

    // time -> mandatory

    private fun setTime(time: String?, isEditable: Boolean) {
        tiet_time.text = editable.newEditable(time ?: "-")
    }

    // pan -> optional

    private fun setPAN(panUrl: String?, isEditable: Boolean) {
        if (isEditable) {
            showPAN()
            panUploader?.isEnabled = true
            panUploader?.applyListener(Constants.OSV_PAN_MEDIA_REQ_CODE)
        } else {
            panUrl?.let {
                showPAN()
                panUploader?.loadImage(it)
                panUploader?.isEnabled = false
            } ?: hidePAN()
        }
    }

    private fun hidePAN() {
        cl_pan_container.visibility = View.GONE
    }

    private fun showPAN() {
        cl_pan_container.visibility = View.VISIBLE
        panUploader = FileUploaderView(context)
        ll_pan.addView(panUploader)
    }

    // address proof -> optional

    private fun setAddressProof(
        addressProofType: String?,
        frontAddressProofUrl: String?,
        rearAddressProofUrl: String?,
        isEditable: Boolean
    ) {
        if (isEditable) {
            showFrontAddressProof()
            showRearAddressProof()
            frontAddressProofUploader?.isEnabled = true
            frontAddressProofUploader?.applyListener(Constants.OSV_FRONT_ADDRESS_PROOF_MEDIA_REQ_CODE)
            rearAddressProofUploader?.isEnabled = true
            rearAddressProofUploader?.applyListener(Constants.OSV_REAR_ADDRESS_PROOF_MEDIA_REQ_CODE)
        } else {
            frontAddressProofUrl?.let {
                showFrontAddressProof()
                frontAddressProofUploader?.loadImage(it)
                frontAddressProofUploader?.isEnabled = false
            }
            rearAddressProofUrl?.let {
                showFrontAddressProof()
                rearAddressProofUploader?.loadImage(it)
                rearAddressProofUploader?.isEnabled = false
            }
        }
    }

    private fun showFrontAddressProof() {
        frontAddressProofUploader = FileUploaderView(context)
        ll_address_proofs.addView(frontAddressProofUploader)
    }

    private fun showRearAddressProof() {
        rearAddressProofUploader = FileUploaderView(context)
        ll_address_proofs.addView(rearAddressProofUploader)
    }

    // photo of user -> optional

    private fun setPhotoOfUser(photoOfUserUrl: String?, isEditable: Boolean) {
        if (isEditable) {
            showPhotoOfUser()
            photoOfUserUploader?.isEnabled = true
            photoOfUserUploader?.applyListener(Constants.OSV_PHOTO_OF_USER_MEDIA_REQ_CODE)
        } else {
            photoOfUserUrl?.let {
                showPhotoOfUser()
                photoOfUserUploader?.loadImage(it)
                photoOfUserUploader?.isEnabled = false
            } ?: hidePhotoOfUser()
        }
    }

    private fun hidePhotoOfUser() {
        cl_photo_of_user_container.visibility = View.GONE
    }

    private fun showPhotoOfUser() {
        cl_photo_of_user_container.visibility = View.VISIBLE
        photoOfUserUploader = FileUploaderView(context)
        ll_photo_of_user.addView(photoOfUserUploader)
    }

    // comment -> optional

    private fun setComment(comment: String?, isEditable: Boolean) {
        if (isEditable) {
            showComment()
        } else {
            comment?.let {
                showComment()
                tiet_comment.text = editable.newEditable(it)
            } ?: hideComment()
        }
    }

    private fun hideComment() {
        til_comment.visibility = View.GONE
    }

    private fun showComment() {
        til_comment.visibility = View.VISIBLE
    }

    // utility methods

    private fun FileUploaderView.applyListener(reqCode: Int) {
        this.setOnClickListener {
            communicator?.onMediaAccessRequested(reqCode)
        }
    }

    // public functions for view operations

    fun upload(reqCode: Int, filePath: String) {
        when (reqCode) {
            Constants.OSV_SELFIE_MEDIA_REQ_CODE -> {
                selfieFilePath = filePath
                selfieUploader?.loadImage(filePath)
            }

            Constants.OSV_PAN_MEDIA_REQ_CODE -> {
                panFilePath = filePath
                panUploader?.loadImage(filePath)
            }

            Constants.OSV_FRONT_ADDRESS_PROOF_MEDIA_REQ_CODE -> {
                frontAddressProofFilePath = filePath
                frontAddressProofUploader?.loadImage(filePath)
            }

            Constants.OSV_REAR_ADDRESS_PROOF_MEDIA_REQ_CODE -> {
                rearAddressProofFilePath = filePath
                rearAddressProofUploader?.loadImage(filePath)
            }

            Constants.OSV_PHOTO_OF_USER_MEDIA_REQ_CODE -> {
                photoOfUserFilePath = filePath
                photoOfUserUploader?.loadImage(filePath)
            }
        }
    }

    fun validate(): Boolean {
        if (rb_selfie_not_verified.isChecked) return false
        if (cl_pan_container.isVisible and rb_pan_not_verified.isChecked) return false
        if (rb_address_proofs_not_verified.isChecked) return false
        return true
    }

    fun submit() {
        val files = arrayOf(
            selfieFilePath,
            frontAddressProofFilePath,
            rearAddressProofFilePath
        )
        communicator?.uploadFilesToCloudinary(files)
    }
}