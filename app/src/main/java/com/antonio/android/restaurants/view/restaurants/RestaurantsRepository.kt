package com.antonio.android.restaurants.view.restaurants

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.antonio.android.restaurants.models.Restaurant
import com.antonio.android.restaurants.models.RestaurantsResponse
import com.google.gson.Gson
import org.json.JSONObject
import org.koin.core.KoinComponent

private const val FAV_KEY = "favs"

class RestaurantsRepository(private val assetManager : AssetManager,
                            private val sharedPreferences: SharedPreferences): KoinComponent {
    fun getRestaurants(): MutableList<Restaurant> {
        val json = JSONObject(
            assetManager.open("sample.json")
                .bufferedReader()
                .use {
                    it.readText()
                })
        val restaurants = Gson().fromJson<RestaurantsResponse>(json.toString(),
            RestaurantsResponse::class.java)
        return restaurants.restaurants!!
    }

    @SuppressLint("ApplySharedPref")
    fun addToFavourites(restaurant: Restaurant) {
        val favourites = sharedPreferences
            .getStringSet(FAV_KEY, HashSet<String>())
        favourites!!.add(restaurant.name)
        sharedPreferences.edit()
            .putStringSet(FAV_KEY, favourites)
            .commit()
    }

    @SuppressLint("ApplySharedPref")
    fun removeFromFavourites(restaurant: Restaurant){
        val favourites = sharedPreferences
            .getStringSet(FAV_KEY, HashSet<String>())
        favourites!!.remove(restaurant.name)
        sharedPreferences.edit()
            .putStringSet(FAV_KEY, favourites)
            .commit()

    }

    fun getFavourites(): HashSet<String> {
        return HashSet<String>(sharedPreferences
            .getStringSet(FAV_KEY, HashSet<String>()))
    }
}