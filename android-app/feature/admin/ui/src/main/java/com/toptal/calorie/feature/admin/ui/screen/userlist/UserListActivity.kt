package com.toptal.calorie.feature.admin.ui.screen.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toptal.calorie.core.utils.Constants.USER_ID_INTENT
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.ui.databinding.ActivityUserListBinding
import com.toptal.calorie.feature.admin.ui.screen.report.ReportActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        loadUserList()
    }

    private fun setupListeners() {
        viewModel.userList.observe(this) {
            when (it) {
                is ResultState.Success -> (binding.userList.adapter as? UserListAdapter)?.submitList(it.data)
                is ResultState.Progress -> binding.swipeRefresh.isRefreshing = it.isLoading
                is ResultState.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        with(binding) {
            swipeRefresh.setOnRefreshListener {
                loadUserList()
            }

            adminOptionButton.setOnClickListener {
                startActivity(Intent(this@UserListActivity, ReportActivity::class.java))
            }
        }
    }

    private fun setupUI() {
        title = "Admin Dashboard"
        with(binding.userList) {
            adapter = UserListAdapter { userId ->
                startActivity(Intent(this@UserListActivity, Class.forName("com.toptal.calorie.feature.home.ui.screen.foodlist.HomeActivity"))
                    .also { it.putExtra(USER_ID_INTENT, userId) })
            }
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun loadUserList() = viewModel.fetchUserList()
}