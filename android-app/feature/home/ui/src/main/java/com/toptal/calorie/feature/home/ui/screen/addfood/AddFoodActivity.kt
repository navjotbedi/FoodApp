package com.toptal.calorie.feature.home.ui.screen.addfood

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.toptal.calorie.core.utils.Constants.FOOD_CALORIE_INTENT
import com.toptal.calorie.core.utils.Constants.FOOD_ID_INTENT
import com.toptal.calorie.core.utils.Constants.FOOD_NAME_INTENT
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
            addFoodButton.setOnClickListener { viewModel.saveFood(nameEditText.text.toString(), calorieEditText.text.toString()) }
            deleteFoodButton.setOnClickListener { viewModel.deleteFood() }

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
        with(intent) {
            viewModel.storeFoodId(getStringExtra(FOOD_ID_INTENT))
            getStringExtra(FOOD_NAME_INTENT)?.let { binding.nameEditText.setText(it) }
            getStringExtra(FOOD_CALORIE_INTENT)?.let { binding.calorieEditText.setText(it) }
            binding.addFoodButton.text = if (viewModel.isAdmin()) "Update Food" else "Add Food"
            if (viewModel.isAdmin()) binding.deleteFoodButton.visibility = View.VISIBLE
        }
    }

    private fun loadFood() {

    }
}