package com.example.acelanandroid.data.singleData

data class Materials(
    val materials: List<Material>
)
data class Material(
    val id: Int,
    val name: String,
    val type: String,
    val source: String,
    val created_at: String,
    val updated_at: String,
    val core: Boolean
)
