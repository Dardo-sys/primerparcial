package com.example.climapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pais_detalle.*
import kotlinx.android.synthetic.main.datospais.*

class PaisDetalle : AppCompatActivity() {

    private lateinit var database: DatabaseReference// ...
    private lateinit var postReference: DatabaseReference
    private lateinit var postKey: String
    private var postListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pais_detalle)

        val pais = intent.getSerializableExtra("pais") as Pais

        val svgFlagString = MenuDeFunciones.consultarDatos(pais.flag)

        txtNombrePaisDet.text = "País: " + pais.nombre
        txtRegionDet.text = "Región: " + pais.region
        txtPoblacionDet.text = "Población: " + pais.population.toString()
        imgBanderaDet.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        try {
            val svg: SVG = SVG.getFromString(svgFlagString)
            val drawable: Drawable = PictureDrawable(svg.renderToPicture())
            imgBanderaDet.setImageDrawable(drawable)
        } catch (e: SVGParseException) {
            Log.d("ERROR SVG", e.message)
        }

        //////////////////////////////////////////////////////////////////////////////////
        //Cpdigo Firebase
        val paisDet = findViewById<TextView>(R.id.txtNombrePaisDet)
        val regionDet = findViewById<TextView>(R.id.txtRegionDet)



        postReference = FirebaseDatabase.getInstance().reference
            .child("pais")

        val btnGuardarPais = findViewById<Button>(R.id.btnGuardarPais)
        btnGuardarPais.setOnClickListener {
            // [START initialize_database_ref]
            database = FirebaseDatabase.getInstance().reference
            // [END initialize_database_ref]
            val key = database.child("Países").push().key
            if (key == null) {
                // Log.w("error", "Couldn't get push key for posts")
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }



            val post = Post(pais.nombre, pais.region, pais.population, pais.flag)
            val postValues = post.toMap()
            val childUpdates = HashMap<String, Any>()
            childUpdates["/Paises/${pais.nombre}/$key"] = postValues
            database.updateChildren(childUpdates)

            val intent = Intent(this@PaisDetalle, Menu::class.java)
            startActivity(intent)
        }

    }
    // [START post_class]
    @IgnoreExtraProperties
    data class Post(
        var paisDet: String? = "",
        var RegionDet: String? = "",
        var poblacionDet: Int? = 0,
        var bandera: String? = ""

    ) {

        // [START post_to_map]
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "paisDet" to paisDet,
                "RegionDet" to RegionDet,
                "poblacionDet" to poblacionDet,
                "bandera" to bandera
            )
        }



    }


}