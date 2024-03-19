package com.example.acelanandroid.common.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    SampleContent(
        param="d",
        row=3,
        col=6,
        widt=width,
        item=listOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,),
    )
}

@Composable
fun DrawTable(
    paramList: List<Float>,
    paramName: String,
    isAnizotropic: Boolean
) {
    val configuration = LocalConfiguration.current
    val screenMin = listOf(configuration.screenHeightDp.dp, configuration.screenWidthDp.dp).min()
    var index = 0
    if (isAnizotropic) {
        if (paramName == "d"&&paramList.size==18 ) {
            Column {
                for (i in 0 until 3) {
                    Row {
                        for (j in 0 until 6) {
                            Text(
                                text = "(${paramList[index]})",
                                modifier = Modifier.padding(8.dp)
                            )
                            index++
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SampleContent(
    param: String, row: Int, col: Int, widt: Int, item: List<Float>
) {
    repeat(row * col) {
        if (item.size > it) {
            Box(
                modifier = Modifier
                    .size(widt.dp)
                    .border(width = 1.dp, color = DarkGray),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = item[it].toString(), color = MaterialTheme.colorScheme.primary
                )

            }
        } else {
            Box(
                modifier = Modifier
                    .size(widt.dp)
                    .border(width = 1.dp, color = DarkGray),
                contentAlignment = Alignment.Center,
            ) {
                val rowIndex = it / row
                val colIndex = it % col
                //Text(it.toString())
                Text(
                    text = param, color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    text = "${rowIndex + 1}${colIndex + 1}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(1.dp)
                )

            }
        }

    }
}