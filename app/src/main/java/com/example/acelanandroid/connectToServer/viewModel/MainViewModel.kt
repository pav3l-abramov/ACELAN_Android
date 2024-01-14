package com.example.acelanandroid.connectToServer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.acelanandroid.connectToServer.helpers.ApiResponse
import com.example.acelanandroid.connectToServer.repository.MainRepository
import com.example.acelanandroid.connectToServer.retrofit.response.TasksResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): BaseViewModel() {

    private val _userInfoResponse = MutableLiveData<ApiResponse<TasksResponse>>()
    val userInfoResponse = _userInfoResponse

    fun getTasks(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _userInfoResponse,
        coroutinesErrorHandler,
    ) {
        mainRepository.getTasks()
    }
}