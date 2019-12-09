package com.monty.domain

import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.Currency
import com.monty.data.model.ui.Interval
import com.monty.data.model.ui.Price
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

val adverts = listOf(
    Advert(
        id = 1,
        title = "Nosič THULE",
        description = "Plně funkční, kolo s blatníky, zvonkem, stojánkem, nové pláště i duše, kvalitní zámek.",
        createdAt = LocalDateTime.MIN,
        price = priceX,
        deposit = priceX,
        image = "https://images-na.ssl-images-amazon.com/images/I/91RqROIuniL._SL1500_.jpg",
        isFavourite = false
    ),
    Advert(
        id = 2,
        title = "Súprava BOSH - vŕtačka",
        description = "Plně funkční, kolo s blatníky, zvonkem, stojánkem, nové pláště i duše, kvalitní zámek.",
        createdAt = LocalDateTime.MIN,
        price = priceY,
        deposit = priceX,
        image = "https://assets.bosch.com/media/global/products_and_solutions/industry_and_trade/power-tools-for-professionals_res_800x450.jpg",
        isFavourite = false
    ),
    Advert(
        id = 3,
        title = "Gitara Les Paul",
        description = "Plně funkční, kolo s blatníky, zvonkem, stojánkem, nové pláště i duše, kvalitní zámek.",
        createdAt = LocalDateTime.MIN,
        price = priceZ,
        deposit = priceY,
        image = "https://www.eastgatemusic.com.au/wp-content/uploads/2018/08/Gibson-Les-Paul-Classic-2018-Ebony-Electric-Guitar-in-case.jpg",
        isFavourite = false
    ),
    Advert(
        id = 4,
        title = "Kolo na víkend",
        description = "Plně funkční, kolo s blatníky, zvonkem, stojánkem, nové pláště i duše, kvalitní zámek.",
        createdAt = LocalDateTime.MIN,
        price = priceX,
        deposit = priceZ,
        image = "https://www.batchbicycles.com/media/wysiwyg/home-slide/batch-fitness-homepage.jpg",
        isFavourite = false
    ),
    Advert(
        id = 5,
        title = "Nosič THULE",
        description = "Plně funkční, kolo s blatníky, zvonkem, stojánkem, nové pláště i duše, kvalitní zámek.",
        createdAt = LocalDateTime.MIN,
        price = priceZ,
        deposit = priceX,
        image = "https://images-na.ssl-images-amazon.com/images/I/91RqROIuniL._SL1500_.jpg",
        isFavourite = false
    ),
    Advert(
        id = 6,
        title = "Súprava BOSH - vŕtačka",
        description = "Plně funkční, kolo s blatníky, zvonkem, stojánkem, nové pláště i duše, kvalitní zámek.",
        createdAt = LocalDateTime.MIN,
        price = priceY,
        deposit = priceZ,
        image = "https://assets.bosch.com/media/global/products_and_solutions/industry_and_trade/power-tools-for-professionals_res_800x450.jpg",
        isFavourite = false
    )
)