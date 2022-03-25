package com.furniture.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.databinding.FragmentCallBinding
import com.furniture.ui.home.HomeViewModel
import com.furniture.utlis.DeleteAppointmentDialog
import com.furniture.utlis.ExtendTimeDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CallFragment : DaggerFragment() {

    private lateinit var binding: FragmentCallBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCallBinding.inflate(inflater, container, false)
        return binding.root

    }
    private fun setListener() {


        binding.ivSpeaker.setOnClickListener {
            val dialog = ExtendTimeDialog()
            dialog.show(requireActivity().supportFragmentManager, ExtendTimeDialog.TAG)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()

    }
}
