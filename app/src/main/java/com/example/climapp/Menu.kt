package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //Declaracion de botones
        val btnVerificar = findViewById<Button>(  R.id.btnVerificar)

        val btnPaises = findViewById<Button>(  R.id.btnPaises)
        val btnClima = findViewById<Button>(  R.id.btnClima)
        val btnPeliculas = findViewById<Button>(  R.id.btnPeliculas)

        //Verifico lac onexion a internet
        btnVerificar.setOnClickListener{
            if (MenuDeFunciones.hayConexion(this))
            {
                Toast.makeText(this,"¡¡¡Estamos conectados!!!", Toast.LENGTH_SHORT).show()

            }else
            {
                Toast.makeText(this,"¡¡¡Estamos desconectados!!!", Toast.LENGTH_SHORT).show()
            }
        }

        //Ir a listado de paises
        btnPaises.setOnClickListener{
            val intent = Intent(this, VistaListado::class.java)
            intent.putExtra("clave","API PAISES")
            startActivity(intent)
        }

        //Ir a listado de clima
        btnClima.setOnClickListener{
            val intent = Intent(this, VistaListadoClima::class.java)
            intent.putExtra("clave","API CLIMA")
            startActivity(intent)
        }

        //Ir a listado de peliculas
        btnPeliculas.setOnClickListener{
            val intent = Intent(this, VistaListadoPelis::class.java)
            intent.putExtra("clave","API PELICULAS")
            startActivity(intent)
        }



    }
}