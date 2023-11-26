package com.estata.libertalia.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.findNavController
import com.estata.libertalia.R
import com.estata.libertalia.data.modelo.Producto
import com.estata.libertalia.data.repositorio.FirebaseRepos
import com.squareup.picasso.Picasso

class EditarProductoFragment : Fragment() {
    private var producto : Producto? = null
    private val GALLERY_INTENT = 1
    lateinit var repos: FirebaseRepos
    var listaUris: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            producto = it.getSerializable("productos") as Producto
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addImg(view)
        iniciar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Añade las URLs de las imagenes a la lista
        val imgUri = data?.data
        listaUris.add(imgUri!!)
    }

    fun iniciar() {
        //Inicializacion de elementos de la vista
        val btnEditar: Button = requireView().findViewById(R.id.btnEditar)
        val edtNombre: EditText = requireView().findViewById(R.id.edtNombre)
        val edtDescripcion: EditText = requireView().findViewById(R.id.edtDescripcion)
        val edtCategorias: EditText = requireView().findViewById(R.id.edtCategorias)
        val edtPrecio: EditText = requireView().findViewById(R.id.edtPrecio)
        val edtTallas: EditText = requireView().findViewById(R.id.edtTallas)
        val edtColores: EditText = requireView().findViewById(R.id.edtColores)
        val ivImagen: ImageView = requireView().findViewById(R.id.ivImagen)

        //Insertar valores en los campos
        edtNombre.setText(producto?.nombre)
        edtDescripcion.setText(producto?.descipcion)
        edtCategorias.setText(producto?.categorias)
        edtPrecio.setText(producto?.precio.toString())
        edtTallas.setText(producto?.tallas.toString())
        edtColores.setText(producto?.colores.toString())
        Picasso.get().load(producto!!.imagenes[0]).into(ivImagen)

        //Listener para cambiar los datos y regresar a la pantalla de admin
        btnEditar.setOnClickListener{
            //Combrueba si hay una o mas tallas
            var tallas = ArrayList<String>()
            if (edtTallas.text.toString().contains(",")) {
                val tallasLimpia = edtTallas.text.toString().replace("[", "").replace("]", "")
                tallas = tallasLimpia.split(",") as ArrayList<String>
            } else {
                val tallasLimpia = edtTallas.text.toString().replace("[", "").replace("]", "")
                tallas.add(tallasLimpia)
            }

            //Combrueba si hay una o mas colores
            var colores  = ArrayList<String>()
            if (edtColores.text.toString().contains(",")) {
                val coloLimpia = edtColores.text.toString().toLowerCase().replace("[", "").replace("]", "")
                colores = coloLimpia.split(",") as ArrayList<String>
            } else {
                val coloLimpia = edtColores.text.toString().toLowerCase().replace("[", "").replace("]", "")
                colores.add(coloLimpia)
            }

            //Convierte el String en Double
            val precio = edtPrecio.text.toString().toDouble()

            repos = FirebaseRepos()
            repos.updateProducto(edtNombre.text.toString(), edtDescripcion.text.toString(), edtCategorias.text.toString(), precio,
                tallas, colores, listaUris, producto?.id.toString(), requireContext())

            view?.findNavController()?.navigate(R.id.adminFragment)
        }
    }

    fun addImg (view: View) {
        //Inicializacion de elemento de la vista
        val ivImagen: ImageView = view.findViewById(R.id.ivImagen)

        //Listener del icono de agregar imagen
        ivImagen.setOnClickListener{
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, GALLERY_INTENT)
        }
    }
}