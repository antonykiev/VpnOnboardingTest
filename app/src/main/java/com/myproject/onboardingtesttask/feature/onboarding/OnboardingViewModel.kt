package com.myproject.onboardingtesttask.feature.onboarding

import androidx.lifecycle.ViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.myproject.onboardingtesttask.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnboardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(getInitialState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private fun getInitialState(): OnboardingUiState {
        return OnboardingUiState(
            currentPage = 0,
            pages = listOf(
                OnboardingPage(
                    title = R.string.onboarding_title_0,
                    description = R.string.onboarding_description_0,
                    lottieCompositionSpec = LottieCompositionSpec.Asset("animations/animation_0.json")
                ),
                OnboardingPage(
                    title = R.string.onboarding_title_1,
                    description = R.string.onboarding_description_1,
                    lottieCompositionSpec = LottieCompositionSpec.Asset("animations/animation_2.json")
                ),
                OnboardingPage(
                    title = R.string.onboarding_title_2,
                    description = R.string.onboarding_description_2,
                    lottieCompositionSpec = LottieCompositionSpec.Asset("animations/animation_0.json")
                ),
            )
        )
    }
}