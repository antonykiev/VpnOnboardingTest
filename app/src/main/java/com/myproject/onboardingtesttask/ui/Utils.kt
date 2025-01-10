package com.myproject.onboardingtesttask.ui

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLES20
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp

@Composable
fun <T> optimizeSizeByHeight(
    onLargeSize: () -> T,
    onSmallSize: () -> T,
    originalSize: Int = 720,
): T {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    return if (screenHeight > originalSize) onLargeSize() else onSmallSize()
}

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
    val gpuVersion = GLES20.glGetString(GLES20.GL_VERSION)

    return (cpuCores <= minCores || memoryInfo.totalMem < minMemory ||
            displayMetrics.widthPixels < minWidth || displayMetrics.heightPixels < minHeight ||
            gpuVersion.contains(minGpuVersion))
}

