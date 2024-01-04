package com.josejordan.getdailymessage

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "AppPrefs"
        const val KNOWLEDGE_KEY = "conocimiento"
        const val LAST_DATE_KEY = "ultimaFecha"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastDate = sharedPreferences.getString(LAST_DATE_KEY, "")
        val currentDate = LocalDate.now().toString()

        var conocimiento = sharedPreferences.getInt(KNOWLEDGE_KEY, 0)
        if (lastDate != currentDate) {
            conocimiento++
            with(sharedPreferences.edit()) {
                putInt(KNOWLEDGE_KEY, conocimiento)
                putString(LAST_DATE_KEY, currentDate)
                apply()
            }
        }

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "${getDailyMessage()}\nPero eres un $conocimiento% mejor 📈"
    }

    private fun getDailyMessage(): String {
        val estados = listOf("¡Qué bueno/a soy! 😊",
            "Burnout 😵", "La IA es mejor que yo 🤖",
            "Síndrome del impostor 😳", "No sé nada 🤔",
            "Quiero dejarlo 😞", "Tengo que intentarlo 😌")

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val formattedDate = currentDate.format(formatter)
        val randomIndex = Random.nextInt(estados.size)

        return "Hoy es $formattedDate\n${estados[randomIndex]}"
    }
}