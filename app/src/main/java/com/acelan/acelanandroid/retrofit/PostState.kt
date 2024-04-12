package com.acelan.acelanandroid.retrofit

import com.acelan.acelanandroid.data.singleData.Token

sealed class PostState {
    object Loading : PostState()
    data class Success(val token: Token) : PostState()
    data class Error(val error: String) : PostState()
}