package com.example.myrijks.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrijks.domain.model.error.ErrorEntity
import com.example.myrijks.domain.util.Result
import com.example.myrijks.ui.model.ResultStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch

abstract class BaseViewModel(schedulerProvider: SchedulerProvider) : ViewModel() {

    val errorLiveData: LiveData<ErrorEntity?>
        get() = _error
    val resultStatusLiveData: LiveData<ResultStatus>
        get() = _resultStatus

    private val _error: MutableLiveData<ErrorEntity?> = MutableLiveData()
    private val _resultStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.DEFAULT)

    private val disposables by lazy { CompositeDisposable() }
    private val ioScheduler = schedulerProvider.io()

    private val uiScheduler = schedulerProvider.ui()
    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun <T> execute(
        coroutineCall: suspend () -> Result<T>,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch {
            _resultStatus.value = ResultStatus.LOADING
            when (val result = coroutineCall.invoke()) {
                is Result.Success -> {
                    onSuccess(result.data)
                    _resultStatus.value = ResultStatus.SUCCESS
                    _error.value = null
                }

                is Result.Error -> {
                    handleError(result.error)
                }
            }
        }
    }

    fun <T : Any> execute(
        single: Single<Result<T>>,
        onSuccess: (T) -> (Unit)
    ): Disposable {
        _resultStatus.value = ResultStatus.LOADING
        val disposable = single
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    when (it) {
                        is Result.Success -> {
                            onSuccess(it.data)
                            _resultStatus.value = ResultStatus.SUCCESS
                            _error.value = null
                        }

                        is Result.Error -> {
                            handleError(it.error)
                        }
                    }
                },
                {
                    handleError()
                }
            )

        disposables.add(disposable)
        return disposable
    }

    private fun handleError(errorEntity: ErrorEntity = ErrorEntity.Unknown) {
        _error.value = errorEntity
        _resultStatus.value = ResultStatus.ERROR
    }
}
