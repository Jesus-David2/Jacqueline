package com.estata.libertalia.data.interfaces

import com.estata.libertalia.data.modelo.Producto

interface OnItemClickListener {
    fun onItemClick(producto: Producto)
}