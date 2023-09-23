package com.example.pokedexapp.listview.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.listview.ui.viewmodel.PokemonListViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun PokemonListView(viewModel: PokemonListViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val gridPadding: Dp = 16.dp
    AnimatedVisibility(uiState.pokemonList.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(gridPadding),
            verticalArrangement = Arrangement.spacedBy(gridPadding),
            modifier = modifier.fillMaxSize().padding(gridPadding),
            content = {
                items(uiState.pokemonList) { pokemon: Pokemon ->
                    PokemonCard(
                        imageUrl = pokemon.imageUrl,
                        name = pokemon.name,
                        id = pokemon.id,
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
    Card(
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            KamelImage(
                asyncPainterResource(imageUrl),
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = id,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}