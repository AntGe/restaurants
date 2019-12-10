package com.antonio.android.restaurants.injection

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.preference.PreferenceManager
import com.antonio.android.restaurants.view.restaurants.RestaurantsRepository
import com.antonio.android.restaurants.view.restaurants.RestaurantsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    factory { RestaurantsRepository(get(), get()) }
    single { PreferenceManager.getDefaultSharedPreferences(get()) as SharedPreferences }
    single<AssetManager> { get<Context>().assets }
    viewModel { RestaurantsViewModel(get()) }
}