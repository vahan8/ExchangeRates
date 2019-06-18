package com.vahan.exchangerates.data.remote

import com.vahan.exchangerates.data.remote.mapper.BankExchangeRateMapper
import com.vahan.exchangerates.data.remote.mapper.BankInfoMapper
import com.vahan.exchangerates.data.remote.model.BankExchangeRateRemoteModel
import com.vahan.exchangerates.data.remote.model.BankInfoRemoteModel
import com.vahan.exchangerates.domain.model.BankBranch
import com.vahan.exchangerates.domain.model.BankExchangeRate
import com.vahan.exchangerates.domain.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiClient {
    fun getRatesAsync(onResponse: OnResponse<List<BankExchangeRate>>) {
        ApiManager.getApiService().getRates("en")
            .enqueue(object : Callback<Map<String, BankExchangeRateRemoteModel>> {
                override fun onFailure(call: Call<Map<String, BankExchangeRateRemoteModel>>, t: Throwable) {
                    onResponse(Result.Error)
                }

                override fun onResponse(
                    call: Call<Map<String, BankExchangeRateRemoteModel>>,
                    response: Response<Map<String, BankExchangeRateRemoteModel>>
                ) {
                    if (response.isSuccessful) {
                        val resultMap: Map<String, BankExchangeRateRemoteModel> = response.body()!!
                        onResponse(Result.Success(BankExchangeRateMapper.toLocal(resultMap)))
                    } else {
                        onResponse(Result.Error)
                    }
                }
            })
    }

    fun getBankInfoAsync(organizationId: String, onResponse: OnResponse<List<BankBranch>>) {
        ApiManager.getApiService().getBankInfo(organizationId)
            .enqueue(object : Callback<BankInfoRemoteModel> {
                override fun onFailure(call: Call<BankInfoRemoteModel>, t: Throwable) {
                    onResponse(Result.Error)
                }

                override fun onResponse(call: Call<BankInfoRemoteModel>, response: Response<BankInfoRemoteModel>) {
                    if (response.isSuccessful) {
                        val result: BankInfoRemoteModel = response.body()!!
                        onResponse(Result.Success(BankInfoMapper.toLocal(result.branchesMap)))
                    } else {
                        onResponse(Result.Error)
                    }
                }
            })
    }
}

