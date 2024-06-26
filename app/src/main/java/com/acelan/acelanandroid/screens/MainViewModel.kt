package com.acelan.acelanandroid.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acelan.acelanandroid.data.MaterialMain
import com.acelan.acelanandroid.data.TaskMain
import com.acelan.acelanandroid.data.UserData
import com.acelan.acelanandroid.data.singleData.Material
import com.acelan.acelanandroid.retrofit.GetStateMaterial
import com.acelan.acelanandroid.retrofit.GetStateMaterialDetail
import com.acelan.acelanandroid.retrofit.GetStateTaskDetail
import com.acelan.acelanandroid.retrofit.GetStateTasks
import com.acelan.acelanandroid.room.MainDB
import com.acelan.acelanandroid.screens.materials.GraphListAnisotropic
import com.acelan.acelanandroid.screens.materials.GraphListIsotropic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class MainViewModel @Inject constructor(val database: MainDB) : ViewModel() {

    init {
        getUserDB()
        userIsExist()
        getMaterialFromDB()
        getTaskFromDB()
        getMaterialFromDBDraw()
    }

    private val _checkOpenApp = MutableStateFlow(false)
    val checkOpenApp: StateFlow<Boolean> = _checkOpenApp
    fun isOpenApp() {
        _checkOpenApp.value = true
    }

    private val _checkOpenMaterialScreen = MutableStateFlow(false)
    val checkOpenMaterialScreen: StateFlow<Boolean> = _checkOpenMaterialScreen

    fun isOpenMaterialScreen() {
        _checkOpenMaterialScreen.value = true
    }

    private val _checkOpenTaskScreen = MutableStateFlow(false)
    val checkOpenTaskScreen: StateFlow<Boolean> = _checkOpenTaskScreen
    fun isOpenTaskScreen() {
        _checkOpenTaskScreen.value = true
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
            Log.d("_taskListDB_taskListDB_taskListDB", _taskListDB.toString())
        }
    }

    suspend fun deleteUserDB() {
        database.dao.deleteUser()
        database.dao.deleteMaterial()
        database.dao.deleteTask()
        //checkUser.value = false
        _materialListDB.value = emptyList()
        _taskListDB.value = emptyList()
    }

    suspend fun insertUserToDB(userData: UserData) {
        database.dao.insertUser(userData)


    }

    private fun userIsExist() {
        viewModelScope.launch {
            database.dao.isUserExists().collect {
                _checkUser.value = it == 0
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
            Log.d("_taskListDB_taskListDB_taskListDB", _taskListDB.toString())
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

    private val _materialListDBDraw = MutableStateFlow<List<MaterialMain>>(emptyList())
    val materialListDBDraw: StateFlow<List<MaterialMain>> = _materialListDBDraw

    private val _materialToSearch = MutableStateFlow<List<Material>>(emptyList())
    val materialToSearch: StateFlow<List<Material>> = _materialToSearch.asStateFlow()

    private val _materialDetailDB = MutableStateFlow(MaterialMain())
    val materialDetailDB: StateFlow<MaterialMain> = _materialDetailDB

    private fun getMaterialFromDB() {
        viewModelScope.launch {
            database.dao.getMaterialMain().collect {
                _materialListDB.value = it
            }
            Log.d("_taskListDB_taskListDB_taskListDB", _taskListDB.toString())
        }
    }

    private fun getMaterialFromDBDraw() {
        viewModelScope.launch {
            database.dao.getDrawMaterial().collect {
                _materialListDBDraw.value = it
            }
            Log.d("_taskListDB_taskListDB_taskListDB", _taskListDB.toString())
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

    suspend fun updateMaterialCardDraw(isDraw: Boolean, id: Int) {
        database.dao.updateMaterialCardDraw(isDraw, id)
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
        density: Int?=null,
        stiffness: List<Float?> ,
        piezo: List<Float?>,
        dielectric: List<Float?>,
        dielectricScalar: Float? = null,
        id: Int
    ) = database.dao.updateMaterialDetailMain(young, poison,density, stiffness, piezo, dielectric,dielectricScalar, id)


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
        val listStiffness = if ( materialDetail.properties?.stiffness!=null) listOf(
            materialDetail.properties.stiffness.c11,
            materialDetail.properties.stiffness.c12,
            materialDetail.properties.stiffness.c13,
            0f,0f,0f,
            materialDetail.properties.stiffness.c12,
            materialDetail.properties.stiffness.c22,
            materialDetail.properties.stiffness.c23,
            0f,0f,0f,
            materialDetail.properties.stiffness.c13,
            materialDetail.properties.stiffness.c23,
            materialDetail.properties.stiffness.c33,
            0f,0f,0f,
            0f,0f,0f,
            materialDetail.properties.stiffness.c44,
            0f,0f,
            0f,0f,0f,0f,
            materialDetail.properties.stiffness.c55,
            0f,
            0f,0f,0f,0f,0f,
            materialDetail.properties.stiffness.c66,
        ) else listOf()
        val listPiezo =if ( materialDetail.properties?.piezo!=null)  listOf(
            materialDetail.properties.piezo.e11,
            materialDetail.properties.piezo.e12,
            materialDetail.properties.piezo.e13,
            materialDetail.properties.piezo.e14,
            materialDetail.properties.piezo.e15,
            materialDetail.properties.piezo.e16,
            materialDetail.properties.piezo.e21,
            materialDetail.properties.piezo.e22,
            materialDetail.properties.piezo.e23,
            materialDetail.properties.piezo.e24,
            materialDetail.properties.piezo.e25,
            materialDetail.properties.piezo.e26,
            materialDetail.properties.piezo.e31,
            materialDetail.properties.piezo.e32,
            materialDetail.properties.piezo.e33,
            materialDetail.properties.piezo.e34,
            materialDetail.properties.piezo.e35,
            materialDetail.properties.piezo.e36,
        ) else listOf()
        val listDielectric =if ( materialDetail.properties?.dielectric!=null) listOf(
            materialDetail.properties.dielectric.k11,
            0f,0f,0f,
            materialDetail.properties.dielectric.k22,
            0f,0f,0f,
            materialDetail.properties.dielectric.k33,
        ) else listOf()
        updateMaterialDetail(
            materialDetail.properties?.young,
            materialDetail.properties?.poison,
            materialDetail.properties?.density,
            listStiffness,
            listPiezo,
            listDielectric,
            materialDetail.properties?.dielectricScalar,
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

    private val _materialIsotropicListDraw = MutableStateFlow(GraphListIsotropic())
    val materialIsotropicListDraw: StateFlow<GraphListIsotropic> = _materialIsotropicListDraw

    private val _materialAnisotropicListDraw = MutableStateFlow(GraphListAnisotropic())
    val materialAnisotropicListDraw: StateFlow<GraphListAnisotropic> = _materialAnisotropicListDraw

    suspend fun updateGraphSetting(
        graphTypeXLabel: Int,
        graphLineShow: Int,
        graphColorLine: Int,
        graphColorPoint: Int ,
        graphDivideFactorStiffness: Int,
        graphDivideFactorPiezo: Int,
        graphDivideFactorDielectric: Int,
        graphDivideFactorYoung: Int,
        graphDivideFactorPoison: Int,
        id: Int
    ) = database.dao.updateGraphSetting(graphTypeXLabel, graphLineShow, graphColorLine, graphColorPoint, graphDivideFactorStiffness, graphDivideFactorPiezo, graphDivideFactorDielectric, graphDivideFactorYoung, graphDivideFactorPoison, id)

    //    suspend fun updateMaterialGraph() {
//        viewModelScope.launch {
//            _materialGraphDB.value = database.dao.getDrawMain()
//        }
//    }
    suspend fun getDataToGraph(userData: UserData) {
        val nameIsotropicList = mutableListOf<String>()
        val youngList = mutableListOf<Float>()
        val poisonList = mutableListOf<Float>()

        val nameAnisotropicList = mutableListOf<String>()
        val stiffnessList = mutableListOf<List<Float>>()
        val piezoList = mutableListOf<List<Float>>()
        val dielectricList = mutableListOf<List<Float>>()
        _materialListDBDraw.value.forEach { material ->
            if(material.type!!.contains("Isotropic")) {
                nameIsotropicList.add(material.name!!)
                youngList.add((material.young!!.toFloat()/10.0.pow(userData.graphDivideFactorYoung)).toFloat())
                poisonList.add((material.poison!!.toFloat()/10.0.pow(userData.graphDivideFactorPoison)).toFloat())
            }
            else if (material.type!!.contains("Anisotropic")){
                nameAnisotropicList.add(material.name!!)
                stiffnessList.add(material.stiffness!!.map { it / 10.0.pow(userData.graphDivideFactorStiffness-9).toFloat() })
                piezoList.add(material.piezo!!.map { it / 10.0.pow(userData.graphDivideFactorPiezo).toFloat() })
                dielectricList.add(material.dielectric!!.map { it / 10.0.pow(userData.graphDivideFactorDielectric).toFloat() })
            }
        }
        _materialIsotropicListDraw.value =    GraphListIsotropic(nameIsotropicList, youngList, poisonList)
        _materialAnisotropicListDraw.value= GraphListAnisotropic(nameAnisotropicList, stiffnessList, piezoList, dielectricList)
    }
}
