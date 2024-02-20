package com.example.acelanandroid.screens

import androidx.lifecycle.ViewModel
import com.example.acelanandroid.room.MainDB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(database: MainDB):ViewModel() {
    val itemsList = database.dao.getTaskMain()
}