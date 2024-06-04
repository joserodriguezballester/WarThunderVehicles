package com.example.warthundervehicles.ui.screens.landing

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.warthundervehicles.data.repository.LocalRepository
import com.example.warthundervehicles.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavHostController
import com.example.warthundervehicles.navigation.Routes

@HiltViewModel
class LandingViewmodel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
):ViewModel() {

    private val _selectedArmy = mutableStateOf("")
    val selectedArmy: State<String> = _selectedArmy

    private val _selectedTiers = mutableStateListOf<Int>()
    val selectedTiers: List<Int> = _selectedTiers

    private val _selectedCountries = mutableStateListOf<String>()
    val selectedCountries: List<String> = _selectedCountries

    fun setSelectedArmy(army: String) {
        _selectedArmy.value = army
    }
    fun addSelectedTier(tier: Int) {
        _selectedTiers.add(tier)
    }
    fun removeSelectedTier(tier: Int) {
        _selectedTiers.remove(tier)
    }
     fun addSelectedCountry(country:String){
         _selectedCountries.add(country)
     }
    fun removeSelectedCountry(country: String){
        _selectedCountries.remove(country)
    }

    fun borrarValores() {
        _selectedCountries.clear()
        _selectedTiers.clear()
        _selectedArmy.value=""
    }

    fun onAcceptButtonClicked(navController: NavHostController) {

        //    recogerDatos
        var army = selectedArmy.value
       if (army.isNullOrEmpty()) army = "tank"

        if (selectedCountries.isEmpty()) addSelectedCountry("all")
        val countries = selectedCountries.joinToString(", ")

        if (selectedTiers.isEmpty()) addSelectedTier(0)
        val tiers = selectedTiers.joinToString(",")

        borrarValores()
        Log.i("MyTag", "army $army  ")
        navController.navigate(
            Routes.CatalogScreen.route + "/${army}/${countries!!}/${tiers}"
        )
    }


}