package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.MainActivity
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.ext.basicButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenTaskScreen(
    modifier: Modifier = Modifier,
    openTaskViewModel: OpenTaskViewModel = hiltViewModel(),
    idTask:Int,
    context: Context = LocalContext.current
) {
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        openTaskViewModel.getToken()
    }
    openTaskViewModel.getIdTask(idTask)
    coroutineScope.launch {
        Log.d("tasks", "startDetailScreen1")
        openTaskViewModel.getTaskById()
    }

    LaunchAppButton(context)



    Text(text = idTask.toString())
}
@Composable
fun LaunchAppButton(context: Context) {
    Button(onClick = {
        val packageName = "com.raywenderlich.android.targetpractice" // замените на пакетное имя нужного вам приложения
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Приложение не найдено", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text(text = "Запустить приложение")
    }
}
