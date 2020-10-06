package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ciudad_detalle.*
import kotlinx.android.synthetic.main.activity_pais_detalle.*

class CiudadDetalle : AppCompatActivity() {

    private lateinit var database: DatabaseReference// ...
    private lateinit var postReference: DatabaseReference
    private lateinit var postKey: String
    private var postListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad_detalle)

        val clima = intent.getSerializableExtra("clima") as Ciudad

        txtNombreCiudadClimaDet.text = "Ciudad: " + clima.nombre
        txtTemDet.text = "Temperatura: " + clima.temp.toString() + "°"
        txtTermica.text = "Sensación Térmica: " + clima.feels_like.toString() + "°"
        txtHumedad.text = "Humedad: " + clima.humidity.toString() + "%"
        txtPresion.text = "Presión Atmosférica: " + clima.pressure.toString() + "hPa"

        ///////////////////////////////////////////////////////////////////////////////
        //Cpdigo Firebase
        val CiudadDet = findViewById<TextView>(R.id.txtClimaCiudad)
        val TemperaturaDet = findViewById<TextView>(R.id.txtTemDet)
        val SensacionTermicaDet = findViewById<TextView>(R.id.txtTermica)
        val HumedadDet = findViewById<TextView>(R.id.txtHumedad)
        val PresionDet = findViewById<TextView>(R.id.txtPresion)



        postReference = FirebaseDatabase.getInstance().reference
            .child("Clima")

        val btnGuardarClima = findViewById<Button>(R.id.btnGuardarClima)
        btnGuardarClima.setOnClickListener {
            // [START initialize_database_ref]
            database = FirebaseDatabase.getInstance().reference
            // [END initialize_database_ref]
            val key = database.child("Climas").push().key
            if (key == null) {
                // Log.w("error", "Couldn't get push key for posts")
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }



            val post = Post(clima.nombre, clima.temp, clima.feels_like, clima.humidity, clima.pressure)
            val postValues = post.toMap()
            val childUpdates = HashMap<String, Any>()
            childUpdates["/Clima/${clima.nombre}/$key"] = postValues
            database.updateChildren(childUpdates)

            val intent = Intent(this@CiudadDetalle, Menu::class.java)
            startActivity(intent)
        }

    }
    // [START post_class]
    @IgnoreExtraProperties
    data class Post(
        var CiudadDet: String? = "",
        var TemperaturaDet: Double? = 0.0,
        var TermicaDet: Double? = 0.0,
        var HumedadDet: Int? = 0,
        var PresionDet: Int? = 0

    ) {

        // [START post_to_map]
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "CiudadDet" to CiudadDet,
                "TemperaturaDet" to TemperaturaDet,
                "TermicaDet" to TermicaDet,
                "HumedadDet" to HumedadDet,
                "PresionDet" to PresionDet
            )
        }

    }
}