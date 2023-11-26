package com.estata.libertalia.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.estata.libertalia.R

class ContactoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar(view)
    }

    private fun iniciar(view: View) {
        //Inicializacion de elementos de la vista
        val btnCorreo: TextView = view.findViewById(R.id.btnCorreo)
        val ivInsta: ImageView = view.findViewById(R.id.ivInsta)

        //Listener para mandar el correo
        btnCorreo.setOnClickListener {
            mandarEmail()
        }

        //Listener para abrir instagram
        ivInsta.setOnClickListener {
            abrirInstagram()
        }
    }

    //Mandar un correo
    fun mandarEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, "libertaliaspm@gmail.com") //Destinatario
            putExtra(Intent.EXTRA_SUBJECT, "Comision") //Asunto
        }

        try {
            startActivity(Intent.createChooser(intent, "Selecciona una aplicaccion de correo"))
        } catch (e: java.lang.Exception) {}
    }

    //Abrir instagram
    fun abrirInstagram () {
        val web: Uri = Uri.parse("https://www.instagram.com/libertaliaspm/") //url
        val intent = Intent(Intent.ACTION_VIEW, web)
        try {
            startActivity(Intent.createChooser(intent, "Selecciona un navegador"))
        } catch (e: java.lang.Exception) {}
    }
}