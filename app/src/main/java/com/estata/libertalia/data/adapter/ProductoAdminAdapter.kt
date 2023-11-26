package com.estata.libertalia.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.interfaces.OnItemClickListener
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.viewholder.ProductoViewHolder

class ProductoAdminAdapter(private var productoList: ArrayList<Producto>, private val listener: OnItemClickListener): RecyclerView.Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.producto_admin, parent, false))
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item = productoList[position]
        holder.bind(item, productoList)

        holder.itemView.setOnClickListener {
            holder.itemView.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = productoList.size

    fun updateData(dataList: ArrayList<Producto>) {
        this.productoList = dataList
        notifyDataSetChanged()
    }
}