package com.monty.domain.advert

import com.monty.data.model.ui.Advert
import com.monty.data.store.AdvertsStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AddAdvertCompletabler @Inject constructor(
    private val advertsStore: AdvertsStore
) : BaseCompletabler() {

    private var advert: Advert = Advert.EMPTY

    fun init(advert: Advert) = apply {
        this.advert = advert
    }

    override fun create(): Completable {
        // TODO remove relay
        return advertsStore.addAdvert(advert)
            .delay(2, TimeUnit.SECONDS)
    }
}
