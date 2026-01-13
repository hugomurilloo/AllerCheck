package com.allercheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant, onItemClick)
    }

    override fun getItemCount(): Int = restaurants.size

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvRestaurantName)
        private val ratingTextView: TextView = itemView.findViewById(R.id.tvRating)
        private val verificationLayout: View = itemView.findViewById(R.id.llVerificat)

        fun bind(restaurant: Restaurant, onItemClick: (Restaurant) -> Unit) {
            nameTextView.text = restaurant.name
            ratingTextView.text = restaurant.rating
            verificationLayout.visibility = if (restaurant.isVerified) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                onItemClick(restaurant)
            }
        }
    }
}
