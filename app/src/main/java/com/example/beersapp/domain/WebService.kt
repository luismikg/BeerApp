package com.example.beersapp.domain

import com.example.beersapp.data.model.Bar
import com.example.beersapp.valueObject.Web
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("${Web.URL_BASE}beers?")
    suspend fun getData(@Query("page") page: String): Bar
}