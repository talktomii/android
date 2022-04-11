package com.talktomii.di

import com.talktomii.ui.home.profile.InfluencerProfileFragment
import com.talktomii.ui.search.SearchFragment
import com.talktomii.VideoActivity
import com.talktomii.ui.FAQ.FaqActivity
import com.talktomii.ui.appointment.AppointmentsFragment
import com.talktomii.ui.banksettings.MyBankSettings
import com.talktomii.ui.banksettings.activities.AddBankAccountActivity
import com.talktomii.ui.callhistory.CallHistory
import com.talktomii.ui.callhistory.activities.CallInvoiceActivity
import com.talktomii.ui.coupon.CouponActivity
import com.talktomii.ui.editpersonalinfo.EditPersonalInfo
import com.talktomii.ui.helpsupport.ChatSupportActivity
import com.talktomii.ui.helpsupport.HelpSupport
import com.talktomii.ui.drawer.bookmark.BookmarkFragment
import com.talktomii.ui.drawer.helpsupport.HelpSupportFragment
import com.talktomii.ui.drawer.settings.SettingsFragment
import com.talktomii.ui.home.HomeActivity
import com.talktomii.ui.home.homeFragment.HomeFragment
import com.talktomii.ui.home.notifications.NotificationFragment
import com.talktomii.ui.home.profile.*
import com.talktomii.ui.homefrag.HomesFragment
import com.talktomii.ui.homefrag.InfluencerHomeFragment
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.ui.loginSignUp.login.ForgetFragment
import com.talktomii.ui.loginSignUp.login.ForgetPasswordFragment
import com.talktomii.ui.loginSignUp.login.FragmentResetPassword
import com.talktomii.ui.loginSignUp.login.LoginFragment
import com.talktomii.ui.loginSignUp.signup.CreateProfileFragment
import com.talktomii.ui.loginSignUp.signup.SignUpFragment
import com.talktomii.ui.loginSignUp.splash.SplashFragment
import com.talktomii.ui.mycards.MyCards
import com.talktomii.ui.mycards.activities.MyCardsActivity
import com.talktomii.ui.mycards.activities.PaymentDetailsActivity
import com.talktomii.ui.mycards.fragments.CardFragment
import com.talktomii.ui.mycards.fragments.PaymentFragment
import com.talktomii.ui.mywallet.MyWallet
import com.talktomii.ui.mywallet.activities.GetPaidActivity
import com.talktomii.ui.mywallet.activities.IncomeDetailsActivity
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import com.talktomii.ui.mywallet.fragments.EarningFragment
import com.talktomii.ui.mywallet.fragments.ExpenseFragment
import com.talktomii.ui.mywallet.fragments.RefillFragment
import com.talktomii.ui.reportabuse.ReportAbuseActivity
import com.talktomii.ui.settings.PrivacyAndPolicyActivity
import com.talktomii.ui.settings.Settings
import com.talktomii.ui.tellusmore.TellUsMore
import com.talktomii.utlis.BackToHomeDialog
import com.talktomii.utlis.CallDialog
import com.talktomii.utlis.DeleteAppointmentDialog
import com.talktomii.utlis.ExtendTimeDialog
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
    abstract fun mycardsActivity(): MyCardsActivity

    @ContributesAndroidInjector
    abstract fun paymentDetailActivity(): PaymentDetailsActivity

    @ContributesAndroidInjector
    abstract fun addBankAccountActivity(): AddBankAccountActivity

    @ContributesAndroidInjector
    abstract fun walletRefillActivity(): RefillWalletActivity

    @ContributesAndroidInjector
    abstract fun walletGetPaidActivity(): GetPaidActivity

    @ContributesAndroidInjector
    abstract fun reportAbuseActivity(): ReportAbuseActivity

    @ContributesAndroidInjector
    abstract fun privacyActivity(): PrivacyAndPolicyActivity

    @ContributesAndroidInjector
    abstract fun helpsupportActivity(): HelpSupport

    @ContributesAndroidInjector
    abstract fun chatActivity(): ChatSupportActivity

    @ContributesAndroidInjector
    abstract fun incomeDetailActivity(): IncomeDetailsActivity

    @ContributesAndroidInjector
    abstract fun callInvoiceActivity(): CallInvoiceActivity

    @ContributesAndroidInjector
    abstract fun couponActivity(): CouponActivity

    @ContributesAndroidInjector
    abstract fun faqActivity(): FaqActivity

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
    abstract fun myWallet(): MyWallet

    @ContributesAndroidInjector
    abstract fun callHistory(): CallHistory

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


    @ContributesAndroidInjector
    abstract fun homesFragment(): HomesFragment

    @ContributesAndroidInjector
    abstract fun cardFragment(): CardFragment

    @ContributesAndroidInjector
    abstract fun paymentFragment(): PaymentFragment

    @ContributesAndroidInjector
    abstract fun earningFragment(): EarningFragment

    @ContributesAndroidInjector
    abstract fun refillFragment(): RefillFragment

    @ContributesAndroidInjector
    abstract fun expenseFragment(): ExpenseFragment

    //drawer
    @ContributesAndroidInjector
    abstract fun bookmarkfragment(): BookmarkFragment

    @ContributesAndroidInjector
    abstract fun settingFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun helpSupportFragment(): HelpSupportFragment

}