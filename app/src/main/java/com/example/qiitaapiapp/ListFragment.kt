package com.example.qiitaapiapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiitaapiapp.data.network.QiitResponse
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment: Fragment() {

    var mContext: Context? = null
    val Retrofit = RetrofitInstance()

    //謎
    companion object {
        fun createInstance(mc: Context): ListFragment {
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

        recyclerViewInitialSetting()
    }

    fun getItemList(callback: (List<QiitResponse>) -> Unit) {
        Retrofit.createService().apiDemo(page = 1, perPage = 20).enqueue(object : Callback<List<QiitResponse>> {

            //非同期処理
            override fun onResponse(call: Call<List<QiitResponse>>, response: Response<List<QiitResponse>>) {

                //ステータスコードが200：OKなので、ここではちゃんと通信できたよー
                if (response.isSuccessful) {
                    response.body()?.let {
                        val dataList = mutableListOf<String?>()
                        for (item in it) {
                            dataList.add(item.url)
                        }
                    }
                    Log.d("test1", "aaa")
                } else {
                    Log.d("test2", "err")
                }
            }

            override fun onFailure(call: Call<List<QiitResponse>>, t: Throwable) {

            }
        })
    }

    //アダプターなどなどの設定
    fun recyclerViewInitialSetting() {
        val rv = recyclerView
        val adapter = ViewAdapter(fetchAllUserData(), object : ViewAdapter.ListListener {
            override fun onClickRow(tappedView: View, userListModel: Model) {
                //onClickRow2(tappedView, userListModel)
            }
        })
        //リストのtrueコンテンツの大きさがデータによって変わらないならを渡す。これをRecyclerViewにいつもすることで、パフォーマンスが良くなる。
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(activity)
        //rv.layoutManager = GridLayoutManager(this, 1)
        rv.adapter = adapter

    }

    //データリストに保存し、そのデータの取得
    fun fetchAllUserData(): List<Model> {

        val dataList = mutableListOf<Model>()
        //リクエストURl作成してデータとる
        Retrofit.createService().apiDemo(page = 1, perPage = 20).enqueue(object : Callback<List<QiitResponse>> {

            //非同期処理
            override fun onResponse(call: Call<List<QiitResponse>>, response: Response<List<QiitResponse>>) {

                //ステータスコードが200：OKなので、ここではちゃんと通信できたよー
                if (response.isSuccessful) {
                    Log.d("TAG3",response.toString())
                    response.body()?.let {
                        for (item in it) {
                            val data: Model = Model().also {
                                it.title = item.title
                            }
                            dataList.add(data)
                        }
                    }
                    Log.d("test1", "aaa")
                } else {
                    Log.d("test2", "err")
                }
            }
            override fun onFailure(call: Call<List<QiitResponse>>, t: Throwable) {
            }
        })
        return dataList
    }
}