package com.estata.libertalia.data.modelo

data class Usuario(
    var id: String = "",
    var pedidos: ArrayList<Pedido> = ArrayList()
)
