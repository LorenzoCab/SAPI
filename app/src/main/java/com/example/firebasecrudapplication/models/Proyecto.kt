package com.example.firebasecrudapplication.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.activity_nuevo_proyecto.*

data class Proyecto(
    val alcances :String ?= null,
    val alumnos : @RawValue Map<String,Any> = mapOf(),
    val areasConoc :String?= null,
    val asignaturas :String?= null,
    val cliente :String?= null,
    val colaboradores :String?= null,
    val compDes :String?= null,
    val compPrev :String?= null,
    val coordinador: String?= null,
    val departamentos  :String?= null,
    val impacto :String?= null,
    val institucion :String?= null,
    val justificacion :String?= null,
    val limityrest :String?= null,
    val materiaEje :String?= null,
    val periodo : Map<String, Any> ?= null,
    val plan :String?= null,
    val planteamiento :String ?= null,
    val profeResp :String?= null,
    val tipoejec :String?= null,
    val tituloproyecto :String ?= null

)

