package com.example.qiitaapiapp.data.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    //パラメータ指定
    @GET("/api/v2/items?page=1&per_page=20")
    //suspend fun fetchGithubUser(@Path("account") account: String?): QiitResponse
    //多分モデルを引き出してる
    fun apiDemo(): Call<List<QiitResponse>>
}