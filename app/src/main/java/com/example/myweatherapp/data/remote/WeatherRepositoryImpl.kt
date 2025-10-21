package com.example.myweatherapp.data.remote

import com.example.myweatherapp.data.model.ForecastResponse
import com.example.myweatherapp.domain.repository.WeatherRepository
import jakarta.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String) =
        api.getWeather(lat, lon, apiKey)

    override suspend fun getWeatherByCity(city: String, apiKey: String) =
        api.getWeatherByCity(city, apiKey)

    override suspend fun getForecast(city: String): ForecastResponse {
        return api.getForecast(city, "70a2a87e787710540d72bf7d74b32aba")
    }
}