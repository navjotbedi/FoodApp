package com.toptal.calorie.feature.home.ui.screen.addfood

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.toptal.calorie.core.utils.Constants
import com.toptal.calorie.core.utils.Constants.FOOD_INTENT
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.home.ui.databinding.ActivityAddFoodBinding
import com.toptal.calorie.feature.home.ui.entity.FoodUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

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
            addFoodButton.setOnClickListener { viewModel.saveFood(nameEditText.text.toString(), calorieEditText.text.toString(), intent.getStringExtra(Constants.USER_ID_INTENT)) }
            deleteFoodButton.setOnClickListener { viewModel.deleteFood() }

            nameEditText.addTextChangedListener {
                it?.let { text -> addFoodButton.isEnabled = text.isNotEmpty() && calorieEditText.text.isNotEmpty() }
            }

            calorieEditText.addTextChangedListener {
                it?.let { text -> addFoodButton.isEnabled = text.isNotEmpty() && nameEditText.text.isNotEmpty() }
            }

            viewModel.addFood.observe(this@AddFoodActivity) {
                when (it) {
                    is ResultState.Success -> onBackPressed()
                    is ResultState.Progress -> {
                        addFoodButton.isEnabled = !it.isLoading
                        deleteFoodButton.isEnabled = !it.isLoading
                    }
                    is ResultState.Error -> Toast.makeText(this@AddFoodActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUI() {
        title = "Add Food"
        with(intent) {
            val foodUIModel = getStringExtra(FOOD_INTENT)?.let { Json.decodeFromString(FoodUIModel.serializer(), it) }
            foodUIModel?.let {
                viewModel.storeFoodId(foodUIModel.id)
                binding.foodModel = foodUIModel
            }
            binding.addFoodButton.text = if (viewModel.isAdmin()) "Update Food" else "Add Food"
            if (viewModel.isAdmin()) binding.deleteFoodButton.visibility = View.VISIBLE
        }
    }

    private fun loadFood() {

    }
}