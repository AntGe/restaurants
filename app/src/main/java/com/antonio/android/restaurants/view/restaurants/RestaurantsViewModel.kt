package com.antonio.android.restaurants.view.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonio.android.restaurants.models.Restaurant
import com.antonio.android.restaurants.models.SortType
import org.koin.core.KoinComponent
import java.lang.IllegalStateException

class RestaurantsViewModel(
    private val restaurantsRepository: RestaurantsRepository
): ViewModel() {
    private val restaurantsViewData =
        RestaurantsViewData()
    private var currentRestaurants = mutableListOf<Restaurant>()
    private var allRestaurants = mutableListOf<Restaurant>()
    private var currentSortType: SortType = SortType.BEST_MATCH

    internal val viewData = MutableLiveData<RestaurantsViewData>().apply {
        postValue(restaurantsViewData)
    }

    fun loadData(){
        this.allRestaurants = restaurantsRepository.getRestaurants()
        this.currentRestaurants = ArrayList<Restaurant>(this.allRestaurants)
        setFavourites()
        sortRestaurants()
        showResults(this.currentRestaurants)
    }
    
    fun setFavourites() {
        this.restaurantsRepository.getFavourites().apply {
            currentRestaurants.forEach { restaurant ->
                restaurant.isFavorite = this.contains(restaurant.name)
            }
        }
    }

    fun favouriteButtonClicked(restaurant: Restaurant){
        if(restaurant.isFavorite){
            this.restaurantsRepository.removeFromFavourites(restaurant)
        }else{
            this.restaurantsRepository.addToFavourites(restaurant)
        }
        setFavourites()
        showResults(this.currentRestaurants)
    }

    fun showResults(restaurants: MutableList<Restaurant>) {
        viewData.value?.let {
            val newData = restaurantsViewData.copy(restaurants = restaurants,
                sortType = currentSortType)
            viewData.value = newData
        }
    }

    fun changeSearchQuery(query: String) {
        currentRestaurants = filterRestaurants(query)
        setFavourites()
        sortRestaurants()
        showResults(currentRestaurants)
    }

    fun changeSortType(position: Int){
        currentSortType = when(position) {
            0 -> SortType.BEST_MATCH
            1 -> SortType.NEWEST
            2 -> SortType.RATING_AVERAGE
            3 -> SortType.DISTANCE
            4 -> SortType.POPULARITY
            5 -> SortType.AVERAGE_PRODUCT_PRICE
            6 -> SortType.DELIVERY_COST
            7 -> SortType.MIN_COST
            else -> throw IllegalStateException("Unexpected sort type")
        }
        sortRestaurants()
        showResults(this.currentRestaurants)
    }

    fun sortRestaurants(){
        this.currentRestaurants = currentRestaurants.sortedWith( compareBy<Restaurant> { !it.isFavorite}
            .thenBy { it.status }
            .thenBy {
            when(currentSortType){
                SortType.BEST_MATCH -> {-it.sortingValues?.bestMatch!!}
                SortType.NEWEST -> {-it.sortingValues?.newest!!}
                SortType.RATING_AVERAGE -> {-it.sortingValues?.ratingAverage!!}
                SortType.DISTANCE ->  {it.sortingValues?.distance}
                SortType.POPULARITY -> {-it.sortingValues?.popularity!!}
                SortType.AVERAGE_PRODUCT_PRICE -> {it.sortingValues?.averageProductPrice}
                SortType.DELIVERY_COST ->  {it.sortingValues?.deliveryCosts}
                SortType.MIN_COST -> {it.sortingValues?.minCost}
            }
        }).toMutableList()
    }

    fun filterRestaurants(name: String): MutableList<Restaurant> {
        if (name.isEmpty()) return allRestaurants
         val filteredList= allRestaurants.filter { restaurant ->  restaurant.name?.
            toLowerCase()!!.contains(name.toLowerCase())}.toMutableList()
        return filteredList
    }
}