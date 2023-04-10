package com.example.myrijks.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrijks.ui.model.ResultStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel(schedulerProvider: SchedulerProvider) : ViewModel() {

    val resultStatusLiveData: LiveData<ResultStatus>
        get() = _resultStatus

    private val disposables by lazy { CompositeDisposable() }

    private val ioScheduler = schedulerProvider.io()
    private val uiScheduler = schedulerProvider.ui()

    private val _resultStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.DEFAULT)

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun <T : Any> execute(
        single: Single<T>,
        onSuccess: (T) -> (Unit),
        onError: (Throwable) -> (Unit)
    ): Disposable {
        _resultStatus.value = ResultStatus.LOADING
        val disposable = single
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    onSuccess(it)
                    _resultStatus.value = ResultStatus.SUCCESS
                },
                {
                    onError(it)
                    _resultStatus.value = ResultStatus.ERROR
                }
            )

        disposables.add(disposable)
        return disposable
    }
}
