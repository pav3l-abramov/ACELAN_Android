package com.example.acelanandroid.screens.materials

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class FilterData(
    val filterYoungOn:Boolean=false,
    val filterCore:String="All",
    val filterType:String="All",
    val filterYoungMin:String="100",
    val filterYoungMax:String="1000",
)