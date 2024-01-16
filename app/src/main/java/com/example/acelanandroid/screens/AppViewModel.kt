package com.example.acelanandroid.screens

import androidx.lifecycle.ViewModel
import com.example.acelanandroid.HOST_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltViewModel
class AppViewModel:ViewModel() {


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