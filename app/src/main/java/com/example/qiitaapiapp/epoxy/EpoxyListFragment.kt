package com.example.qiitaapiapp.epoxy


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiitaapiapp.*
import kotlinx.android.synthetic.main.fragment_epoxy_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EpoxyListFragment : Fragment() {

    var mContext: Context? = null
    val Retrofit = RetrofitInstance()

    //謎
    companion object {
        fun createInstance(mc: Context): EpoxyListFragment {
            val listFragment = EpoxyListFragment()
            listFragment.mContext = mc
            return listFragment
        }
    }


    private val controller by lazy {
        EpoxyListController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_epoxy_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recyclerViewInitialSetting()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        epoxyRecyclerView.let {
            it.adapter = controller.adapter
            it.layoutManager = LinearLayoutManager(this.activity, RecyclerView.VERTICAL, false)
            it.addItemDecoration(
                DividerItemDecoration(
                    this.activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        getData()
    }

    private fun getData() = searchGitHubRepositoryByCoroutines()

    //coroutine利用
    suspend fun qiitRepositoriesByCoroutines(
        page: Int,
        perPage: Int
    ): List<QiitResponse> {
        return Retrofit.createService().apiDemo(page = page, perPage = perPage)
    }

    val coroutineScope = CoroutineScope(context = Dispatchers.Main)
    fun searchGitHubRepositoryByCoroutines() {
        val dataList = mutableListOf<EpoxyModel>()
        Log.d("テスト1", dataList.toString())
        coroutineScope.launch {
            try {
                val qiitaRepositoriesData = qiitRepositoriesByCoroutines(
                    page = 1,
                    perPage = 20
                )
                qiitaRepositoriesData.let {
                    for (item in it) {
                        val data: EpoxyModel = EpoxyModel()
                            .also {
                                it.title = item.title
                                it.url = item.url
                                it.id = item.user?.id
                                it.image = item.user?.profile_image_url
                            }
                        dataList.add(data)
                        Log.d("テスト2", dataList.toString())
                    }
                    //更新
                    controller.list = dataList
                }
            } catch (e: HttpException) {
                Log.d("TAGres", "onFailure")
                // リクエスト失敗時の処理を行う
            }
        }
        Log.d("テスト3", dataList.toString())
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
