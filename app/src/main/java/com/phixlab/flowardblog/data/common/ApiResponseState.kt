package com.phixlab.flowardblog.data.common

data class ApiResponseState<T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?): ApiResponseState<T> {
            return ApiResponseState(Status.SUCCESS, data, null)
        }


        fun <T> error(msg: String): ApiResponseState<T> {
            return ApiResponseState(Status.ERROR, null, msg)
        }

        fun <T> loading(): ApiResponseState<T> {
            return ApiResponseState(Status.LOADING, null, null)
        }
    }
}
