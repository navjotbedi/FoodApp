package com.toptal.calorie.feature.food.ui.screen.foodlist

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.toptal.calorie.core.android.theme.CalorieAppTheme
import com.toptal.calorie.core.utils.Constants.FOOD_INTENT
import com.toptal.calorie.core.utils.Constants.USER_ID_INTENT
import com.toptal.calorie.feature.food.ui.R
import com.toptal.calorie.feature.food.ui.entity.FoodUIModel
import com.toptal.calorie.feature.food.ui.entity.HeaderUIModel
import com.toptal.calorie.feature.food.ui.screen.addfood.AddFoodActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import java.util.*

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieAppTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text(text = stringResource(R.string.food_list_title)) }) },
                    content = { Surface(modifier = Modifier.fillMaxSize()) { FoodListScreen() } }
                )
            }
        }
    }

    @Composable
    private fun FoodItemView(foodUIModel: FoodUIModel) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable(enabled = viewModel.isAdmin()) {
                    if (viewModel.isAdmin()) {
                        startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java).apply {
                            putExtra(FOOD_INTENT, Json.encodeToString(FoodUIModel.serializer(), foodUIModel))
                        })
                    }
                }
                .padding(10.dp)) {
            Text(text = "Food: ${foodUIModel.name}")
            Text(text = "Calorie: ${foodUIModel.calorie}")
        }
    }

    @Composable
    private fun HeaderItemView(headerUIModel: HeaderUIModel) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Text(text = headerUIModel.displayDate)
            Spacer(Modifier.weight(1f))
            Text(text = "Avg Cal: ${headerUIModel.averageCalorie}")
        }
    }

    @Composable
    private fun FoodListScreen() {
        val foodList by viewModel.foodList.observeAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.isLoading),
            onRefresh = { viewModel.saveFoodList() },
        ) {
            foodList?.let {
                LazyColumn {
                    itemsIndexed(it) { index, foodItem ->
                        when (foodItem) {
                            is HeaderUIModel -> HeaderItemView(foodItem)
                            is FoodUIModel -> FoodItemView(foodItem)
                        }
                        if (index < it.lastIndex) {
                            Divider()
                        }
                    }
                }
            }
        }

        addActionButtons()
    }

    @Composable
    private fun addActionButtons() {
        Box(Modifier.padding(15.dp)) {
            if (viewModel.isAdmin()) {
                FloatingActionButton(modifier = Modifier.align(Alignment.BottomStart), onClick = {
                    if (viewModel.isDateFilterApplied) viewModel.fetchFoodList()
                    else showFilterPopup()
                }) {
                    Icon(ImageVector.vectorResource(if (viewModel.isDateFilterApplied) R.drawable.ic_close else R.drawable.ic_filter), "Filter")
                }
            }
            if (!viewModel.isDateFilterApplied) {
                FloatingActionButton(modifier = Modifier.align(Alignment.BottomEnd), onClick = {
                    startActivity(Intent(this@HomeActivity, AddFoodActivity::class.java).apply {
                        putExtra(USER_ID_INTENT, viewModel.userId)
                    })
                }) { Icon(Icons.Filled.Add, "Add Food") }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFoodList()
        viewModel.saveFoodList()
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
                    viewModel.fetchFoodList(startDate, cal.apply {
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
}