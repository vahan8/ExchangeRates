package com.vahan.exchangerates.data.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private const val BASE_URL = "http://rate.am/ws/mobile/v2/"

    private var apiService: ApiService? = null
    private var client: OkHttpClient? = null

    private fun init() {
        //Adding request interceptors
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()


        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory(client)
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    public fun cancelCurrentCalls() {
        if (client != null)
            client!!.dispatcher().cancelAll()
    }

    fun getApiService(): ApiService {
        if (apiService == null)
            init()
        return apiService!!
    }
}
