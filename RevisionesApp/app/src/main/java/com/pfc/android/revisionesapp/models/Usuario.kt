package com.pfc.android.revisionesapp.models

import java.io.Serializable

data class Usuario(
    var id: Long,
    var nombre: String,
    var apellido: String,
    var puesto: String,
    var correo: String,
    var contrasena: String,
    var telefono: String
) : Serializable