package com.toptal.calorie.feature.admin.ui.screen.report

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.ui.databinding.ActivityReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding
    private val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        loadData()
    }

    private fun setupListeners() {
        with(viewModel) {
            report.observe(this@ReportActivity) {
                when (it) {
                    is ResultState.Success -> {
                        it.data?.let { result ->
                            binding.foodReportModel = result.first
                            (binding.userList.adapter as? AvgCaloriePerUserReportListAdapter)?.submitList(result.second)
                        }
                    }
                    is ResultState.Progress -> {

                    }
                    is ResultState.Error -> Toast.makeText(this@ReportActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUI() {
        title = "Report"
        with(binding.userList) {
            adapter = AvgCaloriePerUserReportListAdapter()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun loadData() {
        viewModel.fetchFoodReport()
    }
}