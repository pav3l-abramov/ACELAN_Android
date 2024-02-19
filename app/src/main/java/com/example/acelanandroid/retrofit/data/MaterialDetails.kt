package com.example.acelanandroid.retrofit.data

data class MaterialDetails(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val source: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val core: Boolean? = null,
    //val properties: List<Properties>? = null

)

//нужно доделать
data class Properties(
    val stiffness: List<Stiffness>? = null,

)
data class Stiffness(
    val c11:Float? = null
)