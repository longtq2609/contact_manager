package com.longtq.contact_manager

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.longtq.contact_manager.presentation.screens.home.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        HomeScreenNavigator()
    }
}

@Composable
fun HomeScreenNavigator() {
    Navigator(HomeScreen())
}

