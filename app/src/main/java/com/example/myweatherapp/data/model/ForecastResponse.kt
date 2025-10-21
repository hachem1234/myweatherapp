package com.example.myweatherapp.data.model


data class ForecastResponse(
    val city: City,
    val list: List<ForecastItem>
)

data class City(
    val name: String,
    val country: String
)

data class ForecastItem(
    val dt_txt: String,           // e.g., "2025-10-22 12:00:00"
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String,
    val icon: String
)
