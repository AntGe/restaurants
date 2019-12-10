package com.antonio.android.restaurants.view.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antonio.android.restaurants.R 
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentRestaurants: Fragment() {
    private val restaurantsViewModel: RestaurantsViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var sortSpinner: Spinner
    private lateinit var searchView: SearchView
    private var restaurantsAdapter =
        RestaurantsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataConsumption()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(R.layout.fragment_restaurants, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        sortSpinner = view.findViewById(R.id.sort_types_spinner)
        searchView = view.findViewById(R.id.searchView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initSearchView()
        restaurantsAdapter.clickListener = {
            restaurantsViewModel.favouriteButtonClicked(it)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = restaurantsAdapter
        }

        restaurantsViewModel.loadData()
    }

    fun initSpinner(){
        sortSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                restaurantsViewModel.changeSortType(p2)
            }
        }
    }

    private fun initSearchView(){
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    restaurantsViewModel.changeSearchQuery(it)
                }
                return false
            }

        })
    }

    private fun initViewDataConsumption(){
        restaurantsViewModel.viewData.observe(this, Observer {
            if (it != null){
                it.restaurants?.let { restaurants ->
                    restaurantsAdapter.setItems(restaurants)
                }
                restaurantsAdapter.setSortType(it.sortType)
            }
        })
    }

    companion object {
        fun newInstance(): FragmentRestaurants {
            return FragmentRestaurants()
        }
    }
}