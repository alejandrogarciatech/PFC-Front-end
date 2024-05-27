package com.pfc.android.revisionesapp.models

import java.io.Serializable

data class Incidencia(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var estado: String,
    var prioridad: String,
    var idAlbaran: String,
    var idUsuario: String,
    var createAt: String,
    var updateAt: String,
    //var equipoId: String,
//    var imagen: Image
) : Serializable