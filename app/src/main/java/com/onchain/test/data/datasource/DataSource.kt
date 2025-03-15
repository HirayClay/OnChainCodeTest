package com.onchain.test.data.datasource

import com.onchain.test.data.model.CurrencyRateInfo
import com.onchain.test.data.model.AssetBalanceItem
import com.onchain.test.data.model.Currency
import kotlinx.coroutines.flow.Flow

interface DataSource {

    fun getCurrencyRate(): Flow<CurrencyRateInfo>

    fun getAssets(): Flow<List<AssetBalanceItem>>

    fun getCurrentValueSymbol(): Flow<String>

    fun getCurrencyInfo():Flow<List<Currency>>

    fun saveCurrentValueSymbol(symbol:String)
}