package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_favorite : AppCompatActivity() {

    // VARIABLES
    private lateinit var btnAtras: ImageButton
    private lateinit var btnResenas: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteRestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZAR VISTAS
        btnAtras = findViewById(R.id.btnAtras)
        btnResenas = findViewById(R.id.btnResenas)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_favorites

        // CONFIGURAR RECYCLERVIEW
        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // DATOS EJEMPLO
        // ESTO IRA DESDE UNA BDD
        val favoriteRestaurants = mutableListOf(
            Restaurant("1", "Restaurant Tokyo", "4.9", true),
            Restaurant("6", "Yuki", "4.5", true),
            Restaurant("3", "El Bon Menjar", "4.2", true),
            Restaurant("4", "Polleria Don Juan", "4.5", false),
            Restaurant("5", "Oriental Restaurant", "3", true)
        )

        // CREAR Y PONER ADAPTER
        favoriteAdapter = FavoriteRestaurantAdapter(
            restaurants = favoriteRestaurants,
            onItemClick = { restaurant ->
                val intent = Intent(this, activity_detail::class.java)
                startActivity(intent)
            },
            onDeleteClick = { restaurant ->

                Toast.makeText(this, "Eliminar: ${restaurant.name}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = favoriteAdapter

        // BOTONES y NAVEGACIÓN
        btnAtras.setOnClickListener {
            finish()
        }
        btnResenas.setOnClickListener {
            val intent = Intent(this, activity_review::class.java)
            startActivity(intent)
        }
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, activity_principal::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, activity_profile::class.java))
                    true
                }
                else -> true
            }
        }
    }
}
