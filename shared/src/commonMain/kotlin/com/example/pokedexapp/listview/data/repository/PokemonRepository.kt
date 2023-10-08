package com.example.pokedexapp.listview.data.repository

import com.example.pokedexapp.listview.data.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(): List<Pokemon>
    suspend fun loadNextPage(): List<Pokemon>
    fun closeHttpClient()
}