package com.example.acelanandroid.retrofit

import com.example.acelanandroid.data.singleData.Token

sealed class PostState {
    object Loading : PostState()
    data class Success(val token: Token) : PostState()
    data class Error(val error: String) : PostState()
}