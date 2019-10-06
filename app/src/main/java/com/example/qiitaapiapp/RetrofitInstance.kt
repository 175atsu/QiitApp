package com.example.qiitaapiapp


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.qiitaapiapp.data.network.ApiService



class RetrofitInstance {

    //Clientを作成
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
        //クライアント生成
        var client = httpBuilder.build()
        var retrofit = Retrofit.Builder()
            .baseUrl("https://qiita.com/")//基本のurl設定
            .addConverterFactory(GsonConverterFactory.create())//Gsonの使用
            .client(client)//カスタマイズしたokhttpのクライアントの設定
            .build()
        //Interfaceから実装を取得
        var API = retrofit.create(ApiService::class.java)

        return API
    }
}