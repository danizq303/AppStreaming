package com.example.appstreaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appstreaming.databinding.ActivityMiEspacioBinding

class MiEspacioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMiEspacioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiEspacioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE)
        val email = pref.getString("email", null)

        binding.textView.text = "Hola $email"

        binding.logOut.setOnClickListener {
            val prefs = getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            Intent(this, AuthActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.imageView6.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}