package com.example.warthundervehicles

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.Room
import com.example.warthundervehicles.data.local.database.MachineDatabase
import com.example.warthundervehicles.data.repository.MachineRepository
import com.example.warthundervehicles.navigation.AppNavigation
import com.example.warthundervehicles.ui.screens.home.HomeViewmodel
//import com.example.warthundervehicles.ui.screens.MyViewModel
import com.example.warthundervehicles.ui.theme.WarThunderVehiclesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val db = Room.databaseBuilder(this, MachineDatabase::class.java, "disney_db").build()
//        val dao = db.machineDao
//        val repository = MachineRepository( dao)

        setContent {
            //      val viewModel = hiltViewModel<MyViewModel>()
            val viewModel = hiltViewModel<HomeViewmodel>()
            val vehicles = viewModel.listaVehiculos.value

            WarThunderVehiclesTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AppNavigation()

                }
            }
        }
    }


}
