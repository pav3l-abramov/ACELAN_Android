package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.common.composable.MaterialCard
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.retrofit.data.Material
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MaterialsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    matrialViewModel: MatrialViewModel = hiltViewModel()
) {
    val uiStateUser by loginViewModel.uiStateUser
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        Log.d("tasks", "startMain1")
        loginViewModel.checkUser()
    }
    val dataList: List<Material> by matrialViewModel.dataListMaterial.collectAsState()

    if (!uiStateUser.isActive) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Go to profile and login")
        }

    } else {
        coroutineScope.launch {
            matrialViewModel.getToken()
        }
        coroutineScope.launch {
            if (uiStateUser.isActive) {
                matrialViewModel.getListMaterials()
            }
        }
        LazyColumn {
            itemsIndexed(items = dataList) { index, item ->
                MaterialCard(content = item.name, modifier = Modifier.fieldModifier()){navController.navigate(route = OPEN_MATERIAL_SCREEN+"/${item.id}")}
            }
        }
    }
}