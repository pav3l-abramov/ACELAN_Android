package com.example.acelanandroid.screens.materials

data class GraphMaterialData(
   val yValues: List<Float>,
   val yLabelName: String,
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