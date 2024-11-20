package com.lruiz.urbanapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lruiz.urbanapp.ViewModels.whislistViewModel
import com.lruiz.urbanapp.components.BarraNavegacion
import com.lruiz.urbanapp.data.whislistDTO
import com.lruiz.urbanapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWhislist(navController: NavHostController) {
    val viewModel: whislistViewModel = viewModel()
    val productos by viewModel.WhislistProductos.collectAsState(initial = emptyList())
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getWhislistProductos()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Lista de deseos") })
        },
        bottomBar = {
            BottomAppBar {
                BarraNavegacion(navController)
            }
        }
    ){
            innerPadding ->
        if (errorMessage != null) {
            Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Confirma que productos es no nulo y tiene el tipo correcto
                items(productos) { producto ->
                    ListaDeseo(navController,producto)
                }
            }


        }
    }
}

@Composable
fun ListaDeseo(navController: NavHostController,wis: whislistDTO) { // Añadimos el navController como parámetro
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de deseos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        // Navegar al detalle del producto con su ID
                        navController.navigate("${AppScreens.ScreenProducto.route}?productId=${wis.idProducto}")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Caja que simula una imagen
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray)
                )
                // Mostrar nombre y precio
                Text(
                    text = "${wis.nombreProducto} - $${wis.precioProducto}",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

    }
}


