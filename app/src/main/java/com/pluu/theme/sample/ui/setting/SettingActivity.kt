package com.pluu.theme.sample.ui.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.pluu.theme.sample.databinding.ActivitySettingBinding
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.ui.theme.ThemedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : ThemedActivity() {

    @StyleRes
    override var themeId: Int = 0

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            showSelectTheme()
        }
    }

    private fun showSelectTheme() {
        AlertDialog.Builder(this)
            .setSingleChoiceItems(
                Theme.entries.map { it.name }.toTypedArray(),
                currentTheme.ordinal,
            ) { dialogInterface, item ->
                viewModel.setTheme(Theme.of(item))
                dialogInterface.dismiss()
            }.show()
    }
}