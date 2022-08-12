package com.toptal.calorie.feature.admin.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.domain.usecase.AdminUseCase
import com.toptal.calorie.feature.admin.ui.entity.User
import com.toptal.calorie.feature.admin.ui.entity.mapper.UserUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val adminUseCase: AdminUseCase,
    private val mapper: UserUIMapper
) : ViewModel() {

    private val _userList = MutableLiveData<ResultState<List<User>>>()
    val userList: LiveData<ResultState<List<User>>> = _userList

    fun fetchUserList() {
        viewModelScope.launch {
            (adminUseCase.fetchUsers()
                .map { it.map { userDomainModel -> mapper.map(userDomainModel) } }
                .map { ResultState.Success(it) } as Flow<ResultState<List<User>>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _userList.value = it }
        }
    }

}