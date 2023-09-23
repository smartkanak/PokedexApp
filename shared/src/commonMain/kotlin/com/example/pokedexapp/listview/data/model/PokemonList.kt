package com.example.pokedexapp.listview.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val pokemonPointers: List<PokemonPointer>
) {

    companion object {
        fun empty() = PokemonList(
            count = -1,
            next = null,
            previous = null,
            pokemonPointers = emptyList()
        )
    }

    @Serializable
    data class PokemonPointer(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String
    ) {

        val id: Int
            get() = extractPokemonId(url)

        val image: String
            get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

        private fun extractPokemonId(url: String): Int {
            // Extract the Pokemon ID from the URL
            val parts = url.split("/")
            return parts[parts.size - 2].toInt()
        }
    }
}