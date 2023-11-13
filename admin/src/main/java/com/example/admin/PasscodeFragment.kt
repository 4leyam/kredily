package com.example.admin

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.admin.databinding.FragmentPasscodeBinding
import com.example.common.BBaseFragment
import com.example.common.OTPInputFragment
import com.example.common.data.DataMock

class PasscodeFragment : BBaseFragment<FragmentPasscodeBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasscodeBinding
        get() = FragmentPasscodeBinding::inflate

    private var passCode : String = ""
    override fun setup() {

        binding?.apply {
            this.header.back.setOnClickListener {
                it.findNavController().popBackStack()
            }

            childFragmentManager.fragments.filterIsInstance<OTPInputFragment>().first().let {
                it.linkToHostFragment("Enter your passcode to open admin panel",
                    onChange = { canProceed ->
                        binding?.canProceed = canProceed
                    } , onVerify = { _, value ->
                        passCode = value
                    })
            }

            this.proceed.setOnClickListener {

                activity?.getPreferences(Context.MODE_PRIVATE)?.let {

                    val id = it.getInt("id" , -1)
                    if(id > 0) {

                        val vUser = DataMock.users.firstOrNull { user ->
                            user.pass == passCode
                        }

                        if(vUser == null) {
                            makeText("Invalid passcode please try again!")
                        } else {

                            val toSett = PasscodeFragmentDirections.actionPasscodeFragmentToSettingsFragment()
                            findNavController().navigate(toSett , NavOptions.Builder()
                                .setPopUpTo(R.id.passcodeFragment,
                                    true).build())

                        }

                    }

                }

            }

        }

    }

}