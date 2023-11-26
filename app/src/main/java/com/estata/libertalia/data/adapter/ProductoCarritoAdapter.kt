package com.estata.libertalia.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estata.libertalia.R
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.viewholder.ProductoCarritoViewHolder

//(private var productoList: ArrayList<Producto>, private var cantidad: String)
class ProductoCarritoAdapter (private var productoList: ArrayList<Producto>): RecyclerView.Adapter<ProductoCarritoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoCarritoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoCarritoViewHolder(layoutInflater.inflate(R.layout.producto_carrito, parent, false))
    }

    override fun onBindViewHolder(holder: ProductoCarritoViewHolder, position: Int) {
        val item = productoList[position]
        //holder.bind(item, cantidad)
        holder.bind(item)
    }

    override fun getItemCount(): Int = productoList.size

    fun updateData(dataList: ArrayList<Producto>) {
        this.productoList = dataList
        notifyDataSetChanged()
    }
}