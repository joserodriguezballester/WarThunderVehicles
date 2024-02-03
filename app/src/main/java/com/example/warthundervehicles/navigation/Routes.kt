package com.example.warthundervehicles.navigation

sealed class Routes (val route:String){

    object FirstScreen:Routes("first_screen")
    object DetailScreen:Routes("detail_screen")

//    object PokemonDetailScreen {
//        const val route = "pokemon_detail_screen/{pokemonId}/{pokemonName}"
//
//        fun createRoute(pokemonId: Int, pokemonName: String): String {
//            return "pokemon_detail_screen/$pokemonId/$pokemonName"
//        }
//    }

}