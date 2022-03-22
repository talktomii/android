package com.furniture.di

import com.furniture.VideoActivity
import com.furniture.ui.banksettings.MyBankSettings
import com.furniture.ui.banksettings.activities.AddBankAccountActivity
import com.furniture.ui.editpersonalinfo.EditPersonalInfo
import com.furniture.ui.home.HomeActivity
import com.furniture.ui.home.InstaWebActivity
import com.furniture.ui.home.homeFragment.HomeFragment
import com.furniture.ui.home.profile.EditInterestFragment
import com.furniture.ui.home.profile.ProfileFragment
import com.furniture.ui.homefrag.HomesFragment
import com.furniture.ui.loginSignUp.MainActivity
import com.furniture.ui.loginSignUp.StartFragment
import com.furniture.ui.loginSignUp.login.LoginFragment
import com.furniture.ui.loginSignUp.signup.CreateProfileFragment
import com.furniture.ui.loginSignUp.signup.SignUpFragment
import com.furniture.ui.loginSignUp.splash.SplashFragment
import com.furniture.ui.mycards.MyCards
import com.furniture.ui.mycards.activities.MyCardsActivity
import com.furniture.ui.mycards.activities.PaymentDetailsActivity
import com.furniture.ui.mycards.fragments.CardFragment
import com.furniture.ui.mycards.fragments.PaymentFragment
import com.furniture.ui.settings.Settings
import com.furniture.ui.tellusmore.TellUsMore
import com.furniture.utlis.BackToHomeDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingsModule {

    @ContributesAndroidInjector
    abstract fun instaWebActivity(): InstaWebActivity

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity


    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun videoActivity(): VideoActivity

    @ContributesAndroidInjector
    abstract fun mycardsActivity(): MyCardsActivity

    @ContributesAndroidInjector
    abstract fun paymentDetailActivity(): PaymentDetailsActivity

    @ContributesAndroidInjector
    abstract fun addBankAccountActivity(): AddBankAccountActivity

    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun tellUsMoreFragment(): TellUsMore

    @ContributesAndroidInjector
    abstract fun editPersonalInfo(): EditPersonalInfo

    @ContributesAndroidInjector
    abstract fun editInterestFragment(): EditInterestFragment

    @ContributesAndroidInjector
    abstract fun homeFrag(): MyCards

    @ContributesAndroidInjector
    abstract fun bankSetting(): MyBankSettings

    @ContributesAndroidInjector
    abstract fun settings(): Settings

    @ContributesAndroidInjector
    abstract fun signupFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun createProfileFragment(): CreateProfileFragment


    @ContributesAndroidInjector
    abstract fun backToHomeDialog(): BackToHomeDialog


    @ContributesAndroidInjector
    abstract fun startFragment(): StartFragment

    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment


    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment


    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun homesFragment(): HomesFragment

    @ContributesAndroidInjector
    abstract fun cardFragment(): CardFragment

    @ContributesAndroidInjector
    abstract fun paymentFragment(): PaymentFragment


}