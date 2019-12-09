package com.monty.domain.advert

import com.monty.data.model.ui.Advert
import com.monty.data.store.AdvertsStore
import com.monty.domain.base.BaseObservabler
import io.reactivex.Observable
import javax.inject.Inject

class GetAdvertsObservabler @Inject constructor(
    private val advertsStore: AdvertsStore
) : BaseObservabler<List<Advert>>() {

    override fun create(): Observable<List<Advert>> {
        return advertsStore.getAdverts()
    }
}
