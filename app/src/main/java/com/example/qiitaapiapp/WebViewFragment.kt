package com.example.qiitaapiapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback

class WebViewFragment : Fragment() {

    val mainActivity: MainActivity
        get() = (activity as MainActivity)
    val isOverrideBack = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webShow()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainActivity.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback {
            override fun handleOnBackPressed(): Boolean {
                if (isOverrideBack) {
                    return true
                }else {
                    return false
                }
            }
        })
    }

    fun webShow(){
        val url = getArguments()!!.getString("URL")
        Log.d("TAGurl",url)
        val webView: WebView = view!!.findViewById(R.id.webView)
        webView.loadUrl(url)

    }
}
