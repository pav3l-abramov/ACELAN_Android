package com.example.acelanandroid.retrofit.data

data class MaterialDetails(
    val id: Int,
    val name: String,
    val type: String,
    val source: String,
    val created_at: String,
    val updated_at: String,
    val core: Boolean,
    val properties: List<Properties>

)

//нужно доделать
data class Properties(
    val stiffness: List<Stiffness>,

)
data class Stiffness(
    val c:Float
)