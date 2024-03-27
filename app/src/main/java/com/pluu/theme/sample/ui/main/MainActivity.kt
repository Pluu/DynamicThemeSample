package com.pluu.theme.sample.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pluu.theme.sample.R
import com.pluu.theme.sample.databinding.ActivityMainBinding
import com.pluu.theme.sample.utils.ThemeHelper
import com.pluu.theme.sample.utils.ThemeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ThemeProvider {

    @StyleRes
    override var themeId: Int = 0

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this, viewModel.currentTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

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

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.theme.collect { theme ->
                        ThemeHelper.setCorrectTheme(this@MainActivity, theme)
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        (findVisibleFragment() as? MainFragmentProvider)?.onRestart()
    }

    override fun setTheme(resId: Int) {
        super.setTheme(resId)
        this.themeId = resId
    }

    private fun findVisibleFragment(): Fragment? {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments.firstOrNull()
    }
}