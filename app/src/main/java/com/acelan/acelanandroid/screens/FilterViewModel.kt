package com.acelan.acelanandroid.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.acelan.acelanandroid.data.MaterialMain
import com.acelan.acelanandroid.data.TaskMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {
    var uiStateFilter = mutableStateOf(FilterData())
        private set
    var uiStateSorted = mutableStateOf(SortedData())
        private set

    private val _sortedTaskListDB = MutableStateFlow<List<TaskMain>>(emptyList())
    val sortedTaskListDB: StateFlow<List<TaskMain>> = _sortedTaskListDB

    fun checkCoreFilter(item: MaterialMain, filterParam:String): Boolean {
        return (item.core == true && filterParam == "Yes") || (item.core==false && filterParam == "No") || filterParam == "All"
    }

    fun checkTypeFilter(item: MaterialMain, filterParam:Int): Boolean {
        val materialTypeFull = arrayOf("All", "Materials::IsotropicMaterial", "Materials::AnisotropicMaterial", "Liquid")
        return (item.type?.contains(materialTypeFull[filterParam], ignoreCase = true) == true) ||
                filterParam == 0
    }

    fun onNewValueStatusFilter(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterStatusTask  = newValue)
    }

    fun onNewValueMainYoungFilter(newValue: Boolean) {
        uiStateFilter.value = uiStateFilter.value.copy(filterYoungOn  = newValue)
    }
    fun onCoreFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterCore = newValue)
    }
    fun onTypeFilterChange(newValue: Int) {
        uiStateFilter.value = uiStateFilter.value.copy(filterType = newValue)
    }
    fun onYoungMinFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterYoungMin = newValue)
    }
    fun onYoungMaxFilterChange(newValue: String) {
        uiStateFilter.value = uiStateFilter.value.copy(filterYoungMax = newValue)
    }

    fun onNewSortedParam(newValue: String){
        uiStateSorted.value=uiStateSorted.value.copy(sortedBy = newValue)}
    fun onSortedTaskMain(listNotSorted:List<TaskMain>){
        when(uiStateSorted.value.sortedBy){
            "Not Sorted"->_sortedTaskListDB.value=listNotSorted
            "Start Up"->_sortedTaskListDB.value=listNotSorted.sortedBy { it.started_at }
            "Start Down"->_sortedTaskListDB.value=listNotSorted.sortedBy { it.started_at }.reversed()
            "Finish Up"->_sortedTaskListDB.value=listNotSorted.sortedBy { it.finished_at }
            "Finish Down"->_sortedTaskListDB.value=listNotSorted.sortedBy { it.finished_at }.reversed()
            else->{_sortedTaskListDB.value=listNotSorted}
        }

    }
}