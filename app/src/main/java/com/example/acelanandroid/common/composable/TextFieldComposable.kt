package com.example.acelanandroid.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acelanandroid.ui.theme.DarkEmerald
import com.example.acelanandroid.ui.theme.LightEmerald
import com.example.acelanandroid.R.drawable as AppIcon

@Composable
fun BasicField(
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(text)) }
    )
}

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text("Email") },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    PasswordField(value, "Password", onNewValue, modifier)
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, "Repeat password", onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    placeholder: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(AppIcon.baseline_visibility_24)
        else painterResource(AppIcon.baseline_visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

@Composable
fun TextSignIn(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = getColor(isSystemInDarkTheme()))) {
                append("S")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("ign")
            }

            withStyle(style = SpanStyle(color = getColor(isSystemInDarkTheme()))) {
                append(" I")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("n")
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(16.dp))
    }
}

@Composable
fun TextGraphMaterialType(content:String, modifier: Modifier) {
    val firstLetter =content.first()
    val otherLetter= content.drop(1)

    Column(modifier = modifier,horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = getColor(isSystemInDarkTheme()))) {
                append(firstLetter)
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(otherLetter)
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(16.dp))
    }
}


@Composable
fun TextHello(modifier: Modifier, content: String) {
    Column(modifier = modifier) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = getColor(isSystemInDarkTheme()))) {
                append("H")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("ello")
            }
            withStyle(style = SpanStyle(color = getColor(isSystemInDarkTheme()))) {
                append(" U")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("ser")
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(16.dp))


        Column {
            Row {
                Text(
                    text = "Your email:",
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = getColor(isSystemInDarkTheme()))) {
                        append(content)
                    }
                }, fontSize = 30.sp)
            }
        }
    }
}
@Composable
fun TextWithSubString(param:String,rowIndex:Int,colIndex:Int ){
    val subscript = SpanStyle(
        baselineShift = BaselineShift.Subscript,
        fontSize = 16.sp, // font size of subscript
        color =  getColor(isSystemInDarkTheme())
    )

    Text(
        fontSize = 20.sp,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color =  getColor(isSystemInDarkTheme()))){
                append(param)
            }
            withStyle(subscript) {
                append("${rowIndex + 1}${colIndex + 1}")
            }
        },

    )
}

@Composable
fun TextCardWithSubstring(
    param:String,rowIndex:Int,colIndex:Int,
    modifier: Modifier
) {
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

                    val subscript = SpanStyle(
                        baselineShift = BaselineShift.Subscript,
                        fontSize = 16.sp
                    )

                    Text(
                        fontSize = 20.sp,
                        text = buildAnnotatedString {
                            append(param)
                            withStyle(subscript) {
                                append("${rowIndex + 1}${colIndex + 1}")
                            }
                        },

                        )
                }
            }
        }
    }
}


@Preview
@Composable
fun check() {
    TextHello(Modifier, "sdadadas")
}

fun getColor(systemTheme: Boolean): Color {
    val color = if (systemTheme) {
        DarkEmerald
    } else {
        LightEmerald
    }
    return color
}

