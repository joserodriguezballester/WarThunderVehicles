package com.example.warthundervehicles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.warthundervehicles.ui.screens.DetailScreen
import com.example.warthundervehicles.ui.screens.HomeScreen
import com.example.warthundervehicles.ui.screens.MyViewModel

@Composable
fun AppNavigation() {
    val navController= rememberNavController()

    // Obtener una instancia del ViewModel
    val viewModel: MyViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.FirstScreen.route
    ) {
        composable(Routes.FirstScreen.route) {
            HomeScreen(navController = navController,viewModel=viewModel)
        }
        composable(Routes.DetailScreen.route+ "/{identifier}",
            arguments = listOf(
                navArgument("identifier") { type = NavType.StringType },
            )
        ) {
            val identifier = remember {
               it.arguments?.getString("identifier")
            }
            if (identifier != null) {
                DetailScreen(navController = navController,viewModel=viewModel, identifier)
            }
        }
    }
}