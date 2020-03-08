package com.monty.domain.advert

import com.google.firebase.firestore.GeoPoint
import com.monty.data.model.ui.User
import com.monty.data.store.AdvertsStore
import com.monty.data.store.AuthStore
import com.monty.domain.base.BaseCompletabler
import io.reactivex.Completable
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class AddAdvertCompletabler @Inject constructor(
    private val advertsStore: AdvertsStore,
    private val authStore: AuthStore
) : BaseCompletabler() {

    private var advertId: String = ""
    private var title: String = ""
    private var description: String = ""
    private var image: String = ""
    private var price: Float = 0f
    private var interval: String = ""
    private var deposit: Float = 0f
    private var categoryId: Int = 0

    fun init(
        title: String,
        description: String,
        image: String,
        price: Float,
        interval: String,
        deposit: Float,
        categoryId: Int,
        advertId: String = ""
    ) = apply {
        this.title = title
        this.description = description
        this.image = image
        this.interval = interval
        this.deposit = deposit
        this.price = price
        this.categoryId = categoryId
        this.advertId = advertId
    }

    override fun create(): Completable {
        return authStore.getUser().first(User.EMPTY)
            .flatMapCompletable { profile ->
                val data = hashMapOf(
                    "title" to title,
                    "description" to description,
                    "image" to image,
                    "interval" to interval,
                    "deposit" to deposit,
                    "price" to price,
                    "categoryId" to categoryId,
                    "address" to GeoPoint(49.195061, 16.606836),
                    "currency" to "czk",
                    "createdAt" to LocalDateTime.now().toString(),
                    "userId" to profile.id
                )
                if (advertId.isNotEmpty()) {
                    advertsStore.editAdvert(data, advertId)
                } else {
                    advertsStore.addAdvert(data)
                }
            }
    }
}
