package com.monty.tool.rx

import io.reactivex.Observable
import io.reactivex.functions.Function6

object Rx {
    fun <T1, T2, T3, T4, T5, T6> observableCombineLatestSixfold(
        observable1: Observable<T1>,
        observable2: Observable<T2>,
        observable3: Observable<T3>,
        observable4: Observable<T4>,
        observable5: Observable<T5>,
        observable6: Observable<T6>
    ): Observable<Sixfold<T1, T2, T3, T4, T5, T6>> {
        return Observable.combineLatest(
            observable1,
            observable2,
            observable3,
            observable4,
            observable5,
            observable6,
            Function6 { t1, t2, t3, t4, t5, t6 -> Sixfold(t1, t2, t3, t4, t5, t6) }
        )
    }
}