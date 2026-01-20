package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_register : AppCompatActivity() {

    // VARIABLES
    lateinit var btnRegistrarme: Button
    lateinit var btnAtras: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        // INICIALIZAR
        btnRegistrarme = findViewById(R.id.Registrarme)
        btnAtras = findViewById(R.id.btnAtras)

        // BOTÓN "REGISTRARME"
        btnRegistrarme.setOnClickListener {
            val intent = Intent(this, activity_config_restrictions::class.java)
            startActivity(intent)
        }

        // BOTÓN "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }
    }
}
