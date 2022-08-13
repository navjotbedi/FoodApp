package com.toptal.calorie.feature.admin.ui.screen.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toptal.calorie.feature.admin.ui.databinding.ItemUserReportBinding
import com.toptal.calorie.feature.admin.ui.entity.AvgCaloriePerUserUIModel

class AvgCaloriePerUserReportListAdapter : ListAdapter<AvgCaloriePerUserUIModel, AvgCaloriePerUserReportListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUserReportBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemUserReportBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: AvgCaloriePerUserUIModel) {
            with(binding) {
                binding.viewModel = viewModel
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<AvgCaloriePerUserUIModel>() {
    override fun areItemsTheSame(oldItem: AvgCaloriePerUserUIModel, newItem: AvgCaloriePerUserUIModel) = oldItem == newItem
    override fun areContentsTheSame(oldItem: AvgCaloriePerUserUIModel, newItem: AvgCaloriePerUserUIModel) = oldItem == newItem
}