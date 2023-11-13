package com.example.common

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.common.databinding.FragmentOTPBinding
import com.example.common.databinding.OtpStyleInputLayoutBinding
import com.example.common.extension.changeOtpFocus

class OTPInputFragment : BBaseFragment<OtpStyleInputLayoutBinding>() ,
    TextView.OnEditorActionListener{
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> OtpStyleInputLayoutBinding
        get() = OtpStyleInputLayoutBinding::inflate

    private lateinit var onChange : (canProceed : Boolean) -> Unit
    private lateinit var onVerify : (success : Boolean , value : String) -> Unit
    private lateinit var message : String
    fun linkToHostFragment(message : String ,
                           onChange : (canProceed : Boolean) -> Unit ,
                           onVerify : (success : Boolean , value : String) -> Unit) {
        this.onVerify = onVerify
        this.message = message
        this.onChange = onChange

        binding?.let {
            it.otpMessage = message
        }

    }

    override fun setup() {
        binding?.apply {
            otpMessage = this@OTPInputFragment.message
            setOtpFocusChange()
            binding?.otp1?.setOnEditorActionListener(this@OTPInputFragment)
            binding?.otp2?.setOnEditorActionListener(this@OTPInputFragment)
            binding?.otp3?.setOnEditorActionListener(this@OTPInputFragment)
            binding?.otp4?.setOnEditorActionListener(this@OTPInputFragment)
            binding?.otp5?.setOnEditorActionListener(this@OTPInputFragment)
            binding?.otp6?.setOnEditorActionListener(this@OTPInputFragment)
        }
    }

    private fun setOtpFocusChange() {
        binding?.apply {
            val otpArray = arrayOf(otp1, otp2, otp3, otp4, otp5, otp6)
            changeOtpFocus(otpArray)
            setTextWatcher(otpArray)
        }
    }
    private fun setTextWatcher(array: Array<AppCompatEditText>) {
        for (i in array.indices) {
            array[i].doAfterTextChanged {
                if (binding?.otp1?.text.isNullOrEmpty().not() &&
                    binding?.otp2?.text.isNullOrEmpty().not() &&
                    binding?.otp3?.text.isNullOrEmpty().not() &&
                    binding?.otp4?.text.isNullOrEmpty().not() &&
                    binding?.otp5?.text.isNullOrEmpty().not() &&
                    binding?.otp6?.text.isNullOrEmpty().not()) {
                    checkManualOTPEnter()
                }else {
                    this.onChange.invoke(false)
                }
            }
        }
    }

    private fun checkManualOTPEnter() {
        val otpString = binding?.run { otp1.text.toString()+otp2.text.toString()+
                otp3.text.toString()+otp4.text.toString()+otp5.text.toString()+
                otp6.text.toString() }

        this.onChange.invoke(otpString?.trim()?.length == 6)
        this.onVerify.invoke(false , otpString?.trim() ?: "")
    }



    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }


}