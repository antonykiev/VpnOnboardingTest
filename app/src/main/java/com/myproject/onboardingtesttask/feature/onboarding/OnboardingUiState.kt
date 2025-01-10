package com.myproject.onboardingtesttask.feature.onboarding

import androidx.annotation.StringRes
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec

data class OnboardingUiState(
    val currentPage: Int,
    val pages: List<OnboardingPage>,
)

data class OnboardingPage(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val lottieCompositionSpec: LottieCompositionSpec,
)
