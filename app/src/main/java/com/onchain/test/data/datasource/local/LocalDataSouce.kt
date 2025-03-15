package com.onchain.test.data.datasource.local

import com.BaseApp
import com.onchain.test.data.model.CurrencyRateInfo
import com.onchain.test.data.model.AssetBalanceItem
import com.onchain.test.data.model.AssetBalance
import com.onchain.test.data.datasource.DataSource
import com.onchain.test.data.model.Currency
import com.onchain.test.data.model.CurrencyInfo
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal

class LocalDataSource : DataSource {

    private val DEFAULT_CURRENCY_SYMBOL = "USD"

    object BigDecimalAdapter {
        @FromJson
        fun fromJson(string: String) = BigDecimal(string)

        @ToJson
        fun toJson(value: BigDecimal) = value.toString()
    }


    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(BigDecimalAdapter)
        .build()

    // we now set default symbol with USD
    private val currentSymbolFlow = MutableStateFlow(DEFAULT_CURRENCY_SYMBOL)

    override fun getCurrencyRate(): Flow<CurrencyRateInfo> {
        return flow {
            val json = assetJsonFile2String("live-rates.json")
            val model =
                moshi.adapter(CurrencyRateInfo::class.java).fromJson(json)
                    ?: error("cannot read local currency rate")
            emit(model)
        }
            .flowOn(Dispatchers.IO)
    }

    override fun getAssets(): Flow<List<AssetBalanceItem>> {

        return flow {
            val json = assetJsonFile2String("wallet-balance.json")
            val model =
                moshi.adapter(AssetBalance::class.java).fromJson(json)
                    ?.balanceList ?: error("cannot read local assets data")
            emit(model)
        }
            .flowOn(Dispatchers.IO)
    }

    override fun getCurrentValueSymbol(): Flow<String> {
        return currentSymbolFlow
    }

    override fun getCurrencyInfo(): Flow<List<Currency>> {
        return flow {
            val json = assetJsonFile2String("currencies.json")
            val model =
                moshi.adapter(CurrencyInfo::class.java).fromJson(json)
                    ?.currencies ?: error("cannot read local assets data")
            emit(model)
        }
            .flowOn(Dispatchers.IO)
    }


    private fun assetJsonFile2String(filePath:String): String {
        return BaseApp.app.assets
            .open(filePath)
            .bufferedReader()
            .use { it.readText() }
    }

    /**
     * persist current value symbol
     */
    override fun saveCurrentValueSymbol(symbol: String) {
        currentSymbolFlow.value = symbol
        //TODO save this symbol to file or somewhere
    }
}