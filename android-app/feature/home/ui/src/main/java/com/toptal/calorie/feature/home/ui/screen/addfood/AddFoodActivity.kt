package com.toptal.calorie.feature.home.ui.screen.addfood

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.home.ui.databinding.ActivityAddFoodBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFoodBinding
    private val viewModel: AddFoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        loadFood()
    }

    private fun setupListeners() {
        with(binding) {
            addFoodButton.setOnClickListener {
                viewModel.saveFood(nameEditText.text.toString(), calorieEditText.text.toString())
            }

            viewModel.addFood.observe(this@AddFoodActivity) {
                when (it) {
                    is ResultState.Success -> onBackPressed()
                    is ResultState.Progress -> addFoodButton.isEnabled = !it.isLoading
                    is ResultState.Error -> Toast.makeText(this@AddFoodActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUI() {

    }

    private fun loadFood() {

    }
}