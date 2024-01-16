package com.example.acelanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.acelanandroid.navigation.AppNavigation
import com.example.acelanandroid.ui.theme.AcelanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    val tokenViewModel: TokenViewModel by viewModels()
//    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcelanAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}
