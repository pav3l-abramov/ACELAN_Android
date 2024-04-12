package com.acelan.acelanandroid.opengl

import android.opengl.GLES20
import java.nio.IntBuffer

open class IndexedModel : ArrayModel() {
    protected var indexBuffer: IntBuffer? = null
    protected var indexCount = 0

    override fun drawFunc() {
        if (indexBuffer == null || indexCount == 0) {
            return
        }
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexCount, GLES20.GL_UNSIGNED_INT, indexBuffer)
    }
}