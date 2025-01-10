package com.myproject.onboardingtesttask.feature.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.onboardingtesttask.feature.onboarding.business.GetLoadedUiStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(getInitialState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val useCase = GetLoadedUiStateUseCase()

    fun load(context: Context) {
        viewModelScope.launch {
            _uiState.value = useCase.invoke(context)
        }
    }

    private fun getInitialState(): OnboardingUiState {
        return OnboardingUiState.Loading
    }
}