package com.example.qiitaapiapp


import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInstance {

    private var retrofit: Retrofit
    private val url = "https://qiita.com"

    init {
        //Moshi
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        //クライアント生成
        //var client = httpBuilder.build()
        this.retrofit = Retrofit.Builder()
            .baseUrl(url)//基本のurl設定
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getClient())//カスタマイズしたokhttpのクライアントの設定
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    //Clientを作成、Moshi利用しない場合
    val httpBuilder: OkHttpClient.Builder get() {
        //httpClinetのBuilderの中に入ってるメソッド使う？
        val httpClient = OkHttpClient.Builder()
        //headerの追加
            httpClient.addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()

                var response = chain.proceed(request)

                return@Interceptor response
            })
            .readTimeout(30, TimeUnit.SECONDS)

        //log
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }

    fun createService(): ApiService {
        //Interfaceから実装を取得
        var API = retrofit.create(ApiService::class.java)
        return API
    }
}