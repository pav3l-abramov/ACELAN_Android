package com.acelan.acelanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.acelan.acelanandroid.navigation.AppNavigation
import com.acelan.acelanandroid.ui.theme.AcelanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcelanAndroidTheme {
                    val context= LocalContext.current
                    AppNavigation(context)
            }
        }
    }
}
