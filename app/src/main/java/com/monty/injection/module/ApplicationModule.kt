package com.monty.injection.module

import android.content.Context
import androidx.room.Room
import com.monty.App
import com.monty.data.database.AppDatabase
import com.monty.injection.ApplicationContext
import com.thefuntasty.taste.display.TDisplay
import com.thefuntasty.taste.res.TRes
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @ApplicationContext
    fun context(app: App): Context = app

    @Provides
    fun res(@ApplicationContext context: Context): TRes = TRes(context)

    @Provides
    fun display(@ApplicationContext context: Context): TDisplay = TDisplay(context)

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}
