package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView // <-- 1. IMPORTAR TEXTVIEW
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_principal : AppCompatActivity() {

    // VARIABLES
    lateinit var btnPref: ImageButton
    lateinit var btnRess: ImageButton
    lateinit var tvRestaurant1: TextView
    lateinit var tvRestaurant2: TextView
    lateinit var tvRestaurant3: TextView
    private lateinit var bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZAR
        btnPref = findViewById(R.id.btnPref)
        btnRess = findViewById(R.id.btnRess)
        tvRestaurant1 = findViewById(R.id.tvRestaurant1)
        tvRestaurant2 = findViewById(R.id.tvRestaurant2)
        tvRestaurant3 = findViewById(R.id.tvRestaurant3)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_home


        // BOTÓN "PREFERENCIAS"
        btnPref.setOnClickListener {
            val intent = Intent(this, activity_config_restrictions::class.java)
            startActivity(intent)
        }

        // BOTÓN "RESEÑAS"
        btnRess.setOnClickListener {
            val intent = Intent(this, activity_review::class.java)
            startActivity(intent)
        }

        // Listener nav
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_favorites -> {
                    val intent = Intent(this, activity_favorite::class.java)
                    startActivity(intent)
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


        val detailIntent = Intent(this, activity_detail::class.java)

        tvRestaurant1.setOnClickListener {
            startActivity(detailIntent)
        }
        tvRestaurant2.setOnClickListener {
            startActivity(detailIntent)
        }
        tvRestaurant3.setOnClickListener {
            startActivity(detailIntent)
        }
    }
}
