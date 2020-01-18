package com.monty.data.model.response

import com.google.firebase.firestore.GeoPoint

data class ApiAdvert(
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val price: Float = 0f,
    val interval: String = "",
    val currency: String = "",
    val deposit: Float = 0f,
    val categoryId: Int = 0,
    val createdAt: String = "",
    val address: GeoPoint = GeoPoint(0.0, 0.0)
)