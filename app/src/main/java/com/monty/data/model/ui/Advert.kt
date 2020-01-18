package com.monty.data.model.ui

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monty.data.model.response.ApiAdvert
import com.monty.data.model.ui.mapper.IntervalMapper
import com.monty.tool.currency.CurrencyFormatter
import com.monty.tool.extensions.toLocalDateTime
import org.threeten.bp.LocalDateTime

@Entity(tableName = "advert")
data class Advert(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @Embedded(prefix = "price_")
    val price: Price,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @Embedded(prefix = "deposit_")
    val deposit: Price,

    @Embedded(prefix = "address_")
    val address: Address,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
) {

    companion object {
        val EMPTY = Advert(
            id = -1,
            title = "",
            image = "",
            description = "",
            categoryId = -1,
            createdAt = LocalDateTime.MIN,
            price = Price.EMPTY,
            deposit = Price.EMPTY,
            address = Address.EMPTY,
            isFavourite = false
        )

        fun fromApi(apiAdvert: ApiAdvert) = Advert(
            title = apiAdvert.title,
            description = apiAdvert.description,
            image = apiAdvert.image,
            createdAt = apiAdvert.createdAt.toLocalDateTime(),
            address = Address.fromApi(apiAdvert.address),
            price = Price(
                currency = Currency(code = apiAdvert.currency, sign = "kč"),
                value = apiAdvert.price,
                interval = Interval(apiAdvert.interval)
            ),
            deposit = Price(
                currency = Currency(code = apiAdvert.currency, sign = "kč"),
                value = apiAdvert.deposit,
                interval = Interval(apiAdvert.interval)
            ),
            categoryId = apiAdvert.categoryId,
            id = 1
        )
    }

    fun getPrice(currencyFormatter: CurrencyFormatter): String {
        return currencyFormatter.format(price.value, price.currency)
    }

    fun getDeposit(currencyFormatter: CurrencyFormatter): String {
        return currencyFormatter.format(deposit.value, deposit.currency)
    }

    fun getInterval(resources: Resources): String {
        return IntervalMapper.getInterval(resources, price.interval.name)
    }
}