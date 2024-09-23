package com.dicoding.pokelist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvPokemon: RecyclerView
    private val list = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvPokemon = findViewById(R.id.rv_pokemon)
        rvPokemon.setHasFixedSize(true)

        list.addAll(getListPokemon())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_layout -> {
                rvPokemon.layoutManager = LinearLayoutManager(this)
            }

            R.id.grid_layout -> {
                rvPokemon.layoutManager = GridLayoutManager(this, 2)
            }

            R.id.about_page -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("Recycle")
    private fun getListPokemon(): ArrayList<Pokemon> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataHeight = resources.getStringArray(R.array.data_height)
        val dataWeight = resources.getStringArray(R.array.data_weight)
        val dataType = resources.getStringArray(R.array.data_type)
        val dataAbilities = resources.getStringArray(R.array.data_abilities)
        val dataWeaknesses = resources.getStringArray(R.array.data_weaknesses)
        val listPokemon = ArrayList<Pokemon>()
        for (i in dataName.indices) {
            val pokemon = Pokemon(
                dataName[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1),
                dataHeight[i],
                dataWeight[i],
                dataType[i],
                dataAbilities[i],
                dataWeaknesses[i],
            )
            listPokemon.add(pokemon)
        }
        return listPokemon
    }

    private fun showRecyclerList() {
        rvPokemon.layoutManager = LinearLayoutManager(this)
        val listPokemonAdapter = ListPokemonAdapter(list)
        rvPokemon.adapter = listPokemonAdapter

        listPokemonAdapter.setOnItemClickCallback(object : ListPokemonAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Pokemon) {
                showSelectedPokemon(data)
            }
        })
    }

    private fun showSelectedPokemon(pokemon: Pokemon) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("EXTRA_POKEMON", pokemon)
        startActivity(intent)
    }
}