package com.monty.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.monty.data.model.response.ApiAdvert
import com.monty.data.model.ui.Advert
import com.monty.tool.constant.Constant
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class MontyFirebase @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun QuerySnapshot.parseAdverts() = this.documents
        .mapNotNull { it.toObject(ApiAdvert::class.java) }
        .map { Advert.fromApi(it) }

    fun getAdverts() = Single.create<List<Advert>> { singler ->
        firestore.collection(Constant.Database.ADVERTS).get()
            .addOnSuccessListener {
                singler.onSuccess(it.parseAdverts())
            }.addOnFailureListener {
                //TODO parse error
                singler.onError(it)
            }
    }.observeOn(Schedulers.io())
}