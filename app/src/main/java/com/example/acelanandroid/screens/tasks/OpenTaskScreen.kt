package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.MainActivity
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.retrofit.data.Task
import com.example.acelanandroid.retrofit.data.TaskDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenTaskScreen(
    modifier: Modifier = Modifier,
    openTaskViewModel: OpenTaskViewModel = hiltViewModel(),
    idTask: Int,
    context: Context = LocalContext.current
) {
    val uiState by openTaskViewModel.uiState
    val uiStateMain by openTaskViewModel.uiStateMain
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        openTaskViewModel.getToken()
    }
    openTaskViewModel.getIdTask(idTask)
    coroutineScope.launch {
        Log.d("tasks", "startDetailScreen1")
        openTaskViewModel.getTaskById()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = idTask.toString())
        Text(text = uiStateMain.name.toString())
        Text(text = uiStateMain.status.toString())
        Text(text = uiStateMain.started_at.toString())
        Text(text = uiStateMain.finished_at.toString())
        if (uiState.url != null && uiState.file_type == "obj") {
            Button(onClick = {
                downloadFile(context, uiState.url!!, uiState.file_type!!)
                val packageName =
                    "com.raywenderlich.android.targetpractice" // замените на пакетное имя нужного вам приложения
                val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                if (intent != null) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Приложение не найдено", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Open in AR")
            }
        } else {
            Text(text = "There is nothing to draw in this task")
        }
        // LinearProgressIndicator(progress)
    }
}

private fun downloadFile(context: Context, fileUrl: String, fileExtension: String) {
    val request = DownloadManager.Request(Uri.parse(fileUrl))

    // Указываем путь для сохранения загруженного файла
    val fileName = "downloaded_file"


    // val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)

    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
        "$fileName.$fileExtension"
    )

// Delete the file if it already exists
    if (file.exists()) {
        file.delete()
    }

    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        "$fileName.$fileExtension"
    )
    request.setMimeType(mimeType)

    // Разрешение на запись во внешнее хранилище может потребоваться на Android 10 и выше
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setAllowedOverMetered(true)
    request.setAllowedOverRoaming(true)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}

