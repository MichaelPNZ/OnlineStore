package com.example.onlinestore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.onlinestore.R
import com.example.onlinestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }
}

//class MainActivity : AppCompatActivity() {
//
//    private var _binding: ActivityMainBinding? = null
//    private val binding
//        get() = _binding
//            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")
//    private lateinit var itemsRepository: ItemsRepository
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        itemsRepository = ItemsRepository(application)
//
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//
//        if (checkUserInDatabase()) {
//            binding.bottomNav.setupWithNavController(navController)
//        } else {
//            navController.navigate(R.id.loginFragment)
//        }
//    }
//
//    private fun checkUserInDatabase(): Boolean {
//        var result = false
//        lifecycleScope.launch {
//            val user = itemsRepository.getUserFromCache()
//            if (user != null) result = true
//        }
//        return result
//    }
//}