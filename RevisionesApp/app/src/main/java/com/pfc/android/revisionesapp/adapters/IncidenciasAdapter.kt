package com.pfc.android.revisionesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.models.Incidencia

class IncidenciasAdapter(var lista: ArrayList<Incidencia>, var contexto: Context) :
    RecyclerView.Adapter<IncidenciasAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagen: ImageView = itemView.findViewById(R.id.item_imagen)
        var nombre: TextView = itemView.findViewById(R.id.item_titulo)
        var descripcion: TextView = itemView.findViewById(R.id.item_detalle)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(contexto).inflate(R.layout.card_incidencia, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val incidencia: Incidencia = lista[position]
        //viewHolder.imagen.setImageResource(incidencia.imagen)
        viewHolder.nombre.text = incidencia.nombre
        viewHolder.descripcion.text = incidencia.descripcion

        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(incidencia)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    interface OnItemClickListener {
        fun onItemClick(incidencia: Incidencia)
    }

    lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun updateList(lista: List<Incidencia>?) {
        this.lista = lista as ArrayList<Incidencia>
        notifyDataSetChanged()
    }
}