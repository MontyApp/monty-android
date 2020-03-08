package com.monty.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.monty.data.model.response.ApiAdvert
import com.monty.data.model.response.ApiUser
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.User
import com.monty.tool.constant.Constant
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class MontyFirebase @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun DocumentSnapshot.parseUser(): User {
        val apiUser = this.toObject(ApiUser::class.java)!!
        return User.fromApi(apiUser, this.id)
    }

    private fun DocumentSnapshot.parseAdvert(): Advert {
        val apiAdvert = this.toObject(ApiAdvert::class.java)!!
        return Advert.fromApi(apiAdvert, this.id)
    }

    fun getAdverts() = Single.create<List<Advert>> { singler ->
        firestore.collection(Constant.Database.ADVERTS).get()
            .addOnSuccessListener {
                singler.onSuccess(it.map { it.parseAdvert() })
            }.addOnFailureListener {
                //TODO parse error
                singler.onError(it)
            }
    }.observeOn(Schedulers.io())

    fun getUser(userId: String) = Single.create<User> { singler ->
        firestore.collection(Constant.Database.USERS).document(userId).get()
            .addOnSuccessListener {
                singler.onSuccess(it.parseUser())
            }.addOnFailureListener {
                //TODO parse error
                singler.onError(it)
            }
    }.observeOn(Schedulers.io())

    fun editUser(data: Map<String, Any>, id: String) = Completable.create { completabler ->
        firestore.collection(Constant.Database.USERS).document(id).update(data)
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                //TODO parse error
                completabler.onError(it)
            }
    }.observeOn(Schedulers.io())

    fun signInUser(data: Map<String, Any>) = Single.create<String> { singler ->
        firestore.collection(Constant.Database.USERS).add(data)
            .addOnSuccessListener {
                singler.onSuccess(it.id)
            }.addOnFailureListener {
                //TODO parse error
                singler.onError(it)
            }
    }.observeOn(Schedulers.io())

    fun addAdvert(data: Map<String, Any>) = Completable.create { completabler ->
        firestore.collection(Constant.Database.ADVERTS).add(data)
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                //TODO parse error
                completabler.onError(it)
            }
    }.observeOn(Schedulers.io())

    fun editAdvert(data: Map<String, Any>, id: String) = Completable.create { completabler ->
        firestore.collection(Constant.Database.ADVERTS).document(id).update(data)
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                //TODO parse error
                completabler.onError(it)
            }
    }.observeOn(Schedulers.io())

    fun deleteAdvert(id: String) = Completable.create { completabler ->
        firestore.collection(Constant.Database.ADVERTS).document(id).delete()
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                //TODO parse error
                completabler.onError(it)
            }
    }.observeOn(Schedulers.io())
}