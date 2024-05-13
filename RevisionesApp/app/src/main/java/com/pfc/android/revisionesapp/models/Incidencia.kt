package com.pfc.android.revisionesapp.models

import android.media.Image
import java.io.Serializable
import java.util.Date

data class Incidencia(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var estado: String,
    var prioridad: String,
    var idAlbaran: Int,
    var idUsuario: Int,
    var fechaCreacion: Date,
    var fechaActualizacion: Date,
    var equipoId: String,
    var imagen: Image
) : Serializable