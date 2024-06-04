package com.example.warthundervehicles

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warthundervehicles.navigation.AppNavigation
import com.example.warthundervehicles.ui.screens.home.HomeViewmodel
//import com.example.warthundervehicles.ui.screens.MyViewModel
import com.example.warthundervehicles.ui.theme.WarThunderVehiclesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  //  @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var localBD: Boolean = false
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(Unit) {
                 viewModel.doLocal()
            }
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
