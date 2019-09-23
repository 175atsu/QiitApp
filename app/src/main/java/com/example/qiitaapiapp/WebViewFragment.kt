package com.example.qiitaapiapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class WebViewFragment : Fragment() {


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

    fun webShow(){
        val url = getArguments()!!.getString("URL")
        Log.d("TAGurl",url)
        val webView: WebView = view!!.findViewById(R.id.webView)
        webView.loadUrl(url)

    }
}
