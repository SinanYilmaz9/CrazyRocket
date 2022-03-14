package com.hms.crazyrocket.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hms.crazyrocket.R
import com.hms.crazyrocket.camera.LensEnginePreview
import com.hms.crazyrocket.databinding.FragmentHandGameBinding
import com.hms.crazyrocket.util.GameUtils
import com.hms.crazyrocket.util.viewBinding
import com.hms.crazyrocket.view.GameGraphic

class HandGameFragment : BaseFragment(R.layout.fragment_hand_game) {

    private val binding by viewBinding(FragmentHandGameBinding::bind)

    private var lensEnginePreview: LensEnginePreview? = null
    private var gameGraphic: GameGraphic? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        setup()
    }

    private fun setup() {
        lensEnginePreview = binding.preview
        gameGraphic = binding.graphic

        val bundle = arguments
        if (bundle == null) {
            Log.e("HandGameFragment", "HandGameFragment did not receive information")
            return
        }

        val args = HandGameFragmentArgs.fromBundle(bundle)

        gameGraphic?.initData(binding.root.context,binding.gameover,binding.score, args.level, args.magnification)

        gameGraphic?.let { GameUtils.setHandTransactor(it) }
    }

    private fun initListeners() {
        binding.start.setOnClickListener {
            binding.gamestart.visibility = View.GONE
            gameGraphic?.startGame()
            gameGraphic?.invalidate()
        }

        binding.exit.setOnClickListener {
            findNavController().navigate(R.id.action_handGameFragment_to_homeFragment)
        }

        binding.restart.setOnClickListener {
            binding.gameover.visibility = View.GONE
            gameGraphic?.startGame()
            gameGraphic?.invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        lensEnginePreview?.let { GameUtils.startLensEngine(it) }
    }

    override fun onPause() {
        super.onPause()
        lensEnginePreview?.let { GameUtils.stopPreview(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        GameUtils.releaseAnalyze(1)
    }

}