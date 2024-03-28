package com.example.warthundervehicles.ui.screens.home


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warthundervehicles.R
import com.example.warthundervehicles.data.remote.models.VehicleItem
import com.example.warthundervehicles.navigation.Routes
import com.example.warthundervehicles.utils.Constants
import com.example.warthundervehicles.utils.customToString
import com.example.warthundervehicles.utils.getGradientBrushForTier
import com.example.warthundervehicles.utils.transformSiluetas
import kotlinx.coroutines.launch
import toMachine
import transformCountry
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewmodel,
) {
    val vehicles = viewModel.listaVehiculos.value

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BodyContent(navController, vehicles, viewModel)
    }
}

@Composable
fun BodyContent(
    navController: NavHostController,
    vehicles: List<VehicleItem>,
    viewModel: HomeViewmodel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Black),
                    start = Offset.Infinite,
                    end = Offset.Zero
                )
            )
    ) {
        Title()
        Menu(viewModel)

        VehicleList(navController, vehicles = vehicles) { selectedVehicle ->
            Log.i("MyTag", "Clic en ${selectedVehicle.identifier}")
            viewModel.inserMachines(selectedVehicle.toMachine())
            navController.navigate(Routes.DetailScreen.route + "/${selectedVehicle.identifier}")
        }
    }
}

@Composable
fun Title() {
    val aspectRatio = 378f / 179f
    Image(
        painter = painterResource(id = R.drawable.ic_warthunder),
        contentDescription = "WarThunder",
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
            .aspectRatio(aspectRatio)
        //  .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun Menu(viewModel: HomeViewmodel) {
    Column() {
        Tier(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        Country(viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Siluetas(viewModel)
    }
}

@Composable
fun Siluetas(viewModel: HomeViewmodel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SiluetaImages(viewModel)
    }
}

@Composable
fun SiluetaImages(viewModel: HomeViewmodel) {
    val coroutineScope = rememberCoroutineScope()
    var selectedArmy by remember { mutableStateOf("") }
    val aspectRatio = 330f / 131f
    for (army in Constants.LIST_TYPE_VEHICLE) {
        val armaSilueta = transformSiluetas(army)
        Image(
            painter = painterResource(armaSilueta),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                //     .fillMaxWidth()
                .width(80.dp)
                //      .height(60.dp)
                .aspectRatio(aspectRatio)// Ajusta la altura según tus necesidades

                .border(
                    width = 3.dp,
                    color = if (selectedArmy == army) Color.Yellow else Color.White
                )
                .clickable {
                    Log.i("MyTag", "clickado Silueta $army ")
                    viewModel.limpiarLista()

                    coroutineScope.launch {
                        viewModel.clickedGetVehiclesArmy(army)
                        selectedArmy = army
                    }
                }
        )


    }
}

@Composable
private fun Country(viewModel: HomeViewmodel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Banderas(viewModel)
    }
}

@Composable
private fun Tier(viewModel: HomeViewmodel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TIER ",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        RepeatSquares(viewModel)
    }
}

@Composable
fun RepeatSquares(viewModel: HomeViewmodel) {

    var selectedTier by remember { mutableStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(9) { tier ->
            Square(
                tier,
                isSelected = tier == selectedTier,
                viewModel
            ) { clickedTier ->
                selectedTier = if (selectedTier == clickedTier) 0 else clickedTier
            }
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Composable
fun Square(
    tier: Int,
    isSelected: Boolean,
    viewModel: HomeViewmodel,
    onSquareClicked: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val brush = getGradientBrushForTier(tier)
//    val colTier = getColorForTier(tier)
    Box(
        modifier = Modifier
            .size(26.dp)
            .background(
                brush = brush,
                shape = RectangleShape
            )
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = Color.Yellow
            )
            .clickable {
                Log.i("MyTag", "clickado $tier : $isSelected")
                viewModel.tierSelected = tier
                Log.i("MyTag", "clickado viewModel.tierSelected $viewModel.tierSelected")
                viewModel.limpiarLista()
                coroutineScope.launch {
                    viewModel.clickedGetVehiclesRank(tier)
                }
                onSquareClicked(tier)
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = if (tier == 0) "All" else "$tier",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp, 0.dp)
        )
    }
}


@Composable
fun Banderas(viewModel: HomeViewmodel) {
    val coroutineScope = rememberCoroutineScope()
    var selectedCountry by remember { mutableStateOf("") }
//    var isSelected:Boolean=false
    for (country in Constants.LIST_COUNTRY) {
        val bandera = transformCountry(country)
        Image(
            painter = painterResource(bandera),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                // .fillMaxWidth()
                .width(34.dp)
                .height(25.dp) // Ajusta la altura según tus necesidades
                .border(
                    width = 2.dp,
                    color = if (selectedCountry == country) Color.Yellow else Color.White
                )
                .clickable {
                    Log.i("MyTag", "clickado Country $country ")
                    viewModel.limpiarLista()
                    Log.i("MyTag", " viewModel.limpiarLista()")
                    coroutineScope.launch {
                        viewModel.clickedGetVehiclesCountry(country)
                    }
                    selectedCountry = country // Marca la bandera como seleccionada
                    viewModel.countrySelected = country
                }
        )
        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
fun VehicleList(
    navController: NavHostController,
    vehicles: List<VehicleItem>,
    onItemClick: (VehicleItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        items(vehicles) { vehicle ->
            VehicleCard(vehicle = vehicle) { clickedVehicle ->
                Log.i("MyTag", "Click en ${clickedVehicle.identifier}")
                onItemClick(clickedVehicle)
            }
        }
    }
}

@Composable
fun VehicleCard(vehicle: VehicleItem, onItemClick: (VehicleItem) -> Unit) {
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




