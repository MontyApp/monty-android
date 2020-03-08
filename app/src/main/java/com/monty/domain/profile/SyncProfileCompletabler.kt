package com.monty.domain.profile

import com.monty.data.store.AuthStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class SyncProfileCompletabler @Inject constructor(
    private val authStore: AuthStore
) : BaseCompletabler() {

    override fun create(): Completable {
        return authStore.syncUser()
    }
}
