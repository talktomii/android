package com.talktomii.ui.editpersonalinfo.location


import `in`.madapps.placesautocomplete.PlaceAPI
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.databinding.BottomsheetLocationBinding
import com.talktomii.utlis.PLACE_FINDER_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddEditLocationBottomSheet(var addLocationInterface: AddLocationInterface) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetLocationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomsheetLocationBinding.inflate(LayoutInflater.from(context))
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivClose.setBackgroundResource(R.drawable.closesheeticon_dark)
                binding.ivRemoveSearch.setBackgroundResource(R.drawable.ic_cross_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivRemoveSearch.setBackgroundResource(R.drawable.locationclose)
                binding.ivClose.setBackgroundResource(R.drawable.close_sheet_icon)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        val placesApi = PlaceAPI.Builder().apiKey(PLACE_FINDER_KEY).build(requireContext())
        binding.rvList.adapter = PlacesAutoCompleteRecyclerAdapter(requireContext(), placesApi, binding.etSearch, object : PlacesAutoCompleteRecyclerAdapter.OnItemClickInterface {
            override fun onClick(place: `in`.madapps.placesautocomplete.model.Place) {
                addLocationInterface.addPlace(place.description)
                dismiss()
            }
        })

        binding.ivRemoveSearch.setOnClickListener {
            binding.etSearch.setText("")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }


}