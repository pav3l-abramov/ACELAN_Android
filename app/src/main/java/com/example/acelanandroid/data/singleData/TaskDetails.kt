package com.example.acelanandroid.data.singleData

data class TaskDetails(
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val started_at:String? = null,
    val finished_at:String? = null,
    val artifacts: List<Artifact>? = null,
    val figures: List<Figure>? = null
)

data class Artifact(
    val id: Int? =null,
    val file_type: String? =null,
    val url: String? =null
)
data class Figure(
    val id: Int? =null,
    val file_type: String? =null,
    val data: Data? =null
)
data class Data(
    val x :List<Float>? =null,
    val y :List<Float>? =null
)