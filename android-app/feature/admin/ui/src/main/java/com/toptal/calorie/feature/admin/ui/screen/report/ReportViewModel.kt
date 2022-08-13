package com.toptal.calorie.feature.admin.ui.screen.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.admin.domain.usecase.AdminUseCase
import com.toptal.calorie.feature.admin.ui.entity.AvgCaloriePerUserUIModel
import com.toptal.calorie.feature.admin.ui.entity.FoodReportUIModel
import com.toptal.calorie.feature.admin.ui.entity.mapper.AvgCaloriePerUserUIMapper
import com.toptal.calorie.feature.admin.ui.entity.mapper.FoodReportUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val adminUseCase: AdminUseCase,
    private val foodReportUIMapper: FoodReportUIMapper,
    private val avgCaloriePerUserUIMapper: AvgCaloriePerUserUIMapper
) : ViewModel() {

    private val _report = MutableLiveData<ResultState<Pair<FoodReportUIModel, List<AvgCaloriePerUserUIModel>>>>()
    val report: LiveData<ResultState<Pair<FoodReportUIModel, List<AvgCaloriePerUserUIModel>>>> = _report

    fun fetchFoodReport() {
        viewModelScope.launch {
            (adminUseCase.fetchReport()
                .map { report -> Pair(foodReportUIMapper.map(report.first), report.second.map { avgCaloriePerUserUIMapper.map(it) }) }
                .map { ResultState.Success(it) } as Flow<ResultState<Pair<FoodReportUIModel, List<AvgCaloriePerUserUIModel>>>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _report.value = it }
        }
    }
}