package com.example.appstreaming

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appstreaming.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
        session()
    }

    override fun onStart() {
        super.onStart()
        binding.authLayout.visibility = View.VISIBLE
    }
    private fun session() {
        //Guardamos el email en el shared preferences
        val prefs = getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE)
        val email = prefs.getString("email", null)

        //Si el email no es nulo, es decir, si existe, nos vamos a la actividad principal
        if (email != null) {
            binding.authLayout.visibility = View.INVISIBLE
            Intent(this, MainActivity::class.java).apply {
                Toast.makeText(this@AuthActivity, "Bienvenido $email", Toast.LENGTH_LONG).show()
                startActivity(this)
            }
        }
    }

    private fun setup() {
        //Cambiamos el titulo de la actividad
        title = "Autenticación"

        binding.signInButton.setOnClickListener {
            //Comprobamos que los campos no estén vacíos
            if (binding.editTextTextEmailAddress.text.isNotEmpty() && binding.editTextTextPassword2.text.isNotEmpty()) {
                //Iniciamos sesión con el email y la contraseña
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.editTextTextEmailAddress.text.toString()
                        , binding.editTextTextPassword2.text.toString()).addOnCompleteListener {
                        //Si la tarea es correcta, nos vamos a la actividad principal y guardamos el email en el shared preferences
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Inicio de Sesion correcto", Toast.LENGTH_LONG).show()
                            val prefs = getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE).edit()
                            prefs.putString("email", binding.editTextTextEmailAddress.text.toString())
                            prefs.apply()
                            Intent(this, MainActivity::class.java).apply {
                                startActivity(this)
                            }
                        } else {
                            //Si la tarea no es correcta, mostramos un mensaje de error
                            showAlert()
                        }
                    }
            }
        }

        binding.createAccount.setOnClickListener {
            //Nos vamos a la actividad de registro
            Intent(this, RegisterActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun showAlert() {
        //Creamos un dialogo de alerta
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}