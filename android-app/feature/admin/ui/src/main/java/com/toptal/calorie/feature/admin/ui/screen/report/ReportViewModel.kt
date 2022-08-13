package com.toptal.calorie.feature.admin.ui.screen.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.domain.usecase.AdminUseCase
import com.toptal.calorie.feature.admin.ui.entity.FoodReportUIModel
import com.toptal.calorie.feature.admin.ui.entity.User
import com.toptal.calorie.feature.admin.ui.entity.mapper.FoodReportUIMapper
import com.toptal.calorie.feature.admin.ui.entity.mapper.UserUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val adminUseCase: AdminUseCase,
    private val userUIMapper: UserUIMapper,
    private val foodReportUIMapper: FoodReportUIMapper
) : ViewModel() {

    private val _userList = MutableLiveData<ResultState<List<User>>>()
    val userList: LiveData<ResultState<List<User>>> = _userList

    private val _foodReport = MutableLiveData<ResultState<FoodReportUIModel>>()
    val foodReport: LiveData<ResultState<FoodReportUIModel>> = _foodReport

    fun fetchUserList() {
        viewModelScope.launch {
            (adminUseCase.fetchUsers()
                .map { it.map { userDomainModel -> userUIMapper.map(userDomainModel) } }
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

    fun fetchFoodReport() {
        viewModelScope.launch {
            (adminUseCase.fetchFoodReport()
                .map { foodReportUIMapper.map(it) }
                .map { ResultState.Success(it) } as Flow<ResultState<FoodReportUIModel>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _foodReport.value = it }
        }
    }
}