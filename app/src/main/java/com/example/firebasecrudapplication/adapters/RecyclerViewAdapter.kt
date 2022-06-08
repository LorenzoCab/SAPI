package com.example.firebasecrudapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrudapplication.R
import com.example.firebasecrudapplication.models.Proyecto

class RecyclerViewAdapter (private val projectList : ArrayList<Proyecto>, private val onProyectoClickListener: OnProyectoClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val proyecto : Proyecto = projectList[position]

        holder.tituloproyecto.text = ("Proyecto: " + proyecto.tituloproyecto)
        holder.coordinador.text = ("Coordinador " + proyecto.coordinador)
        holder.cliente.text = ("Cliente: " + proyecto.cliente)

        holder.itemView.setOnClickListener{
            onProyectoClickListener.onProyectoItemClicked(position)
        }

    }

    override fun getItemCount(): Int {

       return  projectList.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tituloproyecto : TextView = itemView.findViewById(R.id.tv_titulo_rv)
        val coordinador: TextView = itemView.findViewById(R.id.tv_coordinador_rv)
        val cliente: TextView = itemView.findViewById(R.id.tv_cliente_rv)
    }
}