package com.pluu.theme.sample.ui.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pluu.theme.sample.databinding.ActivitySettingBinding
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            showSelectTheme()
        }

        showSelectTheme()
    }

    private fun showSelectTheme() {
        AlertDialog.Builder(this)
            .setSingleChoiceItems(
                Theme.entries.map { it.name }.toTypedArray(),
                viewModel.currentTheme.ordinal,
            ) { dialogInterface, item ->
                viewModel.setTheme(Theme.of(item))
                dialogInterface.dismiss()
            }.show()
    }
}