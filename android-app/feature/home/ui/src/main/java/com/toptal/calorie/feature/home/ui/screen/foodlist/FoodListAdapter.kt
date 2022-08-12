package com.toptal.calorie.feature.home.ui.screen.foodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toptal.calorie.feature.home.ui.databinding.ItemFoodBinding
import com.toptal.calorie.feature.home.ui.entity.Food

class FoodListAdapter(private val listener: ((food: Food) -> Unit)? = null) : ListAdapter<Food, FoodListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            with(binding) {
                foodModel = food
                listener?.let {
                    root.setOnClickListener {
                        listener.invoke(food)
                    }
                }
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Food, newItem: Food) = oldItem == newItem
}