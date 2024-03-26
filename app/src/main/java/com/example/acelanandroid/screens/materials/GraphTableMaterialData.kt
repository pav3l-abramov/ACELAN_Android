package com.example.acelanandroid.screens.materials

data class GraphMaterialData(
   val yValues: List<Float>,
   val yLabelName: String,
   val paramName: String,
   val materialNameList: List<String>,
)
data class GraphListIsotropic(
   val nameList: List<String>?=null,
   val youngList: List<Float>?=null,
   val poisonList: List<Float>?=null,
)
data class GraphListAnisotropic(
    val nameList: List<String>?=null,
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