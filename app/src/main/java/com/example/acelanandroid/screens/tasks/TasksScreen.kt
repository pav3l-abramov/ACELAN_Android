package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.RegularCardEditor
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.card
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val uiStateUser by loginViewModel.uiStateUser
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            loginViewModel.checkUser()
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!uiStateUser.isActive) {
            Text(text = "not Nice")
        } else {
            Text(text = "Nice")
        }
    }


}