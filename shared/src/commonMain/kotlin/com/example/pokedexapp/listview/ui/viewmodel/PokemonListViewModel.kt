package com.example.pokedexapp.listview.ui.viewmodel

import com.example.pokedexapp.listview.data.model.PokemonList
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
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
    val pokemonList: PokemonList = PokemonList.empty(),
)

class PokemonListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        updatePokemonList()
    }

    override fun onCleared() {
        httpClient.close()
    }

    fun updatePokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemonList: PokemonList = getPokemonList()
                _uiState.update {
                    it.copy(isLoading = false, pokemonList = pokemonList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println("nope")
                }
                }
        }
    }

    private suspend fun getPokemonList(): PokemonList {
        val pokemonList = httpClient
            .get("https://pokeapi.co/api/v2/pokemon")
            .body<PokemonList>()
        return pokemonList
    }

}