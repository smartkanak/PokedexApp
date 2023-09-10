package com.example.pokedexapp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.pokedexapp.listview.data.model.PokemonList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

@Composable
fun App() {
    PokedexApp()
}

@Composable
fun PokedexApp(
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        LaunchedEffect(Unit) {
            println(getImages())
        }
    }
}

val httpClient = HttpClient() {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getImages(): PokemonList {
    val pokemonList = httpClient
        .get("https://pokeapi.co/api/v2/pokemon")
        .body<PokemonList>()
    return pokemonList
}