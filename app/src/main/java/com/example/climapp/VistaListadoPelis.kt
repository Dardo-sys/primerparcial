package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject

class VistaListadoPelis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_listado_pelis)

        //Declaro listado donde se muestra la consulta obtenida
        var LsvVistaListadoPelis = findViewById<ListView>(R.id.LsvVistaListadoPelis)

        //Traigo el dato de la actividad anterior para saber que boton apreto
        val clave = intent.getStringExtra("clave");
        Toast.makeText(this, "Selecciono:  $clave", Toast.LENGTH_SHORT).show()

        val datos = MenuDeFunciones.consultarDatos("https://api.themoviedb.org/3/movie/popular?api_key=44d0cfaf931127ae6456a322933ebdcc&language=en-US&page=1")

        val arrayDenombreDePelis: ArrayList<String> = ArrayList()
        val arrayDeobjetosDePelis: ArrayList<Pelis> = ArrayList()
        val datosJSONObject = JSONObject(datos)

        for (i in 0..9) {
            //Log.d("ciudad listado", datosJSONObject.getString("name"))
            arrayDeobjetosDePelis.add(
                Pelis(
                    datosJSONObject.getJSONArray("results").getJSONObject(i).getString("title"),
                    datosJSONObject.getJSONArray("results").getJSONObject(i).getString("release_date"),
                    datosJSONObject.getJSONArray("results").getJSONObject(i).getString("original_language"),
                    datosJSONObject.getJSONArray("results").getJSONObject(i).getInt("popularity"),
                    datosJSONObject.getJSONArray("results").getJSONObject(i).getDouble("vote_average"),
                    datosJSONObject.getJSONArray("results").getJSONObject(i).getString("poster_path")

                )
            )

        }


        val adaptadorSimple = AdaptadorLsvPelis(this, arrayDeobjetosDePelis)
        LsvVistaListadoPelis.adapter = adaptadorSimple

        LsvVistaListadoPelis.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PelisDetalle::class.java)
            intent.putExtra("peli", arrayDeobjetosDePelis[position])
            startActivity(intent)
        }
    }
}