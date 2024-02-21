package com.example.onlinestore.ui.catalogScreen

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinestore.R
import com.example.onlinestore.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment() {

    private val binding by lazy {
        FragmentCatalogBinding.inflate(layoutInflater)
    }
    private val viewModel: CatalogViewModel by viewModels()
    private val catalogListAdapter = CatalogListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        catalogListAdapter.setOnItemClickListener(object :
            CatalogListAdapter.OnItemClickListener {
            override fun onItemClick(productId: String) {
                openProductById(productId)
            }
        })

        catalogListAdapter.setOnFavoriteClickListener(object :
            CatalogListAdapter.OnFavoriteClickListener {
            override fun onFavoriteClick(productId: String) {
                viewModel.onFavoritesClicked(productId)
            }
        })
        binding.rvCatalog.adapter = catalogListAdapter

        viewModel.catalogUIState.observe(viewLifecycleOwner) { state ->
            catalogListAdapter.submitList(state.itemsList.sortedBy { it.feedback.rating }
                .reversed())

            popupMenuShow(state)
            chipClicked(state)
        }
        viewModel.loadCatalog()
    }

    private fun chipClicked(state: CatalogViewModel.CatalogUIState) {
        with(binding) {
            chipSeeAll.setOnCheckedChangeListener { _, isChecked ->
                catalogListAdapter.submitList(state.itemsList)
                chipFace.isChecked = false
                chipBody.isChecked = false
                chipTan.isChecked = false
                chipMasks.isChecked = false
                if (isChecked) {
                    chipSeeAll.setChipBackgroundColorResource(R.color.darkBlue)
                    chipSeeAll.setTextColor(Color.WHITE)
                    chipSeeAll.isCloseIconVisible = true
                } else {
                    chipSeeAll.setChipBackgroundColorResource(R.color.lightGrey)
                    chipSeeAll.isCloseIconVisible = false
                    chipSeeAll.setTextColor(Color.GRAY)
                }
            }

            chipFace.setOnCheckedChangeListener { _, isChecked ->
                filterItemsByTag("face", state)
                chipSeeAll.isChecked = false
                chipBody.isChecked = false
                chipTan.isChecked = false
                chipMasks.isChecked = false
                if (isChecked) {
                    chipFace.setChipBackgroundColorResource(R.color.darkBlue)
                    chipFace.setTextColor(Color.WHITE)
                    chipFace.isCloseIconVisible = true
                } else {
                    chipFace.setChipBackgroundColorResource(R.color.lightGrey)
                    chipFace.isCloseIconVisible = false
                    chipFace.setTextColor(Color.GRAY)
                }
            }

            chipBody.setOnCheckedChangeListener { _, isChecked ->
                filterItemsByTag("body", state)
                chipSeeAll.isChecked = false
                chipFace.isChecked = false
                chipTan.isChecked = false
                chipMasks.isChecked = false
                if (isChecked) {
                    chipBody.setChipBackgroundColorResource(R.color.darkBlue)
                    chipBody.setTextColor(Color.WHITE)
                    chipBody.isCloseIconVisible = true
                } else {
                    chipBody.setChipBackgroundColorResource(R.color.lightGrey)
                    chipBody.isCloseIconVisible = false
                    chipBody.setTextColor(Color.GRAY)
                }
            }

            chipTan.setOnCheckedChangeListener { _, isChecked ->
                filterItemsByTag("suntan", state)
                chipSeeAll.isChecked = false
                chipFace.isChecked = false
                chipBody.isChecked = false
                chipMasks.isChecked = false
                if (isChecked) {
                    chipTan.setChipBackgroundColorResource(R.color.darkBlue)
                    chipTan.setTextColor(Color.WHITE)
                    chipTan.isCloseIconVisible = true
                } else {
                    chipTan.setChipBackgroundColorResource(R.color.lightGrey)
                    chipTan.isCloseIconVisible = false
                    chipTan.setTextColor(Color.GRAY)
                }
            }

            chipMasks.setOnCheckedChangeListener { _, isChecked ->
                filterItemsByTag("mask", state)
                chipSeeAll.isChecked = false
                chipFace.isChecked = false
                chipBody.isChecked = false
                chipTan.isChecked = false
                if (isChecked) {
                    chipMasks.setChipBackgroundColorResource(R.color.darkBlue)
                    chipMasks.setTextColor(Color.WHITE)
                    chipMasks.isCloseIconVisible = true
                } else {
                    chipMasks.setChipBackgroundColorResource(R.color.lightGrey)
                    chipMasks.isCloseIconVisible = false
                    chipMasks.setTextColor(Color.GRAY)
                }
            }
        }
    }

    private fun filterItemsByTag(tag: String, state: CatalogViewModel.CatalogUIState) {
        val filteredItems = state.itemsList.filter { it.tags.contains(tag) }
        catalogListAdapter.submitList(filteredItems)
    }

    private fun popupMenuShow(state: CatalogViewModel.CatalogUIState) {
        binding.btnSorted.setOnClickListener {
            val popupMenu = android.widget.PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.sorted_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popular_sort -> {
                        val rating = state.itemsList
                            .sortedBy { it.feedback.rating }
                            .reversed()
                        catalogListAdapter.submitList(rating)
                        binding.btnSorted.setText(R.string.option_1)
                        true
                    }

                    R.id.price_desc_sort -> {
                        val rating = state.itemsList
                            .sortedBy { it.price.priceWithDiscount.toInt() }
                            .reversed()
                        catalogListAdapter.submitList(rating)
                        binding.btnSorted.setText(R.string.option_2)
                        true
                    }

                    R.id.price_asc_sort -> {
                        val rating = state.itemsList
                            .sortedBy { it.price.priceWithDiscount.toInt() }
                        catalogListAdapter.submitList(rating)
                        binding.btnSorted.setText(R.string.option_3)
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun openProductById(productId: String) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToProductFragment(productId)
        )
    }
}