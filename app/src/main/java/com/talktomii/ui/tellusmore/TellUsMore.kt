package com.talktomii.ui.tellusmore

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.talktomii.R
import com.talktomii.adapter.TopicsAdapter
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.TellUsMoreBinding
import com.talktomii.ui.editpersonalinfo.location.AddEditLocationBottomSheet
import com.talktomii.ui.editpersonalinfo.location.AddLocationInterface
import com.talktomii.utlis.*
import com.talktomii.utlis.ImageUtils.getVideoPathFromGallery
import com.talktomii.utlis.dialogs.ProgressDialog
import com.talktomii.viewmodel.SearchViewModel
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class TellUsMore : DaggerFragment(R.layout.tell_us_more), LinkAccountDialog.LinkListener {

    private var videoPath: String = ""
    private var fbLink: SocialNetwork = SocialNetwork(name = "facebook", link = "")
    private var twLink: SocialNetwork = SocialNetwork(name = "twitter", link = "")
    private var insLink: SocialNetwork = SocialNetwork(name = "instagram", link = "")
    private var tikLink: SocialNetwork = SocialNetwork(name = "tiktok", link = "")
    private var interests = arrayListOf<String>()
    private val args by navArgs<TellUsMoreArgs>()
    private lateinit var binding: TellUsMoreBinding
    private val viewModels by viewModels<TellUsMoreVM>()

    @Inject
    lateinit var interestVM: SearchViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    lateinit var progressDialog: ProgressDialog

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
//        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireActivity())
        interestVM.getAllInterests("")

//        binding.rvTopics.adapter = topicsAdapter


        val recyclerview = binding.rvTopics
        viewModels.isUser.set(args.isUser)
        if (args.isUser) {
            binding.tvAboutYou.gone()
            binding.tvRecordVideo.gone()
            binding.tvLinkAccounts.gone()
            binding.llLinkAccounts.gone()
        } else {
            binding.tvAboutYou.visible()
            binding.tvRecordVideo.visible()
            binding.tvLinkAccounts.visible()
            binding.llLinkAccounts.visible()
        }
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager


        setListener()
        bindObservers()

    }

    private fun bindObservers() {
        interestVM.interests.observe(requireActivity(), Observer {
            it ?: return@Observer

            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)
//                    binding.vm?.topicsAdapter?.addItems(it.data?.interest?: arrayListOf())
                    binding.rvTopics.adapter =
                        TopicsAdapter(it.data?.interest ?: arrayListOf(), this)

                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }
            }
        })
        interestVM.updateData.observe(requireActivity(), Observer {
            it ?: return@Observer

            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)

                    var user = getUser(prefsManager)
                    if (user?.admin?.role?.roleName == "user")
                        view?.findNavController()?.navigate(R.id.homeFragment)
                    else
                        view?.findNavController()?.navigate(R.id.homeInfluencerFragment)
                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }
            }
        })

        interestVM.uploadMedia.observe(requireActivity(), Observer {
            it ?: return@Observer

            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.setLoading(false)

                }
                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }
            }
        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        if (requestCode == VIDEO_CAPTURE) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(
//                    requireContext(), """
//     Video saved to:
//     ${data.getData()}
//     """.trimIndent(), Toast.LENGTH_LONG
//                ).show()
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(requireContext(), "Video recording cancelled.", Toast.LENGTH_LONG).show()
//            } else {
//                //Toast.makeText(this, "Failed to record video",                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private fun setListener() {

        binding.tvSetlocation.setOnClickListener {
//            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .build(requireContext())
//            placeApiLauncher.launch(intent)
            val bottomsheet = AddEditLocationBottomSheet(object : AddLocationInterface {
                override fun addPlace(place: String) {
                    binding.tvSetlocation.text = place
                }
            })
            bottomsheet.show(childFragmentManager, "addlocation")

        }
        binding.rlFacebook.setOnClickListener {
            val dialog = LinkAccountDialog("Facebook", "", this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }

        binding.rlTwitter.setOnClickListener {
            val dialog = LinkAccountDialog("Twitter", "", this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }

        binding.rlInstagram.setOnClickListener {
            val dialog = LinkAccountDialog("Instagram", "", this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }

        binding.rlTikTok.setOnClickListener {
            val dialog = LinkAccountDialog("Tiktok", "", this)
            dialog.show(requireActivity().supportFragmentManager, LinkAccountDialog.TAG)
        }

        binding.btnNext.setOnClickListener {
            val location = binding.tvSetlocation.text.toString()
            if (dataIsValid(interests, videoPath, location)) {
                val map: HashMap<String, RequestBody> = HashMap()
                if (!videoPath.isNullOrEmpty()) {
                    val body = File(videoPath).asRequestBody("*/*".toMediaTypeOrNull())
                    map["aboutYou\"; filename=\"aboutYou.mp4\" "] = body
                }
                interestVM.uploadMedia(map, getUser(prefsManager)?.admin?._id ?: "")
                var request = RequestAdminModel(
                    interest = interests,
                    location = binding.tvSetlocation.text.toString(),
                    socialNetwork = arrayListOf(fbLink, twLink, insLink, tikLink)
                )
                interestVM.updateData(getUser(prefsManager)?.admin?._id ?: "", request)
            }
        }

        binding.tvSkip.setOnClickListener {


            var user = getUser(prefsManager)
            if (user?.admin?.role?.roleName == "user")
                view?.findNavController()?.navigate(R.id.homeFragment)
            else
                view?.findNavController()?.navigate(R.id.homeInfluencerFragment)
        }

        binding.tvRecordVideo.setOnClickListener {
            val intent: Intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30)
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
//           intent.putExtra(MediaStore.EXTRA_OUTPUT, ) // set the image file

            startActivityForResult(intent, 101101)
        }
    }

    private fun dataIsValid(
        interest: ArrayList<String>,
        video: String,
        location: String,
    ): Boolean {
        when {
            getUser(prefsManager)?.admin?.role?.roleName != "user" -> {
                return when {
                    interest.isEmpty() -> {
                        binding.rvTopics.showSnackBar("Please select your preferred interest")
                        false
                    }
                    video.isEmpty() -> {
                        binding.tvRecordVideo.showSnackBar("Please record a video that best describes you")
                        false
                    }
                    location.isEmpty() -> {
                        binding.tvLocation.showSnackBar("Please select your location")
                        false
                    }
                    else -> true
                }
            }
            interest.isEmpty() -> {
                binding.rvTopics.showSnackBar("Please select your preferred interest")
                return false
            }
            location.isEmpty() -> {
                binding.tvLocation.showSnackBar("Please select your location")
                return false
            }
            else -> return true
        }
    }
//getUser(prefsManager)?.admin?._id?
    //6257bc87dd85d624ccb6bb8a

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101101 && resultCode == RESULT_OK) {
            val fileToUploadNew = getVideoPathFromGallery(requireContext(), data?.data!!)
            videoPath = fileToUploadNew?.absolutePath ?: ""
            val bMap = ThumbnailUtils.createVideoThumbnail(
                videoPath,
                MediaStore.Video.Thumbnails.MICRO_KIND
            )
            binding.tvRecordVideo.gone()
            binding.ivShowVideo.visible()
            binding.ivShowVideo.setImageBitmap(bMap)
        }
    }

    private fun addItemsOnRecycler() {
        val recyclerview = binding.rvTopics


        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager

//
//        val dataList = ArrayList<ItemsViewModel>()
//
//        dataList.add(ItemsViewModel("Religion"))
//        dataList.add(ItemsViewModel("Technology"))
//        dataList.add(ItemsViewModel("Philosophy"))
//        dataList.add(ItemsViewModel("Cryptocurrency"))
//        dataList.add(ItemsViewModel("Music"))
//        dataList.add(ItemsViewModel("Movie"))
//        dataList.add(ItemsViewModel("Entrepreneurship"))
//        dataList.add(ItemsViewModel("Psychology"))
//        dataList.add(ItemsViewModel("Sociology"))
//
//        dataList.add(ItemsViewModel("Religion"))
//        dataList.add(ItemsViewModel("Technology"))
//        dataList.add(ItemsViewModel("Philosophy"))
//        dataList.add(ItemsViewModel("Cryptocurrency"))
//        dataList.add(ItemsViewModel("Music"))
//        dataList.add(ItemsViewModel("Movie"))
//        dataList.add(ItemsViewModel("Entrepreneurship"))
//        dataList.add(ItemsViewModel("Psychology"))
//        dataList.add(ItemsViewModel("Sociology"))
//
//        val adapter = TopicsAdapter(dataList)

//        recyclerview.adapter = adapter
    }

    override fun onLinkClicked(type: String, value: String) {
        when (type) {
            "Facebook" -> {
                fbLink.link = value
            }
            "Twitter" -> {
                twLink.link = value
            }
            "Instagram" -> {
                insLink.link = value
            }
            "Tiktok" -> {
                tikLink.link = value
            }
        }
    }

    fun dataChanged(itemsViewModel: TopicsAdapter.ItemsViewModel) {
        if (!interests.contains(itemsViewModel._id)) {
            interests.add(itemsViewModel._id)
        }
    }

    fun dataRemoved(itemsViewModel: TopicsAdapter.ItemsViewModel) {
        if (interests.contains(itemsViewModel._id)) {
            interests.remove(itemsViewModel._id)
        }
    }


}