package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_onboarding : AppCompatActivity() {
    // VARIABLES
    lateinit var btnComencar: Button
    lateinit var btnContinuarSenseCompte: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // INICIALIZAR
        btnComencar = findViewById(R.id.btnComencar)
        btnContinuarSenseCompte = findViewById(R.id.btnSinCopte)

        // BOTÓN "COMENÇAR"
        btnComencar.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }
        // BOTÓN "CONTINUAR SENSE COMPTE"
        btnContinuarSenseCompte.setOnClickListener {
            val intent = Intent(this, activity_config_restrictions::class.java)
            startActivity(intent)
        }
    }
}
