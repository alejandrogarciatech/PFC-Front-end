package com.pfc.android.revisionesapp.models

import java.io.Serializable

data class Usuario(
    var id: Long,
    var nombre: String,
    var apellido: String,
    var fechaNacimiento: String,
    var rol: String,
    var telefono: String,
    var email: String,
    var username: String,
    var password: String
) : Serializable
