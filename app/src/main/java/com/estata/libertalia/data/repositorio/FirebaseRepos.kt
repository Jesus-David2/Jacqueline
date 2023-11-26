package com.estata.libertalia.data.repositorio

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.estata.libertalia.R
import com.estata.libertalia.data.modelo.Pedido
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class FirebaseRepos {
    val dataBase = FirebaseFirestore.getInstance() //db
    val autentificacion = FirebaseAuth.getInstance() //aut
    val dataBaseStorage = FirebaseStorage.getInstance() //ds

    //Usuario
    var user = Usuario("", ArrayList<Pedido>())

    //Producto
    var producto = Producto("", "", "", "", 0.0, ArrayList<String>(), ArrayList<String>(), ArrayList<String>())

    //Añadir usuario nuevo
    fun addUser(pedios: ArrayList<Pedido>, contexto: Context) {
        var id = getIdUsuario()
        user.id = id
        if (pedios.isNotEmpty()) { user.pedidos = pedios }

        dataBase.collection("usuarios").document(id).set(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(contexto, "Datos actualizados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(contexto, "No se han actualizado los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Obtener id del usuario
    fun getIdUsuario(): String {
        var id = autentificacion.currentUser?.uid.toString()
        return id
    }

    //Obtener producto por id
    suspend fun getProducto(id:String): Producto? = withContext(Dispatchers.IO) {
        val resultado = dataBase.collection("productos").document(id).get().await()
        if(resultado.exists()) {
            val producto = resultado.toObject(Producto::class.java)
            producto
        } else {
            null
        }
    }

    //Añadir producto
    fun addProducto(nombre: String, descripcion: String, categorias: String,
                    precio: Double, tallas: ArrayList<String>, colores: ArrayList<String>,
                    imagenes: ArrayList<Uri>, contexto: Context) {
        var id = UUID.randomUUID().toString()
        dataBase.collection("productos").document(id).get().addOnSuccessListener { datos ->
            if (id.isNotEmpty()) { producto.id = id}
            if (nombre.isNotEmpty()) { producto.nombre = nombre}
            if (descripcion.isNotEmpty()) { producto.descipcion = descripcion }
            if (categorias.isNotEmpty()) { producto.categorias = categorias }
            if (precio != 0.0) { producto.precio = precio }
            if (tallas.isNotEmpty()) { producto.tallas = tallas }
            if (colores.isNotEmpty()) { producto.colores = colores }

            dataBase.collection("productos").document(id).set(producto).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(contexto, "Datos actualizados", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(contexto, "No se han actualizado los datos", Toast.LENGTH_SHORT).show()
                }
            }

            GlobalScope.launch(Dispatchers.IO) {
                addImageProducto(imagenes, id, contexto)
            }
        }
    }

    //Añadir imagen al producto
    suspend fun addImageProducto(imagenes: ArrayList<Uri>, id: String, contexto: Context){
        var urls = mutableListOf<String>()
        var num_image = 0
        var contextResolver = contexto.contentResolver

        var producto = getProducto(id)

        if (producto!!.imagenes.isNotEmpty()){
            urls = producto!!.imagenes
        }

        for(image in imagenes){
            num_image += 1
            var id_product = id + "_" + num_image

            val reference = dataBaseStorage.reference.child("almacenimags").child(id_product + "_prod_" + contextResolver.getType(image))
            reference.putFile(image).await()
            val url = reference.downloadUrl.await().toString()
            urls.add(url)
        }

        dataBase.collection("productos").document(id).update("imagenes", urls).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(contexto, "Producto subido", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(contexto, "No se ha guardado el producto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Obtener todos los productos
    suspend fun getAllProductos(): MutableList<Producto> = withContext(Dispatchers.IO){
        var listaProducto = mutableListOf<Producto>()

        dataBase.collection("productos").get().await().forEach { data ->
            val producto = data.toObject(Producto::class.java)
            listaProducto.add(producto)
        }
        listaProducto
    }

    //Eliminar producto
    fun deleteProducto(id: String) {
        dataBase.collection("productos").document(id).delete()
    }

    //Editar producto
    fun updateProducto(nombre: String, descripcion: String, categorias: String,
                       precio: Double, tallas: ArrayList<String>, colores: ArrayList<String>,
                       imagenes: ArrayList<Uri>, id: String, contexto: Context) {
        dataBase.collection("productos").document(id).update("nombre", nombre)
        dataBase.collection("productos").document(id).update("descipcion", descripcion)
        dataBase.collection("productos").document(id).update("categorias", categorias)
        dataBase.collection("productos").document(id).update("precio", precio)
        dataBase.collection("productos").document(id).update("tallas", tallas)
        dataBase.collection("productos").document(id).update("colores", colores)

        var updateImg = GlobalScope.launch(Dispatchers.IO) {
            addImageProducto(imagenes, id, contexto)
        }
    }

    //Obtener los pedidos que ya han sido entregados
    suspend fun getPedidosHistorial(): MutableList<Pedido> = withContext(Dispatchers.IO) {
        var listaPedido = mutableListOf<Pedido>()

        dataBase.collection("pedidos").whereEqualTo("estado", "entregado").get().await().forEach { data ->
            val pedido = data.toObject(Pedido::class.java)
            listaPedido.add(pedido)
        }
        listaPedido
    }

    //Obtener los pedidos que no se han entregados
    suspend fun getPedidosEstado(): MutableList<Pedido> = withContext(Dispatchers.IO) {
        var listaPedido = mutableListOf<Pedido>()

        dataBase.collection("pedidos").whereNotEqualTo("estado", "entregado").get().await().forEach { data ->
            val pedido = data.toObject(Pedido::class.java)
            listaPedido.add(pedido)
        }
        listaPedido
    }
}