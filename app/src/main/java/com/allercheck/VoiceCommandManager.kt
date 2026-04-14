package com.allercheck

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import kotlin.text.lowercase

import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

//Control por voz
class VoiceCommandManager(private val activity: AppCompatActivity) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    fun setupVoiceCommand(btnMic: ImageButton) {
        val sharedPreferences = activity.getSharedPreferences(activity_config_restrictions.PREFS_NAME, Context.MODE_PRIVATE)
        val isVoiceEnabled = sharedPreferences.getBoolean("VOICE_ENABLED", true)

        if (!isVoiceEnabled) {
            btnMic.visibility = View.GONE
            return
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ca-ES")
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                isListening = true
                btnMic.setImageResource(R.drawable.baseline_mic_24)
                Toast.makeText(activity, "Listening...", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val spokenText = data?.get(0)?.lowercase()
                Log.d("VoiceCommand", "Result: $spokenText")
                handleVoiceCommand(spokenText)
                resetButton(btnMic)
            }

            override fun onError(error: Int) {
                val message = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio error"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No match found"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permission missing"
                    else -> "Voice error: $error"
                }
                Log.e("VoiceCommand", message)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                resetButton(btnMic)
            }

            override fun onEndOfSpeech() {
                // Wait for results
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        btnMic.setOnClickListener {
            // CHECK PERMISSION
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), 100)
                Toast.makeText(activity, "Microphone permission required", Toast.LENGTH_SHORT).show()
            } else {
                if (!isListening) {
                    try {
                        speechRecognizer?.startListening(recognizerIntent)
                    } catch (e: Exception) {
                        Toast.makeText(activity, "Could not start microphone", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    speechRecognizer?.stopListening()
                }
            }
        }
    }

    private fun resetButton(btnMic: ImageButton) {
        isListening = false
        btnMic.setImageResource(R.drawable.baseline_mic_off_24)
    }

    private fun handleVoiceCommand(command: String?) {
        if (command == null) return
        Toast.makeText(activity, "Command: $command", Toast.LENGTH_SHORT).show()

        when {
            command.contains("principal") || command.contains("lista") -> {
                activity.startActivity(Intent(activity, activity_principal::class.java))
            }
            command.contains("favoritos") || command.contains("favorits") -> {
                activity.startActivity(Intent(activity, activity_favorite::class.java))
            }
            command.contains("perfil") -> {
                activity.startActivity(Intent(activity, activity_profile::class.java))
            }
            command.contains("apagar") -> {
                activity.finishAffinity()
            }
            command.contains("enrere") || command.contains("atrás") -> {
                activity.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    fun destroy() {
        speechRecognizer?.destroy()
    }
}