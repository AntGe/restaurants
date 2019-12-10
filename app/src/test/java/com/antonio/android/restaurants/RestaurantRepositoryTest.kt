package com.antonio.android.restaurants

import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mock
import android.content.res.AssetManager
import com.antonio.android.restaurants.models.OpenStatus
import com.antonio.android.restaurants.models.Restaurant
import com.antonio.android.restaurants.models.SortingValues
import com.antonio.android.restaurants.view.restaurants.RestaurantsRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations

private const val FAV_KEY = "favs"

class RestaurantRepositoryTest{

    @Mock
    private lateinit var assetManager: AssetManager
    @Mock
    private lateinit var preferenceEditor: SharedPreferences.Editor
    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var repository: RestaurantsRepository

    val restaurant = Restaurant(
        "test",
        OpenStatus.OPEN,
        SortingValues(
            1.0f, 1.0f,
            1.0f, 1, 1.0f, 1, 1, 1
        ),
        false
    )

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        repository = RestaurantsRepository(
            assetManager,
            sharedPreferences
        )
        Mockito.`when`(sharedPreferences.edit()).thenReturn(preferenceEditor)
        Mockito.`when`(preferenceEditor.commit()).thenReturn(true)
    }

    @Test
    fun addToFavorites() {
        Mockito.`when`(preferenceEditor.putStringSet(eq(FAV_KEY), any())).thenReturn(preferenceEditor)
        repository.addToFavourites(restaurant)

        verify(sharedPreferences).getStringSet(anyString(),any())
        verify(sharedPreferences).edit()
        verify(preferenceEditor).putStringSet(eq( FAV_KEY),any())
        verify(preferenceEditor).commit()
    }

    @Test
    fun removeFromFavorites() {
        Mockito.`when`(preferenceEditor.putStringSet(eq(FAV_KEY), any())).thenReturn(preferenceEditor)
        repository.removeFromFavourites(restaurant)

        verify(sharedPreferences).getStringSet(anyString(),any())
        verify(sharedPreferences).edit()
        verify(preferenceEditor).putStringSet(eq( FAV_KEY),any())
        verify(preferenceEditor).commit()
    }

    @Test
    fun getFavorites() {
        repository.getFavourites()

        verify(sharedPreferences).getStringSet(eq(FAV_KEY), any())
    }
}