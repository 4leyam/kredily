package com.example.frshome

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.BBaseFragment
import com.example.common.data.DataMock
import com.example.common.data.UserMock
import com.example.frshome.databinding.FragmentFRSHomeBinding
import com.example.frshome.databinding.HomeEmployeeListItemLayoutBinding
import com.example.frshome.databinding.HomeFilterOptionItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FRSHomeFragment : BBaseFragment<FragmentFRSHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFRSHomeBinding
        get() = FragmentFRSHomeBinding::inflate

    private var selectedFilters = mutableSetOf<String>()
    private var userAdaptor : UserAdaptor? = null
    override fun setup() {
        this.setUpEmpSelection()
        this.initFilterSection()
        this.initFilterAdapter()
        this.initUserAdapter()
        this.initSearch()
    }

    private fun initSearch() {
        binding?.apply {

            homeSearch.settingsBt.setOnClickListener {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://com.example.kredily/passcodeFragment".toUri())
                    .build()
                findNavController().navigate(request)
            }
            homeSearch.cPass.addTextChangedListener( object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().isNotBlank()){
                        CoroutineScope(Dispatchers.Main).launch {
                            selection = ""
                            selectedFilters = mutableSetOf()
                            val interest = s.toString().lowercase()
                            userAdaptor?.users = DataMock.users.filter {
                                it.name.lowercase().contains(interest)
                                        || it.name.lowercase() == interest
                            }
                            userAdaptor?.notifyDataSetChanged()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Default).launch {
                            delay(100)
                            CoroutineScope(Dispatchers.Main).launch {
                                requireContext().hideKeyboard(homeSearch.cPass)
                            }
                        }
                    }

                }

            })
        }
    }

    private fun initUserAdapter() {
        binding?.apply {
            userAdaptor = UserAdaptor(DataMock.users)
            this.employees.adapter = userAdaptor
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initFilterAdapter() {
        binding?.apply {
            this.homeFilter.users.adapter = SelectedFilterAdaptor(selectedFilters.toList()) {
                DataMock.sLocation = ""
                DataMock.sDep = ""
                selectedFilters.remove(it)
                if (selectedFilters.isEmpty()) {
                    selection = ""
                }
                (this.homeFilter.users.adapter as SelectedFilterAdaptor)
                    .selection = selectedFilters.toList()
                this.homeFilter.users.adapter?.notifyDataSetChanged()
                checkFilter(empSelection.isTotalSelected ?: true)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initFilterSection() {

        DataMock.sLocation = ""
        DataMock.sDep = ""

        binding?.apply {
            this.firsOption = "Location"
            this.secondOption = "Department"
            this.isTotalSelected = true

            this.homeFilter.first.root.setOnClickListener {
                val toFilter = FRSHomeFragmentDirections.actionFRSHomeFragmentToOTPFilterOverlayFragment(false)
                findNavController().navigate(toFilter)
            }
            this.homeFilter.first.fToggle.setOnClickListener {
                val toFilter = FRSHomeFragmentDirections.actionFRSHomeFragmentToOTPFilterOverlayFragment(false)
                findNavController().navigate(toFilter)
            }
            this.homeFilter.second.root.setOnClickListener {
                val toFilter = FRSHomeFragmentDirections.actionFRSHomeFragmentToOTPFilterOverlayFragment(true)
                findNavController().navigate(toFilter)
            }
            this.homeFilter.second.fToggle.setOnClickListener {
                val toFilter = FRSHomeFragmentDirections.actionFRSHomeFragmentToOTPFilterOverlayFragment(false)
                findNavController().navigate(toFilter)
            }

            this.homeFilter.clearAll.setOnClickListener {
                DataMock.sLocation = ""
                DataMock.sDep = ""
                selection = ""
                selectedFilters = mutableSetOf()
                (this.homeFilter.users.adapter as SelectedFilterAdaptor).selection = listOf()
                this.homeFilter.users.adapter?.notifyDataSetChanged()
            }

            findNavController().addOnDestinationChangedListener { controller, destination , _ ->
                if(DataMock.sLocation.isNotBlank()) {

                    selection = DataMock.sLocation
                    selectedFilters.add(DataMock.sLocation)
                    (this.homeFilter.users.adapter as SelectedFilterAdaptor)
                        .selection = selectedFilters.reversed()
                    this.homeFilter.users.adapter?.notifyDataSetChanged()
                }
                if(DataMock.sDep.isNotBlank()) {
                    selection = DataMock.sDep
                    selectedFilters.add(DataMock.sDep)
                    (this.homeFilter.users.adapter as SelectedFilterAdaptor)
                        .selection = selectedFilters.reversed()
                    this.homeFilter.users.adapter?.notifyDataSetChanged()
                }


                if(destination != controller.findDestination(R.id.OTPFilterOverlayFragment)) {
                    checkFilter(empSelection.isTotalSelected ?: true)
                }

            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun checkFilter(isTT : Boolean) {

        binding?.run {

            CoroutineScope(Dispatchers.Main).launch {
                homeSearch.cPass.setText("")
                requireContext().hideKeyboard(homeSearch.cPass)
            }

            if(selection == null || selection!!.isBlank()) {
                if (isTT) {
                    userAdaptor?.users = DataMock.users
                } else {
                    userAdaptor?.users = DataMock.users.filter { !it.isConfigured }
                }
            } else {

                val locs = DataMock.users.filter {
                    selectedFilters.contains(it.location)
                }

                val deps =  DataMock.users.filter {
                    selectedFilters.contains(it.dep)
                }



                val flt = if (locs.isEmpty() || deps.isEmpty()) {
                    locs.toSet().union(deps.toSet()).toList()
                } else
                    locs.toSet().intersect(deps.toSet()).toList()

                userAdaptor?.users = if (isTT) flt else flt.filter { !it.isConfigured }

            }
            userAdaptor?.notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpEmpSelection() {
        binding?.apply {
            empSelection.total.setOnClickListener {
                isTotalSelected = true
                checkFilter(true)
            }
            empSelection.unconf.setOnClickListener {
                isTotalSelected = false
               checkFilter(false)
            }
        }
    }

}



class UserAdaptor(var users : List<UserMock>): RecyclerView.Adapter<UserAdaptor.UserViewHolder>() {

    inner class UserViewHolder(private val binding: HomeEmployeeListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user : UserMock) {
            binding.user = user
            if(user.photoUrl != null) {
                Glide.with(binding.root.context)
                    .load(user.photoUrl)
                    .centerCrop()
                    .into(binding.image)
            } else {
                binding.image.setImageBitmap(null)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            HomeEmployeeListItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = users.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind( users.elementAt(position))
    }
}



class SelectedFilterAdaptor(
    var selection : List<String> ,
    private val onUpdate : (newSelection : String) -> Unit)
    : RecyclerView.Adapter<SelectedFilterAdaptor.SelectionViewHolder>() {

    inner class SelectionViewHolder(private val binding: HomeFilterOptionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name : String ,
                 onUpdate : (newSelection : String) -> Unit) {

            binding.isSelected = true
            binding.isAddedFilter = true
            binding.name = name

            binding.root.setOnClickListener {
                onUpdate.invoke(binding.name ?: "")
            }
            binding.fCloseBt.setOnClickListener {
                onUpdate.invoke(binding.name ?: "")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        return SelectionViewHolder(
            HomeFilterOptionItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = selection.size


    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        holder.bind( selection.elementAt(position),  onUpdate = onUpdate )
    }
}


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}