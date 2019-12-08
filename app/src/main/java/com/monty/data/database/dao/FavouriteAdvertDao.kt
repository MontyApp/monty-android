package com.monty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.FavouriteAdvert
import io.reactivex.Flowable

@Dao
abstract class FavouriteAdvertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(advert: FavouriteAdvert)

    @Query(value = "SELECT advert.*, favourite_advert.* FROM advert INNER JOIN favourite_advert WHERE advert.id = favourite_advert.id")
    abstract fun getAll(): Flowable<List<Advert>>

    @Query("DELETE FROM favourite_advert WHERE id = :advertId")
    abstract fun delete(advertId: Int)
}
