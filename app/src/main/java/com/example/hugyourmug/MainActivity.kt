package com.example.hugyourmug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.hugyourmug.data.repository.MenuSeeder
import com.example.hugyourmug.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment

        val navController = navHostFragment.navController

        val navOptions = NavOptions.Builder()
            .setPopUpTo(navController.graph.startDestinationId, false)
            .setLaunchSingleTop(true)
            .build()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId, null, navOptions)
            true
        }

        lifecycleScope.launch {
            MenuSeeder().seedOrUpdateMoods()
        }
    }
}
