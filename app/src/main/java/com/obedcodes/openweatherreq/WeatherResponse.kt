package com.obedcodes.openweatherreq

data class WeatherResponse(
    val list: List<WeatherItem>
)

data class WeatherItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val date: String? = null,
    val temp: Double? = null,
    val description: String? = null
)

data class Main(
    val temp: Double
)

data class Weather(
    val description: String
)
