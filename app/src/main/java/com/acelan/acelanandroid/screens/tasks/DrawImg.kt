package com.acelan.acelanandroid.screens.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun DrawImage(
    content: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .size(300.dp)
            .background(
            MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Image(
            painter = rememberAsyncImagePainter(content),
            contentDescription = "image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize().background(
                MaterialTheme.colorScheme.background
            ),
        )
    }
}