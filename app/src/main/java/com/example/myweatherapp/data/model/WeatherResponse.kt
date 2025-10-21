package com.example.myweatherapp.data.model

data class WeatherResponse(
    val name: String,
    val main: Main
) {
    data class Main(val temp: Double)
    data class sys(val country: String)
}