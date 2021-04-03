package com.example.youraccounting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import org.w3c.dom.Text

class EgressAdapter(egresses:List<Egress>): RecyclerView.Adapter<EgressAdapter.ViewHolder>(), View.OnClickListener {
    var egresses:List<Egress>? = null
    var listener: View.OnClickListener? = null
    init {
        this.egresses = egresses
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return this.egresses?.count() ?: 0
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fecha?.text = egresses?.get(position)?.date.toString()
        holder.proveedor?.text = egresses?.get(position)?.proveedor
        holder.descripcion?.text = egresses?.get(position)?.descripcion
        holder.valor?.text = egresses?.get(position)?.valor.toString()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var fecha : TextView? = null
        var proveedor: TextView? = null
        var descripcion : TextView? = null
        var valor : TextView? = null
        init {
            this.fecha = itemView.text_view_fecha
            this.proveedor = itemView.text_view_proveedor
            this.descripcion = itemView.text_view_descripcion
            this.valor = itemView.text_view_valor
        }
    }

    fun setOnclickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        if(listener != null){
            listener?.onClick(v)
        }
    }
}


