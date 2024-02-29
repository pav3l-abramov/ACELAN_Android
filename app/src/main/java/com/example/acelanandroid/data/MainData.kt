package com.example.acelanandroid.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.acelanandroid.DRAW
import com.example.acelanandroid.MATERIAL
import com.example.acelanandroid.TASK
import com.example.acelanandroid.USER
import com.example.acelanandroid.room.FloatListConverter

@Entity(tableName = MATERIAL)
data class MaterialMain(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val source: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val core: Boolean? = null,
    val young:String?=null,
    val poison:String?=null,
    @TypeConverters(FloatListConverter::class)
    val stiffness: List<Float>? = null,
    @TypeConverters(FloatListConverter::class)
    val piezo: List<Float>? = null,
    @TypeConverters(FloatListConverter::class)
    val dielectric: List<Float>? = null
)
@Entity(tableName = TASK)
data class TaskMain(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val started_at:String? = null,
    val finished_at:String? = null,
    val file_type: String? =null,
    val graph_type: String? =null,
    val url: String? =null,
    @TypeConverters(FloatListConverter::class)
    val x :List<Float>? =null,
    @TypeConverters(FloatListConverter::class)
    val y :List<Float>? =null
)

@Entity(tableName = USER)
data class UserData(
    @PrimaryKey(autoGenerate = false)
    val id :Int?=null,
    val email:String?="",
    val token: String? = null
)
@Entity(tableName = DRAW)
data class MaterialToDraw(
    @PrimaryKey(autoGenerate = false)
    val id :Int?=null,
)