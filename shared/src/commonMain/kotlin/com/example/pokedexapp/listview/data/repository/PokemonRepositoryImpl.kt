package com.example.pokedexapp.listview.data.repository

import com.example.pokedexapp.listview.data.api.PokemonListDto
import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.listview.data.utils.PokemonUtils
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class PokemonRepositoryImpl : PokemonRepository {

    private var pokemonList: List<Pokemon> = emptyList()

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun getPokemonList(): List<Pokemon> {

        val pokemonListDto = httpClient
            .get("https://pokeapi.co/api/v2/pokemon")
            .body<PokemonListDto>()

        pokemonList = pokemonListDto.pokemonDtos.map { pokemonDto ->
            PokemonUtils.toPokemonFromDto(pokemonDto)
        }

        return pokemonList
    }

    override fun closeHttpClient() {
        httpClient.close()
    }
}