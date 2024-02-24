package com.example.acelanandroid.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.room.MainDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val database: MainDB):ViewModel() {
    val taskListDB = database.dao.getTaskMain()
    val materialListDB = database.dao.getMaterialMain()
    suspend fun insertTaskToDB(taskFromServer: TaskMain){
        database.dao.insertTask(taskFromServer)
    }
    suspend fun insertMaterialToDB(materialFromServer: MaterialMain){
        database.dao.insertMaterial(materialFromServer)
    }

}