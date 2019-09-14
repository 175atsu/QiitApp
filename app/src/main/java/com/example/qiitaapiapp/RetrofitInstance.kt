package com.example.qiitaapiapp

//import com.example.qiitaapiapp.data.network.ApiService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.qiitaapiapp.data.network.ApiService



class RetrofitInstance {

    //okhttpのclient作成
    val httpClient = OkHttpClient.Builder()
    val httpBuilder: OkHttpClient.Builder get() {
        // create http client
            httpClient.addInterceptor(Interceptor { chain ->
                val original = chain.request()

                //header
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()

                return@Interceptor chain.proceed(request)
            })
            .readTimeout(30, TimeUnit.SECONDS)

        //log interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }


    // core for controller
    //val service: ApiService = create(ApiService::class.java)

//    lateinit var retrofit: Retrofit
//
//    fun <S> create(serviceClass: Class<S>): S {
//        val gson = GsonBuilder()
//            .serializeNulls()
//            .create()
//        // create retrofit
//        retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .baseUrl("https : //qiita.com/") // Put your base URL
//            .client(httpBuilder.build())
//            .build()
//
//        return retrofit.create(serviceClass)
//    }

    //クライアント生成
    var client = httpBuilder.build()
    var retrofit = Retrofit.Builder()
        .baseUrl("https : //qiita.com/")//基本のurl設定
        .addConverterFactory(GsonConverterFactory.create())//Gsonの使用
        .client(client)//カスタマイズしたokhttpのクライアントの設定
        .build()
    //Interfaceから実装を取得
    var API = retrofit.create(ApiService::class.java)

}