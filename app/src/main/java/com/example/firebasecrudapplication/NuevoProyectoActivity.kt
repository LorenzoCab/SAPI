package com.example.firebasecrudapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_proyecto.*

class NuevoProyectoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val ProyectoReference = db.collection("Proyecto")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_proyecto)
        supportActionBar?.setTitle("Alta de Proyectos")



        btn_nuevoproyecto.setOnClickListener {
        saveEditTextDataInDocument()
            val intent = Intent(this@NuevoProyectoActivity, ProyectosProfesorActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@NuevoProyectoActivity, "Se ha creado un nuevo proyecto", Toast.LENGTH_SHORT)
        }


    }

    private fun saveEditTextDataInDocument(){
        val alcances = et_alcances_np.text.toString()
        val alumno = mapOf("ncontrol" to et_alumnonc_np.text.toString().toInt(), "nombre" to et_alumnos_np.text.toString())
        val areasConoc = et_areaconoc_np.text.toString()
        val asignaturas = et_asignaturas_np.text.toString()
        val cliente = et_cliente_np.text.toString()
        val colaboradores = et_colaboradores_np.text.toString()
        val compDes = et_compDes_np.text.toString()
        val compPrev = et_compPrev_np.text.toString()
        val coordinador = et_coordinador_np.text.toString()
        val departamentos  = et_departamentos_np.text.toString()
        val impacto = et_impacto_np.text.toString()
        val institucion = et_institucion_np.text.toString()
        val justificacion = et_justificacion_np.text.toString()
        val limityrest = et_limitaciones_np.text.toString()
        val materiaEje = et_materiaeje_np.text.toString()

        val periodo = mapOf("fin" to et_fechafin_np.text.toString(), "inicio" to et_fechainicio_np.text.toString())

        val plan = et_planesestudio_np.text.toString()
        val planteamiento = et_planteamiento_np.text.toString()
        val profeResp = et_profresponsables_np.text.toString()
        val tipoejec = et_ejecuciones_np.text.toString()
        val tituloproy = et_titulo_np.text.toString()


        var proyectoEntrie = HashMap<String, Any>()
        proyectoEntrie["alcances"] =alcances
        proyectoEntrie["alumnos"] =alumno
        proyectoEntrie["areasConoc"] =areasConoc
        proyectoEntrie["asignaturas"] =asignaturas
        proyectoEntrie["cliente"] =cliente
        proyectoEntrie["colaboradores"] =colaboradores
        proyectoEntrie["compDes"] =compDes
        proyectoEntrie["compPrev"] =compPrev
        proyectoEntrie["departamentos"] =departamentos
        proyectoEntrie["impacto"] =impacto
        proyectoEntrie["institucion"] =institucion
        proyectoEntrie["justificacion"] =justificacion
        proyectoEntrie["limityrest"] =limityrest
        proyectoEntrie["materiaEje"] =materiaEje
        proyectoEntrie["periodo"] =periodo
        proyectoEntrie["plan"] =plan
        proyectoEntrie["planteamiento"] =planteamiento
        proyectoEntrie["profresp"] =profeResp
        proyectoEntrie["tipoejec"] =tipoejec
        proyectoEntrie["tituloproyecto"] =tituloproy
        proyectoEntrie["coordinador"] = coordinador

        ProyectoReference.document(tituloproy)
            .set(proyectoEntrie)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot succesfully written!")
                Toast.makeText(this@NuevoProyectoActivity, "Se ha añadido un nuevo Registro a la colección Proyecto", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }
}