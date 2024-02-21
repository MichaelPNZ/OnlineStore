package com.example.onlinestore.ui.favoriteScreen

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinestore.databinding.FragmentFavoriteBinding
import com.example.onlinestore.ui.catalogScreen.CatalogListAdapter

class FavoriteFragment : Fragment() {

    private val binding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }
    private val viewModel: FavoriteViewModel by viewModels()
    private val catalogListAdapter = CatalogListAdapter()

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

            rvCatalogFavorite.adapter = catalogListAdapter
            btnBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            viewModel.favoriteUIState.observe(viewLifecycleOwner) { state ->

                catalogListAdapter.submitList(state.itemsList)

                btnProductSelect.setOnClickListener {
                    btnProductSelect.setTextColor(Color.BLACK)
                    btnProductSelect.setBackgroundColor(Color.WHITE)

                    btnBrandSelect.setTextColor(Color.GRAY)
                    btnBrandSelect.setBackgroundColor(Color.TRANSPARENT)

                    catalogListAdapter.submitList(state.itemsList)
                }

                btnBrandSelect.setOnClickListener {
                    btnBrandSelect.setTextColor(Color.BLACK)
                    btnBrandSelect.setBackgroundColor(Color.WHITE)

                    btnProductSelect.setTextColor(Color.GRAY)
                    btnProductSelect.setBackgroundColor(Color.TRANSPARENT)

                    catalogListAdapter.submitList(emptyList())
                }
            }
        }
        viewModel.loadFavorite()
    }

    private fun openProductById(productId: String) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToProductFragment(productId))
    }
}