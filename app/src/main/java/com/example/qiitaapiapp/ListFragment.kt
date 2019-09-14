package com.example.qiitaapiapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.io.IOException

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
    }

    fun set() {

        try {
            val response = Retrofit.API.apiDemo().execute()
            if (response.isSuccessful()) {
                return response.body()
            } else {
                // failed
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}