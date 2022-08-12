package com.toptal.calorie.feature.login.ui.screen

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

    private val _currentUserType = MutableLiveData<ResultState<USER_ROLE>>()
    val currentUserType: LiveData<ResultState<USER_ROLE>> = _currentUserType

    private val _performLogin = MutableLiveData<ResultState<USER_ROLE>>()
    val performLogin: LiveData<ResultState<USER_ROLE>> = _performLogin

    fun login(username: String) {
        viewModelScope.launch {
            (loginUseCase.login(username)
                .map { ResultState.Success(it) } as Flow<ResultState<USER_ROLE>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _performLogin.value = it }
        }
    }

    fun fetchLoggedInUserType() {
        viewModelScope.launch {
            (loginUseCase.getLoggedInUserType()
                .map { ResultState.Success(it) } as Flow<ResultState<USER_ROLE>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _currentUserType.value = it }
        }
    }

}