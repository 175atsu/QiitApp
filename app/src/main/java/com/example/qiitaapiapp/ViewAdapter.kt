package com.example.qiitaapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util

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
        //holder.userImage = list[position].image
        Glide.with(holder.itemView)
            .load(list[position].image)
            .error(android.R.drawable.btn_star_big_on)
            .into(holder.userImage)

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