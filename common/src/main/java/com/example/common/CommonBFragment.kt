package com.example.common

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BBaseFragment<VB : ViewBinding?> : Fragment() {

    protected var hostActivity: Activity? = null
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB?
        get() = _binding as VB

    /**
     * abstract function to pre setup the fragment and the data binding.
     */
    abstract fun setup()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    /**
     * Do an action on UI thread.
     */
    fun doOnUI(func: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch { func() }
    }

    /**
     * Do an action on IO thread.
     */
    fun doOnIO(func: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch { func() }
    }

    /**
     * Show snack bar.
     */
    fun makeText(text: String, listener: View.OnClickListener? = null) {
        view?.let {
            Snackbar
                .make(it, text, Snackbar.LENGTH_SHORT)
                .setAction("Action", listener)
                .setTextMaxLines(5)
                .show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        hostActivity = null
    }
}


abstract class BaseBottomSheetFragment<VB : ViewBinding?> : BottomSheetDialogFragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB?
        get() = _binding as VB

    abstract fun setup()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.setCanceledOnTouchOutside(true)
        setup()
    }

    fun enableCanceledOnTouchOutside() {
//        dialog?.setCanceledOnTouchOutside(true)
    }

    fun disableCanceledOnTouchOutside() {
//        dialog?.setCanceledOnTouchOutside(true)
    }

    /**
     * Do an action on UI thread.
     */
    fun doOnUI(func: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch { func() }
    }

    /**
     * Do an action on IO thread.
     */
    fun doOnIO(func: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch { func() }
    }

    fun makeText(view: View?, text: String, listener: View.OnClickListener? = null) {
        view?.let {
            Snackbar
                .make(it, text, Snackbar.LENGTH_SHORT)
                .setAction("Action", listener)
                .setTextMaxLines(5)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}