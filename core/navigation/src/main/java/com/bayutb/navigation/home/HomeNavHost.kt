package com.bayutb.navigation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayutb.generative_ai.presentation.GenerativeAIScreen
import com.bayutb.generative_ai.presentation.GenerativeAIDestinations

@Composable
fun HomeNavHost(
    onNavigationRequested : (GenerativeAIDestinations) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GenerativeAIDestinations.HOME
    ) {
        addHomeNavGraph(onNavigationRequested)
    }
}

private fun NavGraphBuilder.addHomeNavGraph(
    onNavigationRequested: (GenerativeAIDestinations) -> Unit
) {
    composable(GenerativeAIDestinations.HOME) {
        GenerativeAIScreen(
            onNavigationRequested = { destination ->
                onNavigationRequested(destination)
            }
        )
    }
}