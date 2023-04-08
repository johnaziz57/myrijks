package com.example.myrijks.ui.viewmodel

import androidx.lifecycle.ViewModel
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

    fun <T : Any> executeWithSchedulers(
        single: Single<T>,
        onSuccess: (T) -> (Unit),
        onError: (Throwable) -> (Unit)
    ): Disposable {
        val disposable = single
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onSuccess, onError)

        disposables.add(disposable)
        return disposable
    }
}
