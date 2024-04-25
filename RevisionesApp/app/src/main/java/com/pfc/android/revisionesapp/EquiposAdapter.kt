package com.pfc.android.revisionesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pfc.android.revisionesapp.modelos.Equipo

class EquiposAdapter(private val equiposList: List<Equipo>) : RecyclerView.Adapter<EquiposAdapter.EquipoViewHolder>() {

    inner class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEquipoTextView: TextView = itemView.findViewById(R.id.idEquipoTextView)
        val nombreEquipoTextView: TextView = itemView.findViewById(R.id.nombreEquipoTextView)
        val tipoProductoEquipoTextView: TextView = itemView.findViewById(R.id.tipoProductoEquipoTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val equipo = equiposList[position]
                    listener?.onItemClick(equipo)
                }
            }
        }
        // Agrega más TextViews según sea necesario para otros atributos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_equipo, parent, false)
        return EquipoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val currentItem = equiposList[position]
        holder.idEquipoTextView.text = currentItem.id
        holder.nombreEquipoTextView.text = currentItem.nombre
        holder.tipoProductoEquipoTextView.text = currentItem.tipoProducto.toString()
        // Configura más atributos aquí según sea necesario
    }

    interface OnItemClickListener{
        fun onItemClick(equipo: Equipo)
    }

    private var listener: OnItemClickListener?= null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount() = equiposList.size
}
