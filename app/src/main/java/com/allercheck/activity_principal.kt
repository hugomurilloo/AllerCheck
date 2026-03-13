package com.allercheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class activity_principal : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var restaurantAdapter: RestaurantAdapter = RestaurantAdapter(mutableListOf()) { restaurant ->
        val intent = Intent(this, activity_detail::class.java)
        intent.putExtra("EXTRA_RESTAURANT", restaurant)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // INICIALIZAR VISTAS
        val btnPref: ImageButton = findViewById(R.id.btnPref)
        val btnRess: ImageButton = findViewById(R.id.btnRess)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_home

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = restaurantAdapter

        // LISTENERS
        btnPref.setOnClickListener {
            val intent = Intent(this, activity_config_restrictions::class.java)
            startActivity(intent)
        }
        btnRess.setOnClickListener {
            val intent = Intent(this, activity_review::class.java)
            startActivity(intent)
        }
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

    // USUARIO VUELVE A LA PANTALLA
    override fun onResume() {
        super.onResume()
        loadAndShowFilters()
        loadRestaurantsFromApi()
    }

    private fun loadRestaurantsFromApi() {
        val sharedPreferences = getSharedPreferences(activity_config_restrictions.PREFS_NAME, Context.MODE_PRIVATE)
        val filtreSenseGluten = sharedPreferences.getBoolean(activity_config_restrictions.KEY_GLUTEN, false)
        val filtreSenseLactosa = sharedPreferences.getBoolean(activity_config_restrictions.KEY_LACTOSA, false)
        val filtreSenseFruitsSecs = sharedPreferences.getBoolean(activity_config_restrictions.KEY_FRUITS_SECS, false)
        val filtreSenseMarisc = sharedPreferences.getBoolean(activity_config_restrictions.KEY_MARISC, false)
        val filtreVega = sharedPreferences.getBoolean(activity_config_restrictions.KEY_VEGA, false)
        val filtreHalal = sharedPreferences.getBoolean(activity_config_restrictions.KEY_HALAL, false)
        val filtreKosher = sharedPreferences.getBoolean(activity_config_restrictions.KEY_KOSHER, false)
        val filtreSenseOu = sharedPreferences.getBoolean(activity_config_restrictions.KEY_OU, false)

        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().getRestaurants(
                    senseGluten = if (filtreSenseGluten) true else null,
                    senseLactosa = if (filtreSenseLactosa) true else null,
                    senseFruitsSecs = if (filtreSenseFruitsSecs) true else null,
                    senseMarisc = if (filtreSenseMarisc) true else null,
                    esVega = if (filtreVega) true else null,
                    esHalal = if (filtreHalal) true else null,
                    esKosher = if (filtreKosher) true else null,
                    teOu = if (filtreSenseOu) true else null
                )

                if (response.isSuccessful) {
                    val restaurants = response.body() ?: emptyList()
                    restaurantAdapter.updateRestaurants(restaurants)
                } else {
                    Log.e("Principal", "Error HTTP: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Principal", "Error de conexión", e)
            }
        }
    }

    private fun loadAndShowFilters() {
        val sharedPreferences = getSharedPreferences(activity_config_restrictions.PREFS_NAME, Context.MODE_PRIVATE)
        val filtersContainer: LinearLayout = findViewById(R.id.llFiltersContainer)

        if (filtersContainer.childCount > 1) {
            filtersContainer.removeViews(1, filtersContainer.childCount - 1)
        }

        fun addFilterPill(text: String) {
            val pill = TextView(this).apply {
                this.text = text
                textSize = 11f
                setTextColor(ContextCompat.getColor(this@activity_principal, R.color.white))
                background = ContextCompat.getDrawable(this@activity_principal, R.drawable.button_background)
                val horizontalPadding = (12 * resources.displayMetrics.density).toInt()
                val verticalPadding = (4 * resources.displayMetrics.density).toInt()
                setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { marginStart = (10 * resources.displayMetrics.density).toInt() }
                layoutParams = params
            }
            filtersContainer.addView(pill)
        }

        // FILTORS PILLS LAS REDONDITAS
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_GLUTEN, false))
            addFilterPill(getString(R.string.x_gluten))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_LACTOSA, false))
            addFilterPill(getString(R.string.x_lacotsa))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_FRUITS_SECS, false))
            addFilterPill(getString(R.string.fruits_secs))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_MARISC, false))
            addFilterPill(getString(R.string.marisc))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_VEGA, false))
            addFilterPill(getString(R.string.vega))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_HALAL, false))
            addFilterPill(getString(R.string.halal))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_KOSHER, false))
            addFilterPill(getString(R.string.kosher))

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_OU, false))
            addFilterPill(getString(R.string.ou))
    }
}