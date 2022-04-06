package com.talktomii.ui.loginSignUp.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.talktomii.databinding.FragmentCreateProfileBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CreateProfileFragment : DaggerFragment() {

    private val viewModels by viewModels<CreateProfileVM>()

    @Inject
    lateinit var viewModel: CreateProfileVM

    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNEXT.setOnClickListener {
            radioCheck()
        }




    }

    fun radioCheck(){

            val userOrInfluencer = when {
                binding.radioUser.isChecked ->true
                binding.radioInfluencer.isChecked -> false
                else -> null
            }

            view?.findNavController()?.navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToTellUsMore(isUser = userOrInfluencer?:false))

    }
}

