package com.estata.libertalia.ui.fragments

import android.app.Activity
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
import com.estata.libertalia.data.repositorio.FirebaseRepos
import com.squareup.picasso.Picasso

class ProductoNuevoFragment : Fragment() {
    private val GALLERY_INTENT = 1
    lateinit var repos: FirebaseRepos
    var listaUrls: ArrayList<Uri> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto_nuevo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addImg(view)
        iniciar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            //Inicializacion de elementos de la vista
            val edtNombre: EditText = requireView().findViewById(R.id.edtNombre)
            val edtDescripcion: EditText = requireView().findViewById(R.id.edtDescripcion)
            val edtCategorias: EditText = requireView().findViewById(R.id.edtCategorias)
            val edtPrecio: EditText = requireView().findViewById(R.id.edtPrecio)
            val edtTallas: EditText = requireView().findViewById(R.id.edtTallas)
            val edtColores: EditText = requireView().findViewById(R.id.edtColores)
            val ivImagen: ImageView = requireView().findViewById(R.id.ivImagen)

            //Combrueba si hay una o mas tallas
            var tallas = ArrayList<String>()
            if (edtTallas.text.toString().contains(",")) {
                tallas = edtTallas.text.toString().split(",") as ArrayList<String>
            } else {
                tallas.add(edtTallas.text.toString())
            }

            //Combrueba si hay una o mas colores
            var colores  = ArrayList<String>()
            if (edtColores.text.toString().contains(",")) {
                colores = edtColores.text.toString().split(",") as ArrayList<String>
            } else {
                colores.add(edtColores.text.toString())
            }

            //Convierte el String en Double
            val precio = edtPrecio.text.toString().toDouble()

            //Añade las URLs de las imagenes a la lista
            val imgUri = data?.data
            listaUrls.add(imgUri!!)

            Picasso.get().load(listaUrls[0]).into(ivImagen)

            repos = FirebaseRepos()
            repos.addProducto(edtNombre.text.toString(), edtDescripcion.text.toString(), edtCategorias.text.toString(),
                precio, tallas, colores, listaUrls, requireContext())
        }
    }

    //añadir imagen
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

    fun iniciar () {
        //Inicializacion de elemento de la vista
        val btnAñadir: Button = requireView().findViewById(R.id.btnAñadir)

        //Listener para cambiar de pantalla
        btnAñadir.setOnClickListener{
            view?.findNavController()?.navigate(R.id.adminFragment)
        }
    }


}