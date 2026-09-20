package com.example.apimarvel

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Modelo de datos
data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val comicsCount: Int,
    val seriesCount: Int,
    val storiesCount: Int,
    val imageResId: Int,
    val detailGifResId: Int? = null,
    val gifDurationMs: Long = 3000L // Duración por defecto
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

        // Lista de los 5 Vengadores con sus GIFs personalizados y tiempos específicos
        val avengersList = listOf(
            MarvelCharacter(1009368, "Iron Man", 
                "Tony Stark es un genio inventor y multimillonario que usa su armadura de alta tecnología para proteger al mundo.", 
                2600, 640, 3600, R.drawable.iron_man, R.drawable.iron_man_gif, 3000L),
            MarvelCharacter(1009220, "Captain America", 
                "Steve Rogers, el supersoldado de la Segunda Guerra Mundial, es el símbolo viviente de la libertad y el líder de los Vengadores.", 
                2400, 720, 3800, R.drawable.captain_america, R.drawable.captain_america_gif, 3000L),
            MarvelCharacter(1009664, "Thor", 
                "El Dios del Trueno y príncipe de Asgard, Thor empuña el martillo Mjolnir para controlar las tormentas y luchar contra el mal.", 
                1800, 520, 2900, R.drawable.thor, R.drawable.thor_gif, 3000L),
            MarvelCharacter(1009351, "Hulk", 
                "Tras ser expuesto a radiación gamma, el Dr. Bruce Banner se transforma en un gigante verde de fuerza inconmensurable cuando se enfurece.", 
                2100, 480, 2700, R.drawable.hul, R.drawable.hulk_gif, 6000L), // Hulk dura más (6 seg)
            MarvelCharacter(1009189, "Black Widow", 
                "Natasha Romanoff es una de las espías y asesinas más letales del mundo, convertida en una heroína clave para la seguridad global.", 
                600, 150, 800, R.drawable.black_widow, R.drawable.black_widow_gif, 5000L) // Widow dura más (5 seg)
        )

        val rvHeroes = findViewById<RecyclerView>(R.id.rvHeroes)
        rvHeroes.layoutManager = LinearLayoutManager(this)
        
        // Configuramos el adaptador para mostrar el Diálogo al hacer click
        rvHeroes.adapter = HeroAdapter(avengersList) { hero ->
            mostrarDetallePersonaje(hero)
        }
    }

    private fun mostrarDetallePersonaje(hero: MarvelCharacter) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_hero_detail, null)

        val tvName = view.findViewById<TextView>(R.id.tvDialogName)
        val tvDesc = view.findViewById<TextView>(R.id.tvDialogDescription)
        val tvComics = view.findViewById<TextView>(R.id.tvDialogComics)
        val tvSeries = view.findViewById<TextView>(R.id.tvDialogSeries)
        val tvStories = view.findViewById<TextView>(R.id.tvDialogStories)
        val ivHero = view.findViewById<ImageView>(R.id.ivDialogHero)
        val ivGif = view.findViewById<ImageView>(R.id.ivDialogGif)
        val cardGif = view.findViewById<View>(R.id.cardGif)
        val btnClose = view.findViewById<Button>(R.id.btnDialogClose)

        // Asignar datos
        tvName.text = "Nombre: ${hero.name}"
        tvDesc.text = hero.description
        tvComics.text = hero.comicsCount.toString()
        tvSeries.text = hero.seriesCount.toString()
        tvStories.text = hero.storiesCount.toString()
        
        Glide.with(this).load(hero.imageResId).circleCrop().into(ivHero)

        // Cargar el GIF de transición
        val gifToLoad = hero.detailGifResId ?: R.drawable.avengers

        Glide.with(this)
            .asGif()
            .load(gifToLoad)
            .into(ivGif)

        // Crear el diálogo
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        // Desaparecer el GIF después del tiempo personalizado del héroe
        Handler(Looper.getMainLooper()).postDelayed({
            cardGif.visibility = View.GONE
        }, hero.gifDurationMs)
    }
}

class HeroAdapter(
    private val heroes: List<MarvelCharacter>,
    private val onItemClick: (MarvelCharacter) -> Unit
) : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

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
            .load(hero.imageResId)
            .circleCrop()
            .into(holder.ivHero)

        holder.itemView.setOnClickListener {
            onItemClick(hero)
        }
    }

    override fun getItemCount(): Int = heroes.size
}