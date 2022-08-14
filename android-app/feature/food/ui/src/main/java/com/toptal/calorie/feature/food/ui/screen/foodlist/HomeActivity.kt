package com.toptal.calorie.feature.food.ui.screen.foodlist

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toptal.calorie.core.utils.Constants.FOOD_INTENT
import com.toptal.calorie.core.utils.Constants.USER_ID_INTENT
import com.toptal.calorie.feature.food.ui.R
import com.toptal.calorie.feature.food.ui.databinding.ActivityHomeBinding
import com.toptal.calorie.feature.food.ui.entity.FoodUIModel
import com.toptal.calorie.feature.food.ui.screen.addfood.AddFoodActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var fetchFoodTask: Job? = null

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
                startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java).apply {
                    putExtra(USER_ID_INTENT, viewModel.userId)
                })
            }
            addDateFilterButton.setOnClickListener {
                viewModel.isDateFilterApplied.value?.let { idFilterEnable ->
                    if (idFilterEnable) fetchFoodList()
                    else showFilterPopup()
                }
            }
            viewModel.isDateFilterApplied.observe(this@HomeActivity) {
                addDateFilterButton.setImageResource(if (it) R.drawable.ic_close else R.drawable.ic_filter)
                addFoodButton.visibility = if (it) View.GONE else View.VISIBLE
            }
        }

        fetchFoodList()
    }

    private fun fetchFoodList(startDate: Date? = null, endDate: Date? = null) {
        fetchFoodTask?.cancel()
        fetchFoodTask = lifecycleScope.launch {
            viewModel.fetchFoodList(startDate, endDate)
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
                    startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java).apply {
                        putExtra(FOOD_INTENT, Json.encodeToString(FoodUIModel.serializer(), food))
                    })
                }
            } else FoodListAdapter()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun showFilterPopup() {
        val cal = Calendar.getInstance()
        var startDate: Date?
        DatePickerDialog(this@HomeActivity, { _, year, month, dayOfMonth ->
            cal.set(year, month, dayOfMonth)
            startDate = cal.apply {
                set(year, month, dayOfMonth)
                set(Calendar.MINUTE, 0)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.SECOND, 0)
            }.time
            DatePickerDialog(
                this@HomeActivity, { _, y, m, d ->
                    fetchFoodList(startDate, cal.apply {
                        set(y, m, d)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.SECOND, 0)
                        add(Calendar.DATE, 1)
                    }.time)
                }, year, month, dayOfMonth
            ).apply { setTitle("End Date") }.show()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).apply { setTitle("Start Date") }.show()
    }

    private fun loadFoodList() {
        viewModel.saveFoodList()
    }

}