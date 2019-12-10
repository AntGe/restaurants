package com.antonio.android.restaurants.view.restaurants

import com.antonio.android.restaurants.models.Restaurant
import com.antonio.android.restaurants.models.SortType

data class RestaurantsViewData(
    var restaurants: MutableList<Restaurant>? = null,
    var sortType: SortType = SortType.BEST_MATCH
)