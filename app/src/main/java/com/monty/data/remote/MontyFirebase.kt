package com.monty.data.remote

import android.content.Context
import androidx.core.net.toUri
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.monty.R
import com.monty.data.model.response.ApiAdvert
import com.monty.data.model.response.ApiUser
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.User
import com.monty.injection.ApplicationContext
import com.monty.tool.constant.Constant
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject

data class MontyFirebase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @ApplicationContext private val context: Context
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
                singler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun getUser(userId: String) = Single.create<User> { singler ->
        firestore.collection(Constant.Database.USERS).document(userId).get()
            .addOnSuccessListener {
                singler.onSuccess(it.parseUser())
            }.addOnFailureListener {
                singler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun existUser(email: String) = Single.create<List<DocumentSnapshot>> { singler ->
        firestore.collection(Constant.Database.USERS).whereEqualTo("email", email).get()
            .addOnSuccessListener {
                singler.onSuccess(it.documents)
            }.addOnFailureListener {
                singler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun editUser(data: Map<String, Any>, id: String) = Completable.create { completabler ->
        firestore.collection(Constant.Database.USERS).document(id).update(data)
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                completabler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun signInUser(data: Map<String, Any>) = Single.create<String> { singler ->
        firestore.collection(Constant.Database.USERS).add(data)
            .addOnSuccessListener {
                singler.onSuccess(it.id)
            }.addOnFailureListener {
                singler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun addAdvert(data: Map<String, Any>) = Completable.create { completabler ->
        firestore.collection(Constant.Database.ADVERTS).add(data)
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                completabler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun editAdvert(data: Map<String, Any>, id: String) = Completable.create { completabler ->
        firestore.collection(Constant.Database.ADVERTS).document(id).update(data)
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                completabler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun deleteAdvert(id: String) = Completable.create { completabler ->
        firestore.collection(Constant.Database.ADVERTS).document(id).delete()
            .addOnSuccessListener {
                completabler.onComplete()
            }.addOnFailureListener {
                completabler.onError(resolveError(it))
            }
    }.observeOn(Schedulers.io())

    fun uploadFile(file: File): Single<StorageReference> {
        val uuid = UUID.randomUUID()
        val storageRef = storage.reference
        val fileRef = storageRef.child("images/$uuid.jpg")

        return Single.create<StorageReference> { singler ->
            fileRef.putFile(file.toUri())
                .addOnSuccessListener {
                    singler.onSuccess(fileRef)
                }.addOnFailureListener {
                    singler.onError(resolveError(it))
                }
        }.observeOn(Schedulers.io())
    }

    fun getFileUrl(fileRef: StorageReference): Single<String> {
        return Single.create<String> { singler ->
            fileRef.downloadUrl
                .addOnSuccessListener {
                    singler.onSuccess(it.toString())
                }.addOnFailureListener {
                    singler.onError(resolveError(it))
                }
        }.observeOn(Schedulers.io())
    }

    fun resolveError(error: Exception): Exception {
        return MontyException(
            when (error) {
                is IOException -> context.getString(R.string.error_no_internet_connection)
                else -> context.getString(R.string.error_something_went_wrong)
            }
        )
    }
}