package com.example.onlinestore.ui.catalogScreen

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.onlinestore.R
import com.example.onlinestore.model.Item
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class CatalogListAdapter : ListAdapter<Item, CatalogListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var itemClickListener: OnItemClickListener? = null
    private var favoriteClickListener: OnFavoriteClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(productId: String)
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(productId: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnFavoriteClickListener(listener: OnFavoriteClickListener) {
        favoriteClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        private val dotsIndicator: DotsIndicator = view.findViewById(R.id.dots_indicator)
        val cvProduct: CardView = view.findViewById(R.id.cvProduct)
        val btnAddFavorite: ImageButton = view.findViewById(R.id.btnAddFavorite)
        val tvOldPrice: TextView = view.findViewById(R.id.tvOldPrice)
        val tvPriceWithDiscount: TextView = view.findViewById(R.id.tvPriceWithDiscount)
        val tvDiscount: TextView = view.findViewById(R.id.tvDiscount)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvSubtitle)
        val tvFeedback: TextView = view.findViewById(R.id.tvFeedback)
        val tvFeedbackCount: TextView = view.findViewById(R.id.tvFeedbackCount)

        fun bindImages(images: List<Int>) {
            val adapter = ImagePagerAdapter(itemView.context, images)
            viewPager.adapter = adapter
            dotsIndicator.setViewPager(viewPager)
        }

        fun bindFavoriteIcon(isFavorite: Boolean) {
            val imageResource =
                if (isFavorite) R.drawable.heart_fill_icon else R.drawable.heart_icon
            btnAddFavorite.setImageResource(imageResource)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        with(holder) {
            bindFavoriteIcon(currentItem.isFavorite)
            when (currentItem.id) {
                "cbf0c984-7c6c-4ada-82da-e29dc698bb50" ->
                    bindImages(listOf(R.drawable.img_vox, (R.drawable.img_eveline)))

                "54a876a5-2205-48ba-9498-cfecff4baa6e" ->
                    bindImages(listOf(R.drawable.img_deep, (R.drawable.img_body)))

                "75c84407-52e1-4cce-a73a-ff2d3ac031b3" ->
                    bindImages(listOf(R.drawable.img_eveline, (R.drawable.img_vox)))

                "16f88865-ae74-4b7c-9d85-b68334bb97db" ->
                    bindImages(listOf(R.drawable.img_deco, (R.drawable.img_handmask)))

                "26f88856-ae74-4b7c-9d85-b68334bb97db" ->
                    bindImages(listOf(R.drawable.img_body, (R.drawable.img_deco)))

                "15f88865-ae74-4b7c-9d81-b78334bb97db" ->
                    bindImages(listOf(R.drawable.img_vox, (R.drawable.img_deep)))

                "88f88865-ae74-4b7c-9d81-b78334bb97db" ->
                    bindImages(listOf(R.drawable.img_handmask, (R.drawable.img_deco)))

                "55f58865-ae74-4b7c-9d81-b78334bb97db" ->
                    bindImages(listOf(R.drawable.img_deep, (R.drawable.img_eveline)))
            }

            tvOldPrice.text = "${currentItem.price.price} ${currentItem.price.unit}"
            tvOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            tvPriceWithDiscount.text =
                "${currentItem.price.priceWithDiscount} ${currentItem.price.unit}"

            tvDiscount.text = currentItem.price.discount.toString()
            tvTitle.text = currentItem.title
            tvSubtitle.text = currentItem.subtitle
            tvFeedback.text = currentItem.feedback.rating.toString()
            tvFeedbackCount.text = currentItem.feedback.count.toString()

            btnAddFavorite.setOnClickListener {
                favoriteClickListener?.onFavoriteClick(currentItem.id)
            }

            cvProduct.setOnClickListener {
                itemClickListener?.onItemClick(currentItem.id)
            }
        }
    }
}