package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_favorite : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var btnResenas: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteRestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // INICIALITZAR VISTES
        btnAtras = findViewById(R.id.btnAtras)
        btnResenas = findViewById(R.id.btnResenas)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_favorites

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // DATOS
        val favoriteRestaurants = mutableListOf(
            Restaurant("1", "Restaurant Vegà", "4.9", true, true, true, true, true, true, false, false, false, "Vegana", "15-25€", "Carrer Verd, 1", "931234561"),
            Restaurant("6", "Yuki Kosher", "4.5", true, true, true, true, true, false, false, true, false, "Jueva", "25-40€", "Carrer de la Sinagoga, 6", "931234566"),
            Restaurant("3", "El Bon Menjar Marí", "4.2", true, true, true, true, false, false, false, false, true, "Marisqueria", "30-50€", "Passeig Marítim, 3", "931234563")
        )

        // ENVIAR
        favoriteAdapter = FavoriteRestaurantAdapter(
            restaurants = favoriteRestaurants,
            onItemClick = { restaurant ->
                val intent = Intent(this, activity_detail::class.java)
                intent.putExtra("EXTRA_RESTAURANT", restaurant)
                startActivity(intent)
            },
            onDeleteClick = { restaurant ->
                val position = favoriteRestaurants.indexOf(restaurant)
                if (position != -1) {
                    favoriteRestaurants.removeAt(position)
                    favoriteAdapter.notifyItemRemoved(position)
                    Toast.makeText(this, "Eliminat: ${restaurant.name}", Toast.LENGTH_SHORT).show()
                }
            }
        )
        recyclerView.adapter = favoriteAdapter

        // BOTONES y NAVEGACIÓN
        btnAtras.setOnClickListener { finish() }
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
