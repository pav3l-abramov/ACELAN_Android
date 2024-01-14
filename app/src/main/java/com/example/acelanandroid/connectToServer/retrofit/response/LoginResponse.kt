package com.example.acelanandroid.connectToServer.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val token: String
)
