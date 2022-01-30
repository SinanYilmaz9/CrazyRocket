package com.hms.crazyrocket.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hms.crazyrocket.R
import com.hms.crazyrocket.camera.LensEnginePreview
import com.hms.crazyrocket.databinding.ActivityHandGameBinding
import com.hms.crazyrocket.view.GameGraphic
import com.hms.crazyrocket.util.GameUtils
import com.hms.crazyrocket.util.viewBinding

class HandGameActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityHandGameBinding::inflate)

    private var mPreview: LensEnginePreview? = null
    private var gameGraphic: GameGraphic? = null

    private var level = 4
    private var magnification = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setup()
        initListeners()
    }

    private fun setup() {
        mPreview = binding.preview
        gameGraphic = binding.graphic

        intent?.let { i->
            level = i.getIntExtra(getString(R.string.level), 4)
            magnification = i.getFloatExtra(getString(R.string.magnification), 1f)
        }
        gameGraphic?.initData(this,binding.gameover,binding.score, level, magnification)

        gameGraphic?.let { GameUtils.setHandTransactor(it) }
    }

    private fun initListeners() {
        binding.start.setOnClickListener {
            binding.gamestart.visibility = View.GONE
            gameGraphic?.startGame()
            gameGraphic?.invalidate()
        }

        binding.exit.setOnClickListener { finish() }

        binding.restart.setOnClickListener {
            binding.gameover.visibility = View.GONE
            gameGraphic?.startGame()
            gameGraphic?.invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        mPreview?.let { GameUtils.startLensEngine(it) }
    }

    override fun onPause() {
        super.onPause()
        mPreview?.let { GameUtils.stopPreview(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        GameUtils.releaseAnalyze()
    }
}