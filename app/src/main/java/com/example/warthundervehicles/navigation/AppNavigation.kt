package com.example.warthundervehicles.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.warthundervehicles.ui.screens.catalog.CatalogScreen
import com.example.warthundervehicles.ui.screens.catalog.CatalogViewmodel
import com.example.warthundervehicles.ui.screens.detail.DetailScreen
import com.example.warthundervehicles.ui.screens.detail.DetailViewmodel
import com.example.warthundervehicles.ui.screens.home.HomeScreen
import com.example.warthundervehicles.ui.screens.home.HomeViewmodel
import com.example.warthundervehicles.ui.screens.landing.LandingScreen
import com.example.warthundervehicles.ui.screens.landing.LandingViewmodel
import com.example.warthundervehicles.ui.screens.versus.VersusScreen
import com.example.warthundervehicles.ui.screens.versus.VersusViewmodel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Obtener una instancia del ViewModel

    val homeViewModel: HomeViewmodel = hiltViewModel()
    val detailViewmodel: DetailViewmodel = hiltViewModel()
    val landingViewmodel: LandingViewmodel = hiltViewModel()
    val catalogViewmodel: CatalogViewmodel = hiltViewModel()
    val versusViewmodel: VersusViewmodel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LandingScreen.route
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }
        composable(
            Routes.DetailScreen.route + "/{identifier}",
            arguments = listOf(
                navArgument("identifier") { type = NavType.StringType },
            )
        ) {
            val identifier = remember {
                it.arguments?.getString("identifier")
            }
            if (identifier != null) {
                DetailScreen(navController = navController, viewModel = detailViewmodel, identifier)
            }
        }
        composable(Routes.LandingScreen.route) {
            LandingScreen(navController = navController, landingViewmodel)
        }
//        composable(Routes.CatalogScreen.route){
//            CatalogScreen(navController, catalogViewmodel)
//        }

        composable(
            Routes.CatalogScreen.route + "/{army}/{countries}/{tiers}",
            //    Routes.CatalogScreen.route,
            arguments = listOf(
                navArgument("army") { type = NavType.StringType },
                navArgument("countries") { type = NavType.StringType },
                navArgument("tiers") { type = NavType.StringType }
            )
        ) {
            val army = it.arguments?.getString("army")
            val countries = it.arguments?.getString("countries")?.split(",")
            Log.i("MyTag", " countries::${countries}")
            val tiersString = it.arguments?.getString("tiers")
            //      val tiers= tiersString?.split(",")
            Log.i("MyTag", "argumens  tiersString::${tiersString} ")
            CatalogScreen(navController, catalogViewmodel, army, countries!!, tiersString)
        }


        composable(
            Routes.VersusScreen.route + "/{vehicle1}/{vehicle2}",
            arguments = listOf(
                navArgument("vehicle1") { type = NavType.StringType },
                navArgument("vehicle2") { type = NavType.StringType },
            )
        ) {
            val vehicle1 = it.arguments?.getString("vehicle1")
            val vehicle2 = it.arguments?.getString("vehicle2")
            Log.i("MyTag", " vehicle1::${vehicle1} ..vehicle2::$vehicle2")

            VersusScreen(navController, versusViewmodel, vehicle1!!, vehicle2!!)

            //     CatalogScreen(navController, catalogViewmodel, army, countries!!, tiersString)
        }


    }
}