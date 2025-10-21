package com.example.myweatherapp.presentation

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun WeatherScreen(vm: WeatherViewModel = hiltViewModel()) {
    val state by vm.weather.collectAsState()
    var city by remember { mutableStateOf("") }
      // Animate the weather text
    val alphaAnim by animateFloatAsState(
        targetValue = if (state.isNotEmpty()) 1f else 0f
    )
    val offsetAnim by animateDpAsState(
        targetValue = if (state.isNotEmpty()) 0.dp else 50.dp
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search bar
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Search city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { vm.search(city) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Animated weather display
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state == "Loading..." -> {
                        CircularProgressIndicator()
                    }

                    state.startsWith("Error") || state == "City not found" -> {
                        Text(
                            text = state,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    else -> {

                        val cityName = state.substringBefore(":")

                        Spacer(modifier = Modifier.height(12.dp))

                        LaunchedEffect(cityName) {
                            vm.loadForecast(cityName)
                        }
                        ForecastScreens(vm)
                    }
                }
            }
        }
    }
}
