package com.onchain.test.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.math.RoundingMode

@JsonClass(generateAdapter = true)
data class CurrencyRateInfo(
    @Json(name = "tiers")
    val rates: List<Rate>
)

@JsonClass(generateAdapter = true)
data class Rate(
    @Json(name = "from_currency")
    val fromCurrency: String,
    @Json(name = "to_currency")
    val toCurrency: String,
    @Json(name = "rates")
    val exchangeRate: Array<ExChangeRate>
) {


    val rateMultiplier: BigDecimal
        get() = if (exchangeRate.size > 0) safeDivide(
            exchangeRate[0].rate,
            exchangeRate[0].amount
        ) else BigDecimal.ZERO

}

@JsonClass(generateAdapter = true)
data class ExChangeRate(val amount: String, val rate: String)

fun safeDivide(
    divider: String,
    divisor: String,
    defaultResult: BigDecimal = BigDecimal.ZERO
): BigDecimal {
    try {
        return divider.toBigDecimal()
            .divide(divisor.toBigDecimal(), 15, RoundingMode.DOWN)

    } catch (e: Exception) {
        e.printStackTrace()
        return defaultResult
    }
}