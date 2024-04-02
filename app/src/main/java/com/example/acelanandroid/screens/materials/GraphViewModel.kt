package com.example.acelanandroid.screens.materials

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor() : ViewModel() {
    private val _elasticPropertiesList =  MutableStateFlow<MutableList<Int>>(SnapshotStateList())
    val elasticPropertiesList: StateFlow<MutableList<Int>> = _elasticPropertiesList

    private val _piezoelectricPropertiesList =  MutableStateFlow<MutableList<Int>>(SnapshotStateList())
    val piezoelectricPropertiesList: StateFlow<MutableList<Int>> = _piezoelectricPropertiesList

    private val _dielectricPropertiesList =  MutableStateFlow<MutableList<Int>>(SnapshotStateList())
    val dielectricPropertiesList: StateFlow<MutableList<Int>> = _dielectricPropertiesList

    fun onEditGraphDraw(param: String, numberParam: Int) {
        when (param) {
            "ε" -> {
                if (_dielectricPropertiesList.value.contains(numberParam)) {
                    _dielectricPropertiesList.value.remove(numberParam)
                } else {
                    _dielectricPropertiesList.value.add(numberParam)
                }
            }
            "C" -> {
                if (_elasticPropertiesList.value.contains(numberParam)) {
                    _elasticPropertiesList.value.remove(numberParam)
                } else {
                    _elasticPropertiesList.value.add(numberParam)
                }
            }
            "d" -> {
                if (_piezoelectricPropertiesList.value.contains(numberParam)) {
                    _piezoelectricPropertiesList.value.remove(numberParam)
                   // Log.d( "_piezoelectricPropertiesList.value", _piezoelectricPropertiesList.value.toString())
                } else {
                    _piezoelectricPropertiesList.value.add(numberParam)
                   // Log.d( "_piezoelectricPropertiesList.value", _piezoelectricPropertiesList.value.toString())
                }
            }
            else -> {
                Log.d( "_piezoelectricPropertiesList.value", _piezoelectricPropertiesList.value.toString())
            }
        }
    }
    fun getParamToList (allParam : List<List<Float>>,number : Int):List<Float>{
        val listParam = mutableListOf<Float>()
        allParam.forEach{
            listParam.add(it[number])
        }
        return listParam
    }

    fun getYTitle(mainParam:String, divideFactor:String, dimension:String):String{
        val annotatedString = buildAnnotatedString {
            append(mainParam)
            if (divideFactor!="0" || dimension!=""){
                append(", ")
            }
            if (divideFactor!="0") {
                append("10")
                for (ch in divideFactor.iterator()) {
                    if (ch== '-' ||ch== '+'){
                        append(getUpShiftSign(ch))
                    }
                    else {
                        append(getUpShiftString(ch))
                    }
                }
                append(" ")
            }
            append(dimension)
        }
        return annotatedString.text
    }

    fun getSubShiftString(param:Int):String{
        var subString =""
        when (param){
            0->{subString="₀"}
            1->{subString="₁"}
            2->{subString="₂"}
            3->{subString="₃"}
            4->{subString="₄"}
            5->{subString="₅"}
            6->{subString="₆"}
            7->{subString="₇"}
            8->{subString="₈"}
            9->{subString="₉"}
        }
        return subString
    }
    fun getUpShiftString(param:Char):String{
        var subString =""
        when (param){
            '0'->{subString="⁰"}
            '1'->{subString="¹"}
            '2'->{subString="²"}
            '3'->{subString="³"}
            '4'->{subString="⁴"}
            '5'->{subString="⁵"}
            '6'->{subString="⁶"}
            '7'->{subString="⁷"}
            '8'->{subString="⁸"}
            '9'->{subString="⁹"}
        }
        return subString
    }
    fun getUpShiftSign(param: Char):String{
        var subString =""
        when (param){
            '-'->{subString="⁻"}
            '+'->{subString="⁺"}
        }
        return subString
    }
}