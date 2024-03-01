package com.example.acelanandroid.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.MaterialToDraw
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Material
import com.example.acelanandroid.retrofit.GetStateMaterial
import com.example.acelanandroid.retrofit.GetStateMaterialDetail
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.room.MainDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            userDB.value = database.dao.getUser()

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


    //Task
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

    private val _taskDetailDB = MutableStateFlow(TaskMain())
    val taskDetailDB: StateFlow<TaskMain> = _taskDetailDB

    suspend fun getTaskByID(id: Int) {
        viewModelScope.launch {
            _taskDetailDB.value = database.dao.getTaskMainByID(id)
        }
    }

    private fun updateTaskMain(
        name: String? = null,
        status: String? = null,
        started_at: String? = null,
        finish_at: String? = null,
        id: Int
    ) = database.dao.updateTaskMain(name, status, started_at, finish_at, id)

    private fun updateTaskDetail(
        file_type: String? = null,
        url: String? = null,
        graph_type: String? = null,
        x: List<Float>? = null,
        y: List<Float>? = null,
        id: Int
    ) = database.dao.updateTaskDetail(file_type, url, graph_type, x, y, id)

    suspend fun handleSuccessStateTasksScreen(state: GetStateTasks.Success) {
        val tasks = state.tasks.tasks
        tasks.forEach() {
            insertTaskToDB(TaskMain(it.id))
            updateTaskMain(
                it.name, it.status, it.started_at, it.finished_at,
                it.id
            )
        }
    }

    fun handleErrorStateTasksScreen(state: GetStateTasks.Error) {
        val error = state.error
    }

    fun handleSuccessStateOpenTaskScreen(state: GetStateTaskDetail.Success) {
        val taskDetail = state.taskDetails

        updateTaskDetail(
            taskDetail.artifacts?.takeIf { it.isNotEmpty() }?.get(0)?.file_type,
            taskDetail.artifacts?.takeIf { it.isNotEmpty() }?.get(0)?.url,
            taskDetail.figures?.takeIf { it.isNotEmpty() }?.get(0)?.type,
            taskDetail.figures?.takeIf { it.isNotEmpty() }?.get(0)?.data?.x,
            taskDetail.figures?.takeIf { it.isNotEmpty() }?.get(0)?.data?.y,
            taskDetail.id!!
        )
    }

    fun handleErrorStateOpenTaskScreen(state: GetStateTaskDetail.Error) {
        val error = state.error
    }

    suspend fun deleteTaskDB(mainTask: TaskMain) {
        database.dao.deleteTask(mainTask)
    }


    //material
    private val _materialListDB = MutableStateFlow<List<MaterialMain>>(emptyList())
    val materialListDB: StateFlow<List<MaterialMain>> = _materialListDB

    private val _materialToSearch = MutableStateFlow<List<Material>>(emptyList())
    val materialToSearch: StateFlow<List<Material>> = _materialToSearch.asStateFlow()

    private val _materialDetailDB = MutableStateFlow(MaterialMain())
    val materialDetailDB: StateFlow<MaterialMain> = _materialDetailDB

    suspend fun updateMaterialList() {
        viewModelScope.launch {
            _materialListDB.value = database.dao.getMaterialMain()
        }
    }

    private suspend fun insertMaterialToDB(materialFromServer: MaterialMain) {
        database.dao.insertMaterial(materialFromServer)
    }


    suspend fun getMaterialByID(id: Int) {
        viewModelScope.launch {
            _materialDetailDB.value = database.dao.getMaterialMainByID(id)
        }
    }

    private fun updateMaterialMain(
        name: String? = null,
        type: String? = null,
        source: String? = null,
        created_at: String? = null,
        updated_at: String? = null,
        core: Boolean? = null,
        id: Int
    ) = database.dao.updateMaterialMain(name, type, source, created_at, updated_at, core, id)

    private fun updateMaterialDetail(
        young: String? = null,
        poison: String? = null,
        stiffness: List<Float>? = null,
        piezo: List<Float>? = null,
        dielectric: List<Float>? = null,
        id: Int
    ) = database.dao.updateMaterialDetailMain(young, poison, stiffness, piezo, dielectric, id)


    suspend fun insertMaterialToDraw(materialToDraw: MaterialToDraw) {
        database.dao.insertMaterialToDraw(materialToDraw)
    }

    suspend fun deleteMaterialToDraw(id: Int) {
        database.dao.deleteMaterialToDrawById(id)
    }

    suspend fun handleSuccessStateMaterialScreen(state: GetStateMaterial.Success) {

        val materials = state.materials.materials
        if (state.onSearch) {
            _materialToSearch.value = materials
        } else {
            materials.forEach() {
                insertMaterialToDB(MaterialMain(it.id))
                updateMaterialMain(
                    it.name, it.type, it.source, it.created_at, it.updated_at, it.core,
                    it.id
                )
            }
        }

    }

    fun handleErrorStateMaterialsScreen(state: GetStateMaterial.Error) {
        val error = state.error
    }

    fun handleSuccessStateOpenMaterialScreen(state: GetStateMaterialDetail.Success) {
        val materialDetail = state.materialDetails
        updateMaterialDetail(
            materialDetail.properties?.young,
            materialDetail.properties?.poison,
            materialDetail.properties?.stiffness,
            materialDetail.properties?.piezo,
            materialDetail.properties?.dielectric,
            materialDetail.id!!
        )
    }

    fun handleErrorStateOpenMaterialScreen(state: GetStateMaterialDetail.Error) {
        val error = state.error
    }

    suspend fun deleteMaterialDB(materialMain: MaterialMain) {
        database.dao.deleteMaterial(materialMain)
    }



}