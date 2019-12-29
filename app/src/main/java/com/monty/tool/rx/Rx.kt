package com.monty.tool.rx

import io.reactivex.Observable
import io.reactivex.functions.Function4

object Rx {
    fun <T1, T2, T3, T4> observableCombineLatestTriple(
        observable1: Observable<T1>,
        observable2: Observable<T2>,
        observable3: Observable<T3>,
        observable4: Observable<T4>
    ): Observable<FourFold<T1, T2, T3, T4>> {
        return Observable.combineLatest(
            observable1,
            observable2,
            observable3,
            observable4,
            Function4 { t1, t2, t3, t4 -> FourFold(t1, t2, t3, t4) }
        )
    }
}