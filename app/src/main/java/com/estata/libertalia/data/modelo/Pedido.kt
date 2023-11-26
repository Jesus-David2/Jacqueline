package com.estata.libertalia.data.modelo

data class Pedido(
    var id: String = "",
    var estado: String = "",
    var productos: ArrayList<String> = ArrayList(),
    var precioTotal: String = "",
    var totalProductos: String = ""
)
