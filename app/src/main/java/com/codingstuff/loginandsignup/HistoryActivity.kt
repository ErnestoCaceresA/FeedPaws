package com.codingstuff.loginandsignup

import com.codingstuff.loginandsignup.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.codingstuff.loginandsignup.databinding.ActivityHistoryBinding
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BackHist.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.TrashHist.setOnClickListener {
            val sharedPreferences = getSharedPreferences("RegistroServirAlimento", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // Elimina todos los registros en el SharedPreferences
            editor.apply()
        }
        val sharedPreferences = getSharedPreferences("RegistroServirAlimento", MODE_PRIVATE)
        val registrosServirAlimento = sharedPreferences.getStringSet("RegistrosServirAlimento", mutableSetOf())

        val containerLayout = findViewById<LinearLayout>(R.id.containerLayout)

        registrosServirAlimento?.let {
            for (registro in it) {
                val registroTextView = TextView(this)
                registroTextView.text = registro
                registroTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F) // Ajusta el tamaño de letra
                registroTextView.gravity = Gravity.CENTER // Centra el texto en el TextView
                containerLayout.addView(registroTextView)
            }
        }
    }
}
