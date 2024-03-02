package com.example.acelanandroid.screens.materials

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.data.singleData.Material
import com.example.acelanandroid.retrofit.AppRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {
    var uiStateFilter = mutableStateOf(FilterData())
        private set

    fun checkCoreFilter(item: MaterialMain, filterParam:String): Boolean {
        return (item.core == true && filterParam == "Yes") || (item.core==false && filterParam == "No") || filterParam == "All"
    }

    fun checkTypeFilter(item: MaterialMain, filterParam:String): Boolean {
        return (item.type?.contains(filterParam, ignoreCase = true) == true) ||
                filterParam == "All"
    }

    fun onNewValueMainYoungFilter(newValue: Boolean) {
        uiStateFilter.value = uiStateFilter.value.copy(filterYoungOn  = newValue)
    }
    fun onCoreFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterCore = newValue)
    }
    fun onTypeFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterType = newValue)
    }
    fun onYoungMinFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterYoungMin = newValue)
    }
    fun onYoungMaxFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterYoungMax = newValue)
    }
}