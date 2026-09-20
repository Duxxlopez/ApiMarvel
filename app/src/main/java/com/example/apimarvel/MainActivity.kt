package com.example.apimarvel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.security.MessageDigest

// Modelo de datos similar a la estructura de la API de Marvel
data class MarvelCharacter(
    val id: Int,
    val name: String,
    val comicsCount: Int,
    val seriesCount: Int,
    val storiesCount: Int,
    val image: Any // Soporta URL (String) o Resource ID (Int)
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lista de los 5 los Vengadores
        val avengersList = listOf(
            MarvelCharacter(1009368, "Iron Man", 2600, 640, 3600, R.drawable.iron_man),
            MarvelCharacter(1009220, "Captain America", 2400, 720, 3800, R.drawable.captain_america),
            MarvelCharacter(1009664, "Thor", 1800, 520, 2900, R.drawable.thor),
            MarvelCharacter(1009351, "Hulk", 2100, 480, 2700, R.drawable.hul),
            MarvelCharacter(1009189, "Black Widow", 600, 150, 800, R.drawable.black_widow)
        )

        val rvHeroes = findViewById<RecyclerView>(R.id.rvHeroes)
        rvHeroes.layoutManager = LinearLayoutManager(this)
        rvHeroes.adapter = HeroAdapter(avengersList)
    }

    /**
     * Función útil para cuando conectes la API Real de Marvel.
     */
    private fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            val digest = MessageDigest.getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}

class HeroAdapter(private val heroes: List<MarvelCharacter>) :
    RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHero: ImageView = view.findViewById(R.id.ivHero)
        val tvName: TextView = view.findViewById(R.id.tvHeroName)
        val tvComics: TextView = view.findViewById(R.id.tvHeroComics)
        val tvSeries: TextView = view.findViewById(R.id.tvHeroSeries)
        val tvStories: TextView = view.findViewById(R.id.tvHeroStories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return HeroViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroes[position]
        holder.tvName.text = "Nombre: ${hero.name}"
        holder.tvComics.text = "Comics: ${hero.comicsCount}"
        holder.tvSeries.text = "Series: ${hero.seriesCount}"
        holder.tvStories.text = "Historias: ${hero.storiesCount}"

        Glide.with(holder.itemView.context)
            .load(hero.image)
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.ivHero)
    }

    override fun getItemCount(): Int = heroes.size
}