package com.furniture.ui.loginSignUp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.furniture.R
import com.furniture.databinding.FragmentStartBinding
import com.furniture.utlis.Languages
import com.furniture.utlis.LocaleHelper
import com.furniture.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class StartFragment : DaggerFragment() {
    private lateinit var binding: FragmentStartBinding

    @Inject
    lateinit var prefsManager: PrefsManager
    private var language: String = Languages.ENGLISH

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.btnStart.setOnClickListener {
            prefsManager.saveDefaultLocal(language)
            //uncomment this when you want to add local
            LocaleHelper.setLocale(requireContext(), language, prefsManager)
            findNavController().navigate(R.id.loginFragment)
        }
        binding.btnEnglish.setOnClickListener {
            language = Languages.ENGLISH
            binding.btnEnglish.setBackgroundResource(R.drawable.button_selector_squar)
            binding.btnEnglish.setTextColor(requireContext().resources.getColor(R.color.white))
            binding.btnArabic.setTextColor(requireContext().resources.getColor(R.color.black))
            binding.btnArabic.setBackgroundColor(Color.WHITE)
        }
        binding.btnArabic.setOnClickListener {
            language = Languages.ARABIC
            binding.btnArabic.setBackgroundResource(R.drawable.button_selector_squar)
            binding.btnEnglish.setTextColor(requireContext().resources.getColor(R.color.black))
            binding.btnArabic.setTextColor(requireContext().resources.getColor(R.color.white))
            binding.btnEnglish.setBackgroundColor(Color.WHITE)
        }
    }

}