package com.toptal.calorie.feature.admin.ui.screen.report

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toptal.calorie.core.android.theme.CalorieAppTheme
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.ui.R
import com.toptal.calorie.feature.admin.ui.entity.AvgCaloriePerUserUIModel
import com.toptal.calorie.feature.admin.ui.entity.FoodReportUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : ComponentActivity() {

    private val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalorieAppTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text(text = stringResource(R.string.report_title)) }) },
                    content = {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            val reportResult: ResultState<Pair<FoodReportUIModel, List<AvgCaloriePerUserUIModel>>>? by viewModel.report.observeAsState()
                            reportResult?.let {
                                ReportScreen((reportResult as? ResultState.Success)?.data)
                                (reportResult as? ResultState.Error)?.let { Toast.makeText(this@ReportActivity, it.message, Toast.LENGTH_SHORT).show() }
                            }
                        }
                    }
                )
            }
        }
        viewModel.fetchFoodReport()
    }

    @Composable
    private fun ReportScreen(report: Pair<FoodReportUIModel, List<AvgCaloriePerUserUIModel>>?) {
        report?.let {
            Column {
                Text(stringResource(R.string.food_entries_text), modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text("Current Week: ${report.first.currentWeekCalorieAvg}")
                    Spacer(Modifier.weight(1f))
                    Text("Last Week: ${report.first.lastWeekCalorieAvg}")
                }

                Text(stringResource(R.string.avg_calorie_text), modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 15.dp), fontWeight = FontWeight.Bold)

                LazyColumn {
                    items(report.second) {
                        AvgCaloriePerUserItemView(it)
                        Divider()
                    }
                }
            }
        }
    }

    @Composable
    private fun AvgCaloriePerUserItemView(avgCaloriePerUserUIModel: AvgCaloriePerUserUIModel) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(avgCaloriePerUserUIModel.name)
            Spacer(Modifier.weight(1f))
            Text(avgCaloriePerUserUIModel.calorie)
        }

    }
}