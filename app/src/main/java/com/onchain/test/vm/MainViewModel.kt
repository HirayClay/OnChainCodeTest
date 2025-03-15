package com.onchain.test.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onchain.test.data.model.AssetBalanceItem
import com.onchain.test.data.repo.WalletRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    val balanceValueLiveData = MutableLiveData<String>()
    private val repo = WalletRepository()

    val assetDataFlow =
        combine(
            repo.getAssets(),
            repo.getCurrencyRate(),
            repo.getCurrentValueSymbol(),
            repo.getCurrencyInfo()
        ) { assets, currencyRate, valueSymbol, currencies ->

            // compute ,combine all the data
            val balanceSum = assets.sumOf { asset ->
                val rate =
                    currencyRate.rates.first { asset.currencyName == it.fromCurrency && it.toCurrency == valueSymbol }
                val currencyInfo = currencies.find { it.coinId == asset.currencyName }
                asset.currencyInfo = currencyInfo
                asset.calcValue(rate.rateMultiplier)
                asset.tokenValue
            }
            balanceValueLiveData.postValue(balanceSum.toPlainString().plus(valueSymbol))
            return@combine assets
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}