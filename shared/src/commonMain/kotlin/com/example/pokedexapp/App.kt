package com.example.pokedexapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.listview.ui.viewmodel.PokemonListViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun App() {
    val viewModel: PokemonListViewModel = getViewModel(Unit, viewModelFactory { PokemonListViewModel() })
    MaterialTheme {
        PokedexApp(viewModel)
    }
}

@Composable
fun PokedexApp(
    viewModel: PokemonListViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridPadding: Dp = 16.dp
    AnimatedVisibility(uiState.pokemonList.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(gridPadding),
            verticalArrangement = Arrangement.spacedBy(gridPadding),
            modifier = modifier.fillMaxSize().padding(gridPadding),
            content = {
                items(uiState.pokemonList) { pokemon ->
                    PokemonCard(
                        imageUrl = pokemon.imageUrl,
                        name = pokemon.name,
                        id = pokemon.idWithLeadingZeros,
                        detailedUrl = pokemon.detailedUrl
                    )
                }
            }
        )
    }
}

@Composable
fun PokemonCard(
    imageUrl: String,
    name: String,
    id: String,
    detailedUrl: String,
) {
    Column {
        Text(name)
        Text(detailedUrl)
        Text(id)
        KamelImage(
            asyncPainterResource(imageUrl),
            contentDescription = "Pokemon image"
        )
    }
}