package com.antonio.android.restaurants.view.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonio.android.restaurants.R
import com.antonio.android.restaurants.models.Restaurant
import com.antonio.android.restaurants.models.SortType

class RestaurantsAdapter(): RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {

    private var restaurants = mutableListOf<Restaurant>()
    lateinit var clickListener: ((Restaurant) -> Unit)
    private var currentSortType: SortType = SortType.BEST_MATCH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_restaurant,parent,false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    fun setItems(items: MutableList<Restaurant>) {
        restaurants = items
        notifyDataSetChanged()
    }

    fun setSortType(sortType: SortType){
        this.currentSortType = sortType
        notifyDataSetChanged()
    }

    inner class RestaurantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val restaurantName = itemView.findViewById<TextView>(R.id.restaurant_name)
        val sortType = itemView.findViewById<TextView>(R.id.sort_type)
        val sortValue = itemView.findViewById<TextView>(R.id.sort_value)
        val openStatus = itemView.findViewById<TextView>(R.id.restaurant_status)
        val favoriteButton = itemView.findViewById<ImageView>(R.id.favourites_button)
        var restaurant : Restaurant? = null


        fun bind(restaurant: Restaurant) {
            this.restaurant = restaurant
            restaurantName.text = restaurant.name
            openStatus.text = restaurant.status?.status
            sortType.text = currentSortType.name

            sortValue.text = when(currentSortType){
                SortType.BEST_MATCH ->  restaurant.sortingValues?.bestMatch.toString()
                SortType.NEWEST -> restaurant.sortingValues?.newest.toString()
                SortType.RATING_AVERAGE ->  restaurant.sortingValues?.ratingAverage.toString()
                SortType.DISTANCE -> restaurant.sortingValues?.distance.toString()
                SortType.POPULARITY -> restaurant.sortingValues?.popularity.toString()
                SortType.AVERAGE_PRODUCT_PRICE -> restaurant.sortingValues?.averageProductPrice.toString()
                SortType.DELIVERY_COST -> restaurant.sortingValues?.deliveryCosts.toString()
                SortType.MIN_COST -> restaurant.sortingValues?.minCost.toString()
            }

            if(restaurant.isFavorite) {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_on)
            } else {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_off)
            }

            favoriteButton.setOnClickListener {
                restaurant.let {
                    clickListener.invoke(restaurant)
                }
            }
        }
    }
}