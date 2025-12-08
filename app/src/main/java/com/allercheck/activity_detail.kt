package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_detail : AppCompatActivity() {

    // VARIABLES
    lateinit var btnAtras: ImageButton
    lateinit var btnRess: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // INICIALIZAR
        btnAtras = findViewById(R.id.btnAtras)
        btnRess = findViewById(R.id.btnRess)

        // BOTÓN "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }

        // BOTÓN "AFEGIR RESSENYA"
        btnRess.setOnClickListener {
            val intent = Intent(this, activity_create_edit_review::class.java)
            startActivity(intent)
        }
    }
}
