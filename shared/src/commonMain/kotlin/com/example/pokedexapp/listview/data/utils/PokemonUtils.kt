package com.example.pokedexapp.listview.data.utils

import com.example.pokedexapp.listview.data.api.PokemonListDto
import com.example.pokedexapp.listview.data.model.Pokemon


object PokemonUtils {

    fun toPokemonFromDto(dto: PokemonListDto.PokemonDto): Pokemon {
        val id: Int = extractPokemonId(dto.url)

        return Pokemon(
            id = idWithLeadingZeros(id),
            name = capitalize(dto.name),
            imageUrl = getImageUrl(id),
            detailedUrl = dto.url
        )
    }

    private fun extractPokemonId(url: String): Int {
        // Extract the Pokemon ID from the URL
        val parts = url.split("/")
        return parts[parts.size - 2].toInt()
    }

    private fun getImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

    private fun idWithLeadingZeros(id: Int): String {
        return id.toString().padStart(3, '0')
    }
}