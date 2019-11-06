package com.slice.verifly.models.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.slice.verifly.models.ImageDataModel
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Data (

    @field:SerializedName("amountCollected")
    val amountCollected: String? = null,

    @field:SerializedName("followUpNumber")
    val followUpNumber: String? = null,

    @field:SerializedName("userCategory")
    val userCategory: ArrayList<String?>? = null,

    @field:SerializedName("userCategoryReason")
    val userCategoryReason: String? = null,

    @field:SerializedName("scheduleDate")
    val scheduleDate: Date? = null,

    @field:SerializedName("userHostelName")
    val userHostelName: String? = null,

    @field:SerializedName("userHostelRoomNo")
    val userHostelRoomNo: String? = null,

    @field:SerializedName("goodFriendYN")
    val goodFriendYN: String? = null,

    @field:SerializedName("friendAcademicYear")
    val friendAcademicYear: String? = null,

    @field:SerializedName("friendAcademicDept")
    val friendAcademicDept: String? = null,

    @field:SerializedName("userPunctuality")
    val userPunctuality: String? = null,

    @field:SerializedName("userStudy")
    val userStudy: String? = null,

    @field:SerializedName("userExtras")
    val userExtras: String? = null,

    @field:SerializedName("userFinancial")
    val userFinancial: String? = null,

    @field:SerializedName("untruthfulStatus")
    val untruthfulStatus: String? = null,

    @field:SerializedName("yearOfStudy")
    val yearOfStudy: Int? = null,

    @field:SerializedName("backlogsByIntern")
    val backlogsByIntern: Int? = null,

    @field:SerializedName("friendNotes")
    val friendNotes: String? = null,

    @field:SerializedName("yearBack")
    val yearBack: String? = null,

    @field:SerializedName("collegeIdMatchYn")
    val collegeIdMatchYn: String? = null,

    @field:SerializedName("repayLoanOption")
    val repayLoanOption: String? = null,

    @field:SerializedName("financialCapability")
    val financialCapability: String? = null,

    @field:SerializedName("organised")
    val organised: String? = null,

    @field:SerializedName("trust")
    val trust: String? = null,

    @field:SerializedName("openness")
    val openness: String? = null,

    @field:SerializedName("social")
    val social: String? = null,

    @field:SerializedName("alternateSource")
    val alternateSource: String? = null,

    @field:SerializedName("lendMoneyYn")
    val lendMoneyYn: String? = null,

    @field:SerializedName("verificationNotes")
    val verificationNotes: String? = null,

    @field:SerializedName("nachSigned")
    val nachSigned: String? = null,

    @field:SerializedName("userSignMatchYn")
    val userSignMatchYn: String? = null,

    @field:SerializedName("gradeSheetImage")
    val gradeSheetImage: ImageDataModel? = null,

    @field:SerializedName("collegeIdImage")
    val collegeIdImage: ImageDataModel? = null,

    @field:SerializedName("nachUpdateImage")
    val nachUpdateImage: ImageDataModel? = null,

    @field:SerializedName("loanAgreement")
    val loanAgreement: ImageDataModel? = null,

    @field:SerializedName("selfieIm")
    val selfieIm: ImageDataModel? = null,

    @field:SerializedName("gradeSheetImages")
    val gradeSheetImages: List<String?>? = null,

    @field:SerializedName("selfie")
    val selfie: List<String?>? = null,

    @field:SerializedName("loanAgreementImages")
    val loanAgreementImages: List<String?>? = null,

    @field:SerializedName("collegeIdImages")
    val collegeIdImages: List<String?>? = null,

    @field:SerializedName("nachImages")
    val nachImages: List<String?>? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    @field:SerializedName("documents")
    val documents: Map<String?, String?>? = null,

    @field:SerializedName("assignedToName")
    val assignedToName: String? = null,

    @field:SerializedName("assignedToID")
    val assignedToID: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("collegeName")
    val collegeName: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("comment")
    val comment: String? = null
) : Parcelable