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
    val id: String,

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

    @ColumnInfo(name = "user_id")
    val userId: String,

    @Embedded(prefix = "deposit_")
    val deposit: Price,

    @Embedded(prefix = "address_")
    val address: Address,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
) {

    companion object {
        val EMPTY = Advert(
            id = "",
            title = "",
            image = "",
            description = "",
            categoryId = -1,
            createdAt = LocalDateTime.MIN,
            price = Price.EMPTY,
            deposit = Price.EMPTY,
            address = Address.EMPTY,
            isFavourite = false,
            userId = ""
        )

        fun fromApi(apiAdvert: ApiAdvert, id: String) = Advert(
            id = id,
            title = apiAdvert.title,
            description = apiAdvert.description,
            image = apiAdvert.image,
            createdAt = apiAdvert.createdAt.toLocalDateTime(),
            address = Address.fromApi(apiAdvert.address),
            price = Price.fromApi(apiAdvert.currency, apiAdvert.interval, apiAdvert.price),
            deposit = Price.fromApi(apiAdvert.currency, apiAdvert.interval, apiAdvert.deposit),
            categoryId = apiAdvert.categoryId,
            userId = apiAdvert.userId
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