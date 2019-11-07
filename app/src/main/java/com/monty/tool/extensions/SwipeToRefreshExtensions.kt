package com.monty.tool.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Observable

fun SwipeRefreshLayout.onRefresh(): Observable<Unit> {
    return Observable.create {
        this.setOnRefreshListener {
            if (it.isDisposed.not()) {
                it.onNext(Unit)
            }
        }
    }
}
