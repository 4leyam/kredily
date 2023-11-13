package com.example.onboarding.presentation.login

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.utils.widget.MockView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.common.BBaseFragment
import com.example.common.OTPFragmentDirections
import com.example.common.data.DataMock
import com.example.onboarding.R
import com.example.onboarding.databinding.FragmentLoginBinding

class LoginFragment : BBaseFragment<FragmentLoginBinding>() {



    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup() {
        checkUser()
        binding?.apply {

            this.loginContainer.loginForm.loginBt.setOnClickListener {

                with(this.loginContainer.loginForm) {
                    val email = this.email.text.toString()
                    val pass = this.pass.text.toString()
                    if (email.isBlank() && pass.isBlank()) {
                        makeText("Please enter email and password to login")
                    } else if (email.isNotBlank() && pass.isNotBlank()) {

                        val user = DataMock.users.firstOrNull {
                            it.email == email && it.pass == pass
                        }

                        if(user == null) {
                            makeText("No user found!")
                        } else {
                            activity?.getPreferences(Context.MODE_PRIVATE)?.let {
                                with (it.edit()) {
                                    putInt("id", user.userId)
                                    apply()

                                    val uri = Uri.parse("android-app://com.example.kredily/FRSHomeFragment")
                                    findNavController().let { nav ->
                                        nav.navigate(uri , NavOptions.Builder()
                                            .setPopUpTo(nav.currentDestination!!.id,
                                                true).build())
                                    }

                                }
                            }
                        }




                    }
                }

            }

            this.loginContainer.loginForm.loginWithOtp.setOnClickListener {
                //TODO set up a mock up remote with a defined set of user to test
                val toOtp = LoginFragmentDirections.actionLoginFragmentToOTPFragment2(userId = -1)
                findNavController().navigate(toOtp)
            }

        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun checkUser() {


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val id = sharedPref.getInt("id" , -1)
        if(id > 0) {
            val uri = Uri.parse("android-app://com.example.kredily/FRSHomeFragment")
            findNavController().let { nav ->
                nav.navigate(uri , NavOptions.Builder()
                    .setPopUpTo(nav.currentDestination!!.id,
                        true).build())
            }
        }
    }




}