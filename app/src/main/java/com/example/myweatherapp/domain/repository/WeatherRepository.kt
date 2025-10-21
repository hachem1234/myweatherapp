package com.example.myweatherapp.domain.repository

import com.example.myweatherapp.data.model.ForecastResponse
import com.example.myweatherapp.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse
    suspend fun getWeatherByCity(city: String, apiKey: String): WeatherResponse
    suspend fun getForecast(city: String): ForecastResponse


}