package com.acelan.acelanandroid.screens.materials

import androidx.compose.ui.graphics.Color
import com.acelan.acelanandroid.ui.theme.GraphBlack
import com.acelan.acelanandroid.ui.theme.GraphBlue
import com.acelan.acelanandroid.ui.theme.GraphCian
import com.acelan.acelanandroid.ui.theme.GraphGreen
import com.acelan.acelanandroid.ui.theme.GraphOrange
import com.acelan.acelanandroid.ui.theme.GraphPurple
import com.acelan.acelanandroid.ui.theme.GraphRed
import com.acelan.acelanandroid.ui.theme.GraphWhite
import com.acelan.acelanandroid.ui.theme.GraphYellow

data class GraphSettingList(
    val nameParam: String,
    val valueParam: Int,
    val numParam: Int
)

data class GraphSettingDivideList(
    val nameParam: String,
    val valueParam: String,
    val numParam: Int
)

data class ColorList(
    val numColor: Int,
    val color: Color,
    val nameColor:String
)

object ColorProvider {
    val listColor= listOf(
        ColorList(0, GraphBlack,"Black"),
        ColorList(1, GraphWhite,"White"),
        ColorList(2, GraphRed,"Red"),
        ColorList(3, GraphBlue,"Blue"),
        ColorList(4, GraphYellow,"Yellow"),
        ColorList(5, GraphGreen,"Green"),
        ColorList(6, GraphOrange,"Orange"),
        ColorList(7, GraphCian,"Cian"),
        ColorList(8, GraphPurple,"Purple"),
    )
}
