package com.example.warthundervehicles.modelsApp

import androidx.compose.runtime.Composable

data class Opcion (
    val text: String,
    val composable: @Composable () -> Unit
)