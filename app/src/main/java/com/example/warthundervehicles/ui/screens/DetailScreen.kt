package com.example.warthundervehicles.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.utils.customToString
import com.example.warthundervehicles.utils.getTextForProperty
import com.example.warthundervehicles.utils.getUnitForProperty
import com.example.warthundervehicles.utils.textoSinDecimales
import retrofit2.Response
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun DetailScreen(navController: NavController,viewModel: MyViewModel, identifier: String) {
  //  val viewModel = hiltViewModel<MyViewModel>()
    val vehiculo by produceState<Response<RemoteVehiclesItem>?>(initialValue = null) {
        value = viewModel.getVehicle(identifier)
    }
    val vehicle = viewModel.selecVehicle.value
    val aerodynamics = viewModel.selecVehicle.value?.aerodynamics
    viewModel.getFilteredListBySearch()
    Column {
        if (vehicle != null) {
            VehicleSelectedCard(vehicle = vehicle)
            SearchBar(searchText = viewModel.searchText,viewModel,vehicle) { newSearchText ->
                viewModel.searchText = newSearchText
                viewModel.getFilteredListBySearch()
            }
        }
//        if (viewModel.showVehicleDetails) {
//            VehicleDetails(viewModel)
//        }
//        if (viewModel.showGroupContent) {
//            GroupContent(viewModel)
//        }
        Spacer(modifier = Modifier.height(12.dp))
        // Otros componibles aquí


        LazyColumn {
            items(viewModel.listaFiltradaVehiculos.value) { vehiculo ->
                Text(text = vehiculo.name+"::"+vehiculo.arcadeBr)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }


        if (false) {
            if (aerodynamics != null) {
                aerodynamics.javaClass.declaredFields.forEach { field ->
                    field.isAccessible = true
                    var propertyName = field.name
                    var propertyValue = field.get(aerodynamics).toString().toDouble()
                    var propertyUnits = getUnitForProperty(propertyName)
                    var propertyHigh = propertyValue
                    if (field.name == "maxSpeedAtAlt") {
                        if (vehicle != null) {
                            propertyHigh = propertyValue
                            propertyValue = vehicle.engine.maxSpeed.toDouble()
                            propertyUnits = "m/s"
                        }
                    }
                    Log.i("MyTag", "Propiedad: **$propertyName**, Valor: **$propertyValue**")
                    if (propertyValue != 0.0) {
                        VehicleStat(
                            propertyName,
                            propertyValue,
                            propertyUnits,
                            propertyHigh,
                            propertyValue + 10,
                            Color.Yellow,

                            )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

    }
}

@Composable
fun SearchBar(searchText: String,viewModel: MyViewModel,vehicle: VehicleItem, onSearchTextChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Build,
            contentDescription = "Filtro",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    Log.i("MyTag", "filtrando...**")
                    // Lógica para manejar el clic en el icono de filtro
                    viewModel.getFilteredListByArcadeRB(vehicle)
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = searchText,
            onValueChange = { newSearchText ->
                onSearchTextChanged(newSearchText)
            },
            textStyle = TextStyle(color = Color.Black),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .padding(8.dp)
        )
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

@Composable
fun VehicleDetails(viewModel: MyViewModel) {
    val groupedProperties by viewModel.groupedProperties.observeAsState(emptyMap())
    LazyVerticalGrid(GridCells.Fixed(2)) {
        groupedProperties.forEach { (groupName, properties) ->
            var isVisible = true

            properties.forEach { (propertyValue) ->
                if (viewModel.isGrupoVacio(propertyValue)) {
                    isVisible = false
                } else if (viewModel.isAllPropertiesNullOrZeroOrFalse(propertyValue)) {
                    isVisible = false
                }
            }
            if (isVisible) {
                item {
                    Button(
                        onClick = {
                            viewModel.nameGrupo = groupName
                            viewModel.showGroupContent = !viewModel.showGroupContent
                            viewModel.showVehicleDetails = false
                        },
                        modifier = Modifier.padding(8.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = groupName.uppercase())
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupContent(viewModel: MyViewModel) {
    val groupName = viewModel.nameGrupo
    val properties = viewModel.groupedProperties.value?.get(groupName)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red)
            .padding(16.dp)
    ) {
        Text(
            text = groupName.uppercase(Locale.ROOT),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        properties?.forEach { (propertyName, propertyValue) ->
            Log.i("MyTag", "valor::$propertyName: $propertyValue")
            Text(text = "$propertyName: $propertyValue")
        }
    }
    Button(
        onClick = {
            viewModel.showVehicleDetails = true
            viewModel.showGroupContent = false
        },
        modifier = Modifier.padding(top = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Ocultar")
    }
}


@Composable
fun VehicleStat(
    statName: String,
    statValue: Double,
    statUnit: String,
    propertyHigh: Double,
    statMaxValue: Double,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            if (statValue != 0.0) {
                (statValue / statMaxValue).toFloat() // Aquí se convierte el resultado a Float
            } else {
                100f
            }
        } else {
            0f
        },
        animationSpec = tween(
            animDuration,
            animDelay
        ), label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = if (statName == "maxSpeedAtAlt") {
                    getTextForProperty(statName) + " " + textoSinDecimales(propertyHigh) + " m"
                } else {
                    getTextForProperty(statName)
                },
                fontWeight = FontWeight.Bold
            )
            Text(
                text = textoSinDecimales(statValue) + " " + statUnit,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun LazyColumnExample(items: List<String>) {
 //   val items = (1..100).map { "Elemento $it" } // Crea una lista de 100 elementos

    LazyColumn {
        itemsIndexed(items) { index, item ->
            if (index < 3) {
                // Muestra los primeros 3 elementos
                Text(
                    text = item,
                    modifier = Modifier
                        .padding(8.dp)
                )
            } else {
                // Oculta los elementos restantes
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}

