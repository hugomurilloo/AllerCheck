package com.allercheck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_review : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var rvReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var reviewsList: MutableList<Ressenya>

    private val editReviewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loadSampleData()
            reviewAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // INICIALIZAR VISTAS
        btnAtras = findViewById(R.id.btnAtras)
        rvReviews = findViewById(R.id.rvReviews)

        // CONFIGURAR RECYCLERVIEW
        setupRecyclerView()
        loadSampleData()

        // LISTENERS
        btnAtras.setOnClickListener {
            finish()
        }

    }

    private fun loadSampleData() {
        if (!::reviewsList.isInitialized) {
            reviewsList = mutableListOf()
        }
        reviewsList.clear()
        reviewsList.addAll(
            mutableListOf(
                Ressenya("1", "1", "Restaurant Vegà", 4, "Molt bona atenció.", true),
                Ressenya("2", "2", "La Pizzeria Clàssica", 5, "Excel·lent, com sempre.", true),
                Ressenya("3", "5", "Oriental Restaurant", 3, "Podria millorar.", false)
            )
        )
    }

    private fun setupRecyclerView() {
        reviewsList = mutableListOf()
        reviewAdapter = ReviewAdapter(
            reviewsList,
            onRestaurantClick = { review ->

            },
            onEditClick = { review ->
                val intent = Intent(this, activity_create_edit_review::class.java)
                intent.putExtra("EXTRA_REVIEW", review)
                editReviewLauncher.launch(intent) // Usamos el launcher para esperar un resultado
            }
        )
        rvReviews.adapter = reviewAdapter
        rvReviews.layoutManager = LinearLayoutManager(this)
    }
}
