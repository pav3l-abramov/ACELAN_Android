package com.example.acelanandroid.screens.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.EmailField
import com.example.acelanandroid.common.composable.PasswordField
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    context: Context
) {
    val uiState by loginViewModel.uiState
    val uiStateUser by loginViewModel.uiStateUser
    val checkUser by mainViewModel.checkUser
    LaunchedEffect(Unit) {
        GlobalScope.async {
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
            EmailField(uiState.email, loginViewModel::onEmailChange, Modifier.fieldModifier())
            PasswordField(
                uiState.password,
                loginViewModel::onPasswordChange,
                Modifier.fieldModifier()
            )
            BasicButton("Sign In", Modifier.basicButton()) {
                GlobalScope.async {
                    loginViewModel.onSignInClick()
                }
                GlobalScope.async {
                    if(uiStateUser.token!=null) {
                        mainViewModel.insertUserToDB(
                            UserData(
                                1,
                                uiStateUser.email,
                                uiStateUser.token
                            )
                        )
                    }
                }
            }
            BasicButton("Sign Up", Modifier.basicButton()) {
                val url = "https://acelan.ru/signup"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
        } else {
            Text(text = "Hello ${uiStateUser.email}")
            BasicButton("Log Out", Modifier.basicButton()) {
                GlobalScope.launch {
                    mainViewModel.deleteUserDB()
                }
            }
        }
    }
}