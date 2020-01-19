package com.monty.domain.advert

import com.monty.data.model.ui.Advert
import com.monty.data.store.AdvertsStore
import com.monty.domain.base.BaseObservabler
import io.reactivex.Observable
import javax.inject.Inject

class GetAdvertObservabler @Inject constructor(
    private val advertsStore: AdvertsStore
) : BaseObservabler<Advert>() {

    private var advertId: String = ""

    fun init(advertId: String) = apply {
        this.advertId = advertId
    }

    override fun create(): Observable<Advert> {
        return advertsStore.getAdvert(advertId)
    }
}
