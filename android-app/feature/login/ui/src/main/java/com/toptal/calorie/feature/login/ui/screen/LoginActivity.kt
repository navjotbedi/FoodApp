package com.toptal.calorie.feature.login.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toptal.calorie.core.android.theme.CalorieAppTheme
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.core.utils.USER_ROLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieAppTheme {
                TopAppBar(
                    title = { Text(text = "Calorie App") }
                )

                val result: ResultState<USER_ROLE>? by viewModel.performLogin.observeAsState(null)
                result?.let {
                    when (it) {
                        is ResultState.Success -> it.data?.let { userRole -> performNavigation(userRole) }
                        else -> LoginScreen()
                    }
                    if (it is ResultState.Error) Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                } ?: LoginScreen()
            }
        }
    }

    @Composable
    private fun LoginScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.userToken,
                onValueChange = {
                    viewModel.isLoginEnable = it.isNotBlank()
                    viewModel.userToken = it
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text("User Token") },
                modifier = Modifier.fillMaxWidth(fraction = 0.9f)
            )
            Spacer(Modifier.height(5.dp))
            Button(onClick = { viewModel.login() }, enabled = viewModel.isLoginEnable) {
                Text(
                    text = "Login",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(fraction = 0.9f)
                )
            }
        }
    }

    private fun performNavigation(userRole: USER_ROLE) {
        startActivity(
            Intent(
                this@LoginActivity, Class.forName(
                    when (userRole) {
                        USER_ROLE.USER -> "com.toptal.calorie.feature.food.ui.screen.foodlist.HomeActivity"
                        USER_ROLE.ADMIN -> "com.toptal.calorie.feature.admin.ui.screen.userlist.UserListActivity"
                    }
                )
            )
        )
        finish()
    }
}