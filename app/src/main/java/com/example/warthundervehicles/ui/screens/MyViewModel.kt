package com.example.warthundervehicles.ui.screens


//@AssistedFactory
//interface MyViewModelFactory {
//    fun create(machineDao: MachineDao): MyViewModel1
//}
//@HiltViewModel
//class MyViewModel1 @Inject constructor(private val repository: MyRepository,
//                                      @Assisted private val machineDa0: MachineDao
//) : ViewModel() {
//
//
//    lateinit var nameGrupo: String
//    private val _myListaVehiculosRemotos = mutableStateOf<List<RemoteVehicleListItem>>(emptyList())
//    val myListaVehiculosRemotos: MutableState<List<RemoteVehicleListItem>> get() = _myListaVehiculosRemotos
//
//
//    private val _listaVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
//    val listaVehiculos: MutableState<List<VehicleItem>> get() = _listaVehiculos
//
//    // solo en getAllVheicles
//    private val _listaAllVehiculos = mutableStateOf<List<VehicleItem>>(emptyList())
//    val listaAllVehiculos: MutableState<List<VehicleItem>> get() = _listaAllVehiculos
//
//    private val _selectedVehicle = mutableStateOf<RemoteVehicleListItem?>(null)
//    val selectedVehicle: MutableState<RemoteVehicleListItem?> get() = _selectedVehicle
//
//    private var _selecVehicle = mutableStateOf<VehicleItem?>(null)
//    val selecVehicle: MutableState<VehicleItem?> get() = _selecVehicle
//
//
//    private val _groupedProperties = MutableLiveData<Map<String, List<Pair<String, Any>>>>()
//    val groupedProperties: LiveData<Map<String, List<Pair<String, Any>>>> = _groupedProperties
//
//    var showGroupContent by mutableStateOf(false)
//    var showVehicleDetails by mutableStateOf(true)
//
//    var searchText by mutableStateOf("")
//
////    init {
////        Log.e("MyTag", "init****************")
////         getAllVehicles()
////    }
//
////    fun getAllVehicles() {
////        viewModelScope.launch {
////            try {
////                for (country in LIST_COUNTRY) {
////                    for (rank in LIST_RANK) {
////                        fetchVehiclesForCountryAndRank(country, rank)
////                    }
////                }
////                _listaAllVehiculos.value = _listaVehiculos.value
////                Log.e("MyTag", "All vehicle list ${listaAllVehiculos.value.size}")
////            } catch (e: Exception) {
////                handleException("Error fetching vehicle list___", e)
////            }
////        }
////    }
//
//
////    private suspend fun fetchVehiclesForCountryAndRank(country: String, rank: Int) {
////        try {
////            val response = repository.getAllVehiclesCountryRank(1000, country, rank)
////            if (response.isSuccessful) {
////                handleSuccessfulResponse(response)
////            } else {
////                handleErrorResponse(response)
////            }
////        } catch (e: Exception) {
////            handleException("Error fetching vehicles for country: $country, rank: $rank", e)
////        }
////    }
//
//    private fun <T> handleSuccessfulResponse(response: Response<T>): Unit {
//        if (response.isSuccessful) {
//            val data = response.body()
//            if (data is RemoteVehicleListItem) {
//                // Manejar el caso de RemoteVehiclesItem
//                _selectedVehicle.value = response.body() as RemoteVehicleListItem
//        //        _selecVehicle.value = _selectedVehicle.value!!.toVehicleItem()
//                groupPropertiesByClassAndPrefix(vehicle = selectedVehicle.value!!)
//            } else if (data is List<*>) {
//                _myListaVehiculosRemotos.value = (response.body()) as List<RemoteVehicleListItem>
//                if (_myListaVehiculosRemotos.value.isNotEmpty()) {
//                    processVehiclesList()
//                } else {
//                    Log.e("MyTag", "Empty vehicle list")
//                }
//            }
//        } else {
//            Log.e("MyTag", "Manejar el caso de respuesta no exitosa")
//            // Manejar el caso de respuesta no exitosa
//        }
//    }
//
//    private fun handleErrorResponse(response: Response<List<RemoteVehicleListItem>>) {
//        Log.e("MyTag", "Error fetching vehicle list. Code: ${response.code()}")
//    }
//
//    private fun handleException(message: String, exception: Exception) {
//        Log.e("MyTag", message, exception)
//    }
//
//    private fun processVehiclesList() {
//        for (vehicleRemoto in _myListaVehiculosRemotos.value) {
////            Log.i(
////                "MyTag",
////                "Vehicle pos: ${_myListaVehiculosRemotos.value.indexOf(vehicleRemoto)}," +
////                        " Name: ${vehicleRemoto.identifier}," +
////                        " Country: ${vehicleRemoto.country}"
////            )
////            agregarElementoAVehiculos(vehicleRemoto.toVehicleItem())
//        }
//        _listaVehiculos.value = _listaVehiculos.value.sortedBy { it.arcadeBr }
//        Log.i("MyTag", "Total Vehicles: ${listaVehiculos.value.size}")
//    }
//
//    // Función para añadir elementos a la lista
//    private fun agregarElementoAVehiculos(vehicleItem: VehicleItem) {
//        _listaVehiculos.value += listOf(vehicleItem)
//    }
//
////    suspend fun getAllVehiclesRank(rank: Int) {
////        try {
////            val response = repository.getAllVehiclesRank(1000, rank)
////            if (response.isSuccessful) {
////                handleSuccessfulResponse(response)
////            } else {
////                handleErrorResponse(response)
////            }
////        } catch (e: Exception) {
////            handleException("Error fetching vehicles for rank:  $rank", e)
////        }
////    }
//
//    fun limpiarLista() {
//        Log.i("MyTag", "limpiarLista ")
//        listaVehiculos.value = emptyList<VehicleItem>()
//        Log.i("MyTag", "limpiarLista  ${listaVehiculos.value}")
//    }
//
////    suspend fun getAllVehiclesCountry(country: String) {
////        Log.i("MyTag", "getAllVehiclesCountry $country")
////        try {
////            val response = repository.getAllVehiclesCountry(1000, country)
////            if (response.isSuccessful) {
////                Log.i("MyTag", "getAllVehiclesCountry:: $response.isSuccessful")
////                handleSuccessfulResponse(response)
////            } else {
////                Log.i("MyTag", "getAllVehiclesCountry:: $response.isSuccessful")
////                handleErrorResponse(response)
////            }
////        } catch (e: Exception) {
////            handleException("Error fetching vehicles for country:  $country", e)
////        }
////    }
//
//    fun getAllVehiclesArma(silueta: String) {
//        //   LIST_TYPE_VEHICLE = listOf("tank", "plane", "ship")
//        listaVehiculos.value
//        when (silueta) {
//            LIST_TYPE_VEHICLE[0] -> {
//                for (vehicle in listaVehiculos.value) {
//                    if (vehicle.type !in LIST_TYPE_VEHICLE_TANK) {
//                        eliminarVehiculo(vehicle)
//                    }
//                }
//            }
//
//            LIST_TYPE_VEHICLE[1] -> {
//                for (vehicle in listaVehiculos.value) {
//                    if (vehicle.type !in LIST_TYPE_VEHICLE_AIR) {
//                        eliminarVehiculo(vehicle)
//                    }
//                }
//            }
//
//            LIST_TYPE_VEHICLE[2] -> {
//                for (vehicle in listaVehiculos.value) {
//                    if (vehicle.type !in LIST_TYPE_VEHICLE_NAVAL) {
//                        eliminarVehiculo(vehicle)
//                    }
//                }
//            }
//        }
//    }
//
//    // Función para eliminar un vehículo específico
//    fun eliminarVehiculo(vehiculoAEliminar: VehicleItem) {
//        val listaMutable = _listaVehiculos.value.toMutableList()
//        listaMutable.remove(vehiculoAEliminar)
//        _listaVehiculos.value = listaMutable
//    }
//
//    // Función para eliminar un vehículo por índice
//    fun eliminarVehiculoPorIndice(indiceAEliminar: Int) {
//        val listaMutable = _listaVehiculos.value.toMutableList()
//        listaMutable.removeAt(indiceAEliminar)
//        _listaVehiculos.value = listaMutable
//    }
//
//
////    suspend fun getVehicle(identifier: String): Response<RemoteVehicleListItem> {
////
////        try {
////            val response = repository.getVehicle(identifier = identifier)
////            if (response.isSuccessful) {
////                handleSuccessfulResponse(response)
////            } else {
////                //    handleErrorResponse(response)
////                Log.e("MyTag", "Error fetching vehicle: ${response.code()}")
////            }
////            return response
////        } catch (e: Exception) {
////            handleException("Error fetching vehicle", e)
////            return Response.error(500, "Internal Server Error".toResponseBody(null))
////        }
////    }
//
//
//    fun groupPropertiesByClassAndPrefix(vehicle: RemoteVehicleListItem) {
//        val groupedMap = mutableMapOf<String, MutableList<Pair<String, Any>>>()
//
//        vehicle.javaClass.kotlin.declaredMemberProperties.forEach { property ->
//            val propertyName = property.name
//            val propertyValue = property.get(vehicle)
//
//            // Agrupar por clase
//            //   val className = propertyValue?.javaClass?.simpleName ?: "Other"
//            if (propertyName in Constants.LIST_PROPERTIES_VEHICLE)
//                groupedMap.getOrPut(propertyName) { mutableListOf() }
//                    .add((propertyName to propertyValue) as Pair<String, Any>)
//
//            // Agrupar por prefijo "train" o "repair"
//            if (propertyName.startsWith("train")) {
//                groupedMap.getOrPut("Train") { mutableListOf() }
//                    .add((propertyName to propertyValue) as Pair<String, Any>)
//            }
//            // Agrupar por prefijo "train" o "repair"
//            if (propertyName.startsWith("repair")) {
//                groupedMap.getOrPut("Repair") { mutableListOf() }
//                    .add((propertyName to propertyValue) as Pair<String, Any>)
//            }
//            // Agrupar por características en el nombre (por ejemplo, "Br")
//            if (propertyName.contains("Br")) {
//                groupedMap.getOrPut("BR") { mutableListOf() }
//                    .add((propertyName to propertyValue) as Pair<String, Any>)
//            }
//        }
//        _groupedProperties.value = groupedMap
//    }
//
//    fun isGrupoVacio(propertyValue: Any): Boolean {
//        return (propertyValue is Collection<*> && propertyValue.isEmpty())
//    }
//
//    fun isAllPropertiesNullOrZeroOrFalse(propertyValue: Any): Boolean {
//        return LIST_CLASS_VEHICLE.any { it == propertyValue::class.simpleName } &&
//                processProperties(propertyValue)
//    }
//
//    // Función genérica para procesar propiedades de cualquier clase
//    private fun processProperties(propertyValue: Any): Boolean {
//        val properties = propertyValue::class.members
//            .filterIsInstance<KProperty1<Any, Any?>>()
//
//        return properties.all { property ->
//            val value = property.get(propertyValue)
//            value == null || value == 0.0 || value == false
//        }
//    }
//
//    val listaFiltradaVehiculos = mutableStateOf(listOf<VehicleItem>())
//
//    // Obtener la lista filtrada según el texto de búsqueda
//    fun getFilteredListBySearch() {
//        listaFiltradaVehiculos.value = listaVehiculos.value.filter { vehiculo ->
//            vehiculo.name.contains(searchText, ignoreCase = true)
//        }
//    }
//
//    // Obtener la lista filtrada según la condición arcadeRB
//    fun getFilteredListByArcadeRB(vehiculoItem: VehicleItem) {
//
//        // Determinar la lista específica según vehiculoItem
//        val listaEspecifica = when (vehiculoItem.type) {
//           in LIST_TYPE_VEHICLE_AIR -> LIST_TYPE_VEHICLE_AIR
//           in LIST_TYPE_VEHICLE_TANK -> LIST_TYPE_VEHICLE_TANK
//           in LIST_TYPE_VEHICLE_NAVAL -> LIST_TYPE_VEHICLE_NAVAL
//            else -> {
//                Log.i("MyTag", "falta este tipo ${vehiculoItem.type}")
//                emptyList()
//            }
//        }
//        Log.i("MyTag", "lista: $listaEspecifica")
//        Log.i("MyTag", "lista sin filtrar: ${listaFiltradaVehiculos.value.size}")
//        // Filtrar los vehículos según la lista específica y la condición de ArcadeBR
//        listaFiltradaVehiculos.value = listaVehiculos.value.filter { vehiculo ->
//
//            val arcadeInRange =
//                vehiculo.arcadeBr in (vehiculoItem.arcadeBr - 1)..(vehiculoItem.arcadeBr + 1)
//            val mismoTipo = vehiculo.type in listaEspecifica
//            Log.i("MyTag", "evaluando...: ${vehiculo.arcadeBr}-$arcadeInRange ")
//            Log.i("MyTag", "evaluando...: ${vehiculo.type}-$mismoTipo ")
//            arcadeInRange && mismoTipo
//
//        }
//        Log.i("MyTag", "lista filtrada: ${listaFiltradaVehiculos.value.size}")
//    }
//}
//
//
///**
//
////   val aerodynamics = viewModel.selecVehicle.value?.aerodynamics
////    viewModel.getFilteredListBySearch()
//Column {
//    if (vehicle != null) {
//        Log.i("MyTag","Vehiculo $vehicle")
//        //   VehicleSelectedCard(vehicle = vehicle)
////            SearchBar(searchText = viewModel.searchText,viewModel,vehicle) { newSearchText ->
////                viewModel.searchText = newSearchText
////                viewModel.getFilteredListBySearch()
////            }
//    }
////        if (viewModel.showVehicleDetails) {
////            VehicleDetails(viewModel)
////        }
////        if (viewModel.showGroupContent) {
////            GroupContent(viewModel)
////        }
//    Spacer(modifier = Modifier.height(12.dp))
//    // Otros componibles aquí
//
//
//    LazyColumn {
////            items(viewModel.listaFiltradaVehiculos.value) { vehiculo ->
////                Text(text = vehiculo.name+"::"+vehiculo.arcadeBr)
//        //            Spacer(modifier = Modifier.height(10.dp))
//    }
//}
//
//
////        if (false) {
////            if (aerodynamics != null) {
////                aerodynamics.javaClass.declaredFields.forEach { field ->
////                    field.isAccessible = true
////                    var propertyName = field.name
////                    var propertyValue = field.get(aerodynamics).toString().toDouble()
////                    var propertyUnits = getUnitForProperty(propertyName)
////                    var propertyHigh = propertyValue
////                    if (field.name == "maxSpeedAtAlt") {
////                        if (vehicle != null) {
////                            propertyHigh = propertyValue
////                            propertyValue = vehicle.engine.maxSpeed.toDouble()
////                            propertyUnits = "m/s"
////                        }
////                    }
////                    Log.i("MyTag", "Propiedad: **$propertyName**, Valor: **$propertyValue**")
////                    if (propertyValue != 0.0) {
////                        VehicleStat(
////                            propertyName,
////                            propertyValue,
////                            propertyUnits,
////                            propertyHigh,
////                            propertyValue + 10,
////                            Color.Yellow,
////
////                            )
////                        Spacer(modifier = Modifier.height(4.dp))
////                    }
////                }
////            }
////        }
//
////    }
//}
//
//@Composable
//fun SearchBar(searchText: String, viewModel: MyViewModel, vehicle: VehicleItem, onSearchTextChanged: (String) -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = Icons.Default.Build,
//            contentDescription = "Filtro",
//            modifier = Modifier
//                .size(24.dp)
//                .clickable {
//                    Log.i("MyTag", "filtrando...**")
//                    // Lógica para manejar el clic en el icono de filtro
//                    viewModel.getFilteredListByArcadeRB(vehicle)
//                }
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        BasicTextField(
//            value = searchText,
//            onValueChange = { newSearchText ->
//                onSearchTextChanged(newSearchText)
//            },
//            textStyle = TextStyle(color = Color.Black),
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
//                .padding(8.dp)
//        )
//    }
//}
//
//@Composable
//fun VehicleSelectedCard(vehicle: VehicleItem) {
//    //   val brush = getGradientBrushForTier(vehicle.tier)
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp, 12.dp)
//            // .background(brush)
//            //   .clickable { onItemClick(vehicle) }
//            .shadow(12.dp, MaterialTheme.shapes.medium)
//            .clip(MaterialTheme.shapes.medium),
//        shape = MaterialTheme.shapes.medium,
//    ) {
//        Column(
//            modifier = Modifier
//                //             .background(brush)
//                .padding(16.dp)
//
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceAround
//            )
//            {
//                Image(
//                    painter = painterResource(vehicle.bandera),
//                    contentDescription = "Descripción de la imagen",
//                    modifier = Modifier
//                        // .fillMaxWidth()
//                        .width(45.dp)
//                        .height(30.dp) // Ajusta la altura según tus necesidades
//                )
//                Text(
//                    text = vehicle.type.customToString().uppercase(Locale.ROOT),
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    maxLines = 1, // Limita a una línea
//                    overflow = TextOverflow.Ellipsis
//
//                )
//                Text(
//                    text = "AB:" + vehicle.arcadeBr,
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    maxLines = 1,// Configura el número máximo de líneas
//                )
//            }
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(vehicle.imageUrl)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .height(150.dp)
//                    .fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = vehicle.name,
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//        }
//    }
//}
//
//@Composable
//fun VehicleDetails(viewModel: MyViewModel) {
//    val groupedProperties by viewModel.groupedProperties.observeAsState(emptyMap())
//    LazyVerticalGrid(GridCells.Fixed(2)) {
//        groupedProperties.forEach { (groupName, properties) ->
//            var isVisible = true
//
//            properties.forEach { (propertyValue) ->
//                if (viewModel.isGrupoVacio(propertyValue)) {
//                    isVisible = false
//                } else if (viewModel.isAllPropertiesNullOrZeroOrFalse(propertyValue)) {
//                    isVisible = false
//                }
//            }
//            if (isVisible) {
//                item {
//                    Button(
//                        onClick = {
//                            viewModel.nameGrupo = groupName
//                            viewModel.showGroupContent = !viewModel.showGroupContent
//                            viewModel.showVehicleDetails = false
//                        },
//                        modifier = Modifier.padding(8.dp),
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text(text = groupName.uppercase())
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun GroupContent(viewModel: MyViewModel) {
//    val groupName = viewModel.nameGrupo
//    val properties = viewModel.groupedProperties.value?.get(groupName)
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color.Red)
//            .padding(16.dp)
//    ) {
//        Text(
//            text = groupName.uppercase(Locale.ROOT),
//            style = TextStyle(fontWeight = FontWeight.Bold)
//        )
//
//        properties?.forEach { (propertyName, propertyValue) ->
//            Log.i("MyTag", "valor::$propertyName: $propertyValue")
//            Text(text = "$propertyName: $propertyValue")
//        }
//    }
//    Button(
//        onClick = {
//            viewModel.showVehicleDetails = true
//            viewModel.showGroupContent = false
//        },
//        modifier = Modifier.padding(top = 8.dp),
//        shape = RoundedCornerShape(10.dp)
//    ) {
//        Text(text = "Ocultar")
//    }
//}
//
//
//@Composable
//fun VehicleStat(
//    statName: String,
//    statValue: Double,
//    statUnit: String,
//    propertyHigh: Double,
//    statMaxValue: Double,
//    statColor: Color,
//    height: Dp = 28.dp,
//    animDuration: Int = 1000,
//    animDelay: Int = 0,
//) {
//    var animationPlayed by remember {
//        mutableStateOf(false)
//    }
//    val curPercent = animateFloatAsState(
//        targetValue = if (animationPlayed) {
//            if (statValue != 0.0) {
//                (statValue / statMaxValue).toFloat() // Aquí se convierte el resultado a Float
//            } else {
//                100f
//            }
//        } else {
//            0f
//        },
//        animationSpec = tween(
//            animDuration,
//            animDelay
//        ), label = ""
//    )
//    LaunchedEffect(key1 = true) {
//        animationPlayed = true
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(height)
//            .clip(CircleShape)
//            .background(
//                if (isSystemInDarkTheme()) {
//                    Color(0xFF505050)
//                } else {
//                    Color.LightGray
//                }
//            )
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(curPercent.value)
//                .clip(CircleShape)
//                .background(statColor)
//                .padding(horizontal = 8.dp)
//        ) {
//            Text(
//                text = if (statName == "maxSpeedAtAlt") {
//                    getTextForProperty(statName) + " " + textoSinDecimales(propertyHigh) + " m"
//                } else {
//                    getTextForProperty(statName)
//                },
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = textoSinDecimales(statValue) + " " + statUnit,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}
//@Composable
//fun LazyColumnExample(items: List<String>) {
//    //   val items = (1..100).map { "Elemento $it" } // Crea una lista de 100 elementos
//
//    LazyColumn {
//        itemsIndexed(items) { index, item ->
//            if (index < 3) {
//                // Muestra los primeros 3 elementos
//                Text(
//                    text = item,
//                    modifier = Modifier
//                        .padding(8.dp)
//                )
//            } else {
//                // Oculta los elementos restantes
//                Spacer(modifier = Modifier.height(0.dp))
//            }
//        }
//    }
//}
//
//*/