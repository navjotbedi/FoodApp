package com.toptal.calorie.feature.home.ui.screen

import androidx.lifecycle.ViewModel
import com.toptal.calorie.feature.home.domain.usecase.FoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(foodCase: FoodUseCase) : ViewModel()