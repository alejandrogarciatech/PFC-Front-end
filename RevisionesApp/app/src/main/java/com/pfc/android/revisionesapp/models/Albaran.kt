package com.pfc.android.revisionesapp.models

import java.io.Serializable

data class Albaran(
    var id: String,
    var fechaSalida: String,
    var fechaEntrada: String,
    var descripcion: String,
    var cliente: String,
    var idCliente: Int,
    var idEspacio: Int
) : Serializable
