package com.example.adrian012_bbdd.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.adrian012_bbdd.R
import com.example.adrian012_bbdd.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * WARNING!!!! Don't forget to remove the line below (setContentView(R.layout.activity_main)
         * when using binding, otherwise it will cause problems in the view.
         */
        //setContentView(R.layout.activity_main)  <--- ¡REMOVE REMOVE REMOVE REMOVE!

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Tools
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.myToolbar.setupWithNavController(navController, appBarConfiguration)
    }
}