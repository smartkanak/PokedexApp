package com.example.pokedexapp

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokedexapp.listview.ui.view.PokemonListView
import com.example.pokedexapp.listview.ui.viewmodel.PokemonListViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App() {
    val viewModel: PokemonListViewModel = getViewModel(Unit, viewModelFactory { PokemonListViewModel() })
    MaterialTheme {
        Surface {
            PokedexApp(viewModel)
        }
    }
}

@Composable
fun PokedexApp(
    viewModel: PokemonListViewModel,
    modifier: Modifier = Modifier
) {
    PokemonListView(viewModel, modifier)
}
