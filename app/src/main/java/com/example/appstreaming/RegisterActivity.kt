package com.example.appstreaming

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appstreaming.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        title = "Autenticación"
        binding.signUpButton.setOnClickListener {
            // Comprobamos que los campos no estén vacíos
            if (binding.editTextTextEmailAddress.text.isNotEmpty() && binding.editTextTextPassword2.text.isNotEmpty()) {
                // Creamos el usuario con email y contraseña
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(binding.editTextTextEmailAddress.text.toString()
                        , binding.editTextTextPassword2.text.toString()).addOnCompleteListener {
                        // Comprobamos si se ha creado correctamente
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Se ha registrado el usuario con el email: ${binding.editTextTextEmailAddress.text}", Toast.LENGTH_LONG).show()
                            Intent(this, AuthActivity::class.java).apply {
                                startActivity(this)
                            }
                        } else {
                            // Mostramos un mensaje de error
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}