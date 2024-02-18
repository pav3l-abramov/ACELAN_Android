package com.example.acelanandroid.opengl

import android.opengl.Matrix

class Light(private var lightPosInWorldSpace: FloatArray) {
    val positionInEyeSpace = FloatArray(4)
    var ambientColor = floatArrayOf(0.2f, 0.2f, 0.5f, 1.0f)
    var diffuseColor = floatArrayOf(0.7f, 0.7f, 1.0f, 1.0f)
    var specularColor = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)

    fun applyViewMatrix(viewMatrix: FloatArray) {
        Matrix.multiplyMV(positionInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0)
    }
}