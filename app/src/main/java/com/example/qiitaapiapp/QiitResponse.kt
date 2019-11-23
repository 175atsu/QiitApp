package com.example.qiitaapiapp

//後に必要となるqiitaのリンクのurl取得するための箱
data class QiitResponse(
    val url: String?,
    val title: String?,
    val user: User?
)

data class User(
    val id: String?
)