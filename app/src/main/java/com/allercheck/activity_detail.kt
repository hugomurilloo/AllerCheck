package com.allercheck

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class activity_detail : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var btnRess: Button
    private lateinit var cbFavorite: CheckBox
    private lateinit var statsViewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        statsViewModel = ViewModelProvider(this).get(StatsViewModel::class.java)

        btnAtras = findViewById(R.id.btnAtras)
        btnRess = findViewById(R.id.btnRess)
        cbFavorite = findViewById(R.id.csR1)

        val restaurant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_RESTAURANT", Restaurant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Restaurant>("EXTRA_RESTAURANT")
        }

        if (restaurant == null) {
            finish()
            return
        }

        val tvName = findViewById<TextView>(R.id.tvRestaurantNameDetail)
        val tvRating = findViewById<TextView>(R.id.tvRatingDetail)
        val tvTipus = findViewById<TextView>(R.id.tvTipus)
        val tvPreu = findViewById<TextView>(R.id.tvPreu)
        val tvUbi = findViewById<TextView>(R.id.tvUbi)
        val tvTel = findViewById<TextView>(R.id.tvTel)

        tvName.text = restaurant.name ?: "Sense nom"
        tvRating.text = restaurant.rating ?: "0.0"
        tvTipus.text = restaurant.tipusCuina ?: "No especificat"
        tvPreu.text = restaurant.rangPreu ?: "---"
        tvUbi.text = restaurant.ubicacio ?: "Ubicació no disponible"
        tvTel.text = restaurant.telefon ?: "Sense telèfon"

        cbFavorite.isChecked = restaurant.isFavorite

        when (restaurant.tipusCuina?.lowercase()) {
            "japonès" -> statsViewModel.trackFilter("gluten")
            "italià" -> statsViewModel.trackFilter("lactose")
            "vegetarià" -> statsViewModel.trackFilter("halal")
            "marisc" -> statsViewModel.trackFilter("marisc")
            "ou" -> statsViewModel.trackFilter("ou")
            else -> statsViewModel.trackFilter("kosher")
        }

        btnAtras.setOnClickListener { finish() }

        cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                try {
                    if (isChecked) {
                        ItemAPI.API().addFavorite(mapOf("id" to restaurant.id))
                    } else {
                        ItemAPI.API().deleteFavorite(restaurant.id)
                    }
                } catch (e: Exception) {
                    cbFavorite.isChecked = !isChecked
                }
            }
        }

        btnRess.setOnClickListener {
            val intentReview = Intent(this, activity_create_edit_review::class.java)
            intentReview.putExtra("REST_NAME", restaurant.name ?: "Restaurant")
            intentReview.putExtra("REST_ID", restaurant.id)
            startActivity(intentReview)
        }
    }
}