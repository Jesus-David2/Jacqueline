package com.estata.libertalia.data.modelo

data class Producto(
    var id: String = "",
    var nombre: String = "",
    var descipcion: String = "",
    var categorias: String = "",
    var precio:Double = 0.0,
    var tallas: ArrayList<String> = ArrayList(),
    var colores: ArrayList<String> = ArrayList(),
    var imagenes: ArrayList<String> = ArrayList()
) : java.io.Serializable
