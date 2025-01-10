package com.myproject.onboardingtesttask

import android.app.Application
import com.myproject.onboardingtesttask.feature.onboarding.di.onboardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VpnApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(onboardingModule)
            androidContext(this@VpnApplication)
        }
    }
}