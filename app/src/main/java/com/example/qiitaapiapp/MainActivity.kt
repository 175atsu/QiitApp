package com.example.qiitaapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.qiitaapiapp.epoxy.EpoxyListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.detailContainer, EpoxyListFragment.createInstance(this))
            transaction.commit()
        }
    }
}
