package com.allercheck

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class activity_detail : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var btnRess: Button
    private lateinit var cbFavorite: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Iniciar vistaa
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
            Toast.makeText(this, "Error al carregar les dades", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Llenar la UI con ? porque sino peta
        findViewById<TextView>(R.id.tvRestaurantNameDetail).text = restaurant.name ?: "Sense nom"
        findViewById<TextView>(R.id.tvRatingDetail).text = restaurant.rating ?: "0.0"
        findViewById<TextView>(R.id.tvTipus).text = restaurant.tipusCuina ?: "No especificat"
        findViewById<TextView>(R.id.tvPreu).text = restaurant.rangPreu ?: "---"
        findViewById<TextView>(R.id.tvUbi).text = restaurant.ubicacio ?: "Ubicació no disponible"
        findViewById<TextView>(R.id.tvTel).text = restaurant.telefon ?: "Sense telèfon"

        // Estado inicial del CheckBox
        cbFavorite.isChecked = restaurant.isFavorite

        btnAtras.setOnClickListener { finish() }

        // Favoritos
        cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                try {
                    if (isChecked) {
                        ItemAPI.API().addFavorite(mapOf("id" to restaurant.id))
                        Toast.makeText(this@activity_detail, "Afegit a preferits", Toast.LENGTH_SHORT).show()
                    } else {
                        ItemAPI.API().deleteFavorite(restaurant.id)
                        Toast.makeText(this@activity_detail, "Eliminat de preferits", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@activity_detail, "Error de connexió", Toast.LENGTH_SHORT).show()
                    cbFavorite.isChecked = !isChecked // Revertir visualmente si falla
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