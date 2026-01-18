package com.allercheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class activity_config_restrictions : AppCompatActivity() {

    // CONSTANTES
    companion object {
        const val PREFS_NAME = "allercheck_prefs"
        const val KEY_GLUTEN = "key_gluten"
        const val KEY_LACTOSA = "key_lactosa"
        const val KEY_FRUITS_SECS = "key_fruits_secs"
        const val KEY_MARISC = "key_marisc"
        const val KEY_VEGA = "key_vega"
        const val KEY_HALAL = "key_halal"
        const val KEY_KOSHER = "key_kosher"
        const val KEY_OU = "key_ou"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_restrictions)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // INICIALITZAR VISTES
        val cbSinGluten: CheckBox = findViewById(R.id.cbSinGluten)
        val cbSinLactosa: CheckBox = findViewById(R.id.cbSinLactosa)
        val cbFruitsSecs: CheckBox = findViewById(R.id.cbFruitsSecs)
        val cbMarisc: CheckBox = findViewById(R.id.cbMarisc)
        val cbVega: CheckBox = findViewById(R.id.cbVega)
        val cbHalal: CheckBox = findViewById(R.id.cbHalal)
        val cbKosher: CheckBox = findViewById(R.id.cbKosher)
        val cbOu: CheckBox = findViewById(R.id.cbOu)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnAtras: ImageButton = findViewById(R.id.btnAtras)

        // CARREGAR I ESTABLIR PREFERÈNCIES GUARDADES
        cbSinGluten.isChecked = sharedPreferences.getBoolean(KEY_GLUTEN, false)
        cbSinLactosa.isChecked = sharedPreferences.getBoolean(KEY_LACTOSA, false)
        cbFruitsSecs.isChecked = sharedPreferences.getBoolean(KEY_FRUITS_SECS, false)
        cbMarisc.isChecked = sharedPreferences.getBoolean(KEY_MARISC, false)
        cbVega.isChecked = sharedPreferences.getBoolean(KEY_VEGA, false)
        cbHalal.isChecked = sharedPreferences.getBoolean(KEY_HALAL, false)
        cbKosher.isChecked = sharedPreferences.getBoolean(KEY_KOSHER, false)
        cbOu.isChecked = sharedPreferences.getBoolean(KEY_OU, false)

        // BOTÓ "GUARDAR"
        btnGuardar.setOnClickListener {
            val editor = sharedPreferences.edit()

            // GUARDAMOS ESTADOS CAJITAS
            editor.putBoolean(KEY_GLUTEN, cbSinGluten.isChecked)
            editor.putBoolean(KEY_LACTOSA, cbSinLactosa.isChecked)
            editor.putBoolean(KEY_FRUITS_SECS, cbFruitsSecs.isChecked)
            editor.putBoolean(KEY_MARISC, cbMarisc.isChecked)
            editor.putBoolean(KEY_VEGA, cbVega.isChecked)
            editor.putBoolean(KEY_HALAL, cbHalal.isChecked)
            editor.putBoolean(KEY_KOSHER, cbKosher.isChecked)
            editor.putBoolean(KEY_OU, cbOu.isChecked)

            editor.apply() // APLICAR

            // IR PANTALLA INI
            val intent = Intent(this, activity_principal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // BOTÓ "ATRAS"
        btnAtras.setOnClickListener {
            finish()
        }
    }
}
