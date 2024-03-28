package com.example.warthundervehicles.ui.screens.detail

import NewRemoteVehicle
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.data.remote.models.VehicleItem

import com.example.warthundervehicles.utils.Resource
import com.example.warthundervehicles.utils.customToList
import com.example.warthundervehicles.utils.getColorForTier
import com.example.warthundervehicles.utils.parsePropertiesToName
import toVehicle
import java.util.Locale
import kotlin.reflect.full.memberProperties


@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewmodel,
    identifier: String,
    topPadding: Dp = 20.dp,
    vehicleImageSize: Dp = 300.dp
) {
    val miVehiculo = produceState<Resource<NewRemoteVehicle>>(initialValue = Resource.Loading()) {
        value = viewModel.getVehicle(identifier)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getColorForTier(miVehiculo.data?.era))
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        DetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
            //  .border(3.dp, Color.Green)
        )

        VehicleDetailStateWrapper(
            miVehiculo = miVehiculo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + vehicleImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                //  .border(3.dp, Color.Magenta)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + vehicleImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            //   .border(3.dp, Color.Cyan)
        )
        //   Imagen del Vehiculo /////

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()

        ) {
            if (miVehiculo is Resource.Success) {
                val vehicle = miVehiculo.data?.toVehicle()
                //  miVehiculo.data?.images?.image.let {
                vehicle?.imageUrl.let {
                    if (it != null) {
                        if (vehicle != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    //  .data("http://" + it)
                                    .data(vehicle.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                //    placeholder = painterResource(R.drawable.placeholder),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .size(vehicleImageSize)
                                    .offset(y = -(topPadding + 0.dp))
                                //     .border(3.dp, Color.Yellow)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetailTopSection(navController: NavController, modifier: Modifier) {

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable { navController.popBackStack() }
        )
    }
}

@Composable
fun VehicleDetailStateWrapper(
    miVehiculo: Resource<NewRemoteVehicle>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier
) {
    when (miVehiculo) {
        is Resource.Success -> {
            Log.i("MyTag", "detail miVehiculo Success: ${miVehiculo.data}")
            val vehicle = miVehiculo.data?.toVehicle()
            VehicleDetailSection(
                miVehiculo = vehicle!!,
                modifier = modifier
                //  .offset(y = (-20).dp)
            )
        }

        is Resource.Error -> {
            Log.i("MyTag", "detail miVehiculo Error")
            Text(
                text = miVehiculo.message!!,
                color = Color.Red,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        is Resource.Loading -> {
            Log.i("MyTag", "detail miVehiculo Loading ")
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}

/**
 * Seccion principal; Id y Nombre y resto de secciones
 */
@Composable
fun VehicleDetailSection(miVehiculo: VehicleItem, modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            //  .offset(y = 20.dp)
            .verticalScroll(scrollState)
        //   .border(3.dp, Color.Red)

    ) {
        Text(
            text = miVehiculo.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            },
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        VehicleTypeSection(miVehiculo)
        VehiculoBaseProperties(miVehiculo = miVehiculo)
//            pokemonWeight = miVehiculo.weight,
//            pokemonHeight = miVehiculo.height
//        )
//        PokemonBaseStats(miVehiculo = miVehiculo)
    }
}

@Composable
fun VehicleTypeSection(miVehiculo: VehicleItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for (type in miVehiculo.type.customToList()) {
            Log.i("MyTag", "tipo $type")
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    // .background(parseTypeToColor(type))
                    .height(35.dp)
                    .border(3.dp, Color.Red)
            ) {
                Text(
                    text = type.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
    }

}

@Composable
fun VehicleProperties(
    propertiName: String,
    propertiValue: String,
//    statMaxValue: Int,
//    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            2f
            //    statValue / statMaxValue.toFloat()
        } else 0f,
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
                //  .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = propertiName,
                fontWeight = FontWeight.Bold
            )
            Text(
                //   text = (curPercent.value * statMaxValue).toInt().toString(),
                text = propertiValue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun VehiculoBaseProperties(
    miVehiculo: VehicleItem,
    animDelayPerItem: Int = 100
) {
    //  val maxBaseStat = remember {
    //    pokemonInfo.stats.maxOf { it.base_stat }
    // }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 0.dp)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            //   .border(3.dp, Color.Red)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .background(Color.Gray)
            //   .border(3.dp, Color.DarkGray)
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

        aerodynamics::class.memberProperties.forEach { property ->
            var propertyName = parsePropertiesToName(property.name)
            var propertyValue = property.getter.call(aerodynamics).toString()
            Log.i("MyTag", "properties ${property.name} $propertyName = $propertyValue ")
if (property.name=="max_speed_at_altitude") {
    propertyName= propertyName+propertyValue
    propertyValue=miVehiculo.vel_max.toString()
}
            VehicleProperties(propertyName,propertyValue)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}





