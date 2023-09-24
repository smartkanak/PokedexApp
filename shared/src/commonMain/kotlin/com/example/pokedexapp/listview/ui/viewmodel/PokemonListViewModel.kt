package com.example.pokedexapp.listview.ui.viewmodel

import com.example.pokedexapp.listview.data.model.Pokemon
import com.example.pokedexapp.listview.data.repository.PokemonRepository
import com.example.pokedexapp.listview.data.repository.PokemonRepositoryImpl
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class PokemonUiState(
    val isLoading: Boolean = true,
    val pokemonList: List<Pokemon> = emptyList(),
)

class PokemonListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()
    private val repo: PokemonRepository = PokemonRepositoryImpl()

    init {
        updatePokemonList()
    }

    override fun onCleared() {
        repo.closeHttpClient()
    }

    private fun updatePokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update {
                    val pokemonList: List<Pokemon> = repo.getPokemonList()
                    it.copy(isLoading = false, pokemonList = pokemonList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println("Error: ${e.message}")
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

}