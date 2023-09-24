package com.example.pokedexapp.listview.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

data class Pokemon(
    val id: String,
    val name: String,
    val image: ImageBitmap?,
    val detailedUrl: String,
    val backgroundColor: Color?,
)