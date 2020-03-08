package com.monty.domain.profile

import com.monty.data.model.ui.User
import com.monty.data.store.AuthStore
import com.monty.domain.base.BaseObservabler
import io.reactivex.Observable
import javax.inject.Inject

class GetProfileObservabler @Inject constructor(
    private val authStore: AuthStore
) : BaseObservabler<User>() {

    override fun create(): Observable<User> {
        return authStore.getUser()
    }
}
