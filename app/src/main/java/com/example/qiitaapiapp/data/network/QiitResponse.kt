package com.example.qiitaapiapp.data.network

//後に必要となるqiitaのリンクのurl取得するための箱
data class QiitResponse(
    //@SerializedName("avatar_url")
    val url: String?,
    val title: String?,
    val user: User?
)

data class User(
    val id: String?
)