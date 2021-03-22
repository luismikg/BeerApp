package com.example.beersapp.valueObject

import com.example.beersapp.domain.WebService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val werService by lazy {
        Retrofit.Builder()
            .baseUrl("${Web.URL_BASE}")
            .addConverterFactory( //JSON TO OBJECT
                GsonConverterFactory.create(GsonBuilder().create())
            )
            .build()
            .create(WebService::class.java) //Create the retrofit object implementing the interface "WebService"
    }
}