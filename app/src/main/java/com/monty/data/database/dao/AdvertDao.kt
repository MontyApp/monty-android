package com.monty.data.database.dao

import androidx.room.*
import com.monty.data.model.ui.Advert
import io.reactivex.Flowable

@Dao
abstract class AdvertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<Advert>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(advert: Advert)

    @Query("SELECT * FROM advert")
    abstract fun getAll(): Flowable<List<Advert>>

    @Query(value = "SELECT * FROM advert WHERE advert.id = :advertId LIMIT 1")
    abstract fun getAdvertByIdFlowable(advertId: Int): Flowable<Advert>

    @Query("UPDATE advert SET is_favourite = :isFavourite WHERE id = :id")
    abstract fun updateIsFavourite(id: Int, isFavourite: Boolean)

    @Query("DELETE FROM advert")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAll(adverts: List<Advert>) {
        deleteAll()
        insert(adverts)
    }
}
