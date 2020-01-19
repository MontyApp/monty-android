package com.monty.data.store

import com.monty.data.database.AppDatabase
import com.monty.data.model.ui.User
import com.monty.data.remote.MontyFirebase
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStore @Inject constructor(
    private val database: AppDatabase,
    private val montyFirebase: MontyFirebase
) {

    fun syncUser(userId: String): Completable {
        return montyFirebase.getUser(userId)
            .flatMapCompletable { user ->
                Completable.fromCallable {
                    database.userDao().insert(user)
                }
            }
    }

    fun getUser(userId: String): Observable<User> {
        return database.userDao()
            .getUserByIdFlowable(userId)
            .defaultIfEmpty(User.EMPTY)
            .toObservable()
    }
}
