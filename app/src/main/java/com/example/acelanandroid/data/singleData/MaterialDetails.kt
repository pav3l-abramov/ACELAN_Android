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
//    val stiffness: List<Float>? = null,//1x36
//    val piezo: List<Float>? = null,//1x18
    val stiffness: Stiffness? = null,//1x36
    val piezo: Piezo? = null,//1x18
    val density: Int? = null,
//    val dielectric: List<Float>? = null,//1x9
    val dielectric: Dielectric? = null,//1x9
    val dielectricScalar: Float? = null
)

data class Stiffness(
    val c11 :Float?=0f,
    val c12 :Float?=0f,
    val c13 :Float?=0f,
    val c22 :Float?=0f,
    val c23 :Float?=0f,
    val c33 :Float?=0f,
    val c44 :Float?=0f,
    val c55 :Float?=0f,
    val c66 :Float?=0f,
)

data class Dielectric(
    val k11 :Float?=0f,
    val k22 :Float?=0f,
    val k33 :Float?=0f,
)

data class Piezo(
    val e11 :Float?=0f,
    val e12 :Float?=0f,
    val e13 :Float?=0f,
    val e14 :Float?=0f,
    val e15 :Float?=0f,
    val e16 :Float?=0f,
    val e21 :Float?=0f,
    val e22 :Float?=0f,
    val e23 :Float?=0f,
    val e24 :Float?=0f,
    val e25 :Float?=0f,
    val e26 :Float?=0f,
    val e31 :Float?=0f,
    val e32 :Float?=0f,
    val e33 :Float?=0f,
    val e34 :Float?=0f,
    val e35 :Float?=0f,
    val e36 :Float?=0f,
)