package com.monty.data.model.response

import com.google.firebase.firestore.GeoPoint

data class ApiUser(
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    val email: String = "",
    val phone: String = "",
    val address: GeoPoint = GeoPoint(0.0, 0.0)
)