package com.example.frshome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.common.BaseBottomSheetFragment
import com.example.common.data.DataMock
import com.example.frshome.databinding.FragmentFilterOverlayBinding
import com.example.frshome.databinding.ItemSelectionLayoutBinding

class OTPFilterOverlayFragment : BaseBottomSheetFragment<FragmentFilterOverlayBinding>() {
    // TODO: Rename and change types of parameters
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilterOverlayBinding
        get() = FragmentFilterOverlayBinding::inflate

    private lateinit var adapter : EmpLocationAdaptor
    val args : OTPFilterOverlayFragmentArgs by navArgs()

    override fun setup() {
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        binding?.apply {
            adapter = EmpLocationAdaptor(args.isDepartment , DataMock.sLocation) {

                if(args.isDepartment) {
                    DataMock.sDep = it
                } else {
                    DataMock.sLocation = it
                    adapter.selected = DataMock.sLocation
                }
                findNavController().popBackStack()
            }
            users.adapter = adapter
        }
    }

}

class EmpLocationAdaptor(
    private var isDep : Boolean,
    var selected : String,
    private val onUpdate : (newSelection : String) -> Unit)
    : RecyclerView.Adapter<EmpLocationAdaptor.LocationSelectViewHolder>() {

    inner class LocationSelectViewHolder(private val binding: ItemSelectionLayoutBinding) :
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
            ItemSelectionLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = if(isDep) DataMock.departments.size
    else DataMock.locations.size


    override fun onBindViewHolder(holder: LocationSelectViewHolder, position: Int) {
        if(isDep) {
            val tmp = DataMock.departments[position]
            holder.bind( tmp, false , onUpdate = onUpdate )
        } else {
            val tmp = DataMock.locations[position]
            holder.bind( tmp.location, tmp.location == selected , onUpdate = onUpdate )
        }

    }
}