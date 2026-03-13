package com.allercheck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class activity_review : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var rvReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewsList: MutableList<Ressenya> = mutableListOf()

    private val editReviewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loadReviewsFromApi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        btnAtras = findViewById(R.id.btnAtras)
        rvReviews = findViewById(R.id.rvReviews)

        setupRecyclerView()
        loadReviewsFromApi()

        btnAtras.setOnClickListener { finish() }
    }

    private fun loadReviewsFromApi() {
        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().getAllReviews()
                if (response.isSuccessful) {
                    val reviews = response.body() ?: emptyList()
                    reviewAdapter.updateReviews(reviews)
                }
            } catch (e: Exception) {
                Toast.makeText(this@activity_review, "Error de connexió", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        reviewAdapter = ReviewAdapter(
            reviewsList,
            onRestaurantClick = { review ->
            },
            onEditClick = { review ->
                val intent = Intent(this, activity_create_edit_review::class.java)
                intent.putExtra("EXTRA_REVIEW", review)
                intent.putExtra("REST_ID", review.restaurantId)
                intent.putExtra("REST_NAME", review.restaurantName)
                editReviewLauncher.launch(intent)
            }
        )
        rvReviews.adapter = reviewAdapter
        rvReviews.layoutManager = LinearLayoutManager(this)
    }
}