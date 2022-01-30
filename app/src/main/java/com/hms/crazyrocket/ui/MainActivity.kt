package com.hms.crazyrocket.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.hms.crazyrocket.R
import com.hms.crazyrocket.databinding.ActivityMainBinding
import com.hms.crazyrocket.util.GameUtils
import com.hms.crazyrocket.util.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    companion object {
        private val PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        private const val REQUEST_CODE = 1
    }

    private var radioGroup: RadioGroup? = null
    private var cancel: AppCompatButton? = null
    private var dialog: AlertDialog? = null

    private var magnification = 1f
    private var choice = 0
    private var pickString: Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initData()
        setupDialog()
        initListener()
    }

    private fun setupDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null)
        radioGroup = view.findViewById(R.id.radio_group)
        cancel = view.findViewById(R.id.cancel)
        val builder = AlertDialog.Builder(this).setView(view)

        dialog = builder.create()
        dialog?.window.apply {
            this?.setBackgroundDrawableResource(android.R.color.transparent)
            this?.setGravity(Gravity.BOTTOM)
        }
    }

    private fun initListener() {
        binding.hand.setOnClickListener {

            if (!isGranted(Manifest.permission.CAMERA)) {
                requestPermission()
            }
            else {
                GameUtils.createHandAnalyze()
                magnification = GameUtils.getMagnification() + 0.5f
                GameUtils.initLensEngine(this@MainActivity)

                val handIntent = Intent(this@MainActivity, HandGameActivity::class.java)

                when (choice) {
                    0 -> handIntent.putExtra(getString(R.string.level), 8)
                    1 -> handIntent.putExtra(getString(R.string.level), 4)
                    2 -> handIntent.putExtra(getString(R.string.level), 1)
                }
                handIntent.putExtra(getString(R.string.magnification), magnification)
                startActivity(handIntent)
            }
        }

        binding.linearLevel.setOnClickListener { dialog?.show() }

        cancel?.setOnClickListener { dialog?.cancel() }

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.hight -> {
                    choice = 0
                    binding.level.text = pickString[choice]
                }

                R.id.middle -> {
                    choice = 1
                    binding.level.text = pickString[choice]
                }

                R.id.low -> {
                    choice = 2
                    binding.level.text = pickString[choice]
                }
            }
            dialog?.dismiss()
        }
    }

    private fun initData() {
        pickString = arrayOf(getString(R.string.hight), getString(R.string.middle), getString(R.string.low))
    }

    private fun isGranted(permission: String): Boolean {
        val checkSelfPermission = checkSelfPermission(permission)
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(): Boolean {
        if (!isGranted(PERMISSIONS[0])) {
            requestPermissions(PERMISSIONS, REQUEST_CODE)
        }
        return true
    }

}