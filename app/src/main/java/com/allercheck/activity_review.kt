package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView // Importar TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_review : AppCompatActivity() {

    // VARIABLES
    lateinit var btnAtras: ImageButton
    lateinit var tvRestaurant1: TextView
    lateinit var tvRestaurant2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_review)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZAR
        btnAtras = findViewById(R.id.btnAtras)
        tvRestaurant1 = findViewById(R.id.tvRestaurant1)
        tvRestaurant2 = findViewById(R.id.tvRestaurant2)

        // BOTÓN "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }

        // TEXTVIEW "Restaurant Tokyo"
        tvRestaurant1.setOnClickListener {
            val intent = Intent(this, activity_detail::class.java)
            startActivity(intent)
        }

        // TEXTVIEW "La Pizzeria"
        tvRestaurant2.setOnClickListener {
            val intent = Intent(this, activity_detail::class.java)
            startActivity(intent)
        }
    }
}
