package com.example.climapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(){
    private lateinit var database: DatabaseReference// ...
    private lateinit var postReference: DatabaseReference
    private lateinit var postKey: String
    private var postListener: ValueEventListener? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        val txtClave = findViewById<TextView>(R.id.txtClave)

        postReference = FirebaseDatabase.getInstance().reference
            .child("mensajes")

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val intento1 = Intent(this, Menu::class.java)
            startActivity(intento1)
            database=FirebaseDatabase.getInstance().reference
            val key = database.child("mensajes").push().key
            if (key == null) {
                // Log.w("error", "Couldn't get push key for posts")
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }


            val name = this.txtUsuario.getText().toString()
            val password = this.txtClave.getText().toString()
            val post = Post(name, password)

            val postValues = post.toMap()
            val childUpdates = HashMap<String, Any>()

            childUpdates["/mensajes_usuario/$name/$password"] = postValues
            database.updateChildren(childUpdates)
        }

    }

    data class Post(
        var name: String? = "",
        var password: String? = "",
        //var destino: String? = "",
        //var mensaje: String? = "",
        var starCount: Int = 0,
        var stars: MutableMap<String, Boolean> = HashMap()
    ) {

        // [START post_to_map]
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "Nombre" to name,
                "Contraseña" to password
            )
        }
    }
}
//"destino" to destino,