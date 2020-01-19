package com.monty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.monty.data.database.converter.Converters
import com.monty.data.database.dao.AdvertDao
import com.monty.data.database.dao.FavouriteAdvertDao
import com.monty.data.database.dao.UserDao
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.FavouriteAdvert
import com.monty.data.model.ui.User

@Database(
    entities = [
        User::class,
        Advert::class,
        FavouriteAdvert::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "app_database"
    }

    abstract fun userDao(): UserDao

    abstract fun advertDao(): AdvertDao

    abstract fun favouriteAdvertDao(): FavouriteAdvertDao

}
