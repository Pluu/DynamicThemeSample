package com.pluu.theme.sample.ui.main.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pluu.theme.sample.databinding.FragmentNotificationsBinding
import com.pluu.theme.sample.ui.light.LightActivity
import com.pluu.theme.sample.ui.main.MainFragmentProvider
import com.pluu.theme.sample.ui.setting.SettingActivity
import com.pluu.theme.sample.utils.showToast

class NotificationsFragment : Fragment(), MainFragmentProvider {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoSetting.setOnClickListener {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }
        binding.btnGoLight.setOnClickListener {
            startActivity(Intent(requireContext(), LightActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRestart() {
        showToast("Restart Notifications")
    }

    override fun onReselected() {
        showToast("Reselected Notifications")
    }
}