package com.example.onboarding.presentation.password

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.common.BBaseFragment
import com.example.common.databinding.FragmentOTPBinding
import com.example.onboarding.R
import com.example.common.data.DataMock
import com.example.common.data.UserMock
import com.example.onboarding.databinding.FragmentPassBinding

class PassFragment : BBaseFragment<FragmentPassBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPassBinding
        get() = FragmentPassBinding::inflate

    override fun setup() {
        binding?.apply {
            location = DataMock.sLocation
            initLocationOverlayListener()
            initHomeNavigation()
        }

        //this is an ugly quick fix
        findNavController().addOnDestinationChangedListener { _, _, _ ->
            binding?.let {
                it.location = DataMock.sLocation
                it.isShowingModal = !(it.isShowingModal ?: true)
            }
        }
    }


    private fun initHomeNavigation() {
        binding?.apply {
            loginContainer.registerForm.proceed.setOnClickListener {

                with(this.loginContainer.registerForm) {
                    val pass = this.ePass.text.toString()
                    val cpass = this.cPass.text.toString()
                    if (pass.isBlank() && cpass.isBlank()) {
                        makeText("Please choose a password before login in!")
                    } else if (pass == cpass && pass.length == 6) {

                        DataMock.users.add(
                            UserMock(
                                1000 , "tmp@gmail.com" , pass ,
                                "8hE834" , "tmp" ,
                                "https://picsum.photos/200?blur=4",
                                false , DataMock.locations.random().location ,
                                DataMock.departments.random()
                            )
                        )


                        val request = NavDeepLinkRequest.Builder
                            .fromUri("android-app://com.example.kredily/FRSHomeFragment".toUri())
                            .build()
                        findNavController().let {
                            it.navigate(request , NavOptions.Builder()
                                .setPopUpTo(it.graph.startDestinationId,
                                    true).build())
                        }


                    } else {
                        makeText("Please use a 6 digits passcode")
                    }
                }

            }
        }
    }
    private fun initLocationOverlayListener() {
        binding?.apply {
            val factor = {
                val toLocationOverlay = PassFragmentDirections
                    .actionPassFragmentToOTPFilterOverlayFragment(DataMock.sLocation)
                findNavController().navigate(toLocationOverlay)
            }

            this.loginContainer.registerForm.locationToggleBt.setOnClickListener {
                factor.invoke()
            }
            this.loginContainer.registerForm.location.setOnClickListener {
                factor.invoke()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.location = DataMock.sLocation
    }

}