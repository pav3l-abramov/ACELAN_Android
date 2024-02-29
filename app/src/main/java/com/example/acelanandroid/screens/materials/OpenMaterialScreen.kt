package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.acelanandroid.OpenGLES20Activity
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.MaterialDetailCard
import com.example.acelanandroid.common.composable.TaskDetailCard
import com.example.acelanandroid.common.composable.TextCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.retrofit.GetStateMaterialDetail
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.tasks.DrawImage
import com.example.acelanandroid.screens.tasks.OpenTaskViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenMaterialScreen(
    idMaterial: Int,
    modifier: Modifier = Modifier,
    openMaterialViewModel: OpenMaterialViewModel = hiltViewModel(),
    context: Context,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val materialDetailDB by mainViewModel.materialDetailDB.collectAsState()
    val userDB by mainViewModel.userDB
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by openMaterialViewModel.uiCheckStatus
    val isLoading by openMaterialViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    LaunchedEffect(Unit) {
        mainViewModel.userIsExist()
        mainViewModel.getUserDB()

    }
    CoroutineScope(Job()).launch { mainViewModel.getMaterialByID(idMaterial) }
    if (!checkUser) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextCardStandart("Go to profile and login", Modifier.fieldModifier())
        }
    }
        else{
        LaunchedEffect(materialDetailDB) {
            if (materialDetailDB.young==null&& materialDetailDB.id != null) {
                openMaterialViewModel.getListMaterialDetailWithRetry(
                    userDB.token.toString(),
                    idMaterial ,
                    context
                )
                mainViewModel.getMaterialByID(idMaterial)
            }
        }

        SwipeRefresh(state = swipeRefreshState,
            onRefresh = {
                openMaterialViewModel.getListMaterialDetailWithRetry(
                    userDB.token.toString(),
                    idMaterial ,
                    context
                )
            }) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MaterialDetailCard("Name: ", materialDetailDB.name.toString(), false, Modifier.fieldModifier())
                MaterialDetailCard("Type: ", materialDetailDB.type.toString(), false, Modifier.fieldModifier())
                MaterialDetailCard("Create: ", materialDetailDB.created_at.toString(), true, Modifier.fieldModifier())
                MaterialDetailCard("Update: ", materialDetailDB.updated_at.toString(), true, Modifier.fieldModifier())
                MaterialDetailCard("Core: ", materialDetailDB.core.toString(), false, Modifier.fieldModifier())
                MaterialDetailCard("Source: ", materialDetailDB.source.toString(), false, Modifier.fieldModifier())
                TextCardStandart("Properties",Modifier.fieldModifier())
                MaterialDetailCard("Young Modulus: ", materialDetailDB.young.toString(), false, Modifier.fieldModifier())
                MaterialDetailCard("Poison Coefficient: ", materialDetailDB.poison.toString(), false, Modifier.fieldModifier())
                if (isLoading) {
                    CustomLinearProgressBar(Modifier.fieldModifier())
                }

            }


        }
        }
    openMaterialViewModel.materialDetailState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateMaterialDetail.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateMaterialDetail.Success -> {
                CoroutineScope(Job()).launch {
                    mainViewModel.handleSuccessStateOpenMaterialScreen(state)
                }

            }

            is GetStateMaterialDetail.Error -> {
                mainViewModel.handleErrorStateOpenMaterialScreen(state)
                Log.d("Error", state.toString())
                val error = state.error
                openMaterialViewModel.typeError(error)
            }
        }
    }


}