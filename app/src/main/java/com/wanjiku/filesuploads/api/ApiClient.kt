package com.wanjiku.filesuploads.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    val  okHttpClient=OkHttpClient.Builder()
        .connectTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .build()
     val retrofit=Retrofit.Builder()
         .baseUrl("")
         .addConverterFactory(GsonConverterFactory.create())
         .client(okHttpClient)
         .build()

    fun <T> buildClient(apiInterface: Class<T>): T{
        return retrofit.create(apiInterface)
    }
}