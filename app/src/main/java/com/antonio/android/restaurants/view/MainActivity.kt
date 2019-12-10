package com.antonio.android.restaurants.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonio.android.restaurants.R
import com.antonio.android.restaurants.view.restaurants.FragmentRestaurants

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container
        )
        if (currentFragment == null) {
            val fragment =
                FragmentRestaurants.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment).commit()
        }
    }
}