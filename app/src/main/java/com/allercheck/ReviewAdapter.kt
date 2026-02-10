package com.allercheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(
    private var reviews: MutableList<Ressenya>,
    private val onRestaurantClick: (Ressenya) -> Unit,
    private val onEditClick: (Ressenya) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review, onRestaurantClick, onEditClick)
    }

    override fun getItemCount(): Int = reviews.size

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val restaurantButton: Button = itemView.findViewById(R.id.btnRestaurant)
        private val starsLayout: LinearLayout = itemView.findViewById(R.id.llStars)
        private val reviewTextView: TextView = itemView.findViewById(R.id.tvReviewText)
        private val editButton: ImageButton = itemView.findViewById(R.id.btnEditReview)

        fun bind(
            review: Ressenya,
            onRestaurantClick: (Ressenya) -> Unit,
            onEditClick: (Ressenya) -> Unit
        ) {
            restaurantButton.text = review.restaurantName
            reviewTextView.text = review.reviewText

            starsLayout.removeAllViews()
            val starColor = itemView.context.getColor(R.color.verde)
            val greyColor = itemView.context.getColor(android.R.color.darker_gray)

            for (i in 1..5) {
                val star = ImageView(itemView.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setImageResource(R.drawable.baseline_star_rate_24)
                    setColorFilter(if (i <= review.stars) starColor else greyColor)
                }
                starsLayout.addView(star)
            }

            // Listeners
            restaurantButton.setOnClickListener { onRestaurantClick(review) }
            editButton.setOnClickListener { onEditClick(review) }
        }
    }
}
