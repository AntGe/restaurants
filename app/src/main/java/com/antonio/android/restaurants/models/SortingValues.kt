package com.antonio.android.restaurants.models

import com.google.gson.annotations.SerializedName

data class SortingValues(
    @SerializedName("bestMatch")
    val bestMatch : Float?,
    @SerializedName("newest")
    val newest : Float?,
    @SerializedName("ratingAverage")
    val ratingAverage : Float?,
    @SerializedName("distance")
    val distance : Int?,
    @SerializedName("popularity")
    val popularity : Float?,
    @SerializedName("averageProductPrice")
    val averageProductPrice : Int?,
    @SerializedName("deliveryCosts")
    val deliveryCosts : Int?,
    @SerializedName("minCost")
    val minCost : Int?
)