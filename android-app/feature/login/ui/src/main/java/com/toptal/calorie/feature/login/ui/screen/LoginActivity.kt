package com.toptal.calorie.feature.login.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.core.utils.USER_ROLE
import com.toptal.calorie.feature.login.ui.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            loginButton.setOnClickListener {
                viewModel.login(usernameEdittext.text.toString())
            }

            usernameEdittext.addTextChangedListener {
                loginButton.isEnabled = it?.isNotBlank() ?: false
            }

            viewModel.performLogin.observe(this@LoginActivity) {
                when (it) {
                    is ResultState.Success -> it.data?.let { userRole -> performNavigation(userRole) }
                    is ResultState.Progress -> loginButton.isEnabled = !it.isLoading
                    is ResultState.Error -> Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                }
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

    private fun setupUI() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}