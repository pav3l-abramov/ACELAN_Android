package com.example.acelanandroid.screens.materials

import androidx.compose.ui.Modifier
import com.example.acelanandroid.common.ext.fieldModifier

data class GraphMaterialData(
   val yValues: List<Float>,
   val yLabelName: String,
   val paramName: String,
   val materialNameList: List<String>,
)
data class GraphList(
   val nameList: List<String>?=null,
   val youngList: List<Float>?=null,
   val poisonList: List<Float>?=null,
   val stiffnessList: List<List<Float>>?=null,
   val piezoList: List<List<Float>>?=null,
   val dielectricList: List<List<Float>>?=null,
)
data class TableList(
  val param:String,
  val row:Int,
  val col:Int,
  val item:List<Float>,
  val maxItemsInRow:Int,
  val description:String,
  val dimension :String
)