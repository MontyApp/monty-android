package com.monty.data.store

import com.monty.data.database.AppDatabase
import com.monty.data.model.ui.Advert
import com.monty.data.remote.MontyFirebase
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdvertsStore @Inject constructor(
    private val database: AppDatabase,
    private val montyFirebase: MontyFirebase
) {

    fun syncAdverts(): Completable {
        return montyFirebase.getAdverts()
            .flatMapCompletable { adverts ->
                database.favouriteAdvertDao().getAllSingle()
                    .flatMapCompletable { favourites ->
                        Completable.fromCallable {
                            adverts.map { advert -> advert.copy(isFavourite = favourites.find { it.id == advert.id } != null) }
                                .let { database.advertDao().replaceAll(it) }
                        }
                    }
            }
    }

    fun addAdvert(data: Map<String, Any>): Completable {
        return montyFirebase.addAdvert(data).andThen(syncAdverts())
    }

    fun editAdvert(data: Map<String, Any>, id: String): Completable {
        return montyFirebase.editAdvert(data, id).andThen(syncAdverts())
    }

    fun getAdverts(): Observable<List<Advert>> {
        return database.advertDao().getAll().toObservable()
    }

    fun getAdvert(advertId: String): Observable<Advert> {
        return database.advertDao()
            .getAdvertByIdFlowable(advertId)
            .defaultIfEmpty(Advert.EMPTY)
            .toObservable()
    }
}
