package com.example.acelanandroid.opengl

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import kotlin.math.pow
import kotlin.math.sqrt

@SuppressLint("ViewConstructor")
class OpenGLSurfaceView(context: Context, model: Model?) : GLSurfaceView(context) {
    private val renderer: ModelRenderer
    private var startX = 0f
    private var startY = 0f
    private var firstTouchRotate = true
    private var firstTouchZoom = true
    private var startDistance = 0.0f

    init {
        setEGLContextClientVersion(2)
        renderer = ModelRenderer(model)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    if (firstTouchRotate) {
                        startX = event.x
                        startY = event.y
                        firstTouchRotate = false
                        firstTouchZoom = true
                    }
                    val dx = event.x - startX
                    val dy = event.y - startY
                    startX = event.x
                    startY = event.y
                    renderer.rotate(dy, dx)
                } else if (event.pointerCount == 2) {
                    if (firstTouchZoom) {
                        startDistance =
                            vectorLength(event.getX(0), event.getX(1), event.getY(0), event.getY(1))
                        startX = (event.getX(0) + event.getX(1)) * 0.5f
                        startY = (event.getY(0) + event.getY(1)) * 0.5f
                        firstTouchRotate = true
                        firstTouchZoom = false
                    }
                    val newDistance =
                        vectorLength(event.getX(0), event.getX(1), event.getY(0), event.getY(1))
                    val dx = (event.getX(0) + event.getX(1)) * 0.5f - startX
                    val dy = (event.getY(0) + event.getY(1)) * 0.5f - startY
                    startX = (event.getX(0) + event.getX(1)) * 0.5f
                    startY = (event.getY(0) + event.getY(1)) * 0.5f
                    val scaleZoom = newDistance / startDistance
                    startDistance =
                        vectorLength(event.getX(0), event.getX(1), event.getY(0), event.getY(1))
                    renderer.translate(dx, dy, scaleZoom)

                }
                requestRender()
            }

            MotionEvent.ACTION_UP -> {
                startX = 0.0f
                startY = 0.0f
                firstTouchRotate = true
                firstTouchZoom = true
            }
        }
        return true
    }

    private fun vectorLength(x1: Float, x2: Float, y1: Float, y2: Float): Float {
        return sqrt(((x2 - x1).toDouble().pow(2.0) + (y2 - y1).toDouble().pow(2.0))).toFloat()
    }
}