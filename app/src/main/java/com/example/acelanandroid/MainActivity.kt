package com.example.acelanandroid

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.acelanandroid.navigation.AppNavigation
import com.example.acelanandroid.connectToServer.viewModel.MainViewModel
import com.example.acelanandroid.connectToServer.viewModel.TokenViewModel
import com.example.acelanandroid.ui.theme.AcelanAndroidTheme
import dagger.hilt.android.HiltAndroidApp

class MainActivity : ComponentActivity() {
    val tokenViewModel: TokenViewModel by viewModels()
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AcelanAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}
