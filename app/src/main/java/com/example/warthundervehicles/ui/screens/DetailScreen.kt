package com.example.warthundervehicles.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.utils.customToString
import retrofit2.Response
import java.util.Locale

@Composable
fun DetailScreen(navController: NavController, identifier: String) {
    val viewModel = hiltViewModel<MyViewModel>()
    val vehiculo by produceState<Response<RemoteVehiclesItem>?>(initialValue = null) {
        value = viewModel.getVehicle(identifier)
    }
    val vehicle = viewModel.selecVehicle.value
    Column {
        if (vehicle != null) {
            VehicleSelectedCard(vehicle = vehicle)
        }
        VehicleDetails(viewModel)
    }
}

@Composable
fun VehicleDetails(viewModel: MyViewModel) {
    val groupedProperties by viewModel.groupedProperties.observeAsState(emptyMap())
    val groupVisibilityMap = remember { mutableStateMapOf<String, MutableState<Boolean>>() }

    LazyColumn {
        groupedProperties.forEach { (groupName, properties) ->
            // Crear un estado para manejar la visibilidad del grupo
            val isGroupVisible = groupVisibilityMap.getOrPut(groupName) { mutableStateOf(false) }
            // Aquí puedes mostrar cada grupo como un botón
            item {
                Button(
                    onClick = { isGroupVisible.value = !isGroupVisible.value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = if (isGroupVisible.value) "Hide $groupName" else "Show $groupName")
                }
            }

            // Mostrar propiedades solo si el grupo está visible
            if (isGroupVisible.value) {
                properties.forEach { (propertyName, propertyValue) ->
                    item {
                        // Aquí puedes mostrar cada propiedad del grupo
                        Text(text = "$propertyName: $propertyValue")
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleSelectedCard(vehicle: VehicleItem) {
    val brush = getGradientBrushForTier(vehicle.tier)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 12.dp)
            // .background(brush)
            //   .clickable { onItemClick(vehicle) }
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
                    text = "AB:" + vehicle.arcadeBr,
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


