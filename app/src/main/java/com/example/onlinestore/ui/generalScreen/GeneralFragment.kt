package com.example.onlinestore.ui.generalScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.onlinestore.R
import com.example.onlinestore.data.ItemsRepository
import com.example.onlinestore.databinding.FragmentGeneralBinding
import kotlinx.coroutines.launch

class GeneralFragment : Fragment() {

    private val binding by lazy {
        FragmentGeneralBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val user = context?.let { ItemsRepository(it).getUserFromCache() }
            if (user == null) {
                findNavController().navigate(R.id.action_generalFragment_to_loginFragment)
            }
        }
    }
}