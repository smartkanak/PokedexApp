package com.example.pokedexapp.listview.data.repository

import androidx.compose.ui.graphics.ImageBitmap
import com.example.pokedexapp.listview.data.api.PokemonListDto
import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.listview.data.utils.PokemonUtils
import com.kmpalette.loader.NetworkLoader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json

class PokemonRepositoryImpl : PokemonRepository {

    private var pokemonList: List<Pokemon> = emptyList()

    private val loader: NetworkLoader = NetworkLoader()

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
            PokemonUtils.toPokemonFromDto(pokemonDto, ::loadImage)
        }

        return pokemonList
    }

    private suspend fun loadImage(urlStr: String): ImageBitmap? {
        val url = Url(urlStr)
        var imageBitmap: ImageBitmap? = null
        try {
            imageBitmap = loader.load(url)
        } catch (cause: Throwable) {
            cause.printStackTrace()
        }
        return imageBitmap
    }

    override fun closeHttpClient() {
        httpClient.close()
    }
}