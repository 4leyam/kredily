package com.example.admin

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.admin.databinding.FragmentSettingsBinding
import com.example.common.BBaseFragment

class SettingsFragment : BBaseFragment<FragmentSettingsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun setup() {
        binding?.apply {

            this.header.back.setOnClickListener {
                findNavController().popBackStack()
            }

            this.loginForm.keyDesc.setOnClickListener {
                val toOtp = SettingsFragmentDirections.actionSettingsFragmentToOTPFragment()
                findNavController().navigate(toOtp)
            }

            this.logout.setOnClickListener {

                activity?.getPreferences(Context.MODE_PRIVATE)?.let {
                    with (it.edit()) {
                        putInt("id", -1)
                        apply()

                        val uri = Uri.parse("android-app://com.example.kredily/login")
                        findNavController().let { nav ->
                            nav.navigate(uri , NavOptions.Builder()
                                .setPopUpTo("/",
                                    true).build())
                        }
                    }
                }

            }
        }
    }
}