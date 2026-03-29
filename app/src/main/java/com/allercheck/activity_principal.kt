package com.allercheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class activity_principal : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var statsViewModel: StatsViewModel
    private var startTime: Long = 0
    private var restaurantAdapter: RestaurantAdapter = RestaurantAdapter(mutableListOf()) { restaurant ->
        val intent = Intent(this, activity_detail::class.java)
        intent.putExtra("EXTRA_RESTAURANT", restaurant)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // INICIALIZAR
        statsViewModel = ViewModelProvider(this).get(StatsViewModel::class.java)
        val btnPref: ImageButton = findViewById(R.id.btnPref)
        val btnRess: ImageButton = findViewById(R.id.btnRess)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_home

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = restaurantAdapter

        // LISTENERS
        btnPref.setOnClickListener {
            startActivity(Intent(this, activity_config_restrictions::class.java))
        }
        btnRess.setOnClickListener {
            startActivity(Intent(this, activity_review::class.java))
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

    // REGISTRA TIEMPO AL INICIAR
    override fun onStart() {
        super.onStart()
        startTime = System.currentTimeMillis()
    }

    // CALCULA Y GUARDA TIEMPO AL SALIR
    override fun onStop() {
        super.onStop()
        val secondsElapsed = (System.currentTimeMillis() - startTime) / 1000
        if (secondsElapsed > 0) {
            statsViewModel.addTime(secondsElapsed)
        }
    }

    override fun onResume() {
        super.onResume()
        loadAndShowFilters()
        loadRestaurantsFromApi()
    }

    private fun loadRestaurantsFromApi() {
        val sharedPreferences = getSharedPreferences(activity_config_restrictions.PREFS_NAME, Context.MODE_PRIVATE)
        lifecycleScope.launch {
            try {
                val response = ItemAPI.API().getRestaurants(
                    senseGluten = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_GLUTEN, false)) true else null,
                    senseLactosa = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_LACTOSA, false)) true else null,
                    senseFruitsSecs = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_FRUITS_SECS, false)) true else null,
                    senseMarisc = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_MARISC, false)) true else null,
                    esVega = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_VEGA, false)) true else null,
                    esHalal = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_HALAL, false)) true else null,
                    esKosher = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_KOSHER, false)) true else null,
                    teOu = if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_OU, false)) true else null
                )
                if (response.isSuccessful) restaurantAdapter.updateRestaurants(response.body() ?: emptyList())
            } catch (e: Exception) { Log.e("Principal", "Error", e) }
        }
    }

    private fun loadAndShowFilters() {
        val sharedPreferences = getSharedPreferences(activity_config_restrictions.PREFS_NAME, Context.MODE_PRIVATE)
        val filtersContainer: LinearLayout = findViewById(R.id.llFiltersContainer)
        if (filtersContainer.childCount > 1) filtersContainer.removeViews(1, filtersContainer.childCount - 1)

        fun addFilterPill(text: String) {
            val pill = TextView(this).apply {
                this.text = text
                textSize = 11f
                setTextColor(ContextCompat.getColor(this@activity_principal, R.color.white))
                background = ContextCompat.getDrawable(this@activity_principal, R.drawable.button_background)
                setPadding(30, 10, 30, 10)
                layoutParams = LinearLayout.LayoutParams(-2, -2).apply { marginStart = 20 }
            }
            filtersContainer.addView(pill)
        }

        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_GLUTEN, false)) addFilterPill(getString(R.string.x_gluten))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_LACTOSA, false)) addFilterPill(getString(R.string.x_lacotsa))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_FRUITS_SECS, false)) addFilterPill(getString(R.string.fruits_secs))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_MARISC, false)) addFilterPill(getString(R.string.marisc))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_VEGA, false)) addFilterPill(getString(R.string.vega))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_HALAL, false)) addFilterPill(getString(R.string.halal))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_KOSHER, false)) addFilterPill(getString(R.string.kosher))
        if (sharedPreferences.getBoolean(activity_config_restrictions.KEY_OU, false)) addFilterPill(getString(R.string.ou))
    }
}