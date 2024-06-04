package com.example.warthundervehicles.ui.screens.detail

import NewRemoteVehicle
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.data.remote.models.VehicleItem
import com.example.warthundervehicles.navigation.Routes
import com.example.warthundervehicles.utils.Resource
import com.example.warthundervehicles.utils.customToList
import com.example.warthundervehicles.utils.getColorForTier
import com.example.warthundervehicles.utils.getUnitForProperty
import com.example.warthundervehicles.utils.parsePropertiesToName
import com.example.warthundervehicles.utils.parseTypeToColor
import toVehicle
import transformCountry
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getColorForTier(miVehiculo.data?.era))
            .padding(bottom = 16.dp)
        // .border(3.dp, Color.Green),
        //  contentAlignment = Alignment.Center
    ) {

        DetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
            // .border(8.dp, Color.Green)
        )

        VehicleDetailStateWrapper(
            miVehiculo = miVehiculo,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                // .border(5.dp, Color.Magenta)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
            loadingModifier = Modifier
                .size(100.dp)
                .padding(
                    top = topPadding + vehicleImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
  //      VehiculoArmamento(miVehiculo = miVehiculo)
    //    SearchButton(miVehiculo, viewModel, onClick = { /*TODO*/ })
        Buscador(miVehiculo, viewModel,navController)
    }
    //   Imagen del Vehiculo /////
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (miVehiculo is Resource.Success) {
            val vehicle = miVehiculo.data?.toVehicle()
            vehicle?.imageUrl.let { url ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    //    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(vehicleImageSize)
                        .offset(y = -(topPadding + 0.dp))
                    // .border(3.dp, Color.Yellow)
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun SearchButton(
    miVehiculo: Resource<NewRemoteVehicle>,
    viewModel: DetailViewmodel,
    onClick: () -> Unit
) {
    var showVehicles by remember { mutableStateOf(false) }

    var listaNombresVehiculos by remember { mutableStateOf(emptyList<String>()) }
    val listaVehiculosSeleccionables = rememberUpdatedState(
        miVehiculo.data?.let { miVehiculo ->
            // viewModel.VehiculosEnemigos(it)
            // Llama a la función VehiculosEnemigos
            viewModel.VehiculosEnemigos(vehiculo = miVehiculo) { listaVehiculos ->
                // Actualiza el estado con la lista de nombres de vehículos
                listaNombresVehiculos = listaVehiculos
            }
        }
    ).value

    Button(
        onClick = {
            onClick()
            showVehicles = true
        },
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(5.dp)
        )
    }

    if (showVehicles) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
//            items(listaVehiculosSeleccionables) { index ->
//                val vehicle = listaNombresVehiculos[index]
//                VehicleItem(vehicle)
            //           }
        }

    }
}

@Composable
private fun Buscador(
    miVehiculo: Resource<NewRemoteVehicle>,
    viewModel: DetailViewmodel,
    navController: NavController,
    //  modifier: Modifier
) {
    var listaNombresVehiculos by remember { mutableStateOf(emptyList<String>()) }
    //  val listaVehiculosSeleccionables = miVehiculo.data?.let { viewModel.VehiculosEnemigos(it) }
    // Llama a la función suspendida VehiculosEnemigos y espera a que termine
    val listaVehiculosSeleccionables = rememberUpdatedState(
        miVehiculo.data?.let { miVehiculo ->
            // viewModel.VehiculosEnemigos(it)
            // Llama a la función VehiculosEnemigos
            viewModel.VehiculosEnemigos(vehiculo = miVehiculo) { listaVehiculos ->
                // Actualiza el estado con la lista de nombres de vehículos
                listaNombresVehiculos = listaVehiculos
            }
        }
    ).value

    // if (!listaNombresVehiculos.isNullOrEmpty()) {
    CarWithListBox(
        modifier = Modifier
            .fillMaxWidth()
            // .fillMaxHeight(0.2f)
            .fillMaxSize()
        //   .align(Alignment.BottomCenter)
        // .border(5.dp, Color.Blue),
        ,
//            modifier = Modifier
//                //   .fillMaxSize()
//                .wrapContentSize(),
        listaNombresVehiculos,
        onItemSelected = { selectedValue ->
            // Manejar el valor seleccionado aquí
            Log.i("MyTag", "Valor seleccionado::: $selectedValue")
            navController.navigate(Routes.VersusScreen.route + "/${viewModel.selectedVehicle.value?.identifier}/${selectedValue}")
            //  println("Valor seleccionado: $selectedValue")
        }
    )
    //   }
}

@Composable
fun CarWithListBox(
    modifier: Modifier,
    possibleValues: List<String>,
    onItemSelected: (String) -> Unit
) {
    var selectedValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    Log.i("MyTag", "Valores: $possibleValues")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Select an item:")
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                possibleValues.forEach { value ->
                    DropdownMenuItem(
                        text = {
                            Text(value)
                        },
                        onClick = {
                            selectedValue = value
                            onItemSelected(value)
                            expanded = false
                        }
                    )
                }
            }
            Text(text = "Selected item: $selectedValue")
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
                    .wrapContentSize()
                // .border(3.dp, Color.DarkGray)
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
 * Seccion principal; Id y Nombre y resto de secciones (181)
 */
@Composable
fun VehicleDetailSection(miVehiculo: VehicleItem, modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
        // .border(5.dp, Color.Green)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically,
            ) {
            val bandera = transformCountry(miVehiculo.country)
            Image(
                painter = painterResource(bandera),
                contentDescription = "Descripción de la imagen",
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp) // Ajusta la altura según tus necesidades
            )
            Text(
                text = miVehiculo.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                },
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        VehicleTypeSection(miVehiculo)
        VehiculoBaseProperties(miVehiculo = miVehiculo)
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
                    .background(parseTypeToColor(type))
                    .height(35.dp)
                //  // .border(3.dp, Color.Red)
            ) {
                Row() {
                    Text(
                        text = type.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                    )
//                    Text(
//                        text = "Rank: " + miVehiculo.era,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        textAlign = TextAlign.End,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
                }
            }
        }
    }
}

@Composable
fun VehicleProperties(
    propertiName: String,
    propertiValue: String,

    propertiUnits: String,
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
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = propertiValue,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Text(
                text = propertiUnits.padEnd(4),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

// 383
@Composable
fun VehiculoBaseProperties(
    miVehiculo: VehicleItem,
    animDelayPerItem: Int = 100
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 0.dp)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            //   // .border(3.dp, Color.Red)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .background(Color.Gray)
            // .border(3.dp, Color.Blue)
            //   // .border(3.dp, Color.DarkGray)
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
                "%.1f".format(property.getter.call(aerodynamics))
            } else {
                property.getter.call(aerodynamics).toString()
            }
            var propertyUnit = getUnitForProperty(property.name)
            Log.i(
                "MyTag",
                "properties ${property.name} $propertyName = $propertyValue ==$propertyUnit"
            )
            if (property.name == "max_speed_at_altitude") {
                propertyName = propertyName + propertyValue + "m"
                propertyValue = miVehiculo.vel_max.toString()
            }

            // VehicleProperties(propertyName, propertyValue, PROPERTYUNITS[index])
            VehicleProperties(propertyName, propertyValue, propertyUnit)

            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}

@Composable
fun VehiculoArmamento(
    miVehiculo: Resource<NewRemoteVehicle>,
    animDelayPerItem: Int = 100
) {
    if (miVehiculo is Resource.Success) {
        val vehicle = miVehiculo.data?.toVehicle()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 16.dp, 0.dp, 0.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                //   // .border(3.dp, Color.Red)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .background(Color.Gray)
                // .border(3.dp, Color.Blue)
                //   // .border(3.dp, Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = "Armamento:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Log.i("Mytag", "**** vehicle ***}")
            if (vehicle != null) {
                Log.i("Mytag", "**** ${vehicle.weapons}")
                Log.i("Mytags", "custo**** ${vehicle.customizable_presets}")

            }
            /*
            val aerodynamics = vehicle!!.weapons!!
            aerodynamics::class.memberProperties.forEachIndexed { index, property ->
                var propertyName = parsePropertiesToName(property.name)
                var propertyValue = if (property.returnType.toString() == "kotlin.Double") {
                    "%.1f".format(property.getter.call(aerodynamics))
                } else {
                    property.getter.call(aerodynamics).toString()
                }
                var propertyUnit = getUnitForProperty(property.name)
                Log.i(
                    "MyTag",
                    "properties ${property.name} $propertyName = $propertyValue ==$propertyUnit"
                )
                if (property.name == "max_speed_at_altitude") {
                    propertyName = propertyName + propertyValue + "m"
                    propertyValue = vehicle.vel_max.toString()
                }

                // VehicleProperties(propertyName, propertyValue, PROPERTYUNITS[index])
                VehicleProperties(propertyName, propertyValue, propertyUnit)

                Spacer(modifier = Modifier.height(3.dp))

             */
        }
    }
}







