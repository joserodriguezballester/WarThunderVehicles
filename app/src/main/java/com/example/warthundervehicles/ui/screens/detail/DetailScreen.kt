package com.example.warthundervehicles.ui.screens.detail

import NewRemoteVehicle
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

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
import com.example.warthundervehicles.modelsApp.Opcion
import com.example.warthundervehicles.navigation.Routes
import com.example.warthundervehicles.utils.MiLog
import com.example.warthundervehicles.utils.Resource
import com.example.warthundervehicles.utils.customToList
import com.example.warthundervehicles.utils.getColorForTier
import com.example.warthundervehicles.utils.getUnitForProperty
import com.example.warthundervehicles.utils.intToRoman
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
    ) {
        DetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
            //      .border(8.dp, Color.Green),
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
        //    VehiculoArmamento(miVehiculo = miVehiculo)
        //     SearchButton(miVehiculo, viewModel, onClick = { /*TODO*/ })
        Buscador(miVehiculo, viewModel, navController)
    }

    //   Imagen del Vehiculo /////
    ImagenDelVehiculo(miVehiculo, vehicleImageSize, topPadding)
}

@Composable
private fun ImagenDelVehiculo(
    miVehiculo: Resource<NewRemoteVehicle>,
    vehicleImageSize: Dp,
    topPadding: Dp
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
        //    .fillMaxSize()
    ) {
        if (miVehiculo is Resource.Success) {
            val vehicle = miVehiculo.data?.toVehicle()
            vehicle?.imageUrl.let { url ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(vehicleImageSize)
                        .offset(y = -(topPadding + 70.dp))
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
//                       }
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
            .fillMaxSize(),
        //  .wrapContentSize(),
        //   .align(Alignment.BottomCenter)
        // .border(5.dp, Color.Blue),

        listaNombresVehiculos,
        onItemSelected = { selectedValue ->
            // Manejar el valor seleccionado aquí
            MiLog("Valor seleccionado::: $selectedValue")
            navController.navigate(Routes.VersusScreen.route + "/${viewModel.selectedVehicle.value?.identifier}/${selectedValue}")
        }
    )
}


@Composable
fun DetailTopSection(
    navController: NavController,
    modifier: Modifier,
) {
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
            MiLog("detail miVehiculo Success: ${miVehiculo.data}")
            val vehicle = miVehiculo.data?.toVehicle()
            VehicleDetailSection(
                miVehiculo = vehicle!!,
                modifier = modifier
                    .wrapContentSize()
                //     .border(5.dp, Color.DarkGray)
                //     .offset(y = (-50).dp)
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
    var selectedOption by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    val alturaOpciones = 380.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(5.dp, Color.Green)
    ) {
        VehicleNameSection(miVehiculo)
        VehicleTypeSection(miVehiculo)
        Opciones(miVehiculo, alturaOpciones) { selection -> selectedOption = selection }
        ShowOption(alturaOpciones, selectedOption)
    }
}

@Composable
private fun ShowOption(
    alturaOpciones: Dp,
    selectedOption: @Composable() (() -> Unit)?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(alturaOpciones)
        //   .border(5.dp, Color.Green)
    )
    {
        selectedOption?.invoke()
    }
}


@Composable
fun Opciones(
    miVehiculo: VehicleItem,
    alturaOpciones: Dp,
    onItemClicked: (@Composable () -> Unit) -> Unit
) {
    val opciones = remember {
        listOf(
            Opcion(
                text = "Caracteristicas",
                composable = { VehiculoBaseProperties(miVehiculo = miVehiculo, alturaOpciones) }),
            Opcion(
                text = "Armamento",
                composable = { VehiculoArmamento(miVehiculo, alturaOpciones) }),
            Opcion(
                text = "Opcion 3",
                composable = { VehiculoBaseProperties(miVehiculo = miVehiculo, alturaOpciones) }),

            Opcion(
                text = "Opcion 4",
                composable = { VehiculoBaseProperties(miVehiculo = miVehiculo, alturaOpciones) }),

            Opcion(
                text = "Opcion 4",
                composable = { VehiculoBaseProperties(miVehiculo = miVehiculo, alturaOpciones) }),
        )
    }
    var selectedOption by remember { mutableStateOf<Opcion?>(null) }
    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {
        items(opciones) { opcion ->
            val isSelected = opcion == selectedOption
            Text(
                text = opcion.text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .border(
                        2.dp,
                        if (isSelected) Color.Red else Color.Gray,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .clickable {
                        selectedOption = opcion
                        onItemClicked(opcion.composable)
                    }
            )
        }
    }
}

@Composable
fun VehicleNameSection(miVehiculo: VehicleItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        val bandera = transformCountry(miVehiculo.country)
        Image(
            painter = painterResource(bandera),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                .width(140.dp)
                .height(60.dp) // Ajusta la altura según tus necesidades
                .weight(1F)
        )
        Text(
            text = miVehiculo.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            },
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            //    color = MaterialTheme.colorScheme.onSurface,
            color = Color(0xFFFF9900),
            modifier = Modifier
                .weight(2F),

            //  .border(5.dp, Color.Green)
        )
        if (miVehiculo.name.length < 10) {
            Spacer(modifier = Modifier.weight(1F))
        }


    }
}

@Composable
fun VehicleTypeSection(miVehiculo: VehicleItem) {
    Row(
        //  verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp, 0.dp)
        //   .border(3.dp, Color.Red)
    ) {
        for (type in miVehiculo.type.customToList()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
                //        .border(3.dp, Color.Blue)
            ) {
                Row() {
                    Text(
                        text = "Rank " + intToRoman(miVehiculo.era) + " ",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp, 0.dp)
                            .alignByBaseline()
                    )
                    Text(
                        text = type.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp, 0.dp)
                            .alignByBaseline()
                    )
                    Text(
                        text = "ABr " + miVehiculo.arcadeBr,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp, 0.dp)
                            .alignByBaseline()
                    )
                }
            }
        }
    }
}


@Composable
fun VehicleProperties(
    displayName: String,
    displayValue: String,
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
                text = displayName,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = displayValue,
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

// 375
@Composable
fun VehiculoBaseProperties(
    miVehiculo: VehicleItem,
    alturaOpciones: Dp,
    animDelayPerItem: Int = 100,
) {
    val aerodynamics = miVehiculo.aerodynamics!!
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(alturaOpciones)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            //  .border(3.dp, Color.Red)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            //   .border(3.dp, Color.Red)

            .padding(16.dp)
    ) {
        aerodynamics::class.memberProperties.forEachIndexed { index, property ->
            val propertyName = parsePropertiesToName(property.name)
            val propertyValue = if (property.returnType.toString() == "kotlin.Double") {
                "%.1f".format(property.getter.call(aerodynamics))
            } else {
                property.getter.call(aerodynamics).toString()
            }
            val propertyUnit = getUnitForProperty(property.name)

            val displayName = if (property.name == "max_speed_at_altitude") {
                "$propertyName$propertyValue m"
            } else {
                propertyName
            }
            val displayValue = if (property.name == "max_speed_at_altitude") {
                miVehiculo.vel_max.toString()
            } else {
                propertyValue
            }

            VehicleProperties(displayName, displayValue, propertyUnit)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun VehiculoArmamento(
    vehicle: VehicleItem,
    alturaOpciones: Dp,
    animDelayPerItem: Int = 100,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(alturaOpciones)
            //     .padding(0.dp, 16.dp, 0.dp, 0.dp)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            //   // .border(3.dp, Color.Red)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .background(Color.Gray)
            //  .border(3.dp, Color.Blue)
            .padding(16.dp)
    ) {

        vehicle.weapons?.forEachIndexed { index, weapon ->
            MiLog("**-** ${vehicle.weapons[index].name}")
            MiLog("**** ${weapon.count} ${weapon.weapon_type} ${weapon.ammos[0].caliber * 1000} mm")
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Weapon " + (index + 1),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            val (cleanName, isTurret) = weapon.cleanWeaponName()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(
                        if (isSystemInDarkTheme()) {
                            Color(0xFF505050)
                        } else {
                            Color.LightGray
                        }
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = "${weapon.count} ${if (isTurret) "Turret " else ""}${weapon.weapon_type} ${weapon.ammos[0].caliber * 1000} mm $cleanName",
                    //       fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Composable
fun CarWithListBox(
    modifier: Modifier,
    possibleValues: List<String>,
    onItemSelected: (String) -> Unit
) {
    var selectedValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    MiLog("Valores: $possibleValues")
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







