package com.ae.ajnetworkservice.repository

import com.ae.ajnetworkservice.utils.NetworkResponse

class AjNetworkingService(val baseUrl: String) {
    private val mDataRepository: DataRepository
        get() {
            return DataRepository(provideRetrofit(baseUrl = baseUrl))
        }

    suspend fun <T> getCall(endpoint: String): NetworkResponse<T?> {
        return mDataRepository.getCall(endPoint = endpoint)
    }

    suspend fun <T> postCall(endpoint: String, request: Any): NetworkResponse<T?> {
        return mDataRepository.postCall(endPoint = endpoint, request = request)
    }
}