package com.pluu.theme.sample.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pluu.theme.sample.R
import com.pluu.theme.sample.databinding.ActivityMainBinding
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.model.Theme.DEFAULT
import com.pluu.theme.sample.model.Theme.Pluu
import com.pluu.theme.sample.model.Theme.PluuDark
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var currentTheme: Theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentTheme = viewModel.currentTheme
        updateForTheme(currentTheme)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.theme.collect { theme ->
                        if (currentTheme != theme) {
                            currentTheme = theme
                            updateForTheme(theme)
                            recreate()
                        }
                    }
                }
            }
        }
    }

    private fun updateForTheme(currentTheme: Theme) {
        val theme = when (currentTheme) {
            DEFAULT -> R.style.Theme_DynamicThemeSample
            Pluu -> R.style.Pluu
            PluuDark -> R.style.Pluu_Dark
        }
        setTheme(theme)
    }
}