package com.example.acelanandroid

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.acelanandroid.navigation.AppNavigation
import com.example.acelanandroid.ui.theme.AcelanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcelanAndroidTheme {
                // A surface container using the 'background' color from the theme

                    val context= LocalContext.current
                    AppNavigation(context)

            }
        }
    }
}
