package com.example.warthundervehicles.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
//import com.example.warthundervehicles.Cabecera
//import com.example.warthundervehicles.Name
//import com.example.warthundervehicles.VehicleList
import com.example.warthundervehicles.data.models.VehicleItem
import com.example.warthundervehicles.navigation.Routes
import com.example.warthundervehicles.utils.Constants
import com.example.warthundervehicles.utils.customToString
import com.example.warthundervehicles.utils.transformSiluetas
import com.example.warthundervehicles.R
import kotlinx.coroutines.launch
import transformCountry
import java.util.Locale


@Composable
fun HomeScreen(
    navController: NavHostController, viewModel: MyViewModel,
               ) {
  //  val viewModel = hiltViewModel<MyViewModel>()
    val vehicles = viewModel.listaVehiculos.value

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BodyContent(navController,vehicles)
    }
}

@Composable
fun BodyContent(navController: NavHostController, vehicles: List<VehicleItem>) {
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
        Name()
        Cabecera()
        VehicleList(navController,vehicles = vehicles) { selectedVehicle ->
            Log.i("MyTag","Clic en ${selectedVehicle.identifier}")
            navController.navigate(Routes.DetailScreen.route+"/${selectedVehicle.identifier}")
        }
    }
}
@Composable
fun Name() {
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
fun Cabecera() {
    Column() {
        Tier()
        Spacer(modifier = Modifier.height(8.dp))
        Country()
        Spacer(modifier = Modifier.height(10.dp))
        Siluetas()
    }
}

@Composable
fun Siluetas() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SiluetaImages()
    }
}

@Composable
fun SiluetaImages() {
    val viewModel = hiltViewModel<MyViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val aspectRatio = 330f / 131f
    for (silueta in Constants.LIST_TYPE_VEHICLE) {
        val armaSilueta = transformSiluetas(silueta)
        Image(
            painter = painterResource(armaSilueta),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                //     .fillMaxWidth()
                .width(80.dp)
                //      .height(60.dp)
                .aspectRatio(aspectRatio)// Ajusta la altura según tus necesidades
                .border(2.dp, Color.White)
                .clickable {
                    Log.i("MyTag", "clickado Silueta $silueta ")
                    //  viewModel.limpiarLista()
                    //  if (sele)
                    //  coroutineScope.launch {
                    viewModel.getAllVehiclesArma(silueta)
                    //  }
                }
        )


    }
}

@Composable
private fun Country() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Banderas()
    }
}

@Composable
private fun Tier() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TIER ",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        RepeatSquares()
    }
}

@Composable
fun Banderas() {
    val viewModel = hiltViewModel<MyViewModel>()
    val coroutineScope = rememberCoroutineScope()
    for (country in Constants.LIST_COUNTRY) {
        val bandera = transformCountry(country)
        Image(
            painter = painterResource(bandera),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                // .fillMaxWidth()
                .width(34.dp)
                .height(25.dp) // Ajusta la altura según tus necesidades
                .border(2.dp, Color.White)
                .clickable {
                    Log.i("MyTag", "clickado Country $country ")
                    viewModel.limpiarLista()
                    //  if (sele)
                    coroutineScope.launch {
                        viewModel.getAllVehiclesCountry(country)
                    }
                }
        )
        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
fun RepeatSquares() {

    var selectedTier by remember { mutableStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(8) { tier ->
            Square(tier + 1,
                isSelected = tier + 1 == selectedTier,
                onSquareClicked = { clickedTier ->
                    selectedTier = if (selectedTier == clickedTier) 0 else clickedTier
                })
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Composable
fun Square(tier: Int, isSelected: Boolean, onSquareClicked: (Int) -> Unit) {
    val viewModel = hiltViewModel<MyViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val brush = getGradientBrushForTier(tier)
    val colTier = getColorForTier(tier)
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
                viewModel.limpiarLista()
                coroutineScope.launch {
                    viewModel.getAllVehiclesRank(tier)
                }
                onSquareClicked(tier)
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = tier.toString(),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp, 0.dp)
        )
    }
}

@Composable
fun VehicleList(navController: NavHostController, vehicles: List<VehicleItem>, onItemClick: (VehicleItem) -> Unit) {
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
    val brush = getGradientBrushForTier(vehicle.tier)
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

fun getGradientBrushForTier(tier: Int): Brush {

    return when (tier) {
        1 -> Brush.verticalGradient(listOf(Color.Green, Color(130, 0, 0, 0xFF)))
        2 -> Brush.verticalGradient(listOf(Color.Yellow, Color(130, 0, 0, 0xFF)))
        3 -> Brush.verticalGradient(listOf(Color.Red, Color(130, 0, 0, 0xFF)))
        4 -> Brush.verticalGradient(listOf(Color.Blue, Color(130, 0, 0, 0xFF)))
        5 -> Brush.verticalGradient(listOf(Color.Magenta, Color(130, 0, 0, 0xFF)))
        6 -> Brush.verticalGradient(listOf(Color.Gray, Color(130, 0, 0, 0xFF)))


        // Añade más casos según tus necesidades
        else -> Brush.verticalGradient(listOf(Color.White, Color(128, 128, 128, 128)))
    }
}

fun getColorForTier(tier: Int): Color {
    return when (tier) {
        1 -> Color.Green
        2 -> Color.Yellow
        3 -> Color.Red
        4 -> Color.Blue
        5 -> Color.Magenta
        6 -> Color.Gray
        7 -> Color.Cyan
        8 -> Color.DarkGray
        else -> Color.Black
    }
}
