package com.example.myrijks.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrijks.ui.model.ResultStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel(schedulerProvider: SchedulerProvider) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }

    private val ioScheduler = schedulerProvider.io()
    private val uiScheduler = schedulerProvider.ui()


    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun <T : Any> execute(
        single: Single<T>,
        onSuccess: (T) -> (Unit),
        onError: (Throwable) -> (Unit),
        resultStatusLiveData: MutableLiveData<ResultStatus>? = null
    ): Disposable {
        resultStatusLiveData?.value = ResultStatus.LOADING
        val disposable = single
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    onSuccess(it)
                    resultStatusLiveData?.value = ResultStatus.SUCCESS
                },
                {
                    onError(it)
                    resultStatusLiveData?.value = ResultStatus.ERROR
                }
            )

        disposables.add(disposable)
        return disposable
    }
}
