package com.example.acelanandroid.screens.materials

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GraphSettingViewModel@Inject constructor() : ViewModel() {
    private val _settingGraphTypeXLabel = MutableStateFlow(0)
    val settingGraphTypeXLabel: StateFlow<Int> = _settingGraphTypeXLabel

    private val _settingGraphLineShow = MutableStateFlow(0)
    val settingGraphLineShow: StateFlow<Int> = _settingGraphLineShow

    private val _settingGraphColorLine = MutableStateFlow(0)
    val settingGraphColorLine: StateFlow<Int> = _settingGraphColorLine

    private val _settingGraphColorPoint = MutableStateFlow(0)
    val settingGraphColorPoint: StateFlow<Int> = _settingGraphColorPoint

    private val _settingGraphDivideFactorStiffness = MutableStateFlow(0)
    val settingGraphDivideFactorStiffness: StateFlow<Int> = _settingGraphDivideFactorStiffness

    private val _settingGraphDivideFactorPiezo = MutableStateFlow(0)
    val settingGraphDivideFactorPiezo: StateFlow<Int> = _settingGraphDivideFactorPiezo

    private val _settingGraphDivideFactorDielectric = MutableStateFlow(0)
    val settingGraphDivideFactorDielectric: StateFlow<Int> = _settingGraphDivideFactorDielectric

    private val _settingGraphDivideFactorYoung = MutableStateFlow(0)
    val settingGraphDivideFactorYoung: StateFlow<Int> = _settingGraphDivideFactorYoung

    private val _settingGraphDivideFactorPoison = MutableStateFlow(0)
    val settingGraphDivideFactorPoison: StateFlow<Int> = _settingGraphDivideFactorPoison

}