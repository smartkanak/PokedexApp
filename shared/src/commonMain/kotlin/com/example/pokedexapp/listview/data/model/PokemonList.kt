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
    @Serializable
    data class PokemonPointer(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String
    )

    companion object {
        fun empty() = PokemonList(
            count = -1,
            next = null,
            previous = null,
            pokemonPointers = emptyList()
        )
    }
}