package com.example.pokedexapp.listview.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.listview.ui.viewmodel.PokemonListViewModel


@Composable
fun PokemonListView(viewModel: PokemonListViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.isLoading
    val gridState = rememberLazyGridState()
    val isAtBottom = !gridState.canScrollForward

    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !isLoading) {
            viewModel.loadNextPage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        PokemonGrid(
            modifier = modifier,
            pokemonList = uiState.pokemonList,
            gridState = gridState,
            isLoading = isLoading,
        )
    }
}

@Composable
private fun PokemonGrid(
    modifier: Modifier,
    pokemonList: List<Pokemon>,
    gridState: LazyGridState,
    isLoading: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 32.dp),
        modifier = modifier
    ) {
        items(pokemonList) { pokemon: Pokemon ->
            PokemonCard(
                imageBitmap = pokemon.image,
                name = pokemon.name,
                id = pokemon.id,
                bgColor = pokemon.backgroundColor,
            )
        }
        if (isLoading) {
            item(
                span = { GridItemSpan(maxLineSpan) },
            ) {
                CircularLoadingComposable()
            }
        }
    }
}

@Composable
private fun CircularLoadingComposable(
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
fun PokemonCard(
    imageBitmap: ImageBitmap?,
    bgColor: Color?,
    name: String,
    id: String,
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = bgColor ?: MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            PokemonImage(imageBitmap)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = id,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PokemonImage(imageBitmap: ImageBitmap?) {
    if (imageBitmap != null) {
        // If the image loaded successfully, display it
        Image(
            bitmap = imageBitmap,
            contentDescription = "Pokemon Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    } else {
        Box(modifier = Modifier)
    }
}