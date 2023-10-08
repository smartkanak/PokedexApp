package com.example.pokedexapp.listview.data.repository

import androidx.compose.ui.graphics.ImageBitmap
import com.example.pokedexapp.listview.data.api.PokemonListDto
import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.listview.data.mapper.PokemonMapper
import com.kmpalette.loader.NetworkLoader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json

class PokemonRepositoryImpl(
    private val httpClient: HttpClient = HttpClient { install(ContentNegotiation) { json() } },
    private val loader: NetworkLoader = NetworkLoader(),
) : PokemonRepository {

    private var pokemonList: MutableList<Pokemon> = mutableListOf()
    private var currentUrl: String = "https://pokeapi.co/api/v2/pokemon?limit=20"
    private var nextUrl: String? = null

    override suspend fun getPokemonList(): List<Pokemon> {
        val pokemonListDto = httpClient
            .get(currentUrl)
            .body<PokemonListDto>()

        nextUrl = pokemonListDto.next
        val fetchedList: List<Pokemon> = pokemonListDto.pokemonDtos.map { pokemonDto ->
            PokemonMapper.toPokemonFromDto(pokemonDto, ::loadImage)
        }

        pokemonList = fetchedList.toMutableList()

        return pokemonList
    }

    override suspend fun loadNextPage(): List<Pokemon> {
        currentUrl = nextUrl ?: return emptyList()
        return getPokemonList()
    }

    override fun closeHttpClient() {
        httpClient.close()
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
}