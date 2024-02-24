package com.example.acelanandroid.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acelanandroid.MATERIAL
import com.example.acelanandroid.TASK
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.TaskMain
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(materialMain: MaterialMain)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskMain: TaskMain)

    @Delete
    suspend fun deleteMaterial(materialMain: MaterialMain)

    @Delete
    suspend fun deleteTask(taskMain: TaskMain)

    @Query("SELECT * FROM $MATERIAL")
    fun getMaterialMain(): Flow<List<MaterialMain>>

    @Query("SELECT * FROM $TASK")
    fun getTaskMain():Flow<List<TaskMain>>

}