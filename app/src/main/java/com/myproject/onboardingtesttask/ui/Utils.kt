package com.myproject.onboardingtesttask.ui

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLES20

private const val minCores = 2
private const val minMemory = 2 * 1024 * 1024 * 1024
private const val minWidth = 400
private const val minHeight = 720
private const val minGpuVersion = "OpenGL ES 2.0"

fun isLowEndDevice(context: Context): Boolean {
    val cpuCores = Runtime.getRuntime().availableProcessors()
    val memoryInfo = ActivityManager.MemoryInfo()
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.getMemoryInfo(memoryInfo)

    val displayMetrics = context.resources.displayMetrics
    val gpuVersion = GLES20.glGetString(GLES20.GL_VERSION).orEmpty()

    return (cpuCores <= minCores || memoryInfo.totalMem < minMemory ||
            displayMetrics.widthPixels < minWidth || displayMetrics.heightPixels < minHeight ||
            gpuVersion.contains(minGpuVersion))
}

