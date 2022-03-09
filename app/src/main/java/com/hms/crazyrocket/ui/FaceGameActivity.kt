package com.hms.crazyrocket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hms.crazyrocket.R
import com.hms.crazyrocket.camera.LensEnginePreview
import com.hms.crazyrocket.databinding.ActivityFaceGameBinding
import com.hms.crazyrocket.util.viewBinding
import com.hms.crazyrocket.util.GameUtils.releaseAnalyze
import com.hms.crazyrocket.util.GameUtils.startLensEngine
import com.hms.crazyrocket.util.GameUtils.stopPreview
import com.hms.crazyrocket.util.GameUtils


class FaceGameActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityFaceGameBinding::inflate)

    private var lensEnginePreview: LensEnginePreview? = null
    private var level = 4
    private var magnification = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUp()
        initListener()
    }

    private fun setUp() {
        lensEnginePreview = binding.preview

        intent?.let {
            level = intent.getIntExtra(getString(R.string.level), 4)
            magnification = intent.getFloatExtra(getString(R.string.magnification), 1f)
        }

        binding.gameGraphic.initData(this, binding.gameOver,binding.score, level, magnification)
        GameUtils.setFaceTransactor(binding.gameGraphic)
    }

    private fun initListener() {
        binding.start.setOnClickListener {
            binding.gameStart.visibility = View.GONE
            binding.gameGraphic.startGame()
            binding.gameGraphic.invalidate()
        }

        binding.exit.setOnClickListener {
            finish()
        }

        binding.restart.setOnClickListener {
            binding.gameOver.visibility = View.GONE
            binding.gameGraphic.startGame()
            binding.gameGraphic.invalidate()
        }

    }

    override fun onResume() {
        super.onResume()
        lensEnginePreview?.let { startLensEngine(it) }
    }

    override fun onPause() {
        super.onPause()
        lensEnginePreview?.let { stopPreview(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseAnalyze(0)
    }
}