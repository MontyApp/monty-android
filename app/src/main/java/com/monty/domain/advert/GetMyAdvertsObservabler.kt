package com.monty.domain.advert

import com.monty.data.model.ui.Advert
import com.monty.data.store.AdvertsStore
import com.monty.data.store.AuthStore
import com.monty.domain.base.BaseObservabler
import io.reactivex.Observable
import javax.inject.Inject

class GetMyAdvertsObservabler @Inject constructor(
    private val advertsStore: AdvertsStore,
    private val authStore: AuthStore
) : BaseObservabler<List<Advert>>() {

    override fun create(): Observable<List<Advert>> {
        return authStore.getUser()
            .flatMap { advertsStore.getMyAdverts(it.id) }
    }
}
