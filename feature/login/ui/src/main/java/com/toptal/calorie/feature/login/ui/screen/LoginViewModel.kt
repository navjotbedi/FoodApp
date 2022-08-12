package com.toptal.calorie.feature.login.ui.screen

import androidx.lifecycle.ViewModel
import com.toptal.calorie.feature.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(foodCase: LoginUseCase) : ViewModel()