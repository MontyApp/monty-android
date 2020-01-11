package com.monty.domain

import com.monty.data.model.ui.*
import com.monty.data.model.ui.type.IntervalType
import org.threeten.bp.LocalDateTime

val priceX = Price(
    currency = Currency(code = "eur", sign = "€"),
    interval = Interval(IntervalType.WEEK.value),
    value = 100f
)

val priceY = Price(
    currency = Currency(code = "eur", sign = "€"),
    interval = Interval(IntervalType.MONTH.value),
    value = 50f
)

val priceZ = Price(
    currency = Currency(code = "eur", sign = "€"),
    interval = Interval(IntervalType.DAY.value),
    value = 75f
)

val address = Address(
    name = "Brno",
    latitude = 49.195061,
    longitude = 16.606836
)

val description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."

val adverts = listOf(
    Advert(
        id = 1,
        title = "Nosič THULE",
        description = description,
        createdAt = LocalDateTime.MIN,
        price = priceX,
        deposit = priceX,
        image = "https://images-na.ssl-images-amazon.com/images/I/91RqROIuniL._SL1500_.jpg",
        address = address,
        isFavourite = false,
        categoryId = 1
    ),
    Advert(
        id = 2,
        title = "Súprava BOSH - vŕtačka",
        description = description,
        createdAt = LocalDateTime.MIN,
        price = priceY,
        deposit = priceX,
        address = address,
        image = "https://assets.bosch.com/media/global/products_and_solutions/industry_and_trade/power-tools-for-professionals_res_800x450.jpg",
        isFavourite = false,
        categoryId = 2
    ),
    Advert(
        id = 3,
        title = "Gitara Les Paul",
        description = description,
        createdAt = LocalDateTime.MIN,
        price = priceZ,
        deposit = priceY,
        address = address,
        image = "https://www.eastgatemusic.com.au/wp-content/uploads/2018/08/Gibson-Les-Paul-Classic-2018-Ebony-Electric-Guitar-in-case.jpg",
        isFavourite = false,
        categoryId = 2
    ),
    Advert(
        id = 4,
        title = "Bicykel na víkend",
        description = description,
        createdAt = LocalDateTime.MIN,
        price = priceX,
        deposit = priceZ,
        address = address,
        image = "https://www.batchbicycles.com/media/wysiwyg/home-slide/batch-fitness-homepage.jpg",
        isFavourite = false,
        categoryId = 1
    ),
    Advert(
        id = 5,
        title = "Nosič THULE",
        description = description,
        createdAt = LocalDateTime.MIN,
        price = priceZ,
        deposit = priceX,
        address = address,
        image = "https://images-na.ssl-images-amazon.com/images/I/91RqROIuniL._SL1500_.jpg",
        isFavourite = false,
        categoryId = 1
    ),
    Advert(
        id = 6,
        title = "Súprava BOSH - vŕtačka",
        description = description,
        createdAt = LocalDateTime.MIN,
        price = priceY,
        deposit = priceZ,
        address = address,
        image = "https://assets.bosch.com/media/global/products_and_solutions/industry_and_trade/power-tools-for-professionals_res_800x450.jpg",
        isFavourite = false,
        categoryId = 2
    )
)