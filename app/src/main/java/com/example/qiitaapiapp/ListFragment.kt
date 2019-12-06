package com.example.qiitaapiapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ListFragment: Fragment() {

    var mContext: Context? = null
    val Retrofit = RetrofitInstance()
    private val mUser: ViewModel = ViewModel()

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
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInitialSetting()
    }

    //アダプターなどなどの設定
    fun recyclerViewInitialSetting() {
        val rv = recyclerView
        val adapter = ViewAdapter(searchGitHubRepositoryByCoroutines(), object : ViewAdapter.ListListener {
            override fun onClickRow(tappedView: View, userListModel: ViewModel) {
                toDetail(userListModel)
            }
        })
        //リストのtrueコンテンツの大きさがデータによって変わらないならを渡す。これをRecyclerViewにいつもすることで、パフォーマンスが良くなる。
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.adapter = adapter

    }

    //データリストに保存し、そのデータの取得
//    fun fetchAllUserData(): List<ViewModel> {
//
//        val dataList = mutableListOf<ViewModel>()
//        //リクエストURl作成してデータとる
//        Retrofit.createService().apiDemo(page = 1, perPage = 20).enqueue(object : Callback<List<QiitResponse>> {
//
//            //非同期処理
//            override fun onResponse(call: Call<List<QiitResponse>>, response: Response<List<QiitResponse>>) {
//                Log.d("TAGres","onResponse")
//
//                //ステータスコードが200：OKなので、ここではちゃんと通信できたよー
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        for (item in it) {
//                            val data: ViewModel = ViewModel().also {
//                                it.title = item.title
//                                it.url = item.url
//                                it.id = item.user!!.id
//                                Log.d("TAGtitle", it.title)
//                                Log.d("TAGname",it.id)
//                            }
//                            dataList.add(data)
//                        }
//                        //更新
//                        recyclerView.adapter?.notifyDataSetChanged()
//                    }
//                } else {
//                }
//            }
//            override fun onFailure(call: Call<List<QiitResponse>>, t: Throwable) {
//                Log.d("TAGres","onFailure")
//            }
//        })
//        return dataList
//    }

    //coroutine利用
    suspend fun qiitRepositoriesByCoroutines(
        page: Int,
        perPage: Int
    ): List<QiitResponse> {
        return Retrofit.createService().apiDemo(page = page, perPage = perPage)
    }

    val coroutineScope = CoroutineScope(context = Dispatchers.Main)
    fun searchGitHubRepositoryByCoroutines(): List<ViewModel> {
        val dataList = mutableListOf<ViewModel>()
        coroutineScope.launch {
            try {
                val qiitaRepositoriesData = qiitRepositoriesByCoroutines(
                    page = 1,
                    perPage = 20
                )
                qiitaRepositoriesData.let {
                    for (item in it) {
                        val data: ViewModel = ViewModel().also {
                            it.title = item.title
                            it.url = item.url
                            it.id = item.user?.id
                            it.image = item.user?.profile_image_url
                        }
                        dataList.add(data)
                    }
                    //更新
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            } catch (e: HttpException) {
                Log.d("TAGres","onFailure")
                // リクエスト失敗時の処理を行う
            }
        }
        return dataList
    }


    //詳細ページへの遷移
    fun toDetail(urlData: ViewModel) {
        val fragment = WebViewFragment()
        val bundle = Bundle().apply {
            putString("URL", urlData.url)
        }
        fragment.setArguments(bundle)
        // FragmentをFragmentManagerにセットする
        getFragmentManager()!!.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.detailContainer, fragment)
            .commit()
    }
}