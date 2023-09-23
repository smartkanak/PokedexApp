package com.example.pokedexapp.listview.data.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListDto(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val pokemonDtos: List<PokemonDto>
) {

    @Serializable
    data class PokemonDto(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String
    )
}