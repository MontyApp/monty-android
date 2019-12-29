package com.monty.domain.advert

import com.monty.data.model.ui.type.IntervalType
import com.monty.data.model.ui.type.Intervals
import com.monty.domain.base.BaseSingler
import io.reactivex.Single
import javax.inject.Inject

class GetPriceIntervalsSingler @Inject constructor() : BaseSingler<List<IntervalType>>() {

    override fun create(): Single<List<IntervalType>> {
        return Single.just(Intervals.list)
    }
}
