package com.slice.verifly.features.home.ui

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import coil.api.load
import com.slice.verifly.R
import com.slice.verifly.features.home.base.BaseUiComponent
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.AppUtils
import kotlinx.android.synthetic.main.layout_task_osv.view.*

class OsvUiComponent(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val taskDoc: TaskDocuments,
    isEditable: Boolean = true
): BaseUiComponent(context, attrs, defStyleAttr) {

    companion object {
        fun newInstance(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0,
            taskDoc: TaskDocuments,
            isEditable: Boolean = true
        ) = OsvUiComponent(context, attrs, defStyleAttr, taskDoc, isEditable)

        private lateinit var editable: Editable.Factory

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
        if (isEditable) {
            editable = Editable.Factory.getInstance()
            enableEditableForm()
        } else {
            enableUnEditableForm()
        }
    }

    private fun enableEditableForm() {

    }

    private fun enableUnEditableForm() {

    }

    /*private fun enableUnEditableForm() {
        // name
        taskDoc.data?.userName?.let {
            tiet_name.text = editable.newEditable(it)
        } ?: kotlin.run {
            tiet_name.text = editable.newEditable("-")
        }

        // address
        taskDoc.userAddress?.let {
            val address = it.line1?:""
                .plus(", ").plus(it.line2?:"")
                .plus(", ").plus(it.city?:"")
                .plus(", ").plus(it.pincode?:"")
            tiet_address.text = editable.newEditable(address)
        } ?: kotlin.run {
            tiet_address.text = editable.newEditable("-")
        }

        // phone
        taskDoc.userPhone?.let {
            tiet_phone.text = editable.newEditable(it)
        } ?: kotlin.run {
            tiet_phone.text = editable.newEditable("-")
        }

        // selfie
        taskDoc.userSelfie?.let {
            ll_selfie.visibility = View.VISIBLE
            iv_selfie.load(it)
        } ?: kotlin.run {
            ll_selfie.visibility = View.GONE
        }

        taskDoc.data?.documents?.let { doc ->
            // PAN
            if (!doc[PAN].isNullOrEmpty()) {
                ll_pan.visibility = View.VISIBLE
                iv_pan.load(doc[PAN])
                rb_pan_verified_yes.isChecked = true
                rg_pan_verification.isEnabled = false
            } else {
                ll_pan.visibility = View.GONE
            }

            // user photo
            if (!doc[USER_PHOTO].isNullOrEmpty()) {
                ll_selfie_with_user.visibility = View.VISIBLE
                iv_add_selfie_with_user.visibility = View.GONE
                iv_selfie_with_user.visibility = View.VISIBLE
                iv_selfie_with_user.load(doc[USER_PHOTO])
            } else {
                ll_selfie_with_user.visibility = View.GONE
            }

            // address proof type
            if (!doc[ADDRESS_PROOF_TYPE].isNullOrEmpty()) {
                tv_address_proof_chosen.visibility = View.VISIBLE
                val addressProofType = when {
                    (doc[ADDRESS_PROOF_TYPE] ?: error("")).contains(ADDRESS_PROOF_AADHAR) -> AADHAR_CARD
                    (doc[ADDRESS_PROOF_TYPE] ?: error("")).contains(ADDRESS_PROOF_DL) -> DL
                    (doc[ADDRESS_PROOF_TYPE] ?: error("")).contains(ADDRESS_PROOF_PASSPORT) -> PASSPORT
                    (doc[ADDRESS_PROOF_TYPE] ?: error("")).contains(ADDRESS_PROOF_VOTER) -> VOTER_ID
                    else -> ""
                }
                if (addressProofType.isNotEmpty()) {
                    tv_address_proof_chosen.text = editable.newEditable(addressProofType)
                }
            } else {
                tv_address_proof_chosen.visibility = View.GONE
            }

            // front address proof
            ll_add_address_proof_front.visibility = View.GONE
            if (!doc[FRONT_ADDRESS_PROOF].isNullOrEmpty()) {
                iv_address_proof_front.visibility = View.VISIBLE
                iv_address_proof_front.load(doc[FRONT_ADDRESS_PROOF])
            } else {
                iv_address_proof_front.visibility = View.GONE
            }

            // rear address proof
            ll_add_address_proof_back.visibility = View.GONE
            if (!doc[REAR_ADDRESS_PROOF].isNullOrEmpty()) {
                iv_address_proof_back.visibility = View.VISIBLE
                iv_address_proof_back.load(doc[REAR_ADDRESS_PROOF])
            } else {
                iv_address_proof_back.visibility = View.GONE
            }

            rb_address_proof_verified_yes.isChecked = true
            rg_address_proof_verification.isEnabled = false
        }

        // scheduled date
        taskDoc.scheduleDate?.let {
            tiet_date.text = editable.newEditable(AppUtils.formatDate(it))
        } ?: kotlin.run {
            tiet_date.text = editable.newEditable("-")
        }

        // scheduled time slot
        taskDoc.scheduleSlot?.let {
            tiet_time.text = editable.newEditable(it)
        } ?: kotlin.run {
            tiet_time.text = editable.newEditable("-")
        }

        // comments
        taskDoc.data?.comment?.let {
            til_comment.visibility = View.VISIBLE
            tiet_comment.text = editable.newEditable(it)
        } ?: kotlin.run {
            til_comment.visibility = View.GONE
        }
    }*/
}