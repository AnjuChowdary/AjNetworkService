package com.ae.ajnetworkservice.repository

import com.ae.ajnetworkservice.service.IServiceRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val NETWORK_REQUEST_TIMEOUT_SECONDS = 15L

fun provideRetrofit(baseUrl: String): IServiceRequest =
    Retrofit.Builder()
        .client(getHttpClient())
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IServiceRequest::class.java)

private fun getHttpClient() =
    OkHttpClient.Builder()
        .connectTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()