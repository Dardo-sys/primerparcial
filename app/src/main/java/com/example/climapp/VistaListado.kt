package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.datospais.*
import org.json.JSONArray
import org.json.JSONObject

class VistaListado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_listado)

        //Declaro listado donde se muestra la consulta obtenida
        var LsvVistaListado = findViewById<ListView>(R.id.LsvVistaListado)

        //Traigo el dato de la actividad anterior para saber que boton apreto
        val clave = intent.getStringExtra("clave");
        Toast.makeText(this, "Selecciono:  $clave", Toast.LENGTH_SHORT).show()

        val datos =
            MenuDeFunciones.consultarDatos("https://restcountries.eu/rest/v2/region/Americas")
        val arrayDenombreDePaises: ArrayList<String> = ArrayList()
        val arrayDeobjetosDePaises: ArrayList<Pais> = ArrayList()
        val datosArrayJson = JSONArray(datos);
        for (i in 0..5) {
            var pais = datosArrayJson.getJSONObject(i)
            Log.d("pais listado", pais.getString("name"))
            arrayDenombreDePaises.add(pais.getString("name"))
            arrayDeobjetosDePaises.add(
                Pais(
                    pais.getString("name"), pais.getString("region"),
                    pais.getInt("population"), pais.getString("flag")
                )
            )

        }
        val adaptadorSimple = AdaptadopLsvPais(this, arrayDeobjetosDePaises)
        LsvVistaListado.adapter = adaptadorSimple


        LsvVistaListado.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PaisDetalle::class.java)
            intent.putExtra("pais", arrayDeobjetosDePaises[position])
            startActivity(intent)
        }


    }

}