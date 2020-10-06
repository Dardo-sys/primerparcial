package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject

class VistaListadoClima : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_listado_clima)

        //Declaro listado donde se muestra la consulta obtenida
        var LsvVistaListadoClima = findViewById<ListView>(R.id.LsvVistaListadoClima)

        //Traigo el dato de la actividad anterior para saber que boton apreto
        val clave = intent.getStringExtra("clave");
        Toast.makeText(this, "Selecciono:  $clave", Toast.LENGTH_SHORT).show()

        val datos = MenuDeFunciones.consultarDatos("https://api.mocki.io/v1/44945736")

        val arrayDenombreDeCiudad: ArrayList<String> = ArrayList()
        val arrayDeobjetosDeCiudad: ArrayList<Ciudad> = ArrayList()
        val datosJSONObject = JSONObject(datos)

        for (i in 0..4) {
            //Log.d("ciudad listado", datosJSONObject.getString("name"))
            arrayDeobjetosDeCiudad.add(
                Ciudad(
                    datosJSONObject.getJSONArray("list").getJSONObject(i).getString("name"),
                    datosJSONObject.getJSONArray("list").getJSONObject(i).getJSONObject("main")
                        .getDouble("temp"),
                    datosJSONObject.getJSONArray("list").getJSONObject(i).getJSONObject("main")
                        .getDouble("feels_like"),
                    datosJSONObject.getJSONArray("list").getJSONObject(i).getJSONObject("main")
                        .getInt("humidity"),
                    datosJSONObject.getJSONArray("list").getJSONObject(i).getJSONObject("main")
                        .getInt("pressure")
                )
            )

        }

        //Log.d("listado", arrayDeobjetosDeCiudad.toString())

        val adaptadorSimple = AdaptadorLsvCiudad(this, arrayDeobjetosDeCiudad)
        LsvVistaListadoClima.adapter = adaptadorSimple

        LsvVistaListadoClima.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CiudadDetalle::class.java)
            intent.putExtra("clima", arrayDeobjetosDeCiudad[position])
            startActivity(intent)
        }
    }
}