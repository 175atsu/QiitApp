package com.example.qiitaapiapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    //パラメータ指定
//    @GET("api/v2/items")
//    //多分モデルを引き出してる
//    fun apiDemo(
//        @Query("page") page: Int,
//        @Query("par_page") perPage: Int
//    ): Call<List<QiitResponse>>

    //パラメータ指定
    @GET("api/v2/items")
    //多分モデルを引き出してる
    suspend fun apiDemo(
        @Query("page") page: Int,
        @Query("par_page") perPage: Int
    ): List<QiitResponse>

}