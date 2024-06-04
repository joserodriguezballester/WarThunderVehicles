package com.example.warthundervehicles.ui.screens.versus

import NewRemoteVehicle
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.data.remote.models.VehicleItem
import com.example.warthundervehicles.ui.screens.detail.VehicleProperties
import com.example.warthundervehicles.utils.Resource
import com.example.warthundervehicles.utils.customToList
import com.example.warthundervehicles.utils.getUnitForProperty
import com.example.warthundervehicles.utils.parsePropertiesToName
import com.example.warthundervehicles.utils.parseTypeToColor
import toVehicle
import java.util.Locale
import kotlin.reflect.full.memberProperties


@Composable
fun VersusScreen(
    navController: NavController,
    viewModel: VersusViewmodel,
    vehiculo1: String,
    vehiculo2: String,
) {

    val miVehiculoResource = rememberVehicleState(viewModel, vehiculo1, 1)
    val vehiculoRivalResource = rememberVehicleState(viewModel, vehiculo2, 2)

    val miVehiculo = (miVehiculoResource as? Resource.Success)?.data?.toVehicle()
    val vehiculoRival = (vehiculoRivalResource as? Resource.Success)?.data?.toVehicle()
    Column {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            //   verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                //  verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                VehicleName(miVehiculo)
                VehicleImage(vehicle = miVehiculo)
                VehicleTypeSection(vehicle = miVehiculo)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VehicleName(vehiculoRival)
                VehicleImage(vehicle = vehiculoRival)
                VehicleTypeSection(vehicle = vehiculoRival)
            }
        }
        VehiculoBaseProperties(miVehiculo, vehiculoRival)
    }
}

@Composable
fun VehicleName(vehicle: VehicleItem?) {
    if (vehicle != null) {
        Box(
        //    Modifier.border(3.dp, Color.Green)
        ) {


            Text(
                text = vehicle.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,

                )
        }
    }
}

@Composable
fun rememberVehicleState(
    viewModel: VersusViewmodel,
    vehiculo: String,
    index: Int
): Resource<NewRemoteVehicle> {
    return produceState<Resource<NewRemoteVehicle>>(initialValue = Resource.Loading()) {
        value = viewModel.getVehicle(vehiculo, index)
    }.value
}

@Composable
fun VehicleImage(vehicle: VehicleItem?) {
    val vehicleImageSize: Dp = 150.dp
    if (vehicle != null && vehicle.imageUrl != null) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(vehicle.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(vehicleImageSize)
         //       .border(3.dp, Color.Red)
        )
    } else {
        Box(
            modifier = Modifier.size(vehicleImageSize),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun VehicleTypeSection(vehicle: VehicleItem?) {
    if (vehicle != null) {
        val vehicleTypeHeight: Dp = 250.dp
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                //    .padding(16.dp)
                //   .height(vehicleTypeHeight)
                //  .fillMaxWidth()
          //      .border(3.dp, Color.Green)
        ) {
            for (type in vehicle.type.customToList()) {
                Log.i("MyTag", "tipo $type")
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .clip(CircleShape)
                        .background(parseTypeToColor(type))
                        .height(35.dp)
                        //  // .border(3.dp, Color.Red)
                        .padding(horizontal = 18.dp)
                ) {
                    Row() {
                        Text(
                            text = type.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            } + " " + vehicle.arcadeBr,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    }
                }
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun VehiculoBaseProperties(
    miVehiculo: VehicleItem?,
    vehiculoRival: VehicleItem?
) {
    if (miVehiculo != null && vehiculoRival != null) {
        Column(
            modifier = Modifier
                .padding(10.dp, 16.dp, 10.dp, 16.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .background(Color.Gray)
                .border(3.dp, Color.Blue)
                .padding(16.dp)
        ) {
            Text(
                text = "Caracteristicas:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            val aerodynamics = miVehiculo.aerodynamics!!
            aerodynamics::class.memberProperties.forEachIndexed { index, property ->
                var propertyName = parsePropertiesToName(property.name)
                var propertyValue = if (property.returnType.toString() == "kotlin.Double") {
                    "%.1f".format(property.getter.call(miVehiculo.aerodynamics!!))
                } else {
                    property.getter.call(miVehiculo.aerodynamics!!).toString()
                }
                var propertyValueRival = if (property.returnType.toString() == "kotlin.Double") {
                    "%.1f".format(property.getter.call(vehiculoRival.aerodynamics!!))
                } else {
                    property.getter.call(vehiculoRival.aerodynamics!!).toString()
                }
                val propertyUnit = getUnitForProperty(property.name)
                Log.i(
                    "MyTag",
                    "properties ${property.name} $propertyName = $propertyValue ==$propertyUnit"
                )
                if (property.name == "max_speed_at_altitude") {
                    propertyName = propertyName + propertyValue + " / " + propertyValueRival + " m"
                    propertyValue = miVehiculo.vel_max.toString()
                    propertyValueRival = vehiculoRival.vel_max.toString()
                }

                // VehicleProperties(propertyName, propertyValue, PROPERTYUNITS[index])
                VehiclePropertiesRival(
                    propertyName,
                    propertyValue,
                    propertyValueRival,
                    propertyUnit
                )

                Spacer(modifier = Modifier.height(3.dp))
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun VehiclePropertiesRival(
    propertyName: String,
    propertyValue: String,
    propertyValueRival: String,
    propertyUnits: String,
    height: Dp = 50.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) 2f else 0f,
        animationSpec = tween(animDuration, animDelay)
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(getBackgroundColor(propertyName, propertyValue, propertyValueRival))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            PropertyNameRow(propertyName, propertyUnits)
            PropertyValueRow(propertyValue, propertyValueRival, curPercent.value)
        }
    }
}

@Composable
private fun getBackgroundColor(
    propertyName: String,
    propertyValue: String,
    propertyValueRival: String
): Color {
    val value = propertyValue.toDouble()
    val rivalValue = propertyValueRival.toDouble()
    return when {
        propertyName == "Tiempo de giro" -> {
            when {
                value < rivalValue -> Color.Green
                value == rivalValue -> Color.Yellow
                else -> Color.LightGray
            }
        }
        value > rivalValue -> Color.Green
        value == rivalValue -> Color.Yellow
        else -> Color.LightGray
    }
}

@Composable
private fun PropertyNameRow(propertyName: String, propertyUnits: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = propertyName,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = propertyUnits.padEnd(4),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun PropertyValueRow(
    propertyValue: String,
    propertyValueRival: String,
    curPercent: Float
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(curPercent)
            .clip(CircleShape)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = propertyValue,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Text(
            text = propertyValueRival,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}


