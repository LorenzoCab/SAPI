package com.example.firebasecrudapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*




class LoginActivity : AppCompatActivity() {


    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setTitle("Administrador de Proyectos Integradores") // Agrega el título en la Barra de la Activity

        val spinner = findViewById<Spinner>(R.id.sp_modulo)  //variable de correlación con el spinner
        val list = listOf("Alumno", "Profesor") //valores del Spinner

        val adapter_modulos =  ArrayAdapter(this, android.R.layout.simple_spinner_item, list) //ArrayAdaptes que otorga valores al Spinner
        spinner.adapter=adapter_modulos //Se asigna el adaptador al spinner



        btn_login.setOnClickListener{
            when
            {
                TextUtils.isEmpty(et_email_log.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity, "Por favor introduzca un correo electrónico", Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_password_log.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity, "Por favor introduzca una contraseña", Toast.LENGTH_SHORT
                    ).show()
                }
                else ->
                {
                    val email: String = et_email_log.text.toString().trim { it <= ' ' }
                    val password: String = et_password_log.text.toString().trim { it <= ' ' }

                    //Se inicia sesión en Firebase utilizando el email y password en la Activity
                    firebaseLogin(email, password)

                }

            }

        }


    }

    private fun firebaseLogin(email: String, password: String) {


        var comprobante : Boolean = false
        val collectionSelected = sp_modulo.selectedItem.toString()
        val selectedDocument = et_email_log.text.toString()
        db.collection(collectionSelected).document(selectedDocument).get().addOnSuccessListener {
            comprobante = it.get("isprof") as Boolean
        }


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val firebaseUser: FirebaseUser = task.result!!.user!!

                    Toast.makeText(
                        this@LoginActivity,
                        "Se ha iniciado sesión correctamente.",
                        Toast.LENGTH_SHORT
                    ).show()

//
                    if ((sp_modulo.selectedItem.toString() == "Alumno") && comprobante == false){
                        val intent = Intent(this@LoginActivity, ProyectosAlumnoActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()


                    }
//
                    else if (sp_modulo.selectedItem.toString() == "Profesor" && comprobante ==true)
                    {
                        val intent = Intent(this@LoginActivity, ProyectosProfesorActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()

                    }


                } else {
                    //EN caso de no ser exitoso el registro, se muestra un toast de error.
                    Toast.makeText(
                        this@LoginActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

//     fun loginReferences (db: FirebaseFirestore)
//    {
//        db.collection("Profesores").document("PruebaProf").get().addOnSuccessListener {
//
//            comprobacion = it.get("isProfesor") as Boolean
//
//        }
//
//
//        val db = this.db
//        val query = db.collection("Profesores").whereEqualTo("email",et_email_log.text.toString().trim{it <= ' '})
//    }

}