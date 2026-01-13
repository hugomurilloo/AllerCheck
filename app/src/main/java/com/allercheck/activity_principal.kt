package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_principal : AppCompatActivity() {

    // VARIABLES
    private lateinit var btnPref: ImageButton
    private lateinit var btnRess: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZAR VISTAS
        btnPref = findViewById(R.id.btnPref)
        btnRess = findViewById(R.id.btnRess)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_home

        // CONFIGURAR RECYCLERVIEW
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // DATOS EJEMPLO
        val restaurants = listOf(
            Restaurant("1", "Restaurant Tokyo", "4.9", true),
            Restaurant("2", "La Pizzeria", "4.5", false),
            Restaurant("3", "El Bon Menjar", "4.2", true),
            Restaurant("4", "Polleria Don Juan", "4.5", false),
            Restaurant("5", "Oriental Restaurant", "3", true),
            Restaurant("6", "Yuki", "4.5", true)

        )

        // CREAR NAV
        restaurantAdapter = RestaurantAdapter(restaurants) { restaurant ->
            val intent = Intent(this, activity_detail::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = restaurantAdapter

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
                    startActivity(Intent(this, activity_favorite::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, activity_profile::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
