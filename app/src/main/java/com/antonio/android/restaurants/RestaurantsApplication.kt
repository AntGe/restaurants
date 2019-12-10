package com.antonio.android.restaurants

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import com.antonio.android.restaurants.injection.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RestaurantsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RestaurantsApplication)
            modules(listOf(koinModule))
        }
    }
}