package com.allercheck

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class activity_detail : AppCompatActivity() {

    // VARIABLES
    private lateinit var btnAtras: ImageButton
    private lateinit var btnRess: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // INICIALITZAR
        btnAtras = findViewById(R.id.btnAtras)
        btnRess = findViewById(R.id.btnRess)


        // DATOS
        val restaurant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_RESTAURANT", Restaurant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Restaurant>("EXTRA_RESTAURANT")
        }

        if (restaurant != null) {
            // TEXTVIEW DATOS
            val tvName: TextView = findViewById(R.id.tvRestaurantNameDetail)
            val tvRating: TextView = findViewById(R.id.tvRatingDetail)
            val tvTipus: TextView = findViewById(R.id.tvTipus)
            val tvPreu: TextView = findViewById(R.id.tvPreu)
            val tvUbi: TextView = findViewById(R.id.tvUbi)
            val tvTel: TextView = findViewById(R.id.tvTel)

            // TEXTVIEW DATOS
            tvName.text = restaurant.name
            tvRating.text = restaurant.rating
            tvTipus.text = restaurant.tipusCuina
            tvPreu.text = restaurant.rangPreu
            tvUbi.text = restaurant.ubicacio
            tvTel.text = restaurant.telefon

        }


        // BOTON "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }

        // BOTÓ "AFEGIR RESSENYA"
        btnRess.setOnClickListener {
            val intent = Intent(this, activity_create_edit_review::class.java)
            startActivity(intent)
        }
    }
}
