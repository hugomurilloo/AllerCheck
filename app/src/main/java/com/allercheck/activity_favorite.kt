package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.coroutines.launch

class activity_favorite : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var btnResenas: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteRestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        btnAtras = findViewById(R.id.btnAtras)
        btnResenas = findViewById(R.id.btnResenas)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_favorites

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista vacia y cargar de la API
        favoriteAdapter = FavoriteRestaurantAdapter(
            restaurants = mutableListOf(),
            onItemClick = { restaurant ->
                val intent = Intent(this, activity_detail::class.java)
                intent.putExtra("EXTRA_RESTAURANT", restaurant)
                startActivity(intent)
            },
            onDeleteClick = { restaurant ->
                deleteFavoriteFromApi(restaurant)
            }
        )
        recyclerView.adapter = favoriteAdapter

        loadFavoritesFromApi()

        btnAtras.setOnClickListener { finish() }
        btnResenas.setOnClickListener {
            startActivity(Intent(this, activity_review::class.java))
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

    // Cargar favoritos
    private fun loadFavoritesFromApi() {
        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().getFavorites()
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    favoriteAdapter.updateFavorites(favorites)
                } else {
                    Toast.makeText(this@activity_favorite, "Error HTTP: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@activity_favorite, "Error de connexió", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Eliminar favorito
    private fun deleteFavoriteFromApi(restaurant: Restaurant) {
        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().deleteFavorite(restaurant.id)
                if (response.isSuccessful) {
                    Toast.makeText(this@activity_favorite, "Eliminat correctament", Toast.LENGTH_SHORT).show()
                    loadFavoritesFromApi() // Recargar lista
                }
            } catch (e: Exception) {
                Toast.makeText(this@activity_favorite, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}