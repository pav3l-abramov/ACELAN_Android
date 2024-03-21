package com.example.acelanandroid.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.ui.theme.DarkEmerald
import com.example.acelanandroid.ui.theme.LightEmerald

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun Check() {
    val pad = 10;
    val width = with(LocalDensity.current) {
        ((LocalConfiguration.current.screenWidthDp - pad * 2) / 6)
    }
//    DrawTable(
//        paramList = listOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,),
//        paramName = "d",
//        isAnizotropic = true,
//    )

    DrawTable(
        param = "ε",
        row = 3,
        col = 3,
        item = listOf(
            1.1f,
            2.0f,
            3.0f,
            4.0f,
            5.0f,
            6.0f,
            1.0f,
            2.0f,
            3.0f,
            4.0f,
            5.0f,
            6.0f,
            1.0f,
            2.0f,
            3.0f,
            4.0f,
            5.0f,

        ),
        maxItemsInRow=3,
        modifier = Modifier.fieldModifier(),
        description="Piezoelectric Properties:",
        dimension="C/N",
    )


}

//@Composable
//fun DrawTable(
//    paramList: List<Float>,
//    paramName: String,
//    isAnizotropic: Boolean
//) {
//    val configuration = LocalConfiguration.current
//    val screenMin = listOf(configuration.screenHeightDp.dp, configuration.screenWidthDp.dp).min()
//    var index = 0
//    if (isAnizotropic) {
//        if (paramName == "d" && paramList.size == 18) {
//            Column {
//                for (i in 0 until 3) {
//                    Row {
//                        for (j in 0 until 6) {
//                            Text(
//                                text = "(${paramList[index]})",
//                                modifier = Modifier.padding(8.dp)
//                            )
//                            index++
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DrawTable(
    param: String,
    description: String,
    dimension: String,
    row: Int,
    col: Int,
    item: List<Float>,
    maxItemsInRow:Int,
    modifier:Modifier
) {
    val configuration = LocalConfiguration.current
    val screenMin = (listOf(configuration.screenHeightDp.dp, configuration.screenWidthDp.dp).min()-32.dp)/6
    Column (modifier = modifier.fillMaxWidth()) {
        Spacer(Modifier.size(16.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle()) {
                        append(description)
                    }
                    withStyle(style = SpanStyle(color =  getColor(isSystemInDarkTheme()))) {
                        append( " $param"+"ᵢⱼ"+" ,$dimension")
                    }
                }, fontSize = 20.sp, modifier=Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.size(16.dp))

        FlowRow(maxItemsInEachRow = maxItemsInRow, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            repeat(row * col) {
                val rowIndex = it / maxItemsInRow
                val colIndex = it % col
                Box(
                    modifier = Modifier
                        .size(screenMin)
                        .border(width = 1.dp, color = colorBackground(!isSystemInDarkTheme()))
                        .background(
                            //MaterialTheme.colorScheme.background
                            when(param){
                                "C"-> if (rowIndex==colIndex || (rowIndex<3&&colIndex<3&&rowIndex<colIndex)) MaterialTheme.colorScheme.background else colorBackground(isSystemInDarkTheme())
                                "d"->MaterialTheme.colorScheme.background
                                "ε"->if (rowIndex==colIndex) MaterialTheme.colorScheme.background else colorBackground(isSystemInDarkTheme())
                                else -> {MaterialTheme.colorScheme.background}
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (item.size > it) {
                            if (item[it] % 1.0 != 0.0) {
                                item[it].toString()
                            } else {
                                item[it].toInt().toString()
                            }
                        } else {
                            "NaN"
                        },
                        color = MaterialTheme.colorScheme.primary
                    )


                        //Text(it.toString())
                        Text(
                            text = param, color = getColor(isSystemInDarkTheme()),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(vertical = 4.dp, horizontal = 14.dp)
                        )

                        Text(
                            text = "${rowIndex + 1}${colIndex + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            color = getColor(isSystemInDarkTheme()),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(vertical = 1.dp, horizontal = 1.dp)
                        )
                    }


            }
        }
    }
}

fun colorBackground(systemTheme: Boolean): Color {
    val color = if (systemTheme) {
        DarkGray
    } else {
        LightGray
    }
    return color
}
