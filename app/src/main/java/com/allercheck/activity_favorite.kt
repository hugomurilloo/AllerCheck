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

    private lateinit var voiceManager: VoiceCommandManager
    private lateinit var favoriteAdapter: FavoriteRestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val btnAtras: ImageButton = findViewById(R.id.btnAtras)
        val btnResenas: ImageButton = findViewById(R.id.btnResenas)
        val btnMic: ImageButton = findViewById(R.id.btnMic)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_favorites

        // INICIALIZAR VOZ
        voiceManager = VoiceCommandManager(this)
        voiceManager.setupVoiceCommand(btnMic)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

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

    private fun loadFavoritesFromApi() {
        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().getFavorites()
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    favoriteAdapter.updateFavorites(favorites)
                }
            } catch (e: Exception) {
                Toast.makeText(this@activity_favorite, "Error de connexió", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteFavoriteFromApi(restaurant: Restaurant) {
        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().deleteFavorite(restaurant.id)
                if (response.isSuccessful) {
                    loadFavoritesFromApi()
                }
            } catch (e: Exception) {
                Toast.makeText(this@activity_favorite, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceManager.destroy()
    }
}