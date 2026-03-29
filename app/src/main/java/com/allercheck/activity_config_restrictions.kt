package com.allercheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class activity_config_restrictions : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "AllerCheckPrefs"
        const val KEY_GLUTEN = "sense_gluten"
        const val KEY_LACTOSA = "sense_lactosa"
        const val KEY_FRUITS_SECS = "sense_fruits_secs"
        const val KEY_MARISC = "sense_marisc"
        const val KEY_VEGA = "es_vega"
        const val KEY_HALAL = "es_halal"
        const val KEY_KOSHER = "es_kosher"
        const val KEY_OU = "te_ou"
    }

    private lateinit var statsViewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_restrictions)

        // INICIALIZAR
        statsViewModel = ViewModelProvider(this).get(StatsViewModel::class.java)

        val btnAtras: ImageButton = findViewById(R.id.btnAtras)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)

        val cbGluten: CheckBox = findViewById(R.id.cbSinGluten)
        val cbLactosa: CheckBox = findViewById(R.id.cbSinLactosa)
        val cbFruits: CheckBox = findViewById(R.id.cbFruitsSecs)
        val cbMarisc: CheckBox = findViewById(R.id.cbMarisc)
        val cbVega: CheckBox = findViewById(R.id.cbVega)
        val cbHalal: CheckBox = findViewById(R.id.cbHalal)
        val cbKosher: CheckBox = findViewById(R.id.cbKosher)
        val cbOu: CheckBox = findViewById(R.id.cbOu)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // CARGAR ESTADO INICIAL
        cbGluten.isChecked = sharedPreferences.getBoolean(KEY_GLUTEN, false)
        cbLactosa.isChecked = sharedPreferences.getBoolean(KEY_LACTOSA, false)
        cbFruits.isChecked = sharedPreferences.getBoolean(KEY_FRUITS_SECS, false)
        cbMarisc.isChecked = sharedPreferences.getBoolean(KEY_MARISC, false)
        cbVega.isChecked = sharedPreferences.getBoolean(KEY_VEGA, false)
        cbHalal.isChecked = sharedPreferences.getBoolean(KEY_HALAL, false)
        cbKosher.isChecked = sharedPreferences.getBoolean(KEY_KOSHER, false)
        cbOu.isChecked = sharedPreferences.getBoolean(KEY_OU, false)

        // LISTENERS PARA ESTADÍSTICAS
        cbGluten.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("gluten") }
        cbLactosa.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("lactose") }
        cbFruits.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("fruits secs") }
        cbMarisc.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("marisc") }
        cbVega.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("vegà") }
        cbHalal.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("halal") }
        cbKosher.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("kosher") }
        cbOu.setOnCheckedChangeListener { _, isChecked -> if (isChecked) statsViewModel.trackFilter("ou") }

        // BOTÓN GUARDAR Y NAVEGACIÓN A PRINCIPAL
        btnGuardar.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_GLUTEN, cbGluten.isChecked)
            editor.putBoolean(KEY_LACTOSA, cbLactosa.isChecked)
            editor.putBoolean(KEY_FRUITS_SECS, cbFruits.isChecked)
            editor.putBoolean(KEY_MARISC, cbMarisc.isChecked)
            editor.putBoolean(KEY_VEGA, cbVega.isChecked)
            editor.putBoolean(KEY_HALAL, cbHalal.isChecked)
            editor.putBoolean(KEY_KOSHER, cbKosher.isChecked)
            editor.putBoolean(KEY_OU, cbOu.isChecked)
            editor.apply()

            Toast.makeText(this, "Preferències guardades", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, activity_principal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        btnAtras.setOnClickListener { finish() }
    }
}