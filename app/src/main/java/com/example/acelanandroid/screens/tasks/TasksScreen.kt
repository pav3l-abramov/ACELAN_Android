package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.screens.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(appViewModel: AppViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val takeDataApi = appViewModel.retrofit.create(GetDataApi::class.java)
    coroutineScope.launch {
        withContext(Dispatchers.IO){
            //val tasks = takeDataApi.getTasks()
        }
        withContext(Dispatchers.Main) {

        }

    }
}