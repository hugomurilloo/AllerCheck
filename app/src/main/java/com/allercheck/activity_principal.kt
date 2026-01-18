package com.allercheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_principal : AppCompatActivity() {

    private lateinit var btnPref: ImageButton
    private lateinit var btnRess: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // INICIALITZAR VISTES
        btnPref = findViewById(R.id.btnPref)
        btnRess = findViewById(R.id.btnRess)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_home

        val sharedPreferences = getSharedPreferences(activity_config_restrictions.PREFS_NAME, Context.MODE_PRIVATE)
        val filtreSenseGluten = sharedPreferences.getBoolean(activity_config_restrictions.KEY_GLUTEN, false)
        val filtreSenseLactosa = sharedPreferences.getBoolean(activity_config_restrictions.KEY_LACTOSA, false)
        val filtreSenseFruitsSecs = sharedPreferences.getBoolean(activity_config_restrictions.KEY_FRUITS_SECS, false)
        val filtreSenseMarisc = sharedPreferences.getBoolean(activity_config_restrictions.KEY_MARISC, false)
        val filtreVega = sharedPreferences.getBoolean(activity_config_restrictions.KEY_VEGA, false)
        val filtreHalal = sharedPreferences.getBoolean(activity_config_restrictions.KEY_HALAL, false)
        val filtreKosher = sharedPreferences.getBoolean(activity_config_restrictions.KEY_KOSHER, false)
        val filtreSenseOu = sharedPreferences.getBoolean(activity_config_restrictions.KEY_OU, false)

        val filtersContainer: LinearLayout = findViewById(R.id.llFiltersContainer)
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

        if (filtreSenseGluten) addFilterPill(getString(R.string.x_gluten))
        if (filtreSenseLactosa) addFilterPill(getString(R.string.x_lacotsa))
        if (filtreSenseFruitsSecs) addFilterPill("x Fruits secs")
        if (filtreSenseMarisc) addFilterPill("x Marisc")
        if (filtreVega) addFilterPill("x Vegà")
        if (filtreHalal) addFilterPill("x Halal")
        if (filtreKosher) addFilterPill("x Kosher")
        if (filtreSenseOu) addFilterPill("x Ou")

        // DADES
        val allRestaurants = listOf(
            Restaurant("1", "Restaurant Vegà", "4.9", true, true, true, true, true, true, false, false, false, "Vegana", "15-25€", "Carrer Verd, 1", "931234561"),
            Restaurant("2", "La Pizzeria Clàssica", "4.5", false, false, false, false, false, false, false, false, true, "Italiana", "10-20€", "Plaça del Forn, 2", "931234562"),
            Restaurant("3", "El Bon Menjar Marí", "4.2", true, true, true, true, false, false, false, false, true, "Marisqueria", "30-50€", "Passeig Marítim, 3", "931234563"),
            Restaurant("4", "Polleria Halal", "4.5", false, true, true, true, true, false, true, false, true, "Àrab", "5-15€", "Avinguda del Mercat, 4", "931234564"),
            Restaurant("5", "Oriental Restaurant", "3.0", true, false, true, false, true, false, false, false, true, "Asiàtica", "20-35€", "Carrer del Drac, 5", "931234565"),
            Restaurant("6", "Yuki Kosher", "4.5", true, true, true, true, true, false, false, true, false, "Jueva", "25-40€", "Carrer de la Sinagoga, 6", "931234566")
        )

        val filteredRestaurants = allRestaurants.filter { restaurant ->
            val compleixGluten = if (filtreSenseGluten) restaurant.senseGluten else true
            val compleixLactosa = if (filtreSenseLactosa) restaurant.senseLactosa else true
            val compleixFruitsSecs = if (filtreSenseFruitsSecs) restaurant.senseFruitsSecs else true
            val compleixMarisc = if (filtreSenseMarisc) restaurant.senseMarisc else true
            val compleixVega = if (filtreVega) restaurant.esVega else true
            val compleixHalal = if (filtreHalal) restaurant.esHalal else true
            val compleixKosher = if (filtreKosher) restaurant.esKosher else true
            val compleixOu = if (filtreSenseOu) !restaurant.teOu else true
            compleixGluten && compleixLactosa && compleixFruitsSecs && compleixMarisc && compleixVega && compleixHalal && compleixKosher && compleixOu
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ENVIAR
        restaurantAdapter = RestaurantAdapter(filteredRestaurants) { restaurant ->
            val intent = Intent(this, activity_detail::class.java)
            intent.putExtra("EXTRA_RESTAURANT", restaurant)
            startActivity(intent)
        }
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
}
