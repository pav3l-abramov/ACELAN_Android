package com.example.acelanandroid.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.MaterialToDraw
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Material
import com.example.acelanandroid.retrofit.GetStateMaterial
import com.example.acelanandroid.retrofit.GetStateMaterialDetail
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.room.FloatListConverter
import com.example.acelanandroid.room.MainDB
import com.example.acelanandroid.screens.materials.GraphList
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

    init{
        getUserDB()
        userIsExist()
        getMaterialFromDB()
        getTaskFromDB()
    }

    private val _checkOpenMaterialScreen = MutableStateFlow(false)
    val checkOpenMaterialScreen: StateFlow<Boolean> = _checkOpenMaterialScreen

    fun isOpenMaterialScreen() {
        _checkOpenMaterialScreen.value=true
    }
    private val _checkOpenTaskScreen = MutableStateFlow(false)
    val checkOpenTaskScreen: StateFlow<Boolean> = _checkOpenTaskScreen
    fun isOpenTaskScreen() {
        _checkOpenTaskScreen.value=true
    }

    private val _userDB = MutableStateFlow(UserData())
    val userDB: StateFlow<UserData> = _userDB

    private val _checkUser = MutableStateFlow(false)
    val checkUser: StateFlow<Boolean> = _checkUser
//    val checkUser = mutableStateOf(false)
//    val userDB = mutableStateOf(UserData())

    //user
    fun getUserDB() {
        viewModelScope.launch {
            database.dao.getUser().collect {
                _userDB.value = it
            }
            Log.d("_taskListDB_taskListDB_taskListDB",_taskListDB.toString())
        }
    }

    suspend fun deleteUserDB() {
        database.dao.deleteUser()
        database.dao.deleteMaterial()
        database.dao.deleteTask()
        database.dao.deleteDraw()
        //checkUser.value = false
        _materialListDB.value= emptyList()
        _taskListDB.value= emptyList()
    }

    suspend fun insertUserToDB(userData: UserData) {
        database.dao.insertUser(userData)


    }

    private fun userIsExist() {
        viewModelScope.launch {
            database.dao.isUserExists().collect {
                _checkUser.value = it==0
            }
        }
    }


    //Task
    private val _taskListDB = MutableStateFlow<List<TaskMain>>(emptyList())
    val taskListDB: StateFlow<List<TaskMain>> = _taskListDB

    private fun getTaskFromDB() {
        viewModelScope.launch {
            database.dao.getTaskMain().collect {
                _taskListDB.value = it
            }
            Log.d("_taskListDB_taskListDB_taskListDB",_taskListDB.toString())
        }
    }


//    fun updateTaskList() {
//        viewModelScope.launch {
//            _taskListDB.value = database.dao.getTaskMain()
//        }
//    }

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

    fun getMaterialFromDB() {
        viewModelScope.launch {
            database.dao.getMaterialMain().collect {
                _materialListDB.value = it
            }
            Log.d("_taskListDB_taskListDB_taskListDB",_taskListDB.toString())
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
    suspend fun updateMaterialCardDraw(isDraw:Boolean,id: Int) {
        database.dao.updateMaterialCardDraw(isDraw,id)
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

    //graph
    private val _materialGraphDB = MutableStateFlow<List<MaterialToDraw>>(emptyList())
    val materialGraphDB: StateFlow<List<MaterialToDraw>> = _materialGraphDB

    private val _materialListDraw = MutableStateFlow(GraphList())
    val materialListDraw: StateFlow<GraphList> = _materialListDraw

    suspend fun updateMaterialGraph() {
        viewModelScope.launch {
            _materialGraphDB.value = database.dao.getDrawMain()
        }
    }
    suspend fun insertMaterialToDraw(materialToDraw: MaterialToDraw) {
        database.dao.insertMaterialToDraw(materialToDraw)
    }

    suspend fun deleteMaterialToDraw(id: Int) {
        database.dao.deleteMaterialToDrawById(id)
    }
    suspend fun getDataToGraph(){
        val nameList = mutableListOf<String>()
        val youngList = mutableListOf<Float>()
        val poisonList = mutableListOf<Float>()
        val stiffnessList = mutableListOf<List<Float>>()
        val piezoList = mutableListOf<List<Float>>()
        val dielectricList = mutableListOf<List<Float>>()
        val listId = database.dao.getDrawMain()
        listId.forEach { id ->
            val data = id.id?.let { database.dao.getMaterialMainByID(it) }
            if (data != null) {
                nameList.add(data.name!!)
                youngList.add(data.young!!.toFloat()/1E9f)
                poisonList.add(data.poison!!.toFloat())
                stiffnessList.add(data.stiffness!!)
                piezoList.add(data.piezo!!)
                dielectricList.add(data.dielectric!!)
            }
        }
        _materialListDraw.value=GraphList(nameList,youngList,poisonList,stiffnessList,piezoList,dielectricList)
    }
}
