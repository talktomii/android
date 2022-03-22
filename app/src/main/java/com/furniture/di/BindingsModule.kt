package com.furniture.di

import com.furniture.VideoActivity
import com.furniture.databinding.TermsAndConditionsDialogBinding
import com.furniture.ui.appointment.AppointmentsFragment
import com.furniture.ui.editpersonalinfo.EditPersonalInfo
import com.furniture.ui.home.HomeActivity
import com.furniture.ui.home.homeFragment.HomeFragment
import com.furniture.ui.home.profile.EditInterestFragment
import com.furniture.ui.home.profile.InfluencerProfileFragment
import com.furniture.ui.home.profile.ProfileFragment
import com.furniture.ui.homefrag.HomesFragment
import com.furniture.ui.loginSignUp.MainActivity
import com.furniture.ui.loginSignUp.login.LoginFragment
import com.furniture.ui.loginSignUp.signup.CreateProfileFragment
import com.furniture.ui.loginSignUp.signup.SignUpFragment
import com.furniture.ui.loginSignUp.splash.SplashFragment
import com.furniture.ui.search.SearchFragment
import com.furniture.ui.tellusmore.TellUsMore
import com.furniture.utlis.BackToHomeDialog
import com.furniture.utlis.CallDialog
import com.furniture.utlis.DeleteAppointmentDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingsModule {



    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity


    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun videoActivity(): VideoActivity


    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun tellUsMoreFragment(): TellUsMore

    @ContributesAndroidInjector
    abstract fun editPersonalInfo(): EditPersonalInfo

    @ContributesAndroidInjector
    abstract fun editInterestFragment(): EditInterestFragment

    @ContributesAndroidInjector
    abstract fun homeFrag(): HomesFragment

    @ContributesAndroidInjector
    abstract fun signupFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment


    @ContributesAndroidInjector
    abstract fun createProfileFragment(): CreateProfileFragment

    @ContributesAndroidInjector
    abstract fun appointmentsFragment(): AppointmentsFragment


    @ContributesAndroidInjector
    abstract fun backToHomeDialog(): BackToHomeDialog

    @ContributesAndroidInjector
    abstract fun callDialog(): CallDialog

    @ContributesAndroidInjector
    abstract fun deleteAppointmentDialog(): DeleteAppointmentDialog



    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun profileInfluencerFragment(): InfluencerProfileFragment


    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment


    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment


}