package com.example.onlinestore.ui.productScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestore.R
import com.example.onlinestore.model.Info

class ProductDetailListAdapter :ListAdapter<Info, ProductDetailListAdapter.ViewHolder>(
    object :DiffUtil.ItemCallback<Info>() {
        override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean {
            return oldItem == newItem
        }
    }
) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNameDetail: TextView = view.findViewById(R.id.tvNameDetail)
        val tvValueDetail: TextView = view.findViewById(R.id.tvValueDetail)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product_detail, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.tvNameDetail.text = currentItem.title
        holder.tvValueDetail.text = currentItem.value
    }
}