package com.example.acelanandroid.screens.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.EmailField
import com.example.acelanandroid.common.composable.PasswordField
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.retrofit.PostState
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    context: Context
) {
    val uiState by profileViewModel.uiState
    val uiCheckStatus by profileViewModel.uiCheckStatus
    val checkUser by mainViewModel.checkUser
    val userDB by mainViewModel.userDB

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    //val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainViewModel.userIsExist()
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
        if (!checkUser) {
            EmailField(uiState.email, profileViewModel::onEmailChange, Modifier.fieldModifier())
            PasswordField(
                uiState.password,
                profileViewModel::onPasswordChange,
                Modifier.fieldModifier()
            )
            when (uiCheckStatus.status) {
                null -> {}
                "Loading" -> {
                    CustomLinearProgressBar(Modifier.fieldModifier())
                }
                else -> {
                    Toast.makeText(
                        context,
                        uiCheckStatus.body.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    profileViewModel.nullStatus()
                }

            }
            BasicButton("Sign In", Modifier.basicButton()) {

                profileViewModel.loginWithRetry(uiState.email, uiState.password)
                profileViewModel.loginState.observe(lifecycleOwner, Observer { state ->
                    Log.d("start", state.toString())
                    when (state) {
                        PostState.Loading -> {
                            Log.d("Loading", state.toString())
                            // Show loading state

                        }
                        is PostState.Success -> {
                            Log.d("Success", state.toString())
                            val token = state.token.token
                            CoroutineScope(Job()).launch {
                                withContext(Dispatchers.IO) {
                                    mainViewModel.insertUserToDB(
                                        UserData(
                                            1,
                                            uiState.email,
                                            token
                                        )
                                    )

                                    mainViewModel.userIsExist()
                                }
                            }
                            profileViewModel.nullStatus()
                        }

                        is PostState.Error -> {
                            Log.d("Error", state.toString())
                            val error = state.error
                            profileViewModel.typeError(error)
                            // Handle error
                        }

                        else -> {}
                    }
                })
            }

            BasicButton("Sign Up", Modifier.basicButton()) {
                val url = "https://acelan.ru/signup"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
        } else {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    mainViewModel.getUserDB()
                }
            }
                 Text(text = "Hello ${userDB.email}")

                BasicButton("Log Out", Modifier.basicButton()) {
                    CoroutineScope(Job()).launch {
                        mainViewModel.deleteUserDB()
                    }
                }

        }
    }
}