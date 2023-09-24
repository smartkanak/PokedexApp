package com.example.pokedexapp.listview.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.example.pokedexapp.listview.data.api.PokemonListDto
import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.utils.ColorExtractor


object PokemonMapper {

    suspend fun toPokemonFromDto(
        dto: PokemonListDto.PokemonDto,
        loadImage: suspend (String) -> ImageBitmap?,
    ): Pokemon {
        val id: Int = extractPokemonId(dto.url)
        val image: ImageBitmap? = loadImage(getImageUrl(id))
        val backgroundColor: Color? = ColorExtractor.forBgFromImage(image)

        return Pokemon(
            id = idWithLeadingZeros(id),
            name = capitalize(dto.name),
            image = image,
            detailedUrl = dto.url,
            backgroundColor = backgroundColor,
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