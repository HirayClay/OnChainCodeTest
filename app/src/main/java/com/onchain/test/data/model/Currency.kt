package com.onchain.test.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyInfo(
    @Json(name = "currencies")
    val currencies: List<Currency>
)

@JsonClass(generateAdapter = true)
data class Currency(
    @Json(name = "coin_id")
    val coinId: String,
    @Json(name = "colorful_image_url")
    val imageUrl: String,
    @Json(name = "name")
    val tokenFullName: String
)
