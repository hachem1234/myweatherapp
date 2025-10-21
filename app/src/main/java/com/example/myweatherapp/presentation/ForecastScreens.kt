package com.example.myweatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ForecastScreens(vm: WeatherViewModel) {
    val forecastText by vm.forecastText.collectAsState()
    val forecast by vm.forecast.collectAsState()

    if (forecast != null) {
        val data = forecast!!

        // Keep only one forecast per day (first item of each day)
        val dailyForecast = data.list
            .groupBy { it.dt_txt.substring(0, 10) } // group by date
            .map { it.value.first() } // take first forecast per day

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Text(
                text = "${data.city.name}, ${data.city.country}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(dailyForecast) { item ->
                    WeatherItem(
                        time = item.dt_txt,
                        temp = item.main.temp,
                        description = item.weather[0].description,
                        iconCode = item.weather[0].icon
                    )
                }
            }
        }
    } else {
        Text(
            text = forecastText.ifEmpty { "Loading..." },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            fontSize = 18.sp
        )
    }
}

@Composable
fun WeatherItem(
    time: String,
    temp: Double,
    description: String,
    iconCode: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = getDayName(time),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(text = description, fontSize = 14.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${temp.toInt()}°C",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Image(
                    painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${iconCode}@2x.png"),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

fun getDayName(dtTxt: String): String {
    return try {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
        val date = sdf.parse(dtTxt)
        val dayFormat = java.text.SimpleDateFormat("EEEE", java.util.Locale.getDefault())
        dayFormat.format(date!!)
    } catch (e: Exception) {
        dtTxt.substring(0, 10) // fallback
    }
}
