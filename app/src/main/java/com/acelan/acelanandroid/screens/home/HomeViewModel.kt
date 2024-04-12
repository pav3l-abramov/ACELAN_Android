package com.acelan.acelanandroid.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val booleanList = mutableStateListOf<Boolean>()

    init {
        for (i in 0 until 6) {
            booleanList.add(false)
        }
    }
    fun onValueChange(index: Int) {
        booleanList[index] = !booleanList[index]
    }
}