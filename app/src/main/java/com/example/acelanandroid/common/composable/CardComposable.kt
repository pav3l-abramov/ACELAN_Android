/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.acelanandroid.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun RegularCardEditor(
    content: String,
    modifier: Modifier,
    status: String,
    onEditClick: () -> Unit
) {
    CardEditor(content, status, onEditClick, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditor(
    content: String,
    status: String,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    val color = if (status == "failure") {
        Color.Red
    } else if (status == "working") {
        Color.Yellow
    } else if (status == "queued") {
        Color.Cyan
    } else {
        Color.Green
    }
    Card(
        modifier = modifier,
        onClick = onEditClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (content.isNotBlank()) {
                    Text(
                        text = content, modifier = Modifier.padding(16.dp, 0.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(2.dp, color, shape = RoundedCornerShape(10.dp))
                        .width(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = status,
                        modifier = Modifier.padding(12.dp),
                        color = color,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun CardImage(
    content: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier.size(300.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(content),
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CardMaterial(
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardMaterialQ(content, onEditClick, modifier)
}
@Composable
private fun CardMaterialQ(
    content: String,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        onClick = onEditClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (content.isNotBlank()) {
                    Text(
                        text = content, modifier = Modifier.padding(16.dp, 0.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

