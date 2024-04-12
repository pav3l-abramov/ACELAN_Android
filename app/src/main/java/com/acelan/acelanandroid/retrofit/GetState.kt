package com.acelan.acelanandroid.retrofit

import com.acelan.acelanandroid.data.singleData.MaterialDetails
import com.acelan.acelanandroid.data.singleData.Materials
import com.acelan.acelanandroid.data.singleData.TaskDetails
import com.acelan.acelanandroid.data.singleData.Tasks

sealed class GetStateMaterial {
    object Loading : GetStateMaterial()
    data class Success(val materials: Materials,val onSearch:Boolean) : GetStateMaterial()
    data class Error(val error: String) : GetStateMaterial()
}
sealed class GetStateMaterialDetail {
    object Loading : GetStateMaterialDetail()
    data class Success(val materialDetails: MaterialDetails ) : GetStateMaterialDetail()
    data class Error(val error: String) : GetStateMaterialDetail()
}

sealed class GetStateTasks {
    object Loading : GetStateTasks()
    data class Success(val tasks: Tasks) : GetStateTasks()
    data class Error(val error: String) : GetStateTasks()
}
sealed class GetStateTaskDetail {
    object Loading : GetStateTaskDetail()
    data class Success(val taskDetails: TaskDetails) : GetStateTaskDetail()
    data class Error(val error: String) : GetStateTaskDetail()
}