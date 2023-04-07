package com.example.myrijks.ui.model

data class LiveDataResult<T>(val status: Status, val data: T?, val error: String?) {

    companion object {

        fun <T> loading(): LiveDataResult<T> {
            return LiveDataResult(Status.LOADING, null, null)
        }

        fun <T> success(data: T): LiveDataResult<T> {
            return LiveDataResult(Status.SUCCESS, data, null)
        }

        fun <T> error(error: String): LiveDataResult<T> {
            return LiveDataResult(Status.ERROR, null, error)
        }
    }

    val isSuccessful: Boolean
        get() = status == Status.SUCCESS
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}