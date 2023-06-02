package com.codingstuff.loginandsignup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codingstuff.loginandsignup.databinding.ActivityMainBinding
import com.codingstuff.loginandsignup.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.codingstuff.loginandsignup.R


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var PetsLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PetsLL = binding.PetsLL

        val registrosMascotas = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            .getStringSet("registrosMascotas", setOf())


        // Iterar sobre los registros y mostrar solo el nombre en botones o etiquetas
        registrosMascotas?.forEach { registro ->
            val nombre = registro.substringBefore(",") // Obtener solo el nombre
            val button = Button(this)
            button.text = nombre
            button.setOnClickListener {
                // Acción a realizar al hacer clic en el botón
                val intent = Intent(this, ServeFoodActivity::class.java)
                intent.putExtra("nombreMascota", nombre) // Agrega un extra con el nombre de la mascota
                startActivity(intent)
                // Puedes abrir un nuevo activity, mostrar más detalles de la mascota, etc.
                Toast.makeText(this, "Clic en $nombre", Toast.LENGTH_SHORT).show()
            }
            // Agregar el botón al contenedor de tu elección (por ejemplo, un LinearLayout)
            PetsLL.addView(button)

            // Personalizar el diseño del botón
            val layoutParams = button.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(0, 15, 0, 0) // Agregar márgenes alrededor del botón
            button.layoutParams = layoutParams
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                button.background = resources.getDrawable(R.drawable.button_background, null)
            } else {
                button.setBackgroundDrawable(resources.getDrawable(R.drawable.button_background))
            }
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F) // Cambiar el tamaño del texto
            button.setPadding(0, 16, 0, 16) // Agregar padding al botón
        }

        registrosMascotas?.forEach { registro ->
            println(registro)
        }

        binding.AddPetButton.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }

        binding.SAHistMainButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.BackMain.setOnClickListener {
            firebaseAuth.signOut() // Cerrar sesión en Firebase
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpiar la pila de actividades
            startActivity(intent) // Iniciar la actividad de inicio de sesión
            finish() // Finalizar la actividad actual
        }

        binding.TrashMain.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // Elimina todos los registros en el SharedPreferences
            editor.apply()
        }

        //mostrar el correo con el que se inicio sesion
        val user = firebaseAuth.currentUser
        if (user != null) {
            binding.UserNameTV.text = user.email
            binding.UserNameTV.visibility = View.VISIBLE
        }
    }//cierre de oncreate
}