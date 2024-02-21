package com.example.onlinestore.ui.productScreen

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.onlinestore.R
import com.example.onlinestore.databinding.FragmentProductBinding
import com.example.onlinestore.ui.catalogScreen.ImagePagerAdapter

class ProductFragment : Fragment() {

    private val binding by lazy {
        FragmentProductBinding.inflate(layoutInflater)
    }
    private val viewModel: ProductViewModel by viewModels()
    private val productFragmentArgs: ProductFragmentArgs by navArgs()
    private val productDetailListAdapter = ProductDetailListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            viewModel.productUIState.observe(viewLifecycleOwner) { state ->
                btnBack.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }

                btnAddFavorite.setOnClickListener {
                    viewModel.onFavoritesClicked()
                }

                bindFavoriteIcon(state)
                tvTitle.text = state.item?.title
                tvSubTitle.text = state.item?.subtitle
                tvAvailable.text = state.item?.available?.let { formatAvailableString(it) }

                if (state.item?.feedback != null) {
                    ratingBar.rating = state.item!!.feedback.rating.toFloat()
                    tvRating.text = state.item!!.feedback.rating.toString()
                    tvFeedbackCount.text = formatReviewCountString(state.item!!.feedback.count)
                }

                tvPriceWithDiscount.text =
                    "${state.item?.price?.priceWithDiscount} ${state.item?.price?.unit}"
                tvPrice.text = "${state.item?.price?.price} ${state.item?.price?.unit}"
                tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvDiscount.text = state.item?.price?.discount.toString()
                btnNoPressTitle.text = state.item?.title
                tvDescription.text = state.item?.description

                btnHide.setOnClickListener {
                    if (btnNoPressTitle.visibility == View.VISIBLE) {
                        btnHide.text = "Скрыть"
                        btnNoPressTitle.visibility = View.GONE
                        tvDescription.visibility = View.GONE
                    } else {
                        btnHide.text = "Подробнее"
                        btnNoPressTitle.visibility = View.VISIBLE
                        tvDescription.visibility = View.VISIBLE
                    }
                }

                rvProductDetail.adapter = productDetailListAdapter
                productDetailListAdapter.submitList(state.item?.info)

                tvIngredients.text = state.item?.ingredients
                tvIngredients.maxLines = 2

                btnMore.setOnClickListener {
                    if (tvIngredients.maxLines == 2) {
                        tvIngredients.maxLines = Int.MAX_VALUE
                        btnMore.text = "Скрыть"
                    } else {
                        tvIngredients.maxLines = 2
                        btnMore.text = "Подробнее"
                    }
                }

                tvPriceWithDiscountOnButton.text =
                    "${state.item?.price?.priceWithDiscount} ${state.item?.price?.unit}"
                tvPriceOnButton.text =
                    "${state.item?.price?.price} ${state.item?.price?.unit}"
                tvPriceOnButton.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                val imageResource =
                    if (state.item?.isFavorite == true) R.drawable.heart_fill_icon
                    else R.drawable.heart_icon
                btnAddFavorite.setImageResource(imageResource)
            }
        }

        viewModel.loadProduct(productFragmentArgs.productId)
    }

    private fun formatReviewCountString(feedbackCount: Int): String {
        val lastDigit = feedbackCount % 10
        val lastTwoDigits = feedbackCount % 100

        return when {
            lastTwoDigits in 11..14 -> "$feedbackCount отзывов"
            lastDigit == 1 -> "$feedbackCount отзыв"
            lastDigit in 2..4 -> "$feedbackCount отзыва"
            else -> "$feedbackCount отзывов"
        }
    }

    private fun formatAvailableString(available: Int): String {
        val lastDigit = available % 10
        val lastTwoDigits = available % 100

        return when {
            lastTwoDigits in 11..14 -> "Доступно для заказа $available штук"
            lastDigit == 1 -> "Доступно для заказа $available штука"
            lastDigit in 2..4 -> "Доступно для заказа $available штуки"
            else -> "Доступно для заказа $available штук"
        }
    }

    private fun bindFavoriteIcon(state: ProductViewModel.ProductUIState) {
        when (state.item?.id) {
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
    }

    private fun bindImages(images: List<Int>) {
        val adapter = context?.let { ImagePagerAdapter(it, images) }
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.setViewPager(binding.viewPager)
    }
}