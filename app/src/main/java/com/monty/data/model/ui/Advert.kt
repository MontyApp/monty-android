package com.monty.data.model.ui

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monty.R
import com.monty.data.model.ui.type.IntervalType
import com.monty.tool.currency.CurrencyFormatter
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
            createdAt = LocalDateTime.MIN,
            price = Price.EMPTY,
            deposit = Price.EMPTY,
            address = Address.EMPTY,
            isFavourite = false
        )
    }

    fun getPrice(currencyFormatter: CurrencyFormatter): String {
        return currencyFormatter.format(price.value, price.currency)
    }

    fun getDeposit(currencyFormatter: CurrencyFormatter): String {
        return currencyFormatter.format(deposit.value, deposit.currency)
    }

    fun getInterval(resources: Resources): String {
        return when (price.interval.name) {
            IntervalType.DAY.value -> resources.getString(R.string.interval_day)
            IntervalType.WEEK.value -> resources.getString(R.string.interval_week)
            IntervalType.MONTH.value -> resources.getString(R.string.interval_month)
            else -> ""
        }
    }
}