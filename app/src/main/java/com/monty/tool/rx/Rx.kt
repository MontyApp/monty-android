package com.monty.tool.rx

import io.reactivex.Observable
import io.reactivex.functions.Function7

object Rx {
    fun <T1, T2, T3, T4, T5, T6, T7> observableCombineLatestSevenfold(
        observable1: Observable<T1>,
        observable2: Observable<T2>,
        observable3: Observable<T3>,
        observable4: Observable<T4>,
        observable5: Observable<T5>,
        observable6: Observable<T6>,
        observable7: Observable<T7>
    ): Observable<SevenFold<T1, T2, T3, T4, T5, T6, T7>> {
        return Observable.combineLatest(
            observable1,
            observable2,
            observable3,
            observable4,
            observable5,
            observable6,
            observable7,
            Function7 { t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6, t7: T7 ->
                SevenFold(t1, t2, t3, t4, t5, t6, t7)
            }
        )
    }
}