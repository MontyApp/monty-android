package com.monty.domain.base

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseCompletabler {

    abstract fun create(): Completable

    open fun execute(): Completable {
        return create()
            .subscribeOn(Schedulers.io())
            .doOnError { e -> Timber.e(e) }
    }
}
