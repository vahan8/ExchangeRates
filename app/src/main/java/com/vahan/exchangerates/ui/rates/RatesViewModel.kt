package com.vahan.exchangerates.ui.rates

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vahan.exchangerates.data.repository.RatesRepository
import com.vahan.exchangerates.domain.model.BankExchangeRate
import com.vahan.exchangerates.domain.model.Result

class RatesViewModel : ViewModel() {
    private val ratesObservable: MutableLiveData<List<BankExchangeRate>> = MutableLiveData()

    fun getRatesObservable(): LiveData<List<BankExchangeRate>> {
        return ratesObservable
    }

    fun loadData() {
        RatesRepository.getRatesAsync {
            when (it) {
                is Result.Success -> {
                    ratesObservable.postValue(it.data)
                }
                //Error handling not done
                is Error -> {
                    Log.e("RatesViewModel", "error in get rates")
                }
            }
        }
    }

}