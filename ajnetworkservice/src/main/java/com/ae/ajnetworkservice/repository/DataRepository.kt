package com.ae.ajnetworkservice.repository

import com.ae.ajnetworkservice.service.IServiceRequest
import com.ae.ajnetworkservice.utils.NetworkResponse
import retrofit2.Response

class DataRepository(private val mIServiceRequest: IServiceRequest) {

    internal suspend fun <T> getCall(endPoint: String) = kotlin.runCatching {
        wrapNetworkResponse<T>(mIServiceRequest.getCall(fullUrl = endPoint))
    }.getOrElse {
        NetworkResponse.Error(errorMessage = it.message)
    }

    internal suspend fun <T> postCall(endPoint: String, request: Any) = kotlin.runCatching {
        wrapNetworkResponse<T>(mIServiceRequest.postCall(fullUrl = endPoint, body = request))
    }.getOrElse {
        NetworkResponse.Error(errorMessage = it.message)
    }

    private fun <T> wrapNetworkResponse(networkCallResult: Response<T>) = networkCallResult.runCatching {
        if (this.isSuccessful) NetworkResponse.Success(data = this.body())
        else NetworkResponse.Error(errorMessage = this.message())
    }.getOrDefault(NetworkResponse.Error(errorMessage = "Something went wrong"))
}