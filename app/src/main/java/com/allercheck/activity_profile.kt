package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_profile : AppCompatActivity() {

    // VARIABLES
    lateinit var btnAtras: ImageButton
    lateinit var btnResenas: ImageButton
    lateinit var btnRestriccions: Button
    lateinit var btnRessenyes: Button
    lateinit var btnTancarSessio: Button
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // INICIALIZAR
        btnAtras = findViewById(R.id.btnAtras)
        btnResenas = findViewById(R.id.btnResenas)
        btnRestriccions = findViewById(R.id.btnRestriccions)
        btnRessenyes = findViewById(R.id.btnRessenyes)
        btnTancarSessio = findViewById(R.id.btnTancarSessio)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = R.id.navigation_profile

        // BOTÓN "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }

        // BOTÓN "RESEÑAS" (Header)
        btnResenas.setOnClickListener {
            val intent = Intent(this, activity_review::class.java)
            startActivity(intent)
        }

        // BOTÓN "LES MEVES RESTRICCIONS"
        btnRestriccions.setOnClickListener {
            val intent = Intent(this, activity_config_restrictions::class.java)
            startActivity(intent)
        }

        // BOTÓN "LES MEVES RESSENYES"
        btnRessenyes.setOnClickListener {
            val intent = Intent(this, activity_review::class.java)
            startActivity(intent)
        }

        // BOTÓN "TANCAR SESSIÓ"
        btnTancarSessio.setOnClickListener {
            val intent = Intent(this, activity_onboarding::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // NAV
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, activity_principal::class.java))
                    true
                }
                R.id.navigation_favorites -> {
                    startActivity(Intent(this, activity_favorite::class.java))
                    true
                }
                else -> true
            }
        }
    }
}
