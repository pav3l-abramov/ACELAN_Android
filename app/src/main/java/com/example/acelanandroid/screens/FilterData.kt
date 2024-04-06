package com.example.acelanandroid.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class FilterData(
    val filterStatusTask:String="All",
    val filterYoungOn:Boolean=false,
    val filterCore:String="All",
    val filterType:Int=0,
    val filterYoungMin:String="10000000",
    val filterYoungMax:String="1000000000",
)
data class SortedData(
    val sortedBy:String="Not Sorted"
)