package com.example.acelanandroid.data.singleData

data class MaterialDetails(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val source: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val core: Boolean? = null,
    val properties: Properties? = null
)


data class Properties(
    val young:String?=null,
    val poison:String?=null,
    val stiffness: List<Float>? = null,//1x36
    val piezo: List<Float>? = null,//1x18
    val dielectric: List<Float>? = null,//1x9
    val dielectricScalar: Float? = null
)
