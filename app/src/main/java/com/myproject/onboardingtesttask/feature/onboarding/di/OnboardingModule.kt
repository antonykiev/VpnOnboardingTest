package com.myproject.onboardingtesttask.feature.onboarding.di

import com.myproject.onboardingtesttask.feature.onboarding.OnboardingViewModel
import com.myproject.onboardingtesttask.feature.onboarding.business.GetLoadedUiStateUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { OnboardingViewModel(getLoadedUiStateUseCase = get()) }
    factory { GetLoadedUiStateUseCase(get()) }
}