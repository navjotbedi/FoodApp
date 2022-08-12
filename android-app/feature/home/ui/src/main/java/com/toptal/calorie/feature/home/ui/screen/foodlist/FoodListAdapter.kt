package com.toptal.calorie.feature.home.ui.screen.foodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.toptal.calorie.feature.home.ui.databinding.ItemFoodBinding
import com.toptal.calorie.feature.home.ui.databinding.ItemHeaderBinding
import com.toptal.calorie.feature.home.ui.entity.FoodUIModel
import com.toptal.calorie.feature.home.ui.entity.HeaderUIModel

class FoodListAdapter(private val listener: ((foodUIModel: FoodUIModel) -> Unit)? = null) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        const val FOOD_HEADER_VIEW = 0
        const val FOOD_ITEM_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        FOOD_ITEM_VIEW -> ViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        FOOD_HEADER_VIEW -> HeaderViewHolder(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else -> throw IllegalArgumentException("Invalid view type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (holder) {
                is ViewHolder -> holder.bind(it as FoodUIModel)
                is HeaderViewHolder -> holder.bind(it as HeaderUIModel)
            }
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is FoodUIModel -> FOOD_ITEM_VIEW
        is HeaderUIModel -> FOOD_HEADER_VIEW
        else -> throw IllegalArgumentException("Invalid view type")
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(headerUIModel: HeaderUIModel) {
            with(binding) {
                binding.headerUIModel = headerUIModel
                executePendingBindings()
            }
        }
    }

    inner class ViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var foodUIModel: FoodUIModel

        init {
            listener?.let {
                binding.root.setOnClickListener {
                    listener.invoke(foodUIModel)
                }
            }
        }

        fun bind(foodUIModel: FoodUIModel) {
            with(binding) {
                this@ViewHolder.foodUIModel = foodUIModel
                foodModel = foodUIModel
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Any, newItem: Any) = true
}