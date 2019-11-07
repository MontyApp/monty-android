package com.monty.data.model.ui

data class Advert(
    val id: Int,
    val title: String,
    val description: String,
    val interval: String,
    val image: String,
    val price: String,
    val isFavourite: Boolean = false
) {

    companion object {
        val EMPTY = Advert(
            id = -1,
            title = "",
            image = "",
            interval = "",
            description = "",
            price = ""
        )
    }
}