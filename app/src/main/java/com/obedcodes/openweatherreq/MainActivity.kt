package com.obedcodes.openweatherreq


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val apiKey = "817d87bb1899925cd19bedf645ba0550" // Replace with your OpenWeatherMap API key
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var weatherRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fetchButton: Button = findViewById(R.id.fetchButton)
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)

        weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        weatherAdapter = WeatherAdapter(emptyList())
        weatherRecyclerView.adapter = weatherAdapter

        fetchButton.setOnClickListener {
            fetchWeatherData("Lagos")
        }
    }

    private fun fetchWeatherData(city: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/") // Use HTTPS here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.getWeatherForecast(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        updateUI(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("WeatherApp", "Error: $errorBody")
                    Toast.makeText(this@MainActivity, "Error fetching weather data: $errorBody", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherApp", "Failure: ${t.message}", t)
                Toast.makeText(this@MainActivity, "Error fetching weather data: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        val simpleDateFormat = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
        val weatherList = weatherResponse.list.map { item ->
            val date = Date(item.dt * 1000)
            val formattedDate = simpleDateFormat.format(date)
            val temperature = item.main.temp
            val description = item.weather.firstOrNull()?.description ?: "No description"

            WeatherItem(
                dt = item.dt,
                main = item.main,
                weather = item.weather,
                date = formattedDate,
                temp = temperature,
                description = description
            )
        }

        weatherAdapter = WeatherAdapter(weatherList)
        weatherRecyclerView.adapter = weatherAdapter
    }
}

