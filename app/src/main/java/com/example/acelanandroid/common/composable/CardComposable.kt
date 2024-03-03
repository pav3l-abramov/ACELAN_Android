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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.acelanandroid.R
import com.example.acelanandroid.ui.theme.DarkBlue
import com.example.acelanandroid.ui.theme.DarkGreen
import com.example.acelanandroid.ui.theme.DarkRed
import com.example.acelanandroid.ui.theme.DarkYellow
import com.example.acelanandroid.ui.theme.DarkText
import com.example.acelanandroid.ui.theme.LightBlue
import com.example.acelanandroid.ui.theme.LightGreen
import com.example.acelanandroid.ui.theme.LightRed
import com.example.acelanandroid.ui.theme.LightText
import com.example.acelanandroid.ui.theme.LightYellow


@Composable
fun TaskCard(
    content: String,
    modifier: Modifier,
    status: String,
    onEditClick: () -> Unit
) {
    TaskCardMain(content, status, onEditClick, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskCardMain(
    content: String,
    status: String,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    val color = getColorStatus(status, isSystemInDarkTheme())
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
fun MaterialCard(
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    MaterialCardMain(content, onEditClick, modifier)
}

@Composable
private fun MaterialCardMain(
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

@Composable
fun TaskDetailCard(
    contentFirst: String,
    contentSecond: String,
    timeBoolean: Boolean,
    modifier: Modifier
) {
    val color = getColorStatus(contentSecond, isSystemInDarkTheme())
    Card(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(90.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = contentFirst,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (contentSecond != "null") {
                        if (timeBoolean) {
                            val list = contentSecond.split("T", "Z")
                            Text(
                                text = "${list[1]} ${list[0]}",
                                color = color
                            )
                        } else {
                            Text(
                                text = contentSecond,
                                color = color
                            )
                        }

                    } else {
                        Text(
                            text = "no data",
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MaterialDetailCard(
    contentFirst: String,
    contentSecond: String,
    timeBoolean: Boolean,
    modifier: Modifier
) {
    val color = getColorStatus(contentSecond, isSystemInDarkTheme())
    Card(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = contentFirst,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (contentSecond != "null") {
                        if (timeBoolean) {
                            val list = contentSecond.split("T", "Z")
                            Text(
                                text = "${list[1]} ${list[0]}",
                                color = color
                            )
                        } else if (contentSecond.split("::")[0] == "Materials") {
                            val list = contentSecond.split("Material", "::")
                            Text(
                                text = list[2],
                                color = color
                            )
                        } else if (contentFirst == "Core: ") {
                            if (contentSecond == "false") {
                                Text(
                                    text = "No",
                                    color = color
                                )
                            } else {
                                Text(
                                    text = "Yes",
                                    color = color
                                )
                            }
                        } else {
                            Text(
                                text = contentSecond,
                                color = color
                            )
                        }

                    } else {
                        Text(
                            text = "no data",
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextCard(
    content: String,
    modifier: Modifier
) {
    val color = getColorStatus(content, isSystemInDarkTheme())
    Card(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = content,
                        color = color
                    )
                }
            }
        }
    }
}

@Composable
fun TextCardStandart(
    content: String,
    modifier: Modifier
) {
    val color = getColorStatus(content, isSystemInDarkTheme())
    Card(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = content,
                        color = color
                    )
                }
            }
        }
    }
}



fun getColorStatus(status: String, systemTheme: Boolean): Color {
    val color = if (systemTheme) {
        if (status == "failure") {
            LightRed
        } else if (status == "working") {
            LightYellow
        } else if (status == "queued") {
            LightBlue
        } else if (status == "success") {
            LightGreen
        } else {
            LightText
        }
    } else {
        if (status == "failure") {
            DarkRed
        } else if (status == "working") {
            DarkYellow
        } else if (status == "queued") {
            DarkBlue
        } else if (status == "success") {
            DarkGreen
        } else {
            DarkText
        }
    }
    return color
}

