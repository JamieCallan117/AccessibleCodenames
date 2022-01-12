package uk.ac.swansea.codenames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class LaunchScreen : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_screen)

        textToSpeech = TextToSpeech(this, this)

        val sharedPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("firstLaunch", true)) {
            val intent = Intent(applicationContext, StartScreen::class.java)
            startActivity(intent)
        }

        val editor = sharedPreferences.edit()

        editor.putBoolean("firstLaunch", false)

        editor.apply()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }
}