package com.pfc.android.revisionesapp.models

import java.io.Serializable

data class Equipo(
    var id: String,
    var nombre: String,
    var tipoProducto: String,
    var marca: String,
    var modelo: String,
    var nSerie: String,
    var peso: Double,
    var dimensiones: Double,
    var ubicacion: String
) : Serializable
