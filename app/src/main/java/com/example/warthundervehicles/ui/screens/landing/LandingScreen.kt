package com.example.warthundervehicles.ui.screens.landing

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.warthundervehicles.R
import com.example.warthundervehicles.ui.theme.FontARMY
import com.example.warthundervehicles.utils.Constants
import com.example.warthundervehicles.utils.getGradientBrushForTier
import com.example.warthundervehicles.utils.transformSiluetas
import transformCountry

@Composable
fun LandingScreen(
    navController: NavHostController,
    viewModel: LandingViewmodel
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BodyContent(navController, viewModel)
    }
}

@Composable
fun BodyContent(navController: NavHostController, viewModel: LandingViewmodel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Black),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )

    ) {
        NameHeader()
        Options(viewModel)
        ButtonAceptar(navController = navController, modifier = Modifier, viewModel)


    }
}

@Composable
fun NameHeader() {
    val aspectRatio = 378f / 179f
    Image(
        painter = painterResource(id = R.drawable.ic_warthunder),
        contentDescription = "WarThunder",
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
            .aspectRatio(aspectRatio)
    )
}

@Composable
fun Options(viewModel: LandingViewmodel) {
    Column() {
        Spacer(modifier = Modifier.height(20.dp))
        Siluetas(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        Tier(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        Country(viewModel)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun Siluetas(viewModel: LandingViewmodel) {
    Row(
        modifier = Modifier
            .padding(16.dp, 2.dp),
    ) {
        Text(
            fontFamily = FontARMY,
            text = "ARMY",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
    }
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
fun SiluetaImages(viewModel: LandingViewmodel) {
    val aspectRatio = 330f / 131f
    for (army in Constants.LIST_TYPE_VEHICLE) {
        val armaSilueta = transformSiluetas(army)
        Image(
            painter = painterResource(armaSilueta),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                .width(110.dp)
                .aspectRatio(aspectRatio)// Ajusta la altura según tus necesidades
                .border(
                    width = 5.dp,
                    color = if (viewModel.selectedArmy.value == army) Color.Blue else Color.White
                )
                .clickable {
                    Log.i("MyTag", "clickado Silueta $army ")
                    viewModel.setSelectedArmy(army) // Actualizar el ViewModel con el nuevo valor seleccionado
                }
        )
    }
}

@Composable
private fun Country(viewModel: LandingViewmodel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 2.dp),
        //   verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            fontFamily = FontARMY,
            text = "COUNTRY",
            style = MaterialTheme.typography.headlineLarge,
            //  fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
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
private fun Tier(viewModel: LandingViewmodel) {
    Row(
        modifier = Modifier
            .padding(16.dp, 2.dp),
    ) {

        Text(
            fontFamily = FontARMY,
            text = "TIER ",
            style = MaterialTheme.typography.headlineLarge,
            //   fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RepeatSquares(viewModel)
    }
}

@Composable
fun RepeatSquares(viewModel: LandingViewmodel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(8) { tier ->
            Square(tier + 1, viewModel)
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Composable
fun Square(tier: Int, viewModel: LandingViewmodel) {
    val isSelected = remember { mutableStateOf(false) }
    val brush = getGradientBrushForTier(tier)
//    val colTier = getColorForTier(tier)
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                brush = brush,
                shape = RectangleShape
            )
            .border(
                width = if (isSelected.value) 3.dp else 0.dp,
                color = Color.Blue
            )
            .clickable {
                Log.i("MyTag", "clickado $tier :$isSelected:: ${(viewModel.selectedTiers)}")
                isSelected.value = !isSelected.value
                if (isSelected.value) {
                    viewModel.addSelectedTier(tier)
                } else {
                    viewModel.removeSelectedTier(tier)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        val customFontFamily = FontFamily(
            Font(R.font.capsmall)
        )
        Text(
            text = "$tier",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = customFontFamily
//            modifier = Modifier
//                .padding(4.dp, 0.dp)
        )
    }
}

@Composable
fun Banderas(viewModel: LandingViewmodel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(Constants.LIST_COUNTRY) { country ->
            val isSelected = remember { mutableStateOf(false) }
            val bandera = transformCountry(country)
            Box(
                modifier = Modifier
                    .padding(2.dp, 6.dp) // Espacio entre elementos
            ) {
                Image(
                    painter = painterResource(bandera),
                    contentDescription = "Descripción de la imagen",
                    modifier = Modifier
                        .border(
                            width = 5.dp,
                            color = if (isSelected.value) Color.Blue else Color.Transparent
                        )
                        .width(140.dp)
                        .height(60.dp) // Ajusta la altura según tus necesidades
                        .clickable {
                            Log.i("MyTag", "clickado Country $country ")
                            isSelected.value = !isSelected.value
                            if (isSelected.value) {
                                viewModel.addSelectedCountry(country)
                            } else {
                                viewModel.removeSelectedCountry(country)
                            }
                        }
                )
            }
        }
    }
}


@Composable
fun ButtonAceptar(navController: NavHostController, modifier: Modifier, viewModel: LandingViewmodel) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {  viewModel.onAcceptButtonClicked(navController) }
        ) {
            Text(
                fontFamily = FontARMY,
                style = MaterialTheme.typography.headlineLarge,
                text = "Aceptar"
            )
        }
    }
}