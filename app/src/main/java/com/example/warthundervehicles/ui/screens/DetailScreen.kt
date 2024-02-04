package com.example.warthundervehicles.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
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
import com.example.warthundervehicles.data.remote.apimodels.Aerodynamics
import com.example.warthundervehicles.data.remote.apimodels.RemoteVehiclesItem
import com.example.warthundervehicles.utils.Constants.LIST_PROPERTIES_VEHICLE
import com.example.warthundervehicles.utils.customToString
import retrofit2.Response
import java.util.Locale
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

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


@Composable
fun VehicleDetails(remoteVehiclesItem: RemoteVehiclesItem) {
    LazyColumn {
        remoteVehiclesItem.javaClass.kotlin.declaredMemberProperties.forEach { property ->
            val propertyName = property.name
            val propertyValue = property.get(remoteVehiclesItem)

            item {
                when {
                    propertyValue is Aerodynamics -> {
                        // Si la propiedad es de tipo Aerodynamics, muestra una lista desplegable
                        var expanded by remember { mutableStateOf(false) }
                        var selectedItem by remember { mutableStateOf<Aerodynamics?>(null) }

                        Column {
                            BasicTextField(
                                value = selectedItem?.toString() ?: "Select an item",
                                onValueChange = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable {
                                        Log.i("MyTag", "expanded $expanded")
                                        expanded = true
                                    }
                                    .background(MaterialTheme.colorScheme.primary)
                            )

                            if (expanded) {
                                Log.i("MyTag", "dentro expanded $expanded")

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    LazyColumn {
                                        Log.i("MyTag", "dentro  LazyColumn")
                                        selectedItem?.let { aerodynamics ->
                                            aerodynamics::class.declaredMemberProperties.forEach { classProperty ->
                                                item {
                                                    Log.i(
                                                        "MyTag",
                                                        "dentro item ${classProperty.name}"
                                                    )
                                                    Text(
                                                        text = "-_________ ${classProperty.name}: ${
                                                            classProperty.call(
                                                                aerodynamics
                                                            )
                                                        }",
                                                        //  modifier = Modifier.padding(16.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        // Si no es de tipo Aerodynamics, muestra el nombre y el valor de la propiedad en un Text
                        Text(text = "$propertyName: $propertyValue")
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleDetails3(remoteVehiclesItem: RemoteVehiclesItem) {
    LazyColumn {
        remoteVehiclesItem.javaClass.kotlin.declaredMemberProperties.forEach { property ->
            val propertyName = property.name
            val propertyValue = property.get(remoteVehiclesItem)

            item {
                when {

                    propertyName in LIST_PROPERTIES_VEHICLE -> {
                        Log.i("MyTag", "nombre:$propertyName , $propertyValue")
                        // Si el nombre de la propiedad está en la lista, mostrar un botón con el nombre de la clase
                        Button(
                            onClick = {
                                Log.i("MyTag", "click:$propertyName , $propertyValue ")
                                showObjectDetails(propertyValue)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = propertyName)
                        }
                    }

                    else -> {
                        Log.i("MyTag", "else nombre:$propertyName")
                        Text(text = "$propertyName: $propertyValue")
                    }
                }
            }
        }
    }
}

private fun <T : Any> showObjectDetails1(obj: T?) {
    obj?.let {
        obj::class.declaredMemberProperties.forEach { property ->
            val propertyName = property.name
            val propertyValue = property.call(obj)
            Log.i("MyTag", "$propertyName: $propertyValue")
        }
    }
}


private fun <T : Any> showObjectDetails(obj: T?) {
    obj?.let {
        obj::class.declaredMemberProperties.forEach { property ->
            val propertyName = property.name
            val propertyValue = try {
                property.isAccessible = true // Ajustar accesibilidad del campo
                property.call(obj)
            } catch (e: Exception) {
                // Manejar la excepción si ocurre algún problema al acceder al campo
                Log.e("MyTag", "Error al acceder al campo $propertyName: ${e.message}")
                null
            } finally {
                property.isAccessible = false // Restaurar accesibilidad del campo
            }
            Log.i("MyTag", "$propertyName: $propertyValue")
        }
    }
}


//@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VehicleDetails2(remoteVehiclesItem: RemoteVehiclesItem) {

    var expanded by remember { mutableStateOf(false) }
    //   var selectedItem by remember { mutableStateOf<Aerodynamics?>(null) }

    Column {
        Button(
            onClick = {
                expanded = true
                Log.i("MyTag", "expanded $expanded")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Select an item")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Log.i("MyTag", "dentro expanded")

            Log.i("MyTag", "dentro  selectedItem?.let  $ selectedItem?.let ")
            LazyColumn {
                Aerodynamics::class.declaredMemberProperties.forEach { classProperty ->
                    item {
                        Log.i("MyTag", "dentro item $expanded")
                        Text(
                            text = "***${classProperty.name}:}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
    //  }
}

