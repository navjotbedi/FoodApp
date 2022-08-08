package com.toptal.calorie.feature.home.ui.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.toptal.calorie.feature.home.ui.databinding.ItemFoodBinding
import com.toptal.calorie.feature.home.ui.entity.Food

class FoodListAdapter : PagingDataAdapter<Food, FoodListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class ViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Food) {
            with(binding) {
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Food, newItem: Food) = oldItem == newItem
}