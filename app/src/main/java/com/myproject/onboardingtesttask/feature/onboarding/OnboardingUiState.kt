package com.myproject.onboardingtesttask.feature.onboarding

import androidx.annotation.StringRes
import com.airbnb.lottie.LottieComposition

sealed class OnboardingUiState {
    data object Loading: OnboardingUiState()

    data class Loaded(
        val currentPage: Int,
        val pages: List<OnboardingPage>
    ) : OnboardingUiState()
}

data class OnboardingPage(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val lottieComposition: LottieComposition,
)