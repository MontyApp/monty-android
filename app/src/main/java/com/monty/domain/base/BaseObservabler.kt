package com.monty.domain.base

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseObservabler<T> {

    abstract fun create(): Observable<T>

    open fun execute(): Observable<T> {
        return create()
            .subscribeOn(Schedulers.io())
            .doOnError { e -> Timber.e(e) }
    }
}
