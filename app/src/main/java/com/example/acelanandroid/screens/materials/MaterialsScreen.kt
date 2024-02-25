package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.common.composable.MaterialCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Material
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun MaterialsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    matrialViewModel: MatrialViewModel = hiltViewModel(),
    context: Context
) {


    val dataList: List<Material> by matrialViewModel.dataListMaterial.collectAsState()
    val userDB = mainViewModel.getUserDB.collectAsState(initial = UserData())
    val checkUser by mainViewModel.checkUser
    LaunchedEffect(Unit) {
        GlobalScope.async {
            mainViewModel.userIsExist()
        }
    }
    if (!checkUser) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextCardStandart("Go to profile and login",Modifier.fieldModifier())
        }

    } else {
        LaunchedEffect(Unit) {
            GlobalScope.launch {
                if (checkUser) {
                    userDB.value.token?.let { matrialViewModel.getListMaterials("", it) }
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
                            if (checkUser) {
                                userDB.value.token?.let {
                                    matrialViewModel.getListMaterials(searchText.value,
                                        it
                                    )
                                }
                            }
                        }
                    }
                },
                onSearch = { text ->
                    Log.d("searchText.value",searchText.value)
                        GlobalScope.launch {
                            if (checkUser) {
                                userDB.value.token?.let {
                                    matrialViewModel.getListMaterials(searchText.value,
                                        it
                                    )
                                }
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