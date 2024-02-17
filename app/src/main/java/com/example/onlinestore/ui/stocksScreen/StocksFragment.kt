package com.example.onlinestore.ui.stocksScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlinestore.databinding.FragmentStocksBinding


class StocksFragment : Fragment() {

    private val binding by lazy {
        FragmentStocksBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}