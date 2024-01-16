package com.example.acelanandroid.screens

import com.example.acelanandroid.HOST_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class AppRetrofit @Inject constructor() {


    val retrofit = Retrofit.Builder()
        .baseUrl(HOST_NAME)
        .client(buildOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create()).build()

    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}