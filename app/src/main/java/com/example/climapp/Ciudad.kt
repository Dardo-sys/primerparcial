package com.example.climapp

import java.io.Serializable

class Ciudad(var nombre: String, var temp: Double,
             var feels_like: Double, var humidity: Int, var pressure: Int
) : Serializable {


}