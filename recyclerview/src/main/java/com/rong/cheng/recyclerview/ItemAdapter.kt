package com.rong.cheng.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val context: Context?, private val starList: List<Star>?) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.rv_item_star, null)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tv.text = starList!![position].name
    }

    override fun getItemCount(): Int {
        return starList?.size ?: return 0
    }

    /**
     * 当前是否为头
     */
    fun isGroupHeader(position: Int): Boolean {
        if (position == 0) return true

        starList?.apply {
            val currentGroupName: String = get(position).groupName
            val preGroupName: String = get(position - 1).groupName
            return preGroupName != currentGroupName
        }
        return false
    }

    fun getGroupName(position: Int): String {
        starList?.apply {
            return get(position).groupName
        }.also {
            return ""
        }

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView.findViewById(R.id.tv_start)

    }
}
