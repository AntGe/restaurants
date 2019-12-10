package com.antonio.android.restaurants.models

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("name")
val name : String?,
    @SerializedName("status")
val status : OpenStatus?,
    @SerializedName("sortingValues")
val sortingValues : SortingValues?,
    var isFavorite : Boolean = false
)

enum class OpenStatus (val status: String){
    @SerializedName("open")
    OPEN("open"),
    @SerializedName("order ahead")
    ORDER_AHEAD("order ahead"),
    @SerializedName("closed")
    CLOSED("closed")
}