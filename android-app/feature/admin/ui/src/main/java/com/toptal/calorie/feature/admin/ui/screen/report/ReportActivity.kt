package com.toptal.calorie.feature.admin.ui.screen.report

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.ui.databinding.ActivityReportBinding
import com.toptal.calorie.feature.admin.ui.screen.userlist.UserListAdapter
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
            foodReport.observe(this@ReportActivity) {
                when (it) {
                    is ResultState.Success -> binding.foodReportModel = it.data
                    is ResultState.Progress -> {

                    }
                    is ResultState.Error -> Toast.makeText(this@ReportActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            userList.observe(this@ReportActivity) {
                when (it) {
                    is ResultState.Success -> (binding.userList.adapter as? UserListAdapter)?.submitList(it.data)
                    is ResultState.Progress -> {

                    }
                    is ResultState.Error -> Toast.makeText(this@ReportActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUI() {
        title = "Report"
    }

    private fun loadData() {
        viewModel.fetchFoodReport()
    }
}