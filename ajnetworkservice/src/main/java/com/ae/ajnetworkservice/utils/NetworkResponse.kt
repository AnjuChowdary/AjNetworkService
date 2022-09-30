package com.ae.ajnetworkservice.utils

sealed class NetworkResponse<T>(val response: T? = null, val message: String? = null) {

    data class Success<T>(val data: T): NetworkResponse<T>(response = data)

    data class Error<T>(val errorMessage: String?, val data: T? = null): NetworkResponse<T>(response = data, message = errorMessage)

    class Loading<T>(): NetworkResponse<T>()

    class Idle<T>(): NetworkResponse<T>()
}
