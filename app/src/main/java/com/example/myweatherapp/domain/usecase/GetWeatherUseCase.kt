package com.example.myweatherapp.domain.usecase

import com.example.myweatherapp.data.model.ForecastResponse
import com.example.myweatherapp.domain.repository.WeatherRepository
import jakarta.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double, apiKey: String) =
        repository.getWeather(lat, lon, apiKey)

    suspend fun searchCity(city: String, apiKey: String) =
        repository.getWeatherByCity(city, apiKey)

    suspend operator fun invoke(city: String): ForecastResponse {
        return repository.getForecast(city)
    }

}