package com.example.acelanandroid.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acelanandroid.DRAW
import com.example.acelanandroid.MATERIAL
import com.example.acelanandroid.TASK
import com.example.acelanandroid.USER
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.MaterialToDraw
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    //user
    @Query("SELECT COUNT(*) FROM $USER")
    fun isUserExists(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userData: UserData)

    @Query("DELETE FROM $USER")
    suspend fun deleteUser()

    @Query("SELECT * FROM $USER")
    suspend fun getUser(): UserData


    //material and task
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMaterial(materialMain: MaterialMain)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(taskMain: TaskMain)

    @Delete
    suspend fun deleteMaterial(materialMain: MaterialMain)

    @Delete
    suspend fun deleteTask(taskMain: TaskMain)

    @Query("SELECT * FROM $MATERIAL")
    suspend fun getMaterialMain(): List<MaterialMain>

    @Query("SELECT * FROM $TASK")
    suspend fun getTaskMain(): List<TaskMain>

    @Query("SELECT * FROM $TASK WHERE id=:id")
    suspend fun getTaskMainByID(id: Int): TaskMain

    @Query("SELECT * FROM $MATERIAL WHERE id=:id")
    suspend fun getMaterialMainByID(id: Int): MaterialMain

    @Query("UPDATE $MATERIAL SET name=:name,type=:type,source=:source,created_at=:created_at,updated_at=:updated_at,core=:core  WHERE id=:id")
    fun updateMaterialMain(
        name: String?=null,
        type: String?=null,
        source: String?=null,
        created_at: String?=null,
        updated_at: String?=null,
        core: Boolean?=null,
        id: Int
    )
    @Query("UPDATE $MATERIAL SET young=:young,poison=:poison,stiffness=:stiffness,piezo=:piezo,dielectric=:dielectric  WHERE id=:id")
    fun updateMaterialDetailMain(
        young: String?=null,
        poison: String?=null,
        stiffness: List<Float>?=null,
        piezo: List<Float>?=null,
        dielectric: List<Float>?=null,
        id: Int
    )
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMaterialToDraw(materialToDraw: MaterialToDraw)

    @Query("DELETE FROM $DRAW WHERE id = :id")
    suspend fun deleteMaterialToDrawById(id: Int)

    @Query("UPDATE $TASK SET name=:name,status=:status,started_at=:started_at,finished_at=:finished_at  WHERE id=:id")
    fun updateTaskMain(
        name: String? = null,
        status: String? = null,
        started_at: String? = null,
        finished_at: String? = null,
        id: Int
    )

    @Query("UPDATE $TASK SET file_type=:file_type,url=:url,graph_type=:graph_type, x=:x,y=:y  WHERE id=:id")
    fun updateTaskDetail(
        file_type: String? = null,
        url: String? = null,
        graph_type:String?=null,
        x: List<Float>? = null,
        y: List<Float>? = null, id: Int
    )
    @Query("SELECT COUNT(*) FROM $TASK")
    fun countTasks(): Int
}
