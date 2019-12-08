package com.monty.data.store

import com.monty.data.database.AppDatabase
import com.monty.data.model.ui.Advert
import com.monty.domain.adverts
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdvertsStore @Inject constructor(
    private val database: AppDatabase
) {

    fun syncAdverts(): Completable {
        return Completable.fromCallable {
            database.advertDao().insert(adverts)
        }
    }

    fun getAdverts(): Observable<List<Advert>> {
        return database.advertDao().getAll().toObservable()
    }

    fun getAdvert(advertId: Int): Observable<Advert> {
        return database.advertDao()
            .getAdvertByIdFlowable(advertId)
            .defaultIfEmpty(Advert.EMPTY)
            .toObservable()
    }
}
