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
    RecyclerView.Adapter<EquipoAdapter.MyHolder>() {

    interface OnItemClickListener {
        fun onItemClick(equipo: Equipo)
    }

    lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {
        var id: TextView = item.findViewById(R.id.idEquipoTextView)
        var nombre: TextView = item.findViewById(R.id.nombreEquipoTextView)
        var tipoProducto: TextView = item.findViewById(R.id.tipoProductoEquipoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista: View = LayoutInflater.from(contexto).inflate(R.layout.item_equipo, parent, false)
        return MyHolder(vista)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val equipo: Equipo = lista[position]
        holder.id.text = equipo.id
        holder.nombre.text = equipo.nombre
        holder.tipoProducto.text = equipo.tipoProducto.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(equipo)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}