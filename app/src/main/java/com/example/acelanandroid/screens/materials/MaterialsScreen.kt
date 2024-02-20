package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun MaterialsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    matrialViewModel: MatrialViewModel = hiltViewModel(),
    context: Context
) {
    val uiStateUser by loginViewModel.uiStateUser
    LaunchedEffect(Unit) {
        GlobalScope.launch {
            loginViewModel.checkUser()
        }
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
        LaunchedEffect(Unit) {
            GlobalScope.launch {
                if (uiStateUser.isActive) {
                    matrialViewModel.getListMaterials("",uiStateUser.token)
                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val searchText = remember { mutableStateOf("") }
            SearchBar(
                modifier = Modifier.fieldModifier(),
                query = searchText.value,
                onQueryChange = { text ->
                    searchText.value = text
                    if (searchText.value==""){
                        GlobalScope.launch {
                            if (uiStateUser.isActive) {
                                matrialViewModel.getListMaterials(searchText.value,uiStateUser.token)
                            }
                        }
                    }
                },
                onSearch = { text ->
                    Log.d("searchText.value",searchText.value)
                        GlobalScope.launch {
                            if (uiStateUser.isActive) {
                                matrialViewModel.getListMaterials(searchText.value,uiStateUser.token)
                            }
                        }

                },
                placeholder = {
                    Text(text = "Search...")
                },
                active = false,
                onActiveChange = {

                }) {
            }

            LazyColumn {
                itemsIndexed(items = dataList) { index, item ->
                    MaterialCard(
                        content = item.name, modifier = Modifier.fieldModifier()
                    ) { navController.navigate(route = OPEN_MATERIAL_SCREEN + "/${item.id}") }
                }
            }
            if (dataList.isEmpty()){
                Text(text = "No matches")
            }
        }

    }
}