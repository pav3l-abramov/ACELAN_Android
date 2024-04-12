package com.acelan.acelanandroid.data.singleData

data class Tasks(
    val tasks:List<Task>
)
data class Task(
    val id: Int,
    val name: String,
    val status: String,
    val started_at:String,
    val finished_at:String
)