package com.myproject.onboardingtesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.myproject.onboardingtesttask.feature.onboarding.OnboardingScreen
import com.myproject.onboardingtesttask.ui.theme.OnboardingTestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingTestTaskTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnboardingScreen()
                }
            }
        }
    }
}
