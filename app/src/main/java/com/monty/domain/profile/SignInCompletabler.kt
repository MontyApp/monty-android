package com.monty.domain.profile

import com.google.firebase.auth.FirebaseUser
import com.monty.data.model.ui.User
import com.monty.data.store.AuthStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class SignInCompletabler @Inject constructor(
    private val authStore: AuthStore
) : BaseCompletabler() {

    private var firebaseUser: FirebaseUser? = null

    fun init(firebaseUser: FirebaseUser?) = apply {
        this.firebaseUser = firebaseUser
    }

    override fun create(): Completable {
        val name = firebaseUser?.displayName ?: ""
        val i: Int = name.indexOf(' ')
        val firstName: String = name.substring(0, i)
        val lastName: String = name.substring(i)

        val user = User.EMPTY.copy(
            firstName = firstName,
            lastName = lastName,
            email = firebaseUser?.email ?: "",
            phone = firebaseUser?.phoneNumber ?: "",
            avatar = firebaseUser?.photoUrl?.toString() ?: ""
        )
        val data = hashMapOf(
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "email" to user.email,
            "phone" to user.phone,
            "avatar" to user.avatar
        )
        return authStore.signInUser(data, user)
    }
}
