package com.example.warthundervehicles.navigation

sealed class Routes (val route:String){

    object HomeScreen:Routes("home_screen")
    object DetailScreen:Routes("detail_screen")
    object LandingScreen:Routes("landing_screen")
    object CatalogScreen:Routes("catalog_screen")
    object VersusScreen:Routes("versus_screen")


//    object PokemonDetailScreen {
//        const val route = "pokemon_detail_screen/{pokemonId}/{pokemonName}"
//
//        fun createRoute(pokemonId: Int, pokemonName: String): String {
//            return "pokemon_detail_screen/$pokemonId/$pokemonName"
//        }
//    }

}