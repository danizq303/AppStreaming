package com.example.appstreaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appstreaming.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el fragmento de inicio
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            // Cambiar de actividad cuando se selecciona un elemento del menú inferior
            when (item.itemId) {
                R.id.inicio -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.tienda -> {
                    replaceFragment(TiendaFragment())
                    true
                }
                R.id.encontrar -> {
                    replaceFragment(EncontrarFragment())
                    true
                }
                R.id.descargas -> {
                    replaceFragment(DescargasFragment())
                    true
                }
                R.id.miespacio -> {
                    Intent(this, MiEspacioActivity::class.java).apply {
                        startActivity(this)
                    }
                    false
                }
                else -> {
                    false}
            }
        }
    }

    // Al presionar el botón de retroceso, mostrar un mensaje
    override fun onBackPressed() {
        Toast.makeText(this, "Presione el botón de cerrar sesión", Toast.LENGTH_LONG).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        // Reemplazar el fragmento
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}