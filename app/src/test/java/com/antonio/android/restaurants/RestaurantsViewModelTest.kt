package com.antonio.android.restaurants

import com.antonio.android.restaurants.models.OpenStatus
import com.antonio.android.restaurants.models.Restaurant
import com.antonio.android.restaurants.models.SortingValues
import com.antonio.android.restaurants.view.restaurants.RestaurantsRepository
import com.antonio.android.restaurants.view.restaurants.RestaurantsViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RestaurantsViewModelTest {

    @Mock
    private lateinit var repository: RestaurantsRepository

    private lateinit var viewModel: RestaurantsViewModel

    val restaurant1 = Restaurant(
        "test1",
        OpenStatus.OPEN,
        SortingValues(
            1.0f, 1.0f,
            1.0f, 1, 1.0f, 1, 1, 1
        ),
        false
    )
    val restaurant2 = Restaurant(
        "test2",
        OpenStatus.CLOSED,
        SortingValues(
            1.0f, 1.0f,
            1.0f, 1, 1.0f, 1, 1, 1
        ),
        true
    )

    val restaurants = mutableListOf<Restaurant>(restaurant1, restaurant2)

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel =
            RestaurantsViewModel(this.repository)
    }

    @Test
    fun loadData() {
        val viewModelSpy = Mockito.spy(viewModel)

        viewModelSpy.loadData()
        verify(repository).getRestaurants()
        verify(viewModelSpy).setFavourites()
        verify(viewModelSpy).sortRestaurants()
        verify(viewModelSpy).showResults(any())
    }

    @Test
    fun changeSortType(){
        val viewModelSpy = Mockito.spy(viewModel)
        viewModelSpy.changeSortType(0)
        verify(viewModelSpy).sortRestaurants()
        verify(viewModelSpy).showResults(any())
    }

    @Test
    fun updateFavorites() {
        viewModel.setFavourites()
        verify(repository).getFavourites()
    }

    @Test
    fun filterByName(){
        val viewModelSpy = Mockito.spy(viewModel)
        viewModelSpy.changeSearchQuery("restaurant1")
        verify(viewModelSpy).filterRestaurants("restaurant1")
        verify(viewModelSpy).setFavourites()
        verify(viewModelSpy).sortRestaurants()
        verify(viewModelSpy).showResults(any())
    }

    @Test
    fun addToFavorites(){
        val viewModelSpy = Mockito.spy(viewModel)

        viewModelSpy.favouriteButtonClicked(restaurant1)
        verify(repository).addToFavourites(restaurant1)
        verify(viewModelSpy).setFavourites()
        verify(viewModelSpy).showResults(any())
    }

    @Test
    fun removeFromFavorites(){
        val viewModelSpy = Mockito.spy(viewModel)

        viewModelSpy.favouriteButtonClicked(restaurant2)
        verify(repository).removeFromFavourites(restaurant2)
        verify(viewModelSpy).setFavourites()
        verify(viewModelSpy).showResults(any())
    }
}