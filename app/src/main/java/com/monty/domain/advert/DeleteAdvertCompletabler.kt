package com.monty.domain.advert

import com.monty.data.store.AdvertsStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class DeleteAdvertCompletabler @Inject constructor(
    private val advertsStore: AdvertsStore
) : BaseCompletabler() {

    private var advertId: String = ""

    fun init(advertId: String) = apply {
        this.advertId = advertId
    }

    override fun create(): Completable {
        return advertsStore.deleteAdvert(advertId)
    }
}
