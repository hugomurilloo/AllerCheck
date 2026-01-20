package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_login : AppCompatActivity() {

    // VARIABLES
    lateinit var btnAccedir: Button
    lateinit var btnRegistrar: Button
    lateinit var btnAtras: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // INICIALIZAR
        btnAccedir = findViewById(R.id.accedir)
        btnRegistrar = findViewById(R.id.registrar)
        btnAtras = findViewById(R.id.btnAtras)

        // BOTÓN "ACCEDIR"
        btnAccedir.setOnClickListener {
            val intent = Intent(this, activity_config_restrictions::class.java)
            startActivity(intent)
        }

        // BOTÓN "REGISTRAR"
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, activity_register::class.java)
            startActivity(intent)
        }

        // BOTÓN "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }
    }
}
