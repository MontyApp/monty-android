package com.monty.domain.user

import com.monty.data.model.ui.User
import com.monty.data.store.UserStore
import com.monty.domain.base.BaseObservabler
import io.reactivex.Observable
import javax.inject.Inject

class GetUserObservabler @Inject constructor(
    private val userStore: UserStore
) : BaseObservabler<User>() {

    private var userId: String = ""

    fun init(userId: String) = apply {
        this.userId = userId
    }

    override fun create(): Observable<User> {
        return userStore.getUser(userId)
    }
}
