package com.monty.data.persistence

import android.content.Context
import com.monty.injection.ApplicationContext
import com.orhanobut.hawk.Hawk
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Persistence @Inject constructor(
    @ApplicationContext context: Context
) {

    init {
        Hawk.init(context)
            .build()
    }

    fun <T> put(key: String, value: T): Boolean {
        return Hawk.put(key, value)
    }

    operator fun <T> get(key: String): T {
        return Hawk.get(key)
    }

    operator fun <T> get(key: String, defaultValue: T): T {
        return Hawk.get(key, defaultValue)
    }

    fun delete(key: String): Boolean {
        return Hawk.delete(key)
    }

    operator fun contains(key: String): Boolean {
        return Hawk.contains(key)
    }

    fun clear(): Boolean {
        return Hawk.deleteAll()
    }

    fun clearRx(): Completable {
        return Completable.fromAction {
            clear()
        }
    }
}
