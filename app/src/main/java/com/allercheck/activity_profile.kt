package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_profile : AppCompatActivity() {


    //VOICE MANAGER
    private lateinit var voiceManager: VoiceCommandManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnAtras: ImageButton = findViewById(R.id.btnAtras)
        val btnResenas: ImageButton = findViewById(R.id.btnResenas)

        // BOTON MIC
        val btnMic: ImageButton = findViewById(R.id.btnMic)
        val btnRestriccions: Button = findViewById(R.id.btnRestriccions)
        val btnRessenyes: Button = findViewById(R.id.btnRessenyes)
        val btnStats: Button = findViewById(R.id.btnStats)
        val btnTancarSessio: Button = findViewById(R.id.btnTancarSessio)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.navigation_profile

        // INICIALIZAR VOZ
        voiceManager = VoiceCommandManager(this)
        voiceManager.setupVoiceCommand(btnMic)

        btnAtras.setOnClickListener { finish() }
        btnResenas.setOnClickListener {
            startActivity(Intent(this, activity_review::class.java))
        }
        btnRestriccions.setOnClickListener {
            startActivity(Intent(this, activity_config_restrictions::class.java))
        }
        btnRessenyes.setOnClickListener {
            startActivity(Intent(this, activity_review::class.java))
        }
        btnStats.setOnClickListener {
            startActivity(Intent(this, activity_stats::class.java))
        }
        btnTancarSessio.setOnClickListener {
            finishAffinity()
        }

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

    override fun onDestroy() {
        super.onDestroy()
        voiceManager.destroy()
    }
}