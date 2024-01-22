package com.example.acelanandroid.retrofit.data

data class TaskDetails(
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val started_at:String? = null,
    val finished_at:String? = null,
    val artifacts: List<Artifact>? = null
)

data class Artifact(
    val id: Int? =null,
    val file_type: String? =null,
    val url: String? =null
)