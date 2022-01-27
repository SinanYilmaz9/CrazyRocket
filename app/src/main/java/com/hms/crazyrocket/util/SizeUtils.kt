package com.hms.crazyrocket.util

import android.content.Context

object SizeUtils {
    fun dp2Px(context: Context, dp: Float): Int {
        val resources = context.resources ?: return 0
        val displayMetrics = resources.displayMetrics ?: return 0
        val scale = displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}