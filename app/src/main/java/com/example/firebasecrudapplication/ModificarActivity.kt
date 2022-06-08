package com.example.firebasecrudapplication

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebasecrudapplication.models.Proyecto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_detalles_profesor.*
import kotlinx.android.synthetic.main.activity_modificar.*
import kotlinx.android.synthetic.main.activity_nuevo_proyecto.*


class ModificarActivity : AppCompatActivity() {
    lateinit var proyectoactual :Proyecto
    private val db = FirebaseFirestore.getInstance()
    private val ProyectoReference = db.collection("Proyecto")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar)
        supportActionBar?.setTitle("Modificar registro")

        val ref_proyectoactual = intent.getStringExtra("mod")
        Log.d(TAG, "Title of modifiable project: " + ref_proyectoactual)

        populateEditViews()
        btn_confirmar_mod.setOnClickListener()
        {
            saveEditTextDataInDocument()
            finish()

        }
    }

    private fun populateEditViews(){

        val proyecto_ref_pr= intent.getStringExtra("mod").toString()



        var document = db.collection("Proyecto").document(proyecto_ref_pr)
        document.get().addOnSuccessListener{
            proyectoactual = it.toObject<Proyecto>()!!
            Log.d(TAG, "Current project: "+ proyectoactual)

            val alumnomap : Map<String, Any> = proyectoactual.alumnos
            val periodomap : Map<String, Any>? = proyectoactual.periodo


            et_alumnos_mod.setText(alumnomap.get("nombre").toString())
            et_alumnonc_mod.setText(alumnomap.get("ncontrol").toString())
            et_titulo_mod.setText(proyectoactual.tituloproyecto.toString())
            et_institucion_mod.setText(proyectoactual.institucion.toString())
            et_colaboradores_mod.setText(proyectoactual.colaboradores.toString())
            et_departamentos_mod.setText(proyectoactual.departamentos.toString())
            et_asignatura_mod.setText(proyectoactual.asignaturas.toString())
            et_cliente_mod.setText(proyectoactual.cliente.toString())
            et_compDes_mod.setText(proyectoactual.compDes.toString())
            et_compPrev_mod.setText(proyectoactual.compPrev.toString())
            et_areaconoc_mod.setText(proyectoactual.areasConoc.toString())
            et_ejecuciones_mod.setText( proyectoactual.tipoejec.toString())
            et_materiaeje_mod.setText(proyectoactual.materiaEje.toString())
            et_limitaciones_mod.setText(proyectoactual.limityrest.toString())
            et_tipoproy_mod.setText(proyectoactual.tipoejec.toString())
            et_planesestudio_mod.setText(proyectoactual.plan.toString())
            et_fechainicio_mod.setText(periodomap?.get("inicio").toString())
            et_fechafin_mod.setText(periodomap?.get("fin").toString())
            et_planteamiento_mod.setText(proyectoactual.planteamiento.toString())
            et_alcances_mod.setText(proyectoactual.alcances.toString())
            et_justificacion_mod.setText(proyectoactual.justificacion.toString())
            et_profresponsables_mod.setText(proyectoactual.profeResp.toString())
            et_impacto_mod.setText(proyectoactual.impacto.toString())
            et_coordinador_mod.setText(proyectoactual.coordinador.toString())
        }

    }
    private fun saveEditTextDataInDocument(){
        val alcances = et_alcances_mod.text.toString()
        val alumno = mapOf("ncontrol" to et_alumnonc_mod.text.toString().toInt(), "nombre" to et_alumnos_mod.text.toString())

        val areasConoc = et_areaconoc_mod.text.toString()
        val asignaturas = et_asignatura_mod.text.toString()
        val cliente = et_cliente_mod.text.toString()
        val colaboradores = et_colaboradores_mod.text.toString()
        val compDes = et_compDes_mod.text.toString()
        val compPrev = et_compPrev_mod.text.toString()

        val departamentos  = et_departamentos_mod.text.toString()
        val impacto = et_impacto_mod.text.toString()
        val institucion = et_institucion_mod.text.toString()
        val justificacion = et_justificacion_mod.text.toString()
        val limityrest = et_limitaciones_mod.text.toString()
        val materiaEje = et_materiaeje_mod.text.toString()

        val periodo = mapOf("fin" to et_fechafin_mod.text.toString(), "inicio" to et_fechainicio_mod.text.toString())

        val plan = et_planesestudio_mod.text.toString()
        val planteamiento = et_planteamiento_mod.text.toString()
        val profeResp = et_profresponsables_mod.text.toString()
        val tipoejec = et_ejecuciones_mod.text.toString()
        val tituloproy = et_titulo_mod.text.toString()
        val coordinador = et_coordinador_mod.text.toString()

        var proyectoEntrie = HashMap<String, Any>()
        proyectoEntrie["alcances"] =alcances
        proyectoEntrie["alumnos"] =alumno
        proyectoEntrie["areasConoc"] =areasConoc
        proyectoEntrie["asignaturas"] =asignaturas
        proyectoEntrie["cliente"] =cliente
        proyectoEntrie["colaboradores"] =colaboradores
        proyectoEntrie["compDes"] =compDes
        proyectoEntrie["compPrev"] =compPrev
        proyectoEntrie["coordinador"] = coordinador
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


        ProyectoReference.document(tituloproy)
            .set(proyectoEntrie)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot succesfully written!")
                Toast.makeText(this@ModificarActivity, "Proyecto Modificado! Vuelva a iniciar sesion para ver cambios reflejados.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }
}