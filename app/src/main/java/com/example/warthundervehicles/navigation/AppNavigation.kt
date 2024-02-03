package com.example.warthundervehicles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.warthundervehicles.ui.screens.DetailScreen
import com.example.warthundervehicles.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.FirstScreen.route
    ) {
        composable(Routes.FirstScreen.route) {
            HomeScreen(navController = navController)
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
                DetailScreen(navController = navController, identifier)
            }
        }
    }
}