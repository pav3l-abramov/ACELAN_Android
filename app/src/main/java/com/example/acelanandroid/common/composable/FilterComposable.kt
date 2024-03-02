package com.example.acelanandroid.common.composable

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.R
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.screens.materials.FilterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    message: String,
//    youngMin: String,
//    youngMax: String,
//    youngOn: Boolean,
    core: String,
    type: String,
//    onNewValueMainYoungFilter: () -> Unit,
    onNewValueTypeFilter: (String) -> Unit,
    onNewValueCoreFilter: (String) -> Unit,
//    onNewValueYoungMinFilter: (String) -> Unit,
//    onNewValueYoungMaxFilter: (String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    var expanded by remember { mutableStateOf(false) }
    var expandedCore by remember { mutableStateOf(false) }
    val materialType = arrayOf("All", "Isotropic", "Anisotropic", "Liquid")
    val materialCore = arrayOf("All", "Yes", "No")
    Dialog(
        onDismissRequest = onCancel,
    ) {
        Column(
            modifier = Modifier
                .background(color, shape = RoundedCornerShape(20.dp))
                .padding(8.dp),
        ) {
            Text(
                text = message,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                ) {
                    Text(
                        text = "Type",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(50.dp)
                    )
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            TextField(
                                value = type,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                materialType.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            onNewValueTypeFilter(item)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Card(
                modifier = modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                ) {
                    Text(
                        text = "Core",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(50.dp)
                    )
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedCore,
                            onExpandedChange = {
                                expandedCore = !expandedCore
                            }
                        ) {
                            TextField(
                                value = core,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCore) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedCore,
                                onDismissRequest = { expandedCore = false }
                            ) {
                                materialCore.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            onNewValueCoreFilter(item)
                                            expandedCore = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
//            Card(
//                modifier = modifier
//            ) {
//                Row(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp, 16.dp)
//                    .clickable { onNewValueMainYoungFilter() })
//                {
//                    Text(
//                        text = "Young",
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.align(Alignment.CenterVertically)
//                    )
//                    Spacer(Modifier.weight(1f))
//                    Image(
//                        painter = if (youngOn) painterResource(R.drawable.baseline_arrow_drop_up_24) else painterResource(
//                            R.drawable.baseline_arrow_drop_down_24
//                        ),
//                        contentDescription = "arrow",
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(16.dp))
//                            .padding(20.dp, 0.dp)
//                    )
//
//                }
//                if (youngOn) {
//                    Column(
//                        modifier = Modifier
//                            .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 16.dp)
//                            .border(2.dp, color, shape = RoundedCornerShape(10.dp))
//                            .fillMaxWidth(),
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                        ) {
//                            Text(
//                                text = "young min: ",
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier
//                                    .padding(start = 5.dp)
//                                    .width(100.dp)
//                                    .align(Alignment.CenterVertically)
//                            )
//                            Card(
//                                modifier = Modifier
//                                    .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
//                                    .border(1.dp, color, shape = RoundedCornerShape(10.dp))
//                            )
//                            {
//                                TextField(
//                                    singleLine = true,
//                                    modifier = Modifier.defaultMinSize(minHeight = 40.dp),
//                                    value = youngMin,
//                                    onValueChange = { onNewValueYoungMinFilter(it) },
//                                    placeholder = { Text("Young min") }
//                                )
//                            }
//                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            ) {
//                            Text(
//                                text = "young max: ",
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier
//                                    .padding(start = 5.dp)
//                                    .width(100.dp)
//                                    .align(Alignment.CenterVertically)
//                            )
//                            Card(
//                                modifier = Modifier
//                                    .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
//                                    .border(1.dp, color, shape = RoundedCornerShape(10.dp))
//                            )
//                            {
//                                TextField(
//                                    singleLine = true,
//                                    modifier = Modifier.defaultMinSize(minHeight = 40.dp),
//                                    value = youngMax,
//                                    onValueChange = { onNewValueYoungMaxFilter(it) },
//                                    placeholder = { Text("Young max") }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
            Button(
                onClick = { onCancel() },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Close")

            }
        }
    }
}



