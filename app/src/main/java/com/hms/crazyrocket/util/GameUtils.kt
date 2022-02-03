package com.hms.crazyrocket.util

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Camera
import android.util.Log
import com.hms.crazyrocket.camera.LensEnginePreview
import com.hms.crazyrocket.view.GameGraphic
import com.huawei.hms.mlsdk.common.LensEngine
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzer
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerFactory
import java.io.IOException

object GameUtils {

    private const val TAG = "GameUtils"
    private var analyzer : MLHandKeypointAnalyzer? = null

    @SuppressLint("StaticFieldLeak")
    private var lensEngine: LensEngine? = null
    var width = 0
    var height = 0

    fun createHandAnalyze() {
        // TODO : Create Hand Analyzer
    }

    fun setHandTransactor(gameGraphic: GameGraphic) {
        // TODO : Set Hand Transactor
    }

    fun getMagnification(): Float {
        var magnification = 1
        val camera = Camera.open(1)
        val supportedPreviewSizes = camera.parameters.supportedPreviewSizes

        for (i in supportedPreviewSizes.indices.reversed()) {
            width = supportedPreviewSizes[i].width
            height = supportedPreviewSizes[i].height
            if (width >= 300 && height >= 300) {
                break
            }
        }
        camera.release()
        magnification = supportedPreviewSizes[0].width / width
        return magnification.toFloat()
    }

    fun initLensEngine(context: Context?) {
        // TODO : Initialize LensEngine
    }

    //start preview
    fun startLensEngine(preview: LensEnginePreview) {
            try {
                preview.start(lensEngine)
            } catch (e: IOException) {
                Log.e(TAG, "Failed to start lens engine.", e)
                lensEngine?.release()
                lensEngine = null
            }
    }

    fun stopPreview(mPreview: LensEnginePreview) {
        mPreview.stop()
    }

    fun releaseAnalyze() {
        lensEngine?.release()
        analyzer?.destroy()
    }
}