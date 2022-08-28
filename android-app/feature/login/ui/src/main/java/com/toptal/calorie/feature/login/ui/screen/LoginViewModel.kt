package com.toptal.calorie.feature.login.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.core.utils.USER_ROLE
import com.toptal.calorie.feature.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    var userToken by mutableStateOf("")
    var isLoginEnable by mutableStateOf(false)

    private val _performLogin = MutableLiveData<ResultState<USER_ROLE>>()
    val performLogin: LiveData<ResultState<USER_ROLE>> = _performLogin

    fun login() {
        viewModelScope.launch {
            (loginUseCase.login(userToken)
                .map { ResultState.Success(it) } as Flow<ResultState<USER_ROLE>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { isLoginEnable = false }
                .onCompletion { isLoginEnable = true }
                .conflate()
                .collect { _performLogin.value = it }
        }
    }

}