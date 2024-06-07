package com.pluu.theme.sample.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pluu.theme.sample.R
import com.pluu.theme.sample.databinding.ActivityMainBinding
import com.pluu.theme.sample.ui.base.ThemedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ThemedActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, d, _ ->
            // Update destination
            prefetch()
        }

        binding.navView.setupWithNavController(navController)
        binding.navView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications -> {
                    (findVisibleFragment() as? MainFragmentProvider)?.onReselected()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        (findVisibleFragment() as? MainFragmentProvider)?.onRestart()
    }

    private fun findVisibleFragment(): Fragment? {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments.firstOrNull()
    }
}