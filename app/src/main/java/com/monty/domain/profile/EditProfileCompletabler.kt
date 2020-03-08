package com.monty.domain.profile

import com.monty.data.model.ui.User
import com.monty.data.store.AuthStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class EditProfileCompletabler @Inject constructor(
    private val authStore: AuthStore
) : BaseCompletabler() {

    private var firstName: String = ""
    private var lastName: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var user: User = User.EMPTY

    fun init(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        user: User
    ) = apply {
        this.firstName = if (firstName.isEmpty()) user.firstName else firstName
        this.lastName = if (lastName.isEmpty()) user.lastName else lastName
        this.email = if (email.isEmpty()) user.email else email
        this.phone = if (phone.isEmpty()) user.phone else phone
        this.user = user
    }

    override fun create(): Completable {
        val data = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phone" to phone
        )
        return authStore.editUser(data, user.id)
    }
}
