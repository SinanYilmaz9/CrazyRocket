package com.hms.crazyrocket.util

import android.util.SparseArray
import com.hms.crazyrocket.view.GameGraphic
import com.huawei.hms.mlsdk.common.MLAnalyzer
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypoints

class HandKeyPointTransactor(gameGraphic: GameGraphic) : MLAnalyzer.MLTransactor<MLHandKeypoints> {

    private var gameGraphic: GameGraphic? = null

    init {
        this.gameGraphic = gameGraphic
    }

    override fun transactResult(results: MLAnalyzer.Result<MLHandKeypoints?>) {
        // TODO : Transact result for HandKeyPoint
    }

    override fun destroy() {}
}