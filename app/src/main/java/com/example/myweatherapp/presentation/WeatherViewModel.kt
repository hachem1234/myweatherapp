package com.example.myweatherapp.presentation

import com.example.myweatherapp.domain.usecase.GetWeatherUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.model.ForecastResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weather = MutableStateFlow<String>("Loading...")
    val weather: StateFlow<String> = _weather

    private val _forecast = MutableStateFlow<ForecastResponse?>(null)
    val forecast = _forecast.asStateFlow()

    private val _forecastText = MutableStateFlow("")
    val forecastText = _forecastText.asStateFlow()

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = getWeatherUseCase(lat, lon, "70a2a87e787710540d72bf7d74b32aba")
                _weather.value = response.name
            } catch (e: Exception) {
                _weather.value = "Error loading weather"
            }
        }
    }

    fun search(city: String) {
        viewModelScope.launch {
            try {
                val response = getWeatherUseCase.searchCity(city, "70a2a87e787710540d72bf7d74b32aba")
                _weather.value = "${response.name}: ${response.main.temp}°C"
            } catch (e: Exception) {
                _weather.value = "City not found"
            }
        }
    }

    fun loadForecast(city: String) {
        viewModelScope.launch {
            try {
                _forecastText.value = "Loading..."
                val data = getWeatherUseCase(city)
                _forecast.value = data
                _forecastText.value = ""
            } catch (e: Exception) {
                e.printStackTrace()
                _forecast.value = null
                _forecastText.value = "Error loading weather: ${e.message}"
            }
        }
    }
}
