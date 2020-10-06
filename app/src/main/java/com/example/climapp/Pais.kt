package com.example.climapp

import java.io.Serializable

class Pais(nombre:String, region: String, population: Int, flag:String) : Serializable {
    var nombre = ""
    var region = ""
    var population = 0
    var flag = ""
    init{
        this.nombre = nombre
        this.region = region
        this.population = population
        this.flag = flag
    }

}