package com.example.apimarvel // <--- IMPORTANTE: Cambia esto por el nombre del paquete real de tu proyecto

import android.content.Intent
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
            .load(R.drawable.avengers)
            .into(imageViewGif)

        // Temporizador de 5 segundos exactos (5000 milisegundos)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Añadimos una transición suave
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 9000)
    }
}