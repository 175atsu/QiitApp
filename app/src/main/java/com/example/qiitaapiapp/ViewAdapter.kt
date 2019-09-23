package com.example.qiitaapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(private val list: List<Model>, private val listener: ListListener): RecyclerView.Adapter<ViewHolder>() {
    //レイアウト
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowView = layoutInflater.inflate(R.layout.row, parent, false)
        return ViewHolder(rowView)
    }

    //データ
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text =  list[position].title
        holder.userName.text = list[position].id
        holder.itemView.setOnClickListener {
            listener.onClickRow(it,list[position])
        }
    }

    //リストの数取得
    override fun getItemCount(): Int {
        return list.size
    }

    //質問
    interface ListListener {
        fun onClickRow(tappedView: View, rowModel: Model)
    }
}