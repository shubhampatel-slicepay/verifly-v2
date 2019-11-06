package com.slice.verifly.data.remote

object ApiEndPoints {

    // Header params
    const val AUTH = "Authorization: Basic YnVkZHlhcGlhZG1pbjptZW1vbmdvc2gx"
    const val CONTENT = "Content-Type: application/json"
    const val ACCEPT = "Accept: application/json"

    // End points
    const val INTERN_AUTHENTICATION = "/authenticate"
    const val INTERN_LOG_IN = "auth/login/intern"
    const val INTERN_ACCESS_REQUEST = "auth/intern/passwd/token"
    const val INTERN_CHANGE_PASSWORD = "auth/intern/passwd"
    const val SEND_OTP = "auth/intern/sendotp"
    const val VERIFY_OTP = "auth/intern/verifyotp"

    const val GET_ASSIGNED_TASKS = "verifly/users"
    const val GET_EARNINGS_STRUCTURE = "verifly/earnings/structure"
    const val GET_EARNING_BY_MONTH = "verifly/earnings/history"
    const val GET_TOTAL_EARNINGS = "verifly/earnings"
    const val GET_RATINGS = "verifly/ratings"
    const val ADD_USER_NOTES = "verifly/user/notes"
    const val CALL_USER = "verifly/user/call"
    const val SUBMIT_TASK = "verifly/task/details"
    const val UPDATE_TASK_DUE = "verifly/user/date"
    const val UPDATE_TASK_NOT_INTERESTED_REASON = "verifly/task/notinterested"

    const val GET_TASK_NOT_INTERESTED_REASONS = "content/dropdown/verifly"
    const val GET_VERSION_CODE = "content/app/version?platform=androidVerifly"
}