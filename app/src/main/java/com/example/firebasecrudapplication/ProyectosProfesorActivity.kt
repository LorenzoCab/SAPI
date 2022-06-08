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
import com.example.firebasecrudapplication.models.Profesor
import com.example.firebasecrudapplication.models.Proyecto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_proyectos_profesor.*

class ProyectosProfesorActivity : AppCompatActivity() , OnProyectoClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectArrayList: ArrayList<Proyecto>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var emailid: String //variable utilizada para referenciar el documento correspondiente al usuario en sesion actual
    private lateinit var usuarioActual : Profesor
    private lateinit var proyecto_ref_pr : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyectos_profesor)
        supportActionBar?.setTitle("Proyectos Asignados")



        emailid = this.intent.getStringExtra("email_id").toString()
        recyclerView = findViewById(R.id.rv_profesor)
        recyclerView.layoutManager = LinearLayoutManager (this)
        recyclerView.setHasFixedSize(true)
        projectArrayList = arrayListOf()
        recyclerViewAdapter = RecyclerViewAdapter(projectArrayList, this)
        recyclerView.adapter = recyclerViewAdapter

        poblarListaProyectosProfesor()

        btn_crear_pp.setOnClickListener {
            val intent = Intent(this@ProyectosProfesorActivity, NuevoProyectoActivity::class.java)
            startActivity(intent)
        }



    }
    private fun poblarListaProyectosProfesor(){

        var currentUserDocumentQuery = db.collection("Profesor").document(emailid)
        currentUserDocumentQuery.get().addOnSuccessListener {
            this.usuarioActual = it.toObject<Profesor>()!!

            Log.d(ContentValues.TAG,"${it.id} => ${it.data}")
            Log.d(ContentValues.TAG, "The current user (profesor) has been obtained")


            var document = db.collection("Proyecto").whereEqualTo("coordinador" , usuarioActual.nombre.toString())
            document.get().addOnSuccessListener {documentSnapshot->
                for (document in documentSnapshot)
                {
                    projectArrayList.add(document.toObject(Proyecto::class.java))
                    Log.d(ContentValues.TAG,"${document.id} => ${document.data}")
                    Log.d(ContentValues.TAG, "Registro de proyecto del alumno.")
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
        val newintent = Intent(this@ProyectosProfesorActivity, DetallesProfesorActivity::class.java)

        proyecto_ref_pr = projectArrayList[position].tituloproyecto.toString()

        Log.d(TAG, "Title of selected project: "+ proyecto_ref_pr)
        newintent.putExtra("ref", proyecto_ref_pr)
        //Toast.makeText(this@ProyectosProfesorActivity, "Haz clickeado el proyecto:"+ projectArrayList[position].tituloproyecto.toString(), Toast.LENGTH_SHORT).show()
        startActivity(newintent)
    }
}