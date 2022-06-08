package com.example.firebasecrudapplication

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrudapplication.adapters.OnProyectoClickListener
import com.example.firebasecrudapplication.adapters.RecyclerViewAdapter
import com.example.firebasecrudapplication.models.Alumno
import com.example.firebasecrudapplication.models.Proyecto
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject

class ProyectosAlumnoActivity : AppCompatActivity(), OnProyectoClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectArrayList: ArrayList<Proyecto>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var emailid: String //variable utilizada para referenciar el documento correspondiente al usuario en sesion actual
    private lateinit var usuarioActual : Alumno
    private lateinit var proyecto_ref_al : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyectos_alumno)
        supportActionBar?.setTitle("Tus Proyectos:")

        // Se extrae el email de la actividad de login para futuro uso
        emailid = intent.getStringExtra("email_id").toString()


        recyclerView = findViewById(R.id.rv_alumno)
        recyclerView.layoutManager = LinearLayoutManager (this)
        recyclerView.setHasFixedSize(true)

        projectArrayList = arrayListOf()

        recyclerViewAdapter = RecyclerViewAdapter(projectArrayList, this)
        recyclerView.adapter = recyclerViewAdapter


        poblarListaProyectosAlumno()

    }



    //ESTA FUNCIÓN CONSULTA LOS PROYECTOS ASOCIADOS AL USUARIO QUE SE ENCUENTRA EN SESIÓN
    // Y POBLA UNA ARRAYLIST CON OBJETOS DE TIPO PROYECTO PARA SER POSTERIORMENTA USADA EN EL RECYCLERVIEW
    private fun poblarListaProyectosAlumno(){

        var dataAlum: Map<String, Any>
        var currentUserDocumentQuery = db.collection("Alumno").document(emailid)
        currentUserDocumentQuery.get().addOnSuccessListener {
            this.usuarioActual = it.toObject<Alumno>()!!
            dataAlum = mapOf("nombre" to usuarioActual.nombre.toString(), "ncontrol" to usuarioActual.ncontrol!!.toInt())

            Log.d(TAG,"${it.id} => ${it.data}")
            Log.d(TAG, "The current user (alumno) has been obtained")


            var document = db.collection("Proyecto").whereEqualTo("alumnos" , dataAlum)
            document.get().addOnSuccessListener {documentSnapshot->
                for (document in documentSnapshot)
                {
                    projectArrayList.add(document.toObject(Proyecto::class.java))
                    Log.d(TAG,"${document.id} => ${document.data}")
                    Log.d(TAG, "Registro de proyecto del alumno.")
                }
                recyclerViewAdapter.notifyDataSetChanged()
            }
                .addOnFailureListener{ exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }

        }
            .addOnFailureListener{ exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

    }

    override fun onProyectoItemClicked(position: Int) {
        val newintent = Intent(this@ProyectosAlumnoActivity, DetallesAlumnoActivity::class.java)

        proyecto_ref_al = projectArrayList[position].tituloproyecto.toString()

        Log.d(TAG, "Title of selected project: "+ proyecto_ref_al)
        newintent.putExtra("ref", proyecto_ref_al)
        //Toast.makeText(this@ProyectosProfesorActivity, "Haz clickeado el proyecto:"+ projectArrayList[position].tituloproyecto.toString(), Toast.LENGTH_SHORT).show()
        startActivity(newintent)
    }

    // ESTA FUNCIÓN HACÍA UNA CONSULTA DE TODOS LOS DOCUMENTOS EN LA COLECCIÓN PROYECTO, LA DEJO EN EL CÓDIGO COMO REFERENCIA
//    private fun EventChangeListener() {
//        db = FirebaseFirestore.getInstance()
//
//        db.collection("Proyecto").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                    projectArrayList.add(document.toObject(Proyecto::class.java))
//                }
//                myAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { exception ->
//                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
//            }
//
//    }
}

