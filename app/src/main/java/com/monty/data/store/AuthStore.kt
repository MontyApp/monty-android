package com.monty.data.store

import com.jakewharton.rxrelay2.BehaviorRelay
import com.monty.data.model.ui.User
import com.monty.data.persistence.Persistence
import com.monty.data.remote.MontyFirebase
import com.monty.tool.constant.Constant
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStore @Inject constructor(
    private val persistence: Persistence,
    private val montyFirebase: MontyFirebase
) {

    private val userRelay by lazy {
        BehaviorRelay.createDefault(persistence[Constant.Persistence.USER, User.EMPTY])
            .toSerialized()
    }

    fun getUser(): Observable<User> {
        return userRelay
    }

    fun syncUser(): Completable {
        return userRelay.first(User.EMPTY)
            .flatMap { montyFirebase.getUser(it.id) }
            .flatMapCompletable { user ->
                Completable.fromCallable {
                    storeUser(user)
                }
            }
    }

    fun signInUser(data: Map<String, Any>, user: User): Completable {
        return montyFirebase.signInUser(data).flatMapCompletable { userId ->
            Completable.fromCallable { storeUser(user.copy(id = userId)) }
        }
    }

    fun editUser(data: Map<String, Any>, id: String): Completable {
        return montyFirebase.editUser(data, id).andThen(syncUser())
    }

    fun storeUser(user: User) {
        persistence.put(Constant.Persistence.USER, user)
        userRelay.accept(user)
    }
}
