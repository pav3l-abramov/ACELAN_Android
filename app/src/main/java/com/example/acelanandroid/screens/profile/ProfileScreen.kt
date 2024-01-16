package com.example.acelanandroid.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, loginViewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, loginViewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton("Sign In", Modifier.basicButton()) {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    loginViewModel.onSignInClick()
                }
            }
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()){
        }

        BasicTextButton("Forgot password", Modifier.textButton()) {

        }
    }

}