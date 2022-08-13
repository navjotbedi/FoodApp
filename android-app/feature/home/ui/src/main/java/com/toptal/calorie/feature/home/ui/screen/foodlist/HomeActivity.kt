package com.toptal.calorie.feature.home.ui.screen.foodlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toptal.calorie.core.utils.Constants.FOOD_INTENT
import com.toptal.calorie.core.utils.Constants.USER_ID_INTENT
import com.toptal.calorie.feature.home.ui.databinding.ActivityHomeBinding
import com.toptal.calorie.feature.home.ui.entity.FoodUIModel
import com.toptal.calorie.feature.home.ui.screen.addfood.AddFoodActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

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
    }

    override fun onResume() {
        super.onResume()
        loadFoodList()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRefresh.setOnRefreshListener { loadFoodList() }
            addFoodButton.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java))
            }
        }
        lifecycleScope.launch {
            viewModel.fetchFoodList()
                .collectLatest {
                    binding.swipeRefresh.isRefreshing = false
                    (binding.foodList.adapter as? FoodListAdapter)?.submitList(it)
                }
        }
    }

    private fun setupUI() {
        viewModel.storeUserId(intent.getStringExtra(USER_ID_INTENT))
        with(binding.foodList) {
            adapter = if (viewModel.isAdmin()) {
                FoodListAdapter { food ->
                    startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java).also {
                        it.putExtra(FOOD_INTENT, Json.encodeToString(FoodUIModel.serializer(), food))
                    })
                }
            } else FoodListAdapter()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun loadFoodList() {
        viewModel.saveFoodList()
    }

}