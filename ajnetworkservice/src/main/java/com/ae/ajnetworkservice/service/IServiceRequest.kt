package com.ae.ajnetworkservice.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface IServiceRequest {
    @GET
    suspend fun <T> getCall(@Url fullUrl: String): Response<T>

    @POST
    suspend fun <T> postCall(@Url fullUrl: String, @Body body: Any): Response<T>
}