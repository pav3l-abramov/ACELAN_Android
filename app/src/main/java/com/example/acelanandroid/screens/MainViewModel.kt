package com.example.acelanandroid.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Login
import com.example.acelanandroid.data.singleData.Task
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.room.MainDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val database: MainDB) : ViewModel() {

    val checkUser = mutableStateOf(false)
    val userDB = mutableStateOf(UserData())

    //user
    suspend fun getUserDB() {
        return withContext(Dispatchers.IO) {
            userDB.value=database.dao.getUser()

        }
    }

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

    val materialListDB = database.dao.getMaterialMain()

    private val _taskListDB = MutableStateFlow<List<TaskMain>>(emptyList())
    val taskListDB: StateFlow<List<TaskMain>> = _taskListDB

    suspend fun updateTaskList() {
        viewModelScope.launch {
            _taskListDB.value = database.dao.getTaskMain()
        }
    }
    private suspend fun insertTaskToDB(taskFromServer: TaskMain) {
        database.dao.insertTask(taskFromServer)
    }

    suspend fun insertMaterialToDB(materialFromServer: MaterialMain) {
        database.dao.insertMaterial(materialFromServer)
    }

    fun getTaskByID(id: Int) =
        database.dao.getTaskMainByID(id)

    fun getMaterialByID(id: Int) =
        database.dao.getTaskMainByID(id)

    private fun updateTaskMain(
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

    suspend fun handleSuccessState(state: GetStateTasks.Success) {
        val tasks = state.tasks.tasks
        tasks.forEach() {
            insertTaskToDB(TaskMain(it.id))
            updateTaskMain(
                it.name, it.status, it.started_at, it.finished_at,
                it.id
            )
        }
    }
    fun handleErrorState(state: GetStateTasks.Error) {
        val error = state.error
    }

    suspend fun deleteTaskDB(mainTask: TaskMain){database.dao.deleteTask(mainTask)}
}