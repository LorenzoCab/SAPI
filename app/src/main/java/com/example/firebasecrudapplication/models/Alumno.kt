package com.example.firebasecrudapplication.models

data class Alumno
    (
    val departamento : String ?= null,
    val email : String ?= null,
    val isProfesor: Boolean = false,
    val ncontrol: Int ?= null,
    val nombre: String ?= null,
    val password: String ?= null,
    val semestre: Int ?= null

)
