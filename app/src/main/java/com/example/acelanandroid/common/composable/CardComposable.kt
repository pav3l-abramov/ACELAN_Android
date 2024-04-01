package com.example.acelanandroid.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acelanandroid.R
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.screens.materials.ColorProvider
import com.example.acelanandroid.screens.materials.GraphSettingViewModel
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
    startTime: String,
    finishTime: String,
    onEditClick: () -> Unit
) {
    TaskCardMain(content, status, startTime, finishTime, onEditClick, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskCardMain(
    content: String,
    status: String,
    startTime: String,
    finishTime: String,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    val color = getColorStatus(status, isSystemInDarkTheme())
    val listStart = if (startTime != "null") startTime.split("T", "Z", ".") else listOf("", "-")
    val listFinish = if (finishTime != "null") finishTime.split("T", "Z", ".") else listOf("", "-")
    val scale = remember { mutableStateOf(1f) }




    Card(
        modifier = modifier,
        onClick = onEditClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                if (content.isNotBlank()) {
                    Text(
                        text = content,
                        fontWeight = FontWeight.Bold, fontSize = 24.sp
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
            Spacer(modifier = Modifier.size(5.dp))
            Row {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Start:")
                        }
                    }, modifier = Modifier
                        .width(60.dp)
                )
                Text(text = "${listStart[1]}    ${listStart[0]}")
            }
            Spacer(modifier = Modifier.size(5.dp))
            Row {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Finish:")
                        }
                    }, modifier = Modifier
                        .width(60.dp)
                )
                Text(text = "${listFinish[1]}    ${listFinish[0]}")
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
    MaterialCardOnSearch(content, onEditClick, modifier)
}

@Composable
private fun MaterialCardOnSearch(
    content: String,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        onClick = onEditClick
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

@Composable
fun MaterialCardMain(
    content: String,
    typeMaterial: String,
    isDraw: Boolean,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    val list = if (typeMaterial != "null") typeMaterial.split("Material", "::") else listOf("","","no data")
    Card(
        modifier = modifier,
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    if (content.isNotBlank()) {
                        Text(
                            text = content,
                            fontWeight = FontWeight.Bold, fontSize = 24.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(list[2])
                        }
                    }, modifier = Modifier)
                }
            }
            if (isDraw) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .width(30.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_auto_graph_24),
                    contentDescription = null
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
    modifier: Modifier,
    isParam:Boolean
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
                        .width( if (isParam)180.dp else 80.dp),
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
                        color = color,fontSize = 16.sp
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
                        color = color,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}


@Composable
fun HomeCard(
    content: String,
    title: String,
    isShow: Boolean,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        onClick = onEditClick
    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

                ) {

                Column(
//                verticalArrangement = Arrangement.Center,
                    //horizontalAlignment = Alignment.End,
                    modifier = Modifier.width(250.dp)

                ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold, fontSize = 24.sp,
                    modifier = Modifier
                )

            }
            Column(
//                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .width(30.dp)
            ) {
                Icon(
                    painter = painterResource(if (isShow) R.drawable.baseline_expand_more_24 else R.drawable.baseline_chevron_left_24),
                    contentDescription = null
                )
                  }

        }
        if (isShow) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {

                Text(
                    text = content,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun GraphSettingBoolean(
    content: String,
    valueParam:Int,
    numParam:Int,
    onEditClick: (Int,Int) -> Unit,
   // onEditClick: () -> Unit,
    modifier: Modifier
){
    Card(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.width(250.dp)
            ) {
                Text(
                    text = content,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }


            Checkbox(
                checked = valueParam==1,
                onCheckedChange = { isChecked ->
                    onEditClick(numParam,if (isChecked)1 else 0)
                }
            )
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphSettingColor(
    content: String,
    valueParam:Int,
    numParam:Int,
    onEditClick: (Int,Int) -> Unit,
    modifier: Modifier
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.width(150.dp)
            ) {
                Text(
                    text = content,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Box(modifier=Modifier.width(150.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = ColorProvider.listColor.first { it.numColor == valueParam }.nameColor,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        ColorProvider.listColor.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item.nameColor) },
                                onClick = {
                                    onEditClick(numParam,item.numColor)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}
@Composable
fun GraphSettingDivide(
    content: String,
    valueParam:String,
    numParam:Int,
    onEditClick: (Int,String) -> Unit,
    modifier: Modifier
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.width(150.dp)
            ) {
                Text(
                    text = content,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = if (expanded) Color.Red else MaterialTheme.colorScheme.onSurface
                )

            }
            Column(modifier=Modifier.width(150.dp)) {
                OutlinedTextField(
                    singleLine = true,
                    modifier = modifier,
                    value = valueParam.toString(),
                    onValueChange = {
                        try {
                        expanded=false
                            val check = it.toInt()
                        onEditClick(numParam,it)
                    } catch(e: NumberFormatException) {
                        expanded=true
                        onEditClick(numParam,it)
                    }

                                    },
                    placeholder = {Text("Divide") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done),
                )
            }


        }
    }
}

//@Preview
//@Composable
//fun checkCard() {
//    GraphSettingBoolean("content",1,1,{},Modifier.fieldModifier())
//}




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

