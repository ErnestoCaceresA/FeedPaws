package com.codingstuff.loginandsignup

import android.app.Activity
import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.codingstuff.loginandsignup.databinding.ActivityAddPetBinding

class AddPetActivity : Activity() {

    private lateinit var binding: ActivityAddPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.CanRPButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.RegPetButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val nombreEditText = findViewById<EditText>(R.id.RPNameET)
            val nombre = nombreEditText.text.toString()

            val edadEditText = findViewById<EditText>(R.id.RPAgeET)
            val edad = edadEditText.text.toString()

            val razaEditText = findViewById<EditText>(R.id.RPRaceET)
            val raza = razaEditText.text.toString()

            val pesoEditText = findViewById<EditText>(R.id.RPWeightET)
            val peso = pesoEditText.text.toString()

            // Obtener la lista existente de registros de mascotas desde SharedPreferences
            val registrosMascotas = sharedPreferences.getStringSet("Registro Mascotas", mutableSetOf())
            val nuevoRegistro = "$nombre, $edad, $raza, $peso"
            // Agregar el nuevo registro a la lista
            registrosMascotas?.add("$nombre, $edad, $raza, $peso")
            // Limitar la lista a un máximo de tres registros
            if (registrosMascotas?.size ?: 0 < 7) {
                registrosMascotas?.add(nuevoRegistro)
                editor.putStringSet("Registro Mascotas", registrosMascotas)
                editor.apply()
            }else {
                // Notificar que se ha alcanzado el máximo permitido
                Toast.makeText(this, "Se ha alcanzado el máximo de registros permitidos", Toast.LENGTH_SHORT).show()
            }
            // Guardar la lista actualizada en SharedPreferences
            editor.putStringSet("registrosMascotas", registrosMascotas)
            // Aplicar los cambios
            editor.apply()
            editor.putString("nombre", nombre)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        }
}