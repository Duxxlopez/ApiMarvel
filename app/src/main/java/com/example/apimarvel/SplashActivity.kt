package com.example.apimarvel

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Habilitar modo pantalla completa
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Ocultar barras del sistema para que el GIF ocupe todo el emulador
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        // Vincular el ImageView del diseño
        val imageViewGif = findViewById<ImageView>(R.id.ivSplashGif)

        // Cargar y reproducir el GIF de los Vengadores usando Glide
        Glide.with(this)
            .asGif()
            .load(R.raw.avengers)
            .into(imageViewGif)

        // Reproducir el sonido del intro
        // IMPORTANTE: El archivo debe llamarse intro_sound.mp3 y estar en res/raw
        val resId = resources.getIdentifier("intro_sound", "raw", packageName)
        if (resId != 0) {
            try {
                mediaPlayer = MediaPlayer.create(this, resId)
                mediaPlayer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Temporizador de 10 segundos exactos para que el audio termine junto con el splash
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Transición suave
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 10000)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar el reproductor cuando la actividad se destruye
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}