package com.example.binviewapp.network.response

import retrofit2.Response
import java.lang.Exception

data class NetworkResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: java.lang.Exception?
) {

    companion object{
        fun<T> success(data: Response<T>): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }
        fun<T> failure(exception: Exception): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }

    }

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !failed && this.data?.isSuccessful == true

    val body: T
        get() = this.data!!.body()!!

}