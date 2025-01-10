package com.myproject.onboardingtesttask.feature.onboarding.business

import android.content.Context
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.myproject.onboardingtesttask.R
import com.myproject.onboardingtesttask.feature.onboarding.OnboardingPage
import com.myproject.onboardingtesttask.feature.onboarding.OnboardingUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.suspendCoroutine

class GetLoadedUiStateUseCase(
) {

    suspend operator fun invoke(context: Context,): OnboardingUiState {
        return supervisorScope {
            val zeroDiffered =
                async { loadLottieComposition(context, "animations/animation_0.json") }
            val firstDiffered =
                async { loadLottieComposition(context, "animations/animation_1.json") }
            val secondDiffered =
                async { loadLottieComposition(context, "animations/animation_2.json") }

            val zeroComposition = zeroDiffered.await()
            val firstComposition = firstDiffered.await()
            val secondComposition = secondDiffered.await()

            return@supervisorScope OnboardingUiState.Loaded(
                currentPage = 0,
                pages = listOf(
                    OnboardingPage(
                        title = R.string.onboarding_title_0,
                        description = R.string.onboarding_description_0,
                        lottieComposition = zeroComposition
                    ),
                    OnboardingPage(
                        title = R.string.onboarding_title_1,
                        description = R.string.onboarding_description_1,
                        lottieComposition = firstComposition
                    ),
                    OnboardingPage(
                        title = R.string.onboarding_title_2,
                        description = R.string.onboarding_description_2,
                        lottieComposition = secondComposition
                    )
                )
            )
        }
    }

    private suspend fun loadLottieComposition(
        context: Context,
        assetName: String,
    ): LottieComposition {
        return suspendCoroutine { continuation ->
            LottieCompositionFactory.fromAsset(context, assetName)
                .addListener {
                    continuation.resumeWith(Result.success(it))
                }
        }
    }
}