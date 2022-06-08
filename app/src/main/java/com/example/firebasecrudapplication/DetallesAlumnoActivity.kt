package com.example.firebasecrudapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebasecrudapplication.models.Proyecto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_detalles_alumno.*
import kotlinx.android.synthetic.main.activity_detalles_profesor.*

class DetallesAlumnoActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_alumno)
        supportActionBar?.setTitle("Detalles de Proyecto")

        getSelectedProjectQuery()
    }

    private fun getSelectedProjectQuery() {
        var proyectoactual: Proyecto
        val proyecto_ref_al = intent.getStringExtra("ref").toString()
        Log.d(ContentValues.TAG, "Se ha recibido el valor: " + proyecto_ref_al)

        val document = db.collection("Proyecto").document(proyecto_ref_al)
        document.get().addOnSuccessListener {
            proyectoactual = it.toObject<Proyecto>()!!
            Log.d(ContentValues.TAG, "Current project: " + proyectoactual)

            tv_tituloproyecto_al.setText("Título: " + proyectoactual.tituloproyecto.toString())
            tv_inst_al.setText("Institución: " + proyectoactual.institucion.toString())
            tv_coord_al.setText("Coordinador" + proyectoactual.coordinador.toString())
            tv_colab_al.setText("Colaborador(es): " + proyectoactual.colaboradores.toString())
            tv_cliente_al.setText("Cliente: " + proyectoactual.cliente.toString())
            tv_dep_al.setText(proyectoactual.departamentos.toString())
            tv_asign_al.setText("Nombre: " + proyectoactual.asignaturas.toString())
            tv_compdes_al.setText(proyectoactual.compDes.toString())
            tv_compprev_al.setText(proyectoactual.compPrev.toString())
            tv_justificacion_al.setText("Justificacion: "+ proyectoactual.justificacion.toString())
            tv_impacto_al.setText("Impacto: "+ proyectoactual.impacto.toString())
            tv_areacon_al.setText("Area de conocimiento: " + proyectoactual.areasConoc.toString())
            tv_tipoejec_al.setText("Tipo de ejecución: " + proyectoactual.tipoejec.toString())
            tv_mateje_al.setText("Materia Eje: " + proyectoactual.materiaEje.toString())
            tv_limres_al.setText("Limites y Restricciones: " + proyectoactual.limityrest.toString())
            tv_tipopr_al.setText("TIpo de Proyecto: " + proyectoactual.tipoejec.toString())


        }

    }
}