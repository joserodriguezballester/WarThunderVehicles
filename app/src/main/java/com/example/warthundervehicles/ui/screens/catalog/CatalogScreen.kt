package com.example.warthundervehicles.ui.screens.catalog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.modelsApp.MachineListItem
import com.example.warthundervehicles.navigation.Routes
import com.example.warthundervehicles.utils.Constants.LIST_COUNTRY
import com.example.warthundervehicles.utils.Constants.LIST_TYPE_VEHICLE
import com.example.warthundervehicles.utils.customToString
import com.example.warthundervehicles.utils.getGradientBrushForTier
import java.util.Locale


@Composable
fun CatalogScreen(
    navController: NavHostController,
    viewModel: CatalogViewmodel,
    army: String?,
    countries: List<String>,
    tiers: String?,
) {
    var lista:List<MachineListItem> =emptyList()
    // Preparar datos
    val list_countries = if (countries[0] == "all") LIST_COUNTRY else countries
    val calculate_list_tiers = tiers!!.split(",").map { it.toInt() }
    val list_tiers = if (calculate_list_tiers[0] == 0) {
        listOf(1, 2, 3, 4, 5, 6, 7, 8)
    } else calculate_list_tiers

    // Solicitud de vehículos cuando el composible se inicia
    val vehiclesRequested = remember { mutableStateOf(false) }
    // Asegurarnos que solo pide la lista una vez
    if (!vehiclesRequested.value) {
            viewModel.PedirVehiculos(list_countries, list_tiers)
            vehiclesRequested.value = true
    }
// Filtrar por tipo de vehiculo
    Log.i("MyTag", "army $army  ")
   when (army) {
       LIST_TYPE_VEHICLE[0] -> lista = viewModel.listaTank.value
       LIST_TYPE_VEHICLE[1] -> lista = viewModel.listAir.value
       LIST_TYPE_VEHICLE[2] -> lista = viewModel.listNaval.value
   }
// Por ejemplo, mostrar los valores recibidos en algún lugar de la pantalla

    Column {
                 MachineList(
                      navController = navController,
                     vehicles = lista
                  )
                 {
                     Log.i("MyTag", "Clic____ en ${it.identifier}")
                     navController.navigate(Routes.DetailScreen.route + "/${it.identifier}")
                 }
        
//        VehicleList(navController, vehicles = vehicles) { selectedVehicle ->
//            Log.i("MyTag", "Clic en ${selectedVehicle.identifier}")
//            viewModel.inserMachines(selectedVehicle.toMachine())
//            navController.navigate(Routes.DetailScreen.route + "/${selectedVehicle.identifier}")
//        }
    }
}
@Composable
fun MachineList(
    navController: NavHostController,
    vehicles: List<MachineListItem>,
    onItemClick: (MachineListItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        items(vehicles) { vehicle ->
            MachineCard(vehicle = vehicle) { clickedVehicle ->
                onItemClick(clickedVehicle)
            }
        }
    }
}

@Composable
fun MachineCard(vehicle:MachineListItem, onItemClick: (MachineListItem) -> Unit) {
    val brush = getGradientBrushForTier(vehicle.era)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 12.dp)
            // .background(brush)
            .clickable { onItemClick(vehicle) }
            .shadow(12.dp, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .background(brush)
                .padding(16.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                Log.i("MyTag", vehicle.imageUrl)

                Image(
                    painter = painterResource(vehicle.bandera),
                    contentDescription = "Descripción de la imagen",
                    modifier = Modifier
                        // .fillMaxWidth()
                        .width(45.dp)
                        .height(30.dp) // Ajusta la altura según tus necesidades
                )
                Text(
                    text = vehicle.type.customToString().uppercase(Locale.ROOT),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // Limita a una línea
                    overflow = TextOverflow.Ellipsis

                )
                Text(
                    text = "AB:" + vehicle.arcade_br,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,// Configura el número máximo de líneas
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(vehicle.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = vehicle.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
