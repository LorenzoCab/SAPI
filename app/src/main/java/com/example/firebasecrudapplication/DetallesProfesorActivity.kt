package com.example.firebasecrudapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebasecrudapplication.models.Proyecto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_detalles_alumno.*
import kotlinx.android.synthetic.main.activity_detalles_profesor.*
import kotlin.math.log

class DetallesProfesorActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var builder : AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_profesor)
        builder = AlertDialog.Builder(this)

        val proyecto_ref_pr = intent.getStringExtra("ref").toString()

//        val newintent = Intent(this@DetallesProfesorActivity, ProyectosProfesorActivity::class.java)


        getSelectedProjectQuery()
        btn_modificar.setOnClickListener{
            val intent = Intent(this@DetallesProfesorActivity, ModificarActivity::class.java)
            intent.putExtra("mod", proyecto_ref_pr)
            startActivity(intent)
        }

        btn_eliminar.setOnClickListener{
            builder.setTitle("Confirmación")
                .setMessage("¿Seguro que desea eliminar este proyecto?")
                .setCancelable(true)
                .setPositiveButton("Yes"){ dialogInterface, it ->
                    deleteCurrentProject()
                    finish()
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }

    }

    private fun getSelectedProjectQuery()
    {
        var proyectoactual : Proyecto
        val proyecto_ref_pr = intent.getStringExtra("ref").toString()
        Log.d(TAG, "Se ha recibido el valor: "+ proyecto_ref_pr)

        var document = db.collection("Proyecto").document(proyecto_ref_pr)
            document.get().addOnSuccessListener{
                proyectoactual = it.toObject<Proyecto>()!!
                Log.d(TAG, "Current project: "+ proyectoactual)

                tv_tituloproyecto_pr.setText("Título: "+ proyectoactual.tituloproyecto.toString())
                tv_inst_pr.setText("Institución: " +  proyectoactual.institucion.toString())
                tv_coord_pr.setText("Coordinador: " + proyectoactual.coordinador.toString())
                tv_colab_pr.setText("Colaborador(es): " + proyectoactual.colaboradores.toString())
                tv_cliente_pr.setText("Cliente: " + proyectoactual.cliente.toString())
                tv_dep_pr.setText(proyectoactual.departamentos.toString())
                tv_asign_pr.setText("Nombre: "+proyectoactual.asignaturas.toString())
                tv_compdes_pr.setText(proyectoactual.compDes.toString())
                tv_compprev_pr.setText(proyectoactual.compPrev.toString())
                tv_areacon_pr.setText("Area de conocimiento:  "+proyectoactual.areasConoc.toString())
                tv_tipoejec_pr.setText("Tipo de ejecución:  "+ proyectoactual.tipoejec.toString())
                tv_mateje_pr.setText("Materia Eje:  " + proyectoactual.materiaEje.toString())
                tv_justificacion_pr.setText("Justificacion: "+ proyectoactual.justificacion.toString())
                tv_impacto_pr.setText("Impacto: "+ proyectoactual.impacto.toString())
                tv_limres_pr.setText("Limites y Restricciones:  "+proyectoactual.limityrest.toString())
                tv_tipopr_pr.setText("TIpo de Proyecto:  " + proyectoactual.tipoejec.toString())


            }

    }

    private fun deleteCurrentProject(){
        val proyecto_ref_pr = intent.getStringExtra("ref").toString()
            db.collection("Proyecto").document(proyecto_ref_pr)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot succesfully deleted!")
                    Toast.makeText(this@DetallesProfesorActivity, "El proyecto ha sido eliminado con éxito", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{e -> Log.w(TAG, "Error deleting document", e)}
    }


}