package com.example.pokedexapp

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun App() {
    MaterialTheme {
        PokedexApp()
    }
}

@Composable
fun PokedexApp() {
    // center text hello world
    Text("Hello World!")
}