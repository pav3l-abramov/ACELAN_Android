package com.example.acelanandroid.retrofit.data

import android.content.IntentSender.OnFinished

data class Tasks(
    val id: Int,
    val name: String,
    val status: String,
    val started_at:String,
    val finished_at:String
)
