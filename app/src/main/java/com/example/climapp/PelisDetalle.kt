package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ciudad_detalle.*
import kotlinx.android.synthetic.main.activity_pais_detalle.*
import kotlinx.android.synthetic.main.activity_pelis_detalle.*

class PelisDetalle : AppCompatActivity() {

    private lateinit var database: DatabaseReference// ...
    private lateinit var postReference: DatabaseReference
    private lateinit var postKey: String
    private var postListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelis_detalle)

        val peli = intent.getSerializableExtra("peli") as Pelis

        val urlPoster = "https://image.tmdb.org/t/p/original${peli.imagenpeli}"

        txtNombrePeliDet.text = "Nombre: " + peli.nombre
        txtLanzamiento.text = "Estreno: " + peli.lanzamiento
        txtLenguaje.text = "Idioma: " + peli.lenguaje
        txtPublico.text = "Taquilla: " + peli.publico.toString()
        txtPuntaje.text = "Valoración: " + peli.puntaje.toString()
        Picasso.get().load(urlPoster).into(imgPeli)

        /////////////////////////////////////////////////////////////////////////////////


        postReference = FirebaseDatabase.getInstance().reference
            .child("Pelis")

        val btnGuardarPelis = findViewById<Button>(R.id.btnGuardarPelis)
        btnGuardarPelis.setOnClickListener {
            // [START initialize_database_ref]
            database = FirebaseDatabase.getInstance().reference
            // [END initialize_database_ref]
            val key = database.child("Pelis").push().key
            if (key == null) {
                // Log.w("error", "Couldn't get push key for posts")
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }



            val post = Post(peli.nombre, peli.lanzamiento, peli.lenguaje, peli.publico, peli.puntaje, peli.imagenpeli)
            val postValues = post.toMap()
            val childUpdates = HashMap<String, Any>()
            childUpdates["/Pelis/${peli.nombre}/$key"] = postValues
            database.updateChildren(childUpdates)

            val intent = Intent(this@PelisDetalle, Menu::class.java)
            startActivity(intent)
        }

    }
    // [START post_class]
    @IgnoreExtraProperties
    data class Post(
        var NombreDet: String? = "",
        var LanzamientoDet: String? = "",
        var LenguajeDet: String? = "",
        var PublicoDet: Int? = 0,
        var PuntajeDet: Double? = 0.0,
        var Imagen: String? = ""

    ) {

        // [START post_to_map]
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "NombreDet" to NombreDet,
                "LanzamientoDet" to LanzamientoDet,
                "LenguajeDet" to LenguajeDet,
                "PublicoDet" to PublicoDet,
                "PuntajeDet" to PuntajeDet,
                "Imagen" to Imagen
            )
        }

    }
}