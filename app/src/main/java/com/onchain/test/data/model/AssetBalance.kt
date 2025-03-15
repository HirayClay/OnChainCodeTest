package com.onchain.test.data.model

import com.onchain.test.util.downscale
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class AssetBalance(
    val ok: Boolean,
    val warning: String,
    @Json(name = "wallet")
    val balanceList: List<AssetBalanceItem>
)

@JsonClass(generateAdapter = true)
data class AssetBalanceItem(
    @Json(name = "currency")
    val currencyName: String,
    @Json(name = "amount")
    val amount: BigDecimal,
) {
    @Transient
    var tokenValue: BigDecimal = BigDecimal.ZERO

    @Transient
    var valueSymbol:String = ""

    @Transient
    var currencyInfo: Currency? = null

    fun calcValue(rate: BigDecimal) {
        tokenValue = amount.multiply(rate).downscale(2)
    }

}