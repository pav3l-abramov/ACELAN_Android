package com.example.acelanandroid.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Login
import com.example.acelanandroid.room.MainDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val database: MainDB) : ViewModel() {

    val checkUser = mutableStateOf(false)

    //user
    val getUserDB = database.dao.getUser()
    suspend fun deleteUserDB() {
        database.dao.deleteUser()
        checkUser.value = false
    }

    suspend fun insertUserToDB(userData: UserData) {
        database.dao.insertUser(userData)
    }

    suspend fun userIsExist() {
        return withContext(Dispatchers.IO) {
            val count = database.dao.isUserExists()
            checkUser.value = count > 0
        }
    }


    //
    val taskListDB = database.dao.getTaskMain()
    val materialListDB = database.dao.getMaterialMain()

    suspend fun insertTaskToDB(taskFromServer: TaskMain) {
        database.dao.insertTask(taskFromServer)
    }

    suspend fun insertMaterialToDB(materialFromServer: MaterialMain) {
        database.dao.insertMaterial(materialFromServer)
    }

    fun getTaskByID(id: Int) =
        database.dao.getTaskMainByID(id)

    fun getMaterialByID(id: Int) =
        database.dao.getTaskMainByID(id)

    fun updateTaskMain(
        name: String? = null,
        status: String? = null,
        started_at: String? = null,
        finish_at: String? = null,
        id: Int
    ) = database.dao.updateTaskMain(name, status, started_at, finish_at, id)

    fun updateTaskDetail(
        file_type: String? = null,
        url: String? = null,
        x: List<Float>? = null,
        y: List<Float>? = null,
        id: Int
    ) = database.dao.updateTaskDetail(file_type, url, x, y, id)

}