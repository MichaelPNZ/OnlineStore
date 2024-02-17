package com.example.onlinestore.ui.garbageScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlinestore.databinding.FragmentGarbageBinding


class GarbageFragment : Fragment() {

    private val binding by lazy {
        FragmentGarbageBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}