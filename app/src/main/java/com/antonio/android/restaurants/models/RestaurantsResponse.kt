package com.antonio.android.restaurants.models

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("restaurants")
    val restaurants : MutableList<Restaurant>?
)