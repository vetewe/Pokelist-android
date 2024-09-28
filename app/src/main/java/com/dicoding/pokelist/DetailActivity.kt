package com.dicoding.pokelist

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.Menu
import android.widget.Toast

class DetailActivity : AppCompatActivity() {
    private var isFavorite = false
    private var pokemon: Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val imgPhoto: ImageView = findViewById(R.id.img_item_photo)
        val tvName: TextView = findViewById(R.id.tv_item_name)
        val tvDescription: TextView = findViewById(R.id.tv_item_description)
        val tvHeight: TextView = findViewById(R.id.tv_height_value)
        val tvWeight: TextView = findViewById(R.id.tv_weight_value)
        val tvType: TextView = findViewById(R.id.tv_type_value)
        val tvAbilities: TextView = findViewById(R.id.tv_abilities_value)
        val tvWeaknesses: TextView = findViewById(R.id.tv_weaknesses_value)

        pokemon = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("EXTRA_POKEMON", Pokemon::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EXTRA_POKEMON")
        }

        pokemon?.let {
            imgPhoto.setImageResource(it.photo)
            tvName.text = it.name
            tvDescription.text = it.description
            tvHeight.text = it.height
            tvWeight.text = it.weight
            tvType.text = it.type
            tvAbilities.text = it.abilities
            tvWeaknesses.text = it.weaknesses
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.action_favorite -> {
                toggleFavorite(item)
                true
            }

            R.id.action_share -> {
                sharePokemon()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleFavorite(item: MenuItem) {
        isFavorite = !isFavorite
        if (isFavorite) {
            item.setIcon(R.drawable.ic_fav)
            Toast.makeText(this, "Pokemon added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            item.setIcon(R.drawable.ic_emptyfav)
            Toast.makeText(this, "Pokemon removed from favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sharePokemon() {
        pokemon?.let {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Pokemon!")
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "I found ${it.name}! It's a ${it.type} type Pokemon."
            )
            startActivity(Intent.createChooser(shareIntent, "Share Pokemon"))
        }
    }
}