package com.example.onlinestore.ui.profileScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onlinestore.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private val viewModel: ProfileViewModel by viewModels()

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
            viewModel.profileUIState.observe(viewLifecycleOwner) { state ->
                tvUserName.text = "${state.user?.firstNAme} ${state.user?.lastName}"
                tvPhoneNumber.text = state.user?.phoneNumber

                btnToFavorites.setOnClickListener {
                    openFavoriteScreen()
                }

                val favoriteItemCount = state.items?.filter { it.isFavorite }?.size
                if (favoriteItemCount != null) {
                    val buttonText = if (favoriteItemCount > 0) {
                        val word = when {
                            favoriteItemCount % 10 == 1 && favoriteItemCount % 100 != 11 -> "товар"
                            favoriteItemCount % 10 in 2..4 && favoriteItemCount % 100 !in 12..14 -> "товара"
                            else -> "товаров"
                        }
                        "$favoriteItemCount $word"
                    } else {
                        ""
                    }
                    tvFavoriteCount.text = buttonText
                } else {
                    tvFavoriteCount.visibility = View.GONE
                }

                btnExit.setOnClickListener {
                    viewModel.deletedCache()
                    openLoginScreen()
                }
            }
        }
        viewModel.loadUser()
    }

    private fun openLoginScreen() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        )
    }

    private fun openFavoriteScreen() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToFavoriteFragment()
        )
    }
}