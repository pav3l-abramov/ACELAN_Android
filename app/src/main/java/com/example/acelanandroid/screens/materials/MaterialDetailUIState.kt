package com.example.acelanandroid.screens.materials

import com.example.acelanandroid.retrofit.data.Stiffness


data class MaterialDetailUIState(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val source: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val core: Boolean? = null,
    val young:String?=null,
    val poison:String?=null,
    val stiffness: Stiffness?=null
)