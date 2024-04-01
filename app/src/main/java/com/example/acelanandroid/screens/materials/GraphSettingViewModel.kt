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
class GraphSettingViewModel @Inject constructor() : ViewModel() {
    private val _settingCheck = MutableStateFlow(true)
    val settingCheck: StateFlow<Boolean> = _settingCheck

    private val _settingGraphTypeXLabel = MutableStateFlow(0)
    val settingGraphTypeXLabel: StateFlow<Int> = _settingGraphTypeXLabel

    private val _settingGraphLineShow = MutableStateFlow(0)
    val settingGraphLineShow: StateFlow<Int> = _settingGraphLineShow

    private val _settingGraphColorLine = MutableStateFlow(0)
    val settingGraphColorLine: StateFlow<Int> = _settingGraphColorLine

    private val _settingGraphColorPoint = MutableStateFlow(0)
    val settingGraphColorPoint: StateFlow<Int> = _settingGraphColorPoint

    private val _settingGraphDivideFactorStiffness = MutableStateFlow("")
    val settingGraphDivideFactorStiffness: StateFlow<String> = _settingGraphDivideFactorStiffness

    private val _settingGraphDivideFactorPiezo = MutableStateFlow("")
    val settingGraphDivideFactorPiezo: StateFlow<String> = _settingGraphDivideFactorPiezo

    private val _settingGraphDivideFactorDielectric = MutableStateFlow("")
    val settingGraphDivideFactorDielectric: StateFlow<String> = _settingGraphDivideFactorDielectric

    private val _settingGraphDivideFactorYoung = MutableStateFlow("")
    val settingGraphDivideFactorYoung: StateFlow<String> = _settingGraphDivideFactorYoung

    private val _settingGraphDivideFactorPoison = MutableStateFlow("")
    val settingGraphDivideFactorPoison: StateFlow<String> = _settingGraphDivideFactorPoison

    suspend fun setStartSetting(userDB: UserData) {
        _settingGraphTypeXLabel.value = userDB.graphTypeXLabel
        _settingGraphLineShow.value = userDB.graphLineShow

        _settingGraphColorLine.value = userDB.graphColorLine
        _settingGraphColorPoint.value = userDB.graphColorPoint

        _settingGraphDivideFactorStiffness.value = userDB.graphDivideFactorStiffness.toString()
        _settingGraphDivideFactorPiezo.value = userDB.graphDivideFactorPiezo.toString()
        _settingGraphDivideFactorDielectric.value = userDB.graphDivideFactorDielectric.toString()
        _settingGraphDivideFactorYoung.value = userDB.graphDivideFactorYoung.toString()
        _settingGraphDivideFactorPoison.value = userDB.graphDivideFactorPoison.toString()
    }

    fun onBooleanChange(param: Int, newValue: Int) {
        if (param == 0) {
            _settingGraphTypeXLabel.value = newValue
        } else {
            _settingGraphLineShow.value = newValue
        }
    }

    fun onColorChange(param: Int, newValue: Int) {
        if (param == 0) {
            _settingGraphColorLine.value = newValue
        } else {
            _settingGraphColorPoint.value = newValue
        }
    }

    fun onDivideChange(param: Int, newValue: String) {
        when (param) {
            0 -> _settingGraphDivideFactorStiffness.value = newValue
            1 -> _settingGraphDivideFactorPiezo.value = newValue
            2 -> _settingGraphDivideFactorDielectric.value = newValue
            3 -> _settingGraphDivideFactorYoung.value = newValue
            4 -> _settingGraphDivideFactorPoison.value = newValue
        }
        try{
            _settingGraphDivideFactorStiffness.value = _settingGraphDivideFactorStiffness.value.toInt().toString()
            _settingGraphDivideFactorPiezo.value = _settingGraphDivideFactorPiezo.value.toInt().toString()
            _settingGraphDivideFactorDielectric.value = _settingGraphDivideFactorDielectric.value.toInt().toString()
            _settingGraphDivideFactorYoung.value = _settingGraphDivideFactorYoung.value.toInt().toString()
            _settingGraphDivideFactorPoison.value = _settingGraphDivideFactorPoison.value.toInt().toString()
            _settingCheck.value=true
        }
        catch (e: NumberFormatException){
            _settingCheck.value=false
        }


    }

}