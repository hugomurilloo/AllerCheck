package com.allercheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteRestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit,
    private val onDeleteClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<FavoriteRestaurantAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant, onItemClick, onDeleteClick)
    }

    override fun getItemCount(): Int = restaurants.size

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvRestaurantName)
        private val ratingTextView: TextView = itemView.findViewById(R.id.tvRating)
        private val verificationLayout: View = itemView.findViewById(R.id.llVerificat)
        private val deleteButton: ImageView = itemView.findViewById(R.id.ivDelete)
        private val deleteRadioButton: RadioButton = itemView.findViewById(R.id.rbDelete)

        fun bind(
            restaurant: Restaurant,
            onItemClick: (Restaurant) -> Unit,
            onDeleteClick: (Restaurant) -> Unit
        ) {
            nameTextView.text = restaurant.name
            ratingTextView.text = restaurant.rating
            verificationLayout.visibility = if (restaurant.isVerified) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                onItemClick(restaurant)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(restaurant)
            }
            deleteRadioButton.setOnClickListener {
                onDeleteClick(restaurant)
            }
        }
    }
}
