package com.monty.domain.user

import com.monty.data.store.UserStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class SyncUserCompletabler @Inject constructor(
    private val userStore: UserStore
) : BaseCompletabler() {

    private var userId: String = ""

    fun init(userId: String) = apply {
        this.userId = userId
    }

    override fun create(): Completable {
        return userStore.syncUser(userId)
    }
}
