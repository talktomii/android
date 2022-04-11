package com.talktomii.ui.home.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talktomii.R
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.model.getallslotbydate.TimeStop
import com.talktomii.databinding.FragmentInfluencerProfileBinding
import com.talktomii.interfaces.AdminDetailInterface
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.OnSlotSelectedInterface
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.utlis.AboutMeDialog
import com.talktomii.utlis.CallDialog
import com.talktomii.utlis.DeleteAppointmentDialog
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class InfluencerProfileFragment : DaggerFragment(), CommonInterface, AdminDetailInterface,
    OnSlotSelectedInterface {

    private lateinit var binding: FragmentInfluencerProfileBinding
    private var socialMediaAdapter: AdapterMySocialMedias? = null
    private var adapterInterests: AdapterInterests? = null

    @Inject
    lateinit var viewModel: HomeScreenViewModel

    private var horizontalCalendar: HorizontalCalendar? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInfluencerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun init() {
        progressDialog = ProgressDialog(requireActivity())

        viewModel.commonInterface = this
        viewModel.adminDetailInterface = this
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


        binding.txtBookACall.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_influencer_profile_to_call_fragmnet)
        }

        binding.ivBookMark.setOnClickListener {
            viewModel.checkAndSetBookMark()


        }
        binding.txtAboutMe.setOnClickListener {
            val dialog = AboutMeDialog()
            dialog.show(requireActivity().supportFragmentManager, AboutMeDialog.TAG)
        }
    }

    //    try
    val startDate: Calendar = Calendar.getInstance()
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
                viewModel.getAllSlotByDate(SimpleDateFormat("yyyy-MM-dd").format(date!!.time))
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

    override fun onslotselect(timeStop: TimeStop) {
        progressDialog.dismiss()
        binding.rvTimeSlot.adapter = AdapterTimeSlot(timeStop)
    }

}