package com.example.acelanandroid.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ModelScreen(urlModel:String,typeModel:String) {
    Column {
        Text(text =urlModel )
        Text(text =typeModel )
    }

}