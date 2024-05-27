package com.pfc.android.revisionesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pfc.android.revisionesapp.R
import com.pfc.android.revisionesapp.models.Equipo

class EquipoAdapter(var lista: ArrayList<Equipo>, var contexto: Context) :
    RecyclerView.Adapter<EquipoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.idEquipoTextView)
        var nombre: TextView = itemView.findViewById(R.id.nombreEquipoTextView)
        var tipoProducto: TextView = itemView.findViewById(R.id.tipoProductoEquipoTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(contexto).inflate(R.layout.item_equipo, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val equipo: Equipo = lista[position]
        viewHolder.id.text = equipo.id
        viewHolder.nombre.text = equipo.nombre
        viewHolder.tipoProducto.text = equipo.tipoProducto.toString()

        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(equipo)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    interface OnItemClickListener {
        fun onItemClick(equipo: Equipo)
    }

    lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun updateList(lista: List<Equipo>?) {
        this.lista = lista as ArrayList<Equipo>
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        lista.removeAt(position)
        notifyItemRemoved(position)
    }
}