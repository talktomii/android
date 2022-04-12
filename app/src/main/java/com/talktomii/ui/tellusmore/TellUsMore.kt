package com.talktomii.ui.tellusmore

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.talktomii.R
import com.talktomii.adapter.TopicsAdapter
import com.talktomii.databinding.TellUsMoreBinding
import com.talktomii.utlis.PrefsManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TellUsMore : DaggerFragment(R.layout.tell_us_more) {

    private val args by navArgs<TellUsMoreArgs>()
    private lateinit var binding: TellUsMoreBinding
    private val viewModels by viewModels<TellUsMoreVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)


    var latitude: String? = null
    var longitude: String? = null

    @Inject
    lateinit var viewModel: TellUsMoreVM

    private val topicsAdapter: TopicsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TellUsMoreBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.rvTopics
        viewModels.isUser.set(args.isUser)
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager


        setListener()

    }

    private fun setListener() {

    binding.tvSetlocation.setOnClickListener {
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireContext())
        placeApiLauncher.launch(intent)


       }

    }


    var placeApiLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result?.data
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i("TAG", "Place: ${place.name}, ${place.id}")
                        binding.tvSetlocation?.text = place.name
                        latitude = place.latLng?.latitude.toString()
                        longitude = place.latLng?.longitude.toString()
                    }
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("TAG", status?.statusMessage ?: "")
                    }
                }

                Activity.RESULT_CANCELED -> {

                }
            }
        }



    private fun addItemsOnRecycler() {
        val recyclerview = binding.rvTopics


        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager


        val dataList = ArrayList<ItemsViewModel>()

        dataList.add(ItemsViewModel("Religion"))
        dataList.add(ItemsViewModel("Technology"))
        dataList.add(ItemsViewModel("Philosophy"))
        dataList.add(ItemsViewModel("Cryptocurrency"))
        dataList.add(ItemsViewModel("Music"))
        dataList.add(ItemsViewModel("Movie"))
        dataList.add(ItemsViewModel("Entrepreneurship"))
        dataList.add(ItemsViewModel("Psychology"))
        dataList.add(ItemsViewModel("Sociology"))

        dataList.add(ItemsViewModel("Religion"))
        dataList.add(ItemsViewModel("Technology"))
        dataList.add(ItemsViewModel("Philosophy"))
        dataList.add(ItemsViewModel("Cryptocurrency"))
        dataList.add(ItemsViewModel("Music"))
        dataList.add(ItemsViewModel("Movie"))
        dataList.add(ItemsViewModel("Entrepreneurship"))
        dataList.add(ItemsViewModel("Psychology"))
        dataList.add(ItemsViewModel("Sociology"))

        val adapter = TopicsAdapter(dataList)

        recyclerview.adapter = adapter
    }


}