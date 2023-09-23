package com.example.pokedexapp.listview.data.model

import com.example.pokedexapp.listview.data.api.PokemonListDto

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val detailedUrl: String,
) {
    companion object {
        fun fromDto(dto: PokemonListDto.PokemonDto): Pokemon {
            val id: Int = extractPokemonId(dto.url)
            return Pokemon(
                id = id,
                name = dto.name,
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
    }

    val idWithLeadingZeros: String
        get() = id.toString().padStart(4, '0')
}

