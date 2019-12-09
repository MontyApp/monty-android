package com.monty.tool.currency

import com.monty.data.model.ui.Currency
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyFormatter @Inject constructor() {

    private val decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = ' '
    }

    private val formatter = DecimalFormat("#.##").apply {
        groupingSize = 3
        isGroupingUsed = true
        decimalFormatSymbols = this@CurrencyFormatter.decimalFormatSymbols
    }

    fun format(amount: Float, currency: Currency, locale: Locale = Locale.getDefault()): String {
        var formatted = formatAmount(amount, locale)
        try {
            formatted = String.format(
                locale,
                "%s %s",
                formatAmount(amount, locale),
                currency.sign
            )
        } catch (e: Exception) {
           // do nothing
        }
        return formatted
    }

    fun formatAmount(amount: Float, locale: Locale = Locale.getDefault()) = String.format(
        locale,
        "%s",
        formatter.format(
            BigDecimal(amount.toDouble()).apply { setScale(2, RoundingMode.CEILING) }.toDouble()
        )
    )
}