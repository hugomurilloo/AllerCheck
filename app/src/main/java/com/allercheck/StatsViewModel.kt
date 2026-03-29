package com.allercheck

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "stats_prefs")

data class AppStats(
    val gluten: Int = 0,
    val lactose: Int = 0,
    val fruitsSecs: Int = 0,
    val marisc: Int = 0,
    val vega: Int = 0,
    val halal: Int = 0,
    val kosher: Int = 0,
    val ou: Int = 0,
    val reviews: Int = 0,
    val secondsUsed: Long = 0
)

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val context = application.applicationContext

    private val K_GLUTEN = intPreferencesKey("gluten")
    private val K_LACTOSE = intPreferencesKey("lactose")
    private val K_FRUITS = intPreferencesKey("fruits_secs")
    private val K_MARISC = intPreferencesKey("marisc")
    private val K_VEGA = intPreferencesKey("vega")
    private val K_HALAL = intPreferencesKey("halal")
    private val K_KOSHER = intPreferencesKey("kosher")
    private val K_OU = intPreferencesKey("ou")
    private val K_REVIEWS = intPreferencesKey("reviews")
    private val K_TIME = longPreferencesKey("time")

    val stats: LiveData<AppStats> = context.dataStore.data.map { p ->
        AppStats(
            p[K_GLUTEN] ?: 0, p[K_LACTOSE] ?: 0, p[K_FRUITS] ?: 0,
            p[K_MARISC] ?: 0, p[K_VEGA] ?: 0, p[K_HALAL] ?: 0,
            p[K_KOSHER] ?: 0, p[K_OU] ?: 0, p[K_REVIEWS] ?: 0,
            p[K_TIME] ?: 0
        )
    }.asLiveData()

    fun trackFilter(type: String) {
        viewModelScope.launch {
            context.dataStore.edit { p ->
                when(type.lowercase()) {
                    "gluten" -> p[K_GLUTEN] = (p[K_GLUTEN] ?: 0) + 1
                    "lactose" -> p[K_LACTOSE] = (p[K_LACTOSE] ?: 0) + 1
                    "fruits secs" -> p[K_FRUITS] = (p[K_FRUITS] ?: 0) + 1
                    "marisc" -> p[K_MARISC] = (p[K_MARISC] ?: 0) + 1
                    "vegà" -> p[K_VEGA] = (p[K_VEGA] ?: 0) + 1
                    "halal" -> p[K_HALAL] = (p[K_HALAL] ?: 0) + 1
                    "kosher" -> p[K_KOSHER] = (p[K_KOSHER] ?: 0) + 1
                    "ou" -> p[K_OU] = (p[K_OU] ?: 0) + 1
                }
            }
            forceSync()
        }
    }

    fun trackReview() {
        viewModelScope.launch {
            context.dataStore.edit { it[K_REVIEWS] = (it[K_REVIEWS] ?: 0) + 1 }
            forceSync()
        }
    }

    fun addTime(seconds: Long) {
        viewModelScope.launch {
            context.dataStore.edit { p ->
                val current = p[K_TIME] ?: 0L
                p[K_TIME] = current + seconds
            }
            forceSync()
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            context.dataStore.edit { it.clear() }
            db.collection("stats").document("user_unique_id").delete()
        }
    }

    private suspend fun forceSync() {
        val p = context.dataStore.data.first()
        val currentStats = AppStats(
            p[K_GLUTEN] ?: 0, p[K_LACTOSE] ?: 0, p[K_FRUITS] ?: 0,
            p[K_MARISC] ?: 0, p[K_VEGA] ?: 0, p[K_HALAL] ?: 0,
            p[K_KOSHER] ?: 0, p[K_OU] ?: 0, p[K_REVIEWS] ?: 0,
            p[K_TIME] ?: 0
        )
        db.collection("stats").document("user_unique_id").set(currentStats)
    }
}