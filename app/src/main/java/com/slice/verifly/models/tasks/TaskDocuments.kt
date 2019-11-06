package com.slice.verifly.models.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class TaskDocuments (

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("assignedTo")
    val assignedTo: String? = null,

    @field:SerializedName("taskID")
    val taskID: String? = null,

    @field:SerializedName("userid")
    val userid: String? = null,

    @field:SerializedName("assignDate")
    val assignDate: String? = null,

    @field:SerializedName("scheduleDate")
    val scheduleDate: Date? = null,

    @field:SerializedName("scheduleSlot")
    val scheduleSlot: String? = null,

    @field:SerializedName("assignedToName")
    val assignedToName: String? = null,

    @field:SerializedName("assignedToCollege")
    val assignedToCollege: String? = null,

    @field:SerializedName("userUuid")
    val userUuid: String? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    @field:SerializedName("userCollege")
    val userCollege: String? = null,

    @field:SerializedName("userFbUserId")
    val userFbUserId: String? = null,

    @field:SerializedName("userSelfie")
    val userSelfie: String? = null,

    @field:SerializedName("userBranch")
    val userBranch: String? = null,

    @field:SerializedName("userAccomodation")
    val userAccomodation: String? = null,

    @field:SerializedName("userAddress")
    val userAddress: UserAddress? = null,

    @field:SerializedName("taskStatus")
    val taskStatus: String? = null,

    @field:SerializedName("taskName")
    val taskName: String? = null,

    @field:SerializedName("differentColleges")
    val differentColleges: Boolean? = null,

    @field:SerializedName("userFriendNumber")
    val userFriendNumber: String? = null,

    @field:SerializedName("userFriendName")
    val userFriendName: String? = null,

    @field:SerializedName("notes")
    val notes: ArrayList<Notes?>? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("userCollegeId")
    val userCollegeId: UserCollegeID? = null,

    @field:SerializedName("TASK_TYPE")
    val TASK_TYPE: Int? = null,

    @field:SerializedName("pan")
    val pan: String? = null,

    @field:SerializedName("addressProofFront")
    val addressProofFront: String? = null,

    @field:SerializedName("addressProofBack")
    val addressProofBack: String? = null,

    @field:SerializedName("addressProofType")
    val addressProofType: String? = null,

    @field:SerializedName("userPhone")
    val userPhone: String? = null
) : Parcelable