package com.furniture.di

import com.furniture.VideoActivity
import com.furniture.ui.appointment.AppointmentsFragment
import com.furniture.ui.drawer.bookmark.BookmarkFragment
import com.furniture.ui.drawer.bookmark.SettingFragment
import com.furniture.ui.editpersonalinfo.EditPersonalInfo
import com.furniture.ui.home.HomeActivity
import com.furniture.ui.home.homeFragment.HomeFragment
import com.furniture.ui.home.notifications.NotificationFragment
import com.furniture.ui.home.profile.*
import com.furniture.ui.homefrag.HomesFragment
import com.furniture.ui.homefrag.InfluencerHomeFragment
import com.furniture.ui.loginSignUp.MainActivity
import com.furniture.ui.loginSignUp.login.ForgetFragment
import com.furniture.ui.loginSignUp.login.ForgetPasswordFragment
import com.furniture.ui.loginSignUp.login.FragmentResetPassword
import com.furniture.ui.loginSignUp.login.LoginFragment
import com.furniture.ui.loginSignUp.signup.CreateProfileFragment
import com.furniture.ui.loginSignUp.signup.SignUpFragment
import com.furniture.ui.loginSignUp.splash.SplashFragment
import com.furniture.ui.search.SearchFragment
import com.furniture.ui.tellusmore.TellUsMore
import com.furniture.utlis.BackToHomeDialog
import com.furniture.utlis.CallDialog
import com.furniture.utlis.DeleteAppointmentDialog
import com.furniture.utlis.ExtendTimeDialog
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
    abstract fun myBudgesFragment(): MyBudgesFragment

    @ContributesAndroidInjector
    abstract fun forgetPasswordFragment(): ForgetPasswordFragment

    @ContributesAndroidInjector
    abstract fun forgetFragment(): ForgetFragment

    @ContributesAndroidInjector
    abstract fun resetPasswordFragment(): FragmentResetPassword

    @ContributesAndroidInjector
    abstract fun notificationFragment(): NotificationFragment


    @ContributesAndroidInjector
    abstract fun createProfileFragment(): CreateProfileFragment

    @ContributesAndroidInjector
    abstract fun appointmentsFragment(): AppointmentsFragment

    @ContributesAndroidInjector
    abstract fun homeInlfuenceFragment(): InfluencerHomeFragment

    @ContributesAndroidInjector
    abstract fun callFragment(): CallFragment

    @ContributesAndroidInjector
    abstract fun callEndFragment(): CallEndFragment


    @ContributesAndroidInjector
    abstract fun backToHomeDialog(): BackToHomeDialog

    @ContributesAndroidInjector
    abstract fun callDialog(): CallDialog

    @ContributesAndroidInjector
    abstract fun extendTimeDialog(): ExtendTimeDialog

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


    //drawer
    @ContributesAndroidInjector
    abstract fun bookmarkfragment(): BookmarkFragment

    @ContributesAndroidInjector
    abstract fun settingFragment(): SettingFragment

}