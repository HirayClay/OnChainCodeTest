package com.onchain.test.data.repo

import com.onchain.test.data.model.CurrencyRateInfo
import com.onchain.test.data.model.AssetBalanceItem
import com.onchain.test.data.datasource.DataSource
import com.onchain.test.data.datasource.local.LocalDataSource
import com.onchain.test.data.model.Currency
import kotlinx.coroutines.flow.Flow


class WalletRepository {

    // use di later
    private var localDataSource: DataSource = LocalDataSource()

    fun getCurrencyRate(): Flow<CurrencyRateInfo> {
        return localDataSource.getCurrencyRate()
    }

    fun getAssets(): Flow<List<AssetBalanceItem>> {
        return localDataSource.getAssets()
    }

    fun getCurrentValueSymbol(): Flow<String> {
        return localDataSource.getCurrentValueSymbol()
    }

    fun getCurrencyInfo():Flow<List<Currency>>{
        return  localDataSource.getCurrencyInfo()
    }

    fun saveCurrentValueSymbol(symbol: String) {
        localDataSource.saveCurrentValueSymbol(symbol)
    }
}