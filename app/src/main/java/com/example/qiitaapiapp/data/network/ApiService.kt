package com.example.qiitaapiapp.data.network

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/v2/items?page=1&per_page=20")
    //suspend fun fetchGithubUser(@Path("account") account: String?): QiitResponse
    fun apiDemo(): Call<QiitResponse>
}