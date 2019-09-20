package com.example.qiitaapiapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemeView: View): RecyclerView.ViewHolder(itemeView) {

    val titleView: TextView = itemeView.findViewById(R.id.title)
    val userName: TextView = itemeView.findViewById(R.id.user_name)

}