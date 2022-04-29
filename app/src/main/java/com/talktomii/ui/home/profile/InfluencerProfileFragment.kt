package com.talktomii.ui.home.profile


import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.talktomii.R
import com.talktomii.data.model.TimeSlotSpinner
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.model.drawer.bookmark.BookMarkResponse
import com.talktomii.data.model.getallslotbydate.Payload
import com.talktomii.data.model.getallslotbydate.TimeSlotsWithData
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.databinding.CallDialogBinding
import com.talktomii.databinding.FragmentInfluencerProfileBinding
import com.talktomii.interfaces.*
import com.talktomii.ui.appointment.AppointmentViewModel
import com.talktomii.ui.home.AdapterHomeTimeSlot
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.utlis.*
import com.talktomii.utlis.DateUtils.addMinutes
import com.talktomii.utlis.DateUtils.shortDateToLocalToUTCDate
import com.talktomii.utlis.DateUtils.simpleDateToLocalToUTCDate
import com.talktomii.utlis.common.CommonUtils.Companion.showToastMessage
import com.talktomii.utlis.common.Constants.Companion.DATE
import com.talktomii.utlis.common.Constants.Companion.DURATON
import com.talktomii.utlis.common.Constants.Companion.END_TIME
import com.talktomii.utlis.common.Constants.Companion.IF_ID
import com.talktomii.utlis.common.Constants.Companion.START_TIME
import com.talktomii.utlis.common.Constants.Companion.UID
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import okhttp3.ResponseBody
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class InfluencerProfileFragment : DaggerFragment(), CommonInterface, AdminDetailInterface,
    OnSlotSelectedInterface, AddAppointmentInterface, FailureAPI400, onStopProgress {

    private lateinit var userData: Admin1
    private lateinit var binding: FragmentInfluencerProfileBinding
    private var socialMediaAdapter: AdapterMySocialMedias? = null
    private var adapterInterests: AdapterInterests? = null

    @Inject
    lateinit var viewModel: HomeScreenViewModel

    @Inject
    lateinit var viewModelAppoinemnt: AppointmentViewModel

    private var horizontalCalendar: HorizontalCalendar? = null
    private lateinit var progressDialog: ProgressDialog
    private val startDate: Calendar = Calendar.getInstance()

    @Inject
    lateinit var prefsManager: PrefsManager
    private var availableTimeSlots: Payload? = null
    private var selectedTimeSlots: TimeStop? = null
    private var selectedStartTime: String? = null
    private var selectedEndTime: String? = null
    private var selectedDate: String? = null
    private var selectedAdmin: Admin1? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfluencerProfileBinding.inflate(inflater, container, false)
        layout = binding.profileLayout
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivShare.setBackgroundResource(R.drawable.share)
                binding.backprofile.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivShare.setBackgroundResource(R.drawable.ic_share)
                binding.backprofile.setImageResource(R.drawable.back_arrow_light)
            }
        }

        return binding.root
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.adminDetailInterface = this
        viewModelAppoinemnt.addAppointment = this
        viewModelAppoinemnt.commonInterface = this
        viewModelAppoinemnt.apiFailure = this
        viewModel.onSlotSelectedInterface = this
        viewModel.onStopProgress = this
        binding.lifecycleOwner = this
        if (arguments != null) {
            requireArguments().getString("profileId")?.let { viewModel.getAdminById(it) }
        }
        initAdapter()

        binding.txtItemCount.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("interest", selectedAdmin!!.interest)
            findNavController().navigate(
                R.id.action_influencerProfileFragment_to_viewInterestFragment,
                bundle
            )

        }

        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.DAY_OF_MONTH, +7)

        horizontalCalendar =
            HorizontalCalendar.Builder(requireActivity(), R.id.calendarView)
                .range(startDate, endDate)
                .configure()
                .showTopText(false)
                .end()
                .datesNumberOnScreen(7)
                .build()

        horizontalCalendar!!.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date!!.time)
                viewModel.getAllSlotByDate(selectedDate.toString())
            }
        }

        selectedDate = SimpleDateFormat("yyyy-MM-dd").format(startDate.time)

    }

    private fun initAdapter() {
        socialMediaAdapter = AdapterMySocialMedias(requireContext())
        binding.rvSocialMedia.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSocialMedia.adapter = socialMediaAdapter

        adapterInterests = AdapterInterests()
        binding.rvInterest.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvInterest.adapter = adapterInterests
    }

    private fun setListener() {
        binding.txtBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.txtCallNow.setOnClickListener {
            showPopup()
        }

        binding.tvBookAppointment.setOnClickListener {
            addAppointment()
        }

        binding.ivBookMark.setOnClickListener {
            viewModel.checkAndSetBookMark()
        }
        binding.tvBadgesName.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putSerializable("badges", selectedAdmin!!.badges)
            findNavController().navigate(R.id.action_influencerProfileFragment_to_myBudgesFragment, bundle)
        }

        binding.txtAboutMe.setOnClickListener {
            if (selectedAdmin!!.aboutYou != null) {
                val dialog = AboutMeDialog(selectedAdmin!!.aboutYou)
                dialog.show(requireActivity().supportFragmentManager, AboutMeDialog.TAG)
            }else{
                val dialog = AboutMeDialog("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                dialog.show(requireActivity().supportFragmentManager, AboutMeDialog.TAG)
            }

        }

        binding.ivShare.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT, "I'm being sent!!")
            startActivity(Intent.createChooser(share, "Share Text"))
        }
    }

    private fun showPopup() {
        var customDialog: AlertDialog? = null
        val customDialogBuilder =
            AlertDialog.Builder(requireContext())
        val customView = CallDialogBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )
        customView.txtCall.setOnClickListener {
            customDialog?.cancel()
//            userData=Admin1(_id = "625e09d929499b944fc9e6a5")
//            var test=selectedAdmin
            view?.findNavController()
                ?.navigate(R.id.callFragment, bundleOf("DATA" to Gson().toJson(selectedAdmin)))
        }
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                customView.ivCancel.setImageResource(R.drawable.closesheeticon_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                customView.ivCancel.setImageResource(R.drawable.close_sheet_icon)
            }
        }
        customView.ivCancel.setOnClickListener {
            customDialog?.dismiss()
        }
        customDialogBuilder.setView(customView.root)
        customDialog = customDialogBuilder.create()
        customDialog?.setCancelable(true)
        customDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()
    }

    //    try
//    val startDate: Calendar = Calendar.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel2 = viewModel

        setListener()
        init()

    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?) {
        progressDialog.dismiss()
        ApisRespHandler.handleError(
            ApiUtils.handleError(
                code,
                errorBody!!.string()
            ), requireActivity(), prefsManager
        )

    }

    override fun onStarted() {
        progressDialog.show()
    }


    override fun onAdminDetails(admin1: Admin1) {
        progressDialog.dismiss()
        selectedAdmin = admin1
        context?.let {
            Glide.with(it).load(admin1.coverPhoto)
                .placeholder(R.drawable.ic_image1).error(R.drawable.ic_image1)
                .into(binding.ivCoverPhoto)
        }

        context?.let {
            Glide.with(it).load(admin1.profilePhoto)
                .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                .into(binding.imgDefault)
        }
        if (admin1.socialNetwork != null && admin1.socialNetwork.isNotEmpty()) {
            socialMediaAdapter?.setItemList(admin1.socialNetwork)
        }
        if (admin1.interest.size > 0) {
            if (admin1.interest.size > 3) {
                binding.txtItemCount.visibility = View.VISIBLE
                binding.txtItemCount.text = "+" + viewModel.userField.get()!!.interest.size.minus(3)
            } else {
                binding.txtItemCount.visibility = View.GONE
            }
            binding.txtInterests.visibility = View.VISIBLE
            adapterInterests?.setItemList(admin1.interest)
        } else {
            binding.txtInterests.visibility = View.GONE
            binding.txtItemCount.visibility = View.GONE
        }
        viewModel.getAllSlotByDate(selectedDate.toString())
    }

    private fun setTimeSlot() {
        progressDialog.dismiss()
        val arrayList: ArrayList<TimeSlotSpinner> = arrayListOf()
        for (i in 0 until (availableTimeSlots?.timeStops?.size ?: 0)) {
            arrayList.add(
                TimeSlotSpinner(
                    availableTimeSlots!!.timeStops[i].time, i
                )
            )
        }

        val adapter: ArrayAdapter<TimeSlotSpinner> = ArrayAdapter<TimeSlotSpinner>(
            requireContext(),
            R.layout.drop_down_custom_layout, arrayList
        )
        adapter.setDropDownViewResource(R.layout.spinner_list)
        binding.spinnerTimeDuration.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedTimeSlots = availableTimeSlots!!.timeStops[position]
                    (view as TextView).setTextColor(resources.getColor(R.color.calText))
                    var arrayList: ArrayList<TimeSlotsWithData> = arrayListOf()
                    for (i in availableTimeSlots!!.timeStops[position].slot) {
                        arrayList.add(TimeSlotsWithData(i, false))

                    }
                    binding.rvTimeSlot.adapter =
                        AdapterHomeTimeSlot(requireContext(), arrayList,
                            object : AdapterHomeTimeSlot.onViewItemClick {
                                override fun onViewItemTimeSelect(text: String) {
                                    selectedStartTime = text
                                    try {
                                        selectedEndTime = addMinutes(
                                            selectedStartTime!!,
                                            selectedTimeSlots!!.time
                                        )
                                    } catch (e: Exception) {

                                    }

                                }

                            })

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding.spinnerTimeDuration.adapter = adapter
    }


    private fun addAppointment() {
        if (isValidateAppointment()) {
            val hashMap: HashMap<String, Any> = hashMapOf()
            hashMap[IF_ID] = viewModel.userField.get()!!._id
            hashMap[UID] = getUser(prefsManager)!!.admin._id
            hashMap[DATE] = shortDateToLocalToUTCDate(selectedDate!!)
            hashMap[START_TIME] = simpleDateToLocalToUTCDate(selectedStartTime!!)
            hashMap[END_TIME] = simpleDateToLocalToUTCDate(selectedEndTime!!)
            hashMap[DURATON] = selectedTimeSlots!!.time
            viewModelAppoinemnt.addAppointment(hashMap)
        }

    }

    private fun isValidateAppointment(): Boolean {
        if (selectedStartTime == null) {
            showToastMessage(requireContext(), getString(R.string.select_time_slot))
            return false
        } else if (selectedDate == null) {
            showToastMessage(requireContext(), getString(R.string.select_appointment_date))
            return false
        }
        return true
    }

    override fun onSlotTimesList(list: Payload) {
        progressDialog.dismiss()
        availableTimeSlots = list

        if (availableTimeSlots!!.timeStops.isNotEmpty()) {
            setTimeSlot()
            binding.spinnerTimeDuration.visibility = View.VISIBLE
            binding.rvTimeSlot.visibility = View.VISIBLE
        } else {
            binding.spinnerTimeDuration.visibility = View.GONE
            binding.rvTimeSlot.visibility = View.GONE
        }
    }


    override fun onFailureAPI400(message: String) {
        progressDialog.dismiss()
        AlertDialogCommon.instance.createOkCancelDialogWithLayout(requireContext(),
            message,
            getString(R.string.yes),
            getString(R.string.no),
            true,
            object : AlertDialogCommon.OnOkCancelDialogListener {

                override fun onOkButtonClicked() {
                    findNavController().navigate(R.id.action_influencerProfileFragment_to_myWalletFragment)

                }

                override fun onCancelButtonClicked() {
                    //do nothing
                }
            })
    }


    override fun onAddAppointment(response: BookMarkResponse?) {
        progressDialog.dismiss()
        response?.let { showToastMessage(requireContext(), it.message) }
        findNavController().popBackStack()
    }

    override fun onStopProgress() {
        progressDialog.dismiss()
    }


    companion object {
        lateinit var layout: ConstraintLayout
    }
}