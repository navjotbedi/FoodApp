package com.toptal.calorie.feature.admin.ui.screen.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toptal.calorie.feature.admin.ui.databinding.ItemUserBinding
import com.toptal.calorie.feature.admin.ui.entity.User

class UserListAdapter(private val listener: ((userId: String) -> Unit)? = null) : ListAdapter<User, UserListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                userModel = user
                listener?.let {
                    root.setOnClickListener {
                        listener.invoke(user.id)
                    }
                }
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}