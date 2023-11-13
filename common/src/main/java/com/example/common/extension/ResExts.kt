package com.example.common.extension

import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.common.R

fun Fragment.setStatusBarColor(colorResId: Int, isDark: Boolean = false) {
    requireActivity().window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDark) decorView.windowInsetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            else decorView.windowInsetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }else {
            if (isDark) decorView.systemUiVisibility = 0
            else decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        statusBarColor = ContextCompat.getColor(requireContext(), colorResId)
    }
}
fun Fragment.changeOtpFocus(array: Array<AppCompatEditText>) {
    for (i in array.indices) {
        val prevOtp = if (i <= 0) array[0] else array[i - 1]
        val nextOtp = if (i >= 5) array[5] else array[i + 1]
        val currentOtp = array[i]
        currentOtp.doOnTextChanged { text, _, _, _ ->
            when (text?.length) {
                1 -> {
                    currentOtp.background = ContextCompat.getDrawable(requireContext(), R.drawable.focused_edit_text_bg)
                }
                2 -> {
                    currentOtp.setText(text.first().toString())
                    nextOtp.setText(text.last().toString())
                    nextOtp.requestFocus()
                    nextOtp.setSelection(nextOtp.text.toString().length)
                    prevOtp.background = ContextCompat.getDrawable(requireContext(), R.drawable.focused_edit_text_bg)
                    nextOtp.background = ContextCompat.getDrawable(requireContext(), R.drawable.focused_edit_text_bg)
                }
                0 -> {
                    prevOtp.requestFocus()
                    prevOtp.setSelection(prevOtp.text.toString().length)
                    currentOtp.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_bg)
                }
            }
        }
    }
}
