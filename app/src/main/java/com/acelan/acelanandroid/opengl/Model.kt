package com.acelan.acelanandroid.opengl

import android.opengl.Matrix

abstract class Model {

    // Center of mass to be populated by subclasses
    protected var centerMassX = 0f
    protected var centerMassY = 0f
    protected var centerMassZ = 0f

    var floorOffset = 0f
        protected set

    protected var glProgram = -1

    var modelMatrix = FloatArray(16)
        protected set

    protected var mvMatrix = FloatArray(16)
    protected var mvpMatrix = FloatArray(16)

    protected var maxX = Float.MIN_VALUE
    protected var maxY = Float.MIN_VALUE
    protected var maxZ = Float.MIN_VALUE
    protected var minX = Float.MAX_VALUE
    protected var minY = Float.MAX_VALUE
    protected var minZ = Float.MAX_VALUE

    open fun setup(boundSize: Float) {
        initModelMatrix(boundSize)
    }

    protected open fun initModelMatrix(boundSize: Float) {
        initModelMatrix(boundSize, 0.0f, 0.0f, 0.0f)
    }

    protected fun initModelMatrix(boundSize: Float, rotateX: Float, rotateY: Float, rotateZ: Float) {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.rotateM(modelMatrix, 0, rotateX, 1.0f, 0.0f, 0.0f)
        Matrix.rotateM(modelMatrix, 0, rotateY, 0.0f, 1.0f, 0.0f)
        Matrix.rotateM(modelMatrix, 0, rotateZ, 0.0f, 0.0f, 1.0f)
        scaleModelMatrixToBounds(boundSize)
        Matrix.translateM(modelMatrix, 0, -centerMassX, -centerMassY, -centerMassZ)
    }

    abstract fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray, light: Light)

    protected fun adjustMaxMin(x: Float, y: Float, z: Float) {
        if (x > maxX) {
            maxX = x
        }
        if (y > maxY) {
            maxY = y
        }
        if (z > maxZ) {
            maxZ = z
        }
        if (x < minX) {
            minX = x
        }
        if (y < minY) {
            minY = y
        }
        if (z < minZ) {
            minZ = z
        }
    }

    protected fun getBoundScale(boundSize: Float): Float {
        val scaleX = (maxX - minX) / boundSize
        val scaleY = (maxY - minY) / boundSize
        val scaleZ = (maxZ - minZ) / boundSize
        var scale = scaleX
        if (scaleY > scale) {
            scale = scaleY
        }
        if (scaleZ > scale) {
            scale = scaleZ
        }
        return scale
    }

    private fun scaleModelMatrixToBounds(boundSize: Float) {
        var scale = getBoundScale(boundSize)
        if (scale != 0f) {
            scale = 1f / scale
            Matrix.scaleM(modelMatrix, 0, scale, scale, scale)
        }
    }
}