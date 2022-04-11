package com.talktomii.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.talktomii.ui.editpersonalinfo.EditPersonalInfoVM
import com.talktomii.ui.home.HomeScreenViewModel
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.loginSignUp.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun viewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory =
            factory
    }

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindMainViewModel(viewModel: HomeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    abstract fun homeScreenViewModel(viewModel: HomeScreenViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(EditPersonalInfoVM::class)
    abstract fun editPersonalInfoViewModel(viewModel: EditPersonalInfoVM): ViewModel

}

//run please