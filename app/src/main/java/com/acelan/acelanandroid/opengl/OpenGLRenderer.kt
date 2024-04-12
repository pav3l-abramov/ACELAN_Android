package com.acelan.acelanandroid.opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val model: Model?) : GLSurfaceView.Renderer {
    private val light = Light(floatArrayOf(0.0f, 0.0f,10f, 1.0f))

    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private var rotateX = 0f
    private var rotateY = 0f
    private var translateX = 0f
    private var translateY = 0f
    private var translateZ = 0f
    private val delta=0.1f
    private val zNear=1f
    private val zFar=200f
    private val xLeft=-50f
    private val xRight=50f
    private val yTop=50f
    private val yBottom=-50f

    fun translateModel(dx: Float, dy: Float, dz: Float) {
        if ( translateX+ dx * delta<xRight && translateX+ dx * delta>xLeft) {
            translateX += dx * delta
        }
        if ( translateY+ dy * delta<yTop && translateY+ dy * delta>yBottom) {
            translateY += dy * delta
        }
        if (dz != 0f && translateZ/dz<-zNear && translateZ/dz>-zFar) {
            translateZ /= dz
        }
        updateViewMatrix()
    }

    fun rotateModel(dx: Float, dy: Float) {
        rotateX -= dx * delta
        rotateY += dy * delta
        updateViewMatrix()
    }

    private fun updateViewMatrix() {
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, translateZ, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        Matrix.translateM(viewMatrix, 0, -translateX, -translateY, 0f)
        Matrix.rotateM(viewMatrix, 0, rotateX, 1f, 0f, 0f)
        Matrix.rotateM(viewMatrix, 0, rotateY, 0f, 1f, 0f)
    }

    override fun onDrawFrame(unused: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        model?.draw(viewMatrix, projectionMatrix, light)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val ratio = width.toFloat() / height
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, zNear, zFar+50f)
        rotateX = 0f
        rotateY = 0f
        translateX = 0f
        translateY = 0f
        translateZ = -100f
        updateViewMatrix()
        light.applyViewMatrix(viewMatrix)
        updateViewMatrix()
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        GLES20.glClearColor(0.95f, 0.95f, 0.95f, 1f)
        GLES20.glEnable(GLES20.GL_CULL_FACE)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        model?.setup(100f)
    }
}

class Light(private var lightPosInWorldSpace: FloatArray) {
    val positionInEyeSpace = FloatArray(4)
    var ambientColor = floatArrayOf(0.2f, 0.2f, 0.2f, 1.0f)
    var diffuseColor = floatArrayOf(0.8f, 0.8f, 0.8f, 1.0f)
    var specularColor = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)

    fun applyViewMatrix(viewMatrix: FloatArray) {
        Matrix.multiplyMV(positionInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0)
    }
}