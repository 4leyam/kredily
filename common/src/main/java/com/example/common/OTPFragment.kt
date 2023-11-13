package com.example.common

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.findFragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.common.databinding.FragmentOTPBinding


class OTPFragment : BBaseFragment<FragmentOTPBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOTPBinding
        get() = FragmentOTPBinding::inflate

    override fun setup() {

        binding?.apply {
            this.header.back.setOnClickListener {
                it.findNavController().popBackStack()
            }

            childFragmentManager.fragments.filterIsInstance<OTPInputFragment>().first().let {
                it.linkToHostFragment("Please enter the OTP sent to your " +
                        "email V****@gmail.com and your phone number 9******802" ,
                    onChange = { canProceed ->
                               this@OTPFragment.binding?.canProceed = canProceed
                    } , onVerify = { _ , _ ->})
            }

            this.proceed.setOnClickListener {
                val id = arguments?.getInt("userId") ?: -1
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://com.example.kredily/passFragment/$id".toUri())
                    .build()
                findNavController().apply {
                    navigate(request , NavOptions.Builder()
                        .setPopUpTo(
                            graph.startDestinationId,
                            true).build())
                }
            }

        }

    }

}