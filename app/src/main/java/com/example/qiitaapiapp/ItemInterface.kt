package com.example.qiitaapiapp

import android.content.ClipData
import android.telecom.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ItemInterface {
    @GET("/api/v2/items?page=1&per_page=20")
    val apiDemo : Call<List<ClipData.Item>>
}

fun createService(): ItemInterface {
    val baseApiUrl = "https://qiita.com/"

    val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseApiUrl)
        .client(httpClientBuilder.build())
        .build()

    return retrofit.create(ItemInterface::class.java)
}