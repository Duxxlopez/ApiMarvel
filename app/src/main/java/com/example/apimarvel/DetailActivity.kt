package com.example.apimarvel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        val ivDetailGif = findViewById<ImageView>(R.id.ivDetailGif)
        val ivDetailHero = findViewById<ImageView>(R.id.ivDetailHero)
        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvDescription = findViewById<TextView>(R.id.tvDetailDescription)
        val tvComics = findViewById<TextView>(R.id.tvDetailComics)
        val tvSeries = findViewById<TextView>(R.id.tvDetailSeries)
        val tvStories = findViewById<TextView>(R.id.tvDetailStories)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Obtener datos del Intent
        val name = intent.getStringExtra("name") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val comics = intent.getIntExtra("comics", 0)
        val series = intent.getIntExtra("series", 0)
        val stories = intent.getIntExtra("stories", 0)
        val imageResId = intent.getIntExtra("imageResId", 0)

        // Asignar datos a la UI
        tvName.text = name
        tvDescription.text = description
        tvComics.text = comics.toString()
        tvSeries.text = series.toString()
        tvStories.text = stories.toString()
        
        Glide.with(this).load(imageResId).into(ivDetailHero)

        // Mostrar el GIF de transición (usamos el de drawable que es donde está)
        Glide.with(this)
            .asGif()
            .load(R.drawable.avengers)
            .into(ivDetailGif)

        // Hacer que el GIF desaparezca después de 3 segundos
        // Esto cumple con "desaparezca cuando termine el gif"
        Handler(Looper.getMainLooper()).postDelayed({
            ivDetailGif.visibility = View.GONE
        }, 3000)

        btnBack.setOnClickListener {
            finish()
        }
    }
}