# Inyeccion de Dependencias

## Añadir dependencias

https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419
libs
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt-android" }
hilt-android-compiler = { module = "com.google.dagger:hilt-android-compiler", version.ref = "hilt-android" }


### Crear Clase App:Aplication

#### poner en el Manifest y permiso de Internet

### Punto de entrada en activity

### Crear Modulo

En paquete DI, creamos el modulo que contendrá los proveedores

# Llamadas a APIs (Retrofit)

## Añadir dependencias
// Retrofit
implementation (libs.retrofit)
implementation(libs.retrofit2.converter.gson)
implementation(libs.okhttp)
implementation(libs.logging.interceptor)
// libs
logging-interceptor = { module = "com.squareup.okhttp3:logging-interceptor", version.ref = "okhttp" }
okhttp = { module = "com.squareup.okhttp3:okhttp", version.ref = "okhttp" }
retrofit = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
retrofit2-converter-gson = { module = "com.squareup.retrofit2:converter-gson", version.ref = "retrofit" }
okhttp = "4.12.0"
retrofit = "2.9.0"

## Crear repositorio, api y viewmodel

### Comprobar que funciona y no peta; Vigilar versiones a nivel app-modulo

## Otras dependencias
Navegacion
androidx-navigation-compose = { module = "androidx.navigation:navigation-compose", version.ref = "navigation-compose" }
imagenes
coil-compose = { module = "io.coil-kt:coil-compose", version.ref = "coil-compose" }
ViewModel (posiblemente deprecada)
androidx-lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycle-runtime-ktx" }


