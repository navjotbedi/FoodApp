package com.toptal.calorie.feature.admin.ui.screen.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.toptal.calorie.core.android.theme.CalorieAppTheme
import com.toptal.calorie.core.utils.Constants.USER_ID_INTENT
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.ui.R
import com.toptal.calorie.feature.admin.ui.entity.User
import com.toptal.calorie.feature.admin.ui.screen.report.ReportActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : ComponentActivity() {

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieAppTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text(text = stringResource(R.string.admin_dashboard_title)) }) },
                    content = {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            val userListResult: ResultState<List<User>>? by viewModel.userList.observeAsState()
                            userListResult?.let {
                                UserListScreen((userListResult as? ResultState.Success)?.data)
                                (userListResult as? ResultState.Error)?.let { Toast.makeText(this@UserListActivity, it.message, Toast.LENGTH_SHORT).show() }
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            startActivity(Intent(this@UserListActivity, ReportActivity::class.java))
                        }) {
                            Icon(ImageVector.vectorResource(R.drawable.ic_report), "Report")
                        }
                    }
                )
            }
        }
        viewModel.fetchUserList()
    }

    @Composable
    private fun UserItemView(userUIModel: User) {
        Box(
            Modifier
                .fillMaxWidth()
                .clickable {
                    startActivity(Intent(this@UserListActivity, Class.forName("com.toptal.calorie.feature.food.ui.screen.foodlist.HomeActivity"))
                        .apply { putExtra(USER_ID_INTENT, userUIModel.id) })
                }
                .padding(10.dp)
        ) {
            Text(text = userUIModel.name)
        }
    }

    @Composable
    private fun UserListScreen(userList: List<User>?) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.isLoading),
            onRefresh = { viewModel.fetchUserList() },
        ) {
            userList?.let {
                LazyColumn {
                    items(it) { userItem ->
                        UserItemView(userItem)
                        Divider()
                    }
                }
            }
        }
    }
}