package com.example.onboarding.presentation.password

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.common.BaseBottomSheetFragment
import com.example.common.data.DataMock
import com.example.onboarding.databinding.FragmentOTPFilterOverlayBinding
import com.example.onboarding.databinding.ObItemSelectionLayoutBinding


class OTPFilterOverlayFragment : BaseBottomSheetFragment<FragmentOTPFilterOverlayBinding>() {
    // TODO: Rename and change types of parameters
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOTPFilterOverlayBinding
        get() = FragmentOTPFilterOverlayBinding::inflate

    private lateinit var adapter : EmpLocationAdaptor

    override fun setup() {
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        binding?.apply {
            adapter = EmpLocationAdaptor(DataMock.sLocation) {
                DataMock.sLocation = it
                adapter.selected = DataMock.sLocation
                findNavController().popBackStack()
            }
            users.adapter = adapter
        }
    }

}

class EmpLocationAdaptor(var selected : String,
                         private val onUpdate : (newSelection : String) -> Unit) : RecyclerView.Adapter<EmpLocationAdaptor.LocationSelectViewHolder>() {

    inner class LocationSelectViewHolder(private val binding: ObItemSelectionLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(location : String , isSelected : Boolean ,
                 onUpdate : (newSelection : String) -> Unit) {
            binding.location = location
            binding.isSelected = isSelected
            binding.selection.setOnClickListener {
                onUpdate.invoke(binding.location ?: "")
            }
            binding.root.setOnClickListener {
                onUpdate.invoke(binding.location ?: "")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationSelectViewHolder {
        return LocationSelectViewHolder(
            ObItemSelectionLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = DataMock.locations.size


    override fun onBindViewHolder(holder: LocationSelectViewHolder, position: Int) {
        val tmp = DataMock.locations[position]
        holder.bind( tmp.location, tmp.location == selected , onUpdate = onUpdate )
    }
}