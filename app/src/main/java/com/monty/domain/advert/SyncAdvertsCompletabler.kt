package com.monty.domain.advert

import com.monty.data.store.AdvertsStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class SyncAdvertsCompletabler @Inject constructor(
    private val advertsStore: AdvertsStore
) : BaseCompletabler() {

    override fun create(): Completable {
        return advertsStore.syncAdverts()
    }
}
