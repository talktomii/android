package com.furniture.ui.home.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.furniture.R
import com.furniture.data.model.admin1.Admin1
import com.furniture.databinding.FragmentInfluencerProfileBinding
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.ui.home.HomeScreenViewModel
import com.furniture.utlis.CallDialog
import com.furniture.utlis.DeleteAppointmentDialog
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*
import javax.inject.Inject


class InfluencerProfileFragment : DaggerFragment(), CommonInterface, AdminDetailInterface {

    private lateinit var binding: FragmentInfluencerProfileBinding
    private var socialMediaAdapter: AdapterMySocialMedias? = null
    private var adapterInterests: AdapterInterests? = null

    @Inject
    lateinit var viewModel: HomeScreenViewModel

    private var horizontalCalendar: HorizontalCalendar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentInfluencerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun init() {
        viewModel.commonInterface = this
        viewModel.adminDetailInterface = this
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

        adapterInterests = AdapterInterests(requireContext())
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
                ?.navigate(com.furniture.R.id.action_influencer_profile_to_call_fragmnet)
        }

        binding.ivBookMark.setOnClickListener {
            if (viewModel.userField.get()!!.isBookmark) {
                viewModel.removeBookmark()
                binding.ivBookMark.setImageResource(R.drawable.apple)
            } else {
                viewModel.addBookmark()
                binding.ivBookMark.setImageResource(R.drawable.ic_bookmark)
            }
        }

    }

//    try

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel2 = viewModel

        setListener()

        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)
        val startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        horizontalCalendar =
            HorizontalCalendar.Builder(requireActivity(), com.furniture.R.id.calendarView)
                .range(startDate, endDate)
                .configure()
                .showTopText(false)
                .end()
                .datesNumberOnScreen(5)
                .build()
        horizontalCalendar!!.calendarListener = object : HorizontalCalendarListener() {

            override fun onDateSelected(date: Calendar?, position: Int) {

            }
        }


        binding.rvTimeSlot.adapter = AdapterTimeSlot()

        init()
    }

    override fun onFailure(message: String) {

    }

    override fun onStarted() {
    }


    override fun onAdminDetails(admin1: Admin1) {
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
}