package com.example.qiitaapiapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.qiitaapiapp.data.network.QiitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment: Fragment() {

    var mContext :Context? = null
    val Retrofit = RetrofitInstance()

    //謎
    companion object {
        fun createInstance( mc : Context): ListFragment {
            val listFragment = ListFragment()
            listFragment.mContext = mc
            return listFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getItemList { QiitRespons ->
            Log.d("test","$QiitRespons")
        }
    }

    fun getItemList(callback: (List<QiitResponse>) -> Unit) {
        Retrofit.API.apiDemo().enqueue(object : Callback<List<QiitResponse>> {

            override fun onResponse(call: Call<List<QiitResponse>>, response: Response<List<QiitResponse>>) {

                //ステータスコードが200：OKなので、ここではちゃんと通信できたよー
                if (response.isSuccessful) {
                    println("aa")
                    response.body()
                } else {

                }
            }

//            override fun onResponse(Call<List<QiitResponse>>, Response<List<QiitResponse>>) {
//                if (response.isSuccessful()) {
//                    //通信結果をオブジェクトで受け取る
//                    QiitResponse demo = response.body();
//                    Log.d("RETROFIT_TEST", demo.info.seed);
//                } else {
//                    //通信が成功したが、エラーcodeが返ってきた場合はこちら
//                    Log.d("RETROFIT_TEST", "error_code" + response.code());
//                }
//            }

            override fun onFailure(call: Call<List<QiitResponse>>, t: Throwable) {

            }
        })
    }

}