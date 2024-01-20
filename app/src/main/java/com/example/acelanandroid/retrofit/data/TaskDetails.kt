package com.example.acelanandroid.retrofit.data

data class TaskDetails(
    val id: Int,
    val name: String,
    val status: String,
    val started_at:String,
    val finished_at:String,
    val artifacts: List<Artifact>
)

data class Artifact(
    val id: Int? =null,
    val file_type: String? =null,
    val url: String? =null
)