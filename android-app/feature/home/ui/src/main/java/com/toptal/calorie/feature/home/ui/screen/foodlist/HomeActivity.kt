package com.toptal.calorie.feature.home.ui.screen.foodlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toptal.calorie.core.utils.Constants.FOOD_CALORIE_INTENT
import com.toptal.calorie.core.utils.Constants.FOOD_ID_INTENT
import com.toptal.calorie.core.utils.Constants.FOOD_NAME_INTENT
import com.toptal.calorie.core.utils.Constants.USER_ID_INTENT
import com.toptal.calorie.feature.home.ui.databinding.ActivityHomeBinding
import com.toptal.calorie.feature.home.ui.screen.addfood.AddFoodActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        loadFoodList()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRefresh.setOnRefreshListener { loadFoodList() }
            addFoodButton.setOnClickListener { startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java)) }
        }
    }

    private fun setupUI() {
        viewModel.storeUserId(intent.getStringExtra(USER_ID_INTENT))
        with(binding.foodList) {
            adapter = if (viewModel.isAdmin()) {
                FoodListAdapter { food ->
                    startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java).also {
                        it.putExtra(FOOD_ID_INTENT, food.id)
                        it.putExtra(FOOD_NAME_INTENT, food.name)
                        it.putExtra(FOOD_CALORIE_INTENT, food.calorie)
                    })
                }
            } else FoodListAdapter()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun loadFoodList() {
        viewModel.saveFoodList()
        lifecycleScope.launch {
            viewModel.fetchFoodList()
                .distinctUntilChanged()
                .collectLatest {
                    (binding.foodList.adapter as? FoodListAdapter)?.submitData(it)
                }
        }
    }

}