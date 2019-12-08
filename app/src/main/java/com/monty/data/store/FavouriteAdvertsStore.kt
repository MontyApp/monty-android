package com.monty.data.store

import com.monty.data.database.AppDatabase
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.FavouriteAdvert
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteAdvertsStore @Inject constructor(
    private val database: AppDatabase
) {

    fun addFavouriteAdvert(advertId: Int): Completable {
        return Completable.fromCallable {
            database.favouriteAdvertDao().insert(FavouriteAdvert(advertId))
        }
    }

    fun removeFavouriteAdvert(advertId: Int): Completable {
        return Completable.fromCallable {
            database.favouriteAdvertDao().delete(advertId)
        }
    }

    fun getFavouriteAdverts(): Observable<List<Advert>> {
        return database.favouriteAdvertDao()
            .getAll()
            .map { it.map { it.copy(isFavourite = true) } }
            .toObservable()
    }
}
