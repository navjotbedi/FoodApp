package com.toptal.calorie.feature.home.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toptal.calorie.feature.home.ui.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()

        startActivity(Intent())
    }

    private fun setupListeners() {
        TODO("Not yet implemented")
    }

    private fun setupUI() {
        TODO("Not yet implemented")
    }
}