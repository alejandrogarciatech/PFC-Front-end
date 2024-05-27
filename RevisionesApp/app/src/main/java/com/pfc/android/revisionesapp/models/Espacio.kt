package com.pfc.android.revisionesapp.models

import java.io.Serializable

data class Espacio(
    var id: Int,
    var nombre: String,
    var ubicacion: String,
    //var url: String
) : Serializable

