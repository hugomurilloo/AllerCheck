package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_favorite : AppCompatActivity() {

    // VARIABLES
    lateinit var btnAtras: ImageButton
    lateinit var btnResenas: ImageButton
    lateinit var tvRestaurant1: TextView
    lateinit var tvRestaurant2: TextView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZAR
        btnAtras = findViewById(R.id.btnAtras)
        btnResenas = findViewById(R.id.btnResenas)
        tvRestaurant1 = findViewById(R.id.tvRestaurant1)
        tvRestaurant2 = findViewById(R.id.tvRestaurant2)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = R.id.navigation_favorites

        // BOTÓN "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }

        // BOTÓN "RESEÑAS"
        btnResenas.setOnClickListener {
            val intent = Intent(this, activity_review::class.java)
            startActivity(intent)
        }

        val detailIntent = Intent(this, activity_detail::class.java)

        // TEXTVIEW "RESTAURANT 1"
        tvRestaurant1.setOnClickListener {
            startActivity(detailIntent)
        }

        // TEXTVIEW "RESTAURANT 2"
        tvRestaurant2.setOnClickListener {
            startActivity(detailIntent)
        }

        // NAV
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, activity_principal::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_favorites -> {
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, activity_profile::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
