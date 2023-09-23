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
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.listview.data.model.PokemonList
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
    AnimatedVisibility(uiState.pokemonList.pokemonPointers.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
            content = {
                items(uiState.pokemonList.pokemonPointers) {
                    PokemonCard(it)
                }
            }
        )
    }
}

@Composable
fun PokemonCard(pokemon: PokemonList.PokemonPointer) {
    Column {
        Text(pokemon.name)
        Text(pokemon.url)
        Text(pokemon.id.toString())
        KamelImage(
            asyncPainterResource(pokemon.image),
            contentDescription = "Pokemon image"
        )
    }
}