package com.talktomii.ui.home.profile


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talktomii.R
import com.talktomii.data.model.TimeSlotSpinner
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.model.drawer.bookmark.BookMarkResponse
import com.talktomii.data.model.getallslotbydate.Payload
import com.talktomii.data.model.getallslotbydate.TimeSlotsWithData
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.databinding.FragmentInfluencerProfileBinding
import com.talktomii.interfaces.*
import com.talktomii.ui.appointment.AppointmentViewModel
import com.talktomii.ui.home.AdapterHomeTimeSlot
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.utlis.*
import com.talktomii.utlis.DateUtils.addMinutes
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class InfluencerProfileFragment : DaggerFragment(), CommonInterface, AdminDetailInterface,
    OnSlotSelectedInterface, AddAppointmentInterface, FailureAPI400 {

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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfluencerProfileBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivShare.setBackgroundResource(R.drawable.share)
                binding.iivBookmark.setBackgroundResource(R.drawable.bookmarkprofile)
                binding.backprofile.setImageResource(R.drawable.back_arrow)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivShare.setBackgroundResource(R.drawable.ic_share)
                binding.iivBookmark.setBackgroundResource(R.drawable.bookmark_light)
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
        binding.lifecycleOwner = this
        if (arguments != null) {
            requireArguments().getString("profileId")?.let { viewModel.getAdminById(it) }
        }
        initAdapter()
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
            val dialog = CallDialog()
            dialog.show(requireActivity().supportFragmentManager, CallDialog.TAG)
        }

        binding.txtAboutMe.setOnClickListener {
            val dialog = DeleteAppointmentDialog()
            dialog.show(requireActivity().supportFragmentManager, DeleteAppointmentDialog.TAG)
        }


        binding.tvBookAppointment.setOnClickListener {
            addAppointment()
//            view?.findNavController()
//                ?.navigate(R.id.action_influencer_profile_to_call_fragmnet)
        }

        binding.iivBookmark.setOnClickListener {
            viewModel.checkAndSetBookMark()


        }
        binding.txtAboutMe.setOnClickListener {
            val dialog = AboutMeDialog()
            dialog.show(requireActivity().supportFragmentManager, AboutMeDialog.TAG)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel2 = viewModel

        setListener()

        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.DAY_OF_MONTH, +7)
//        val startDate: Calendar = Calendar.getInstance()
//        startDate.add(Calendar.DAY_OF_MONTH, 0)

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

        init()
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
    }

    override fun onFailureAPI(message: String) {
        progressDialog.dismiss()
    }

    override fun onStarted() {
        progressDialog.show()
    }


    override fun onAdminDetails(admin1: Admin1) {
        viewModel.getAllSlotByDate(SimpleDateFormat("yyyy-MM-dd").format(startDate.time))

        progressDialog.dismiss()
        socialMediaAdapter?.setItemList(admin1.socialNetwork)
        if (admin1.interest.size > 0) {
            binding.txtInterests.visibility = View.VISIBLE
            adapterInterests?.setItemList(admin1.interest)
            binding.txtItemCount.visibility = View.VISIBLE
        } else {
            binding.txtInterests.visibility = View.GONE
            binding.txtItemCount.visibility = View.GONE
        }
    }

    private fun setTimeSlot() {

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
            android.R.layout.simple_spinner_item, arrayList
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
            hashMap[DATE] = selectedDate!!
            hashMap[START_TIME] = selectedStartTime!!
            hashMap[END_TIME] = selectedEndTime!!
            hashMap[DURATON] = selectedTimeSlots!!.time
            viewModelAppoinemnt.addAppointment(hashMap)
        }

    }

    private fun isValidateAppointment(): Boolean {
        if (selectedStartTime == null) {
            showToastMessage(requireContext(), getString(R.string.select_time_slot))
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

    override fun onAddAppointment(response: BookMarkResponse?) {
        progressDialog.dismiss()
        response?.let { showToastMessage(requireContext(), it.message) }
        findNavController().popBackStack()
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


}