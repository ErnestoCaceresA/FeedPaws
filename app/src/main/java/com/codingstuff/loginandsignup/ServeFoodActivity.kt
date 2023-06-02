package com.codingstuff.loginandsignup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.codingstuff.loginandsignup.databinding.ActivityServeFoodBinding
import com.codingstuff.loginandsignup.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.codingstuff.loginandsignup.R
import com.codingstuff.loginandsignup.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class ServeFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServeFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serve_food)
        binding = ActivityServeFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Puedes agregar cualquier lógica adicional necesaria para esta actividad

        binding.BackSA.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val mascotaTextView = findViewById<TextView>(R.id.NamePetTV)
        val nombreMascota = intent.getStringExtra("nombreMascota")
        mascotaTextView.text = nombreMascota

        val sacantET = findViewById<EditText>(R.id.SACantET)

        val registrosMascotas = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            .getStringSet("registrosMascotas", setOf())

        registrosMascotas?.forEach { registro ->
            val nombre = registro.substringBefore(",")
            val peso = registro.substringAfterLast(",").trim()

            if (nombre == nombreMascota) {
                val pesoNumero = peso.toDoubleOrNull()
                if (pesoNumero != null) {
                    val resultado = pesoNumero * 1000 * 0.025
                    sacantET.setText(resultado.toString())
                }
                return@forEach
            }
        }

        val saButton = findViewById<Button>(R.id.saButton)
        saButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("RegistroServirAlimento", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val nombreMascota = binding.NamePetTV.text.toString()
            val resultado = binding.SACantET.text.toString()

            // Obtener la fecha y hora actual
            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

            // Crear el registro con nombre de la mascota, resultado y fecha y hora
            val registro = "Nombre: $nombreMascota, Alimento(gr): $resultado, Fecha: $currentDate, Hora: $currentTime"

            // Obtener la lista existente de registros de servir alimento desde SharedPreferences
            val registrosServirAlimento = sharedPreferences.getStringSet("RegistrosServirAlimento", mutableSetOf())

            // Agregar el nuevo registro a la lista
            registrosServirAlimento?.add(registro)

            // Guardar la lista actualizada en SharedPreferences
            editor.putStringSet("RegistrosServirAlimento", registrosServirAlimento)
            editor.apply()

            // Notificar al usuario que el registro se ha creado exitosamente
            Toast.makeText(this, "Registro creado correctamente", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}