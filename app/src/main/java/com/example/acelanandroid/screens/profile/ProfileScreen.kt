package com.example.acelanandroid.screens.profile

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.acelanandroid.common.composable.BasicTextButton
import com.example.acelanandroid.common.composable.EmailField
import com.example.acelanandroid.common.composable.PasswordField
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.common.ext.textButton
import com.example.acelanandroid.dataStore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState
    val uiStateUser by loginViewModel.uiStateUser

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        loginViewModel.checkUser()
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
            EmailField(uiState.email, loginViewModel::onEmailChange, Modifier.fieldModifier())
            PasswordField(
                uiState.password,
                loginViewModel::onPasswordChange,
                Modifier.fieldModifier()
            )
            BasicButton("Sign In", Modifier.basicButton()) {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        loginViewModel.onSignInClick()
                    }
                }
            }
        } else {
            Text(text = "Hello ${uiStateUser.email}")
            BasicButton("Log Out", Modifier.basicButton()) {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        loginViewModel.onLogOutClick()
                    }
                }
            }
        }
    }

}