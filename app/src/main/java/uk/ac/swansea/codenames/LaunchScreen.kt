package uk.ac.swansea.codenames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import java.util.*

class LaunchScreen : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    private var continueButton: MaterialButton? = null
    private var ttsInfo: MaterialTextView? = null
    private var welcomeText: MaterialTextView? = null
    private var ttsSwitch: SwitchMaterial? = null
    private var constraintLayout: ConstraintLayout? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_screen)

        continueButton = findViewById(R.id.continueButton)
        ttsInfo = findViewById(R.id.ttsInfo)
        welcomeText = findViewById(R.id.welcomeText)
        ttsSwitch = findViewById(R.id.ttsSwitch)
        constraintLayout = findViewById(R.id.constraintLayout)

        textToSpeech = TextToSpeech(this, this)

        val sharedPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (!sharedPreferences.getBoolean("firstLaunch", true)) {
            val intent = Intent(applicationContext, StartScreen::class.java)
            startActivity(intent)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                if (ttsSwitch!!.isChecked) {
                    speakOut(ttsInfo!!.text.toString())
                }

                continueButton?.visibility = View.VISIBLE
            }, 2000)
        }

        ttsSwitch?.setOnCheckedChangeListener { _, _ ->
            editor.putBoolean("textToSpeech", ttsSwitch!!.isChecked)

            if (!ttsSwitch!!.isChecked) {
                textToSpeech!!.stop()
            } else {
                speakOut(ttsSwitch!!.text.toString() + ", enabled.")
            }
        }

        continueButton?.setOnClickListener {
            editor.putBoolean("firstLaunch", false)
            editor.apply()

            textToSpeech?.stop()

            val intent = Intent(applicationContext, StartScreen::class.java)
            startActivity(intent)
        }

        welcomeText?.setOnLongClickListener {
            speakOut(welcomeText!!.text.toString())
            true
        }

        ttsInfo?.setOnLongClickListener {
            speakOut(ttsInfo!!.text.toString())
            true
        }

        ttsSwitch?.setOnLongClickListener {
            if (ttsSwitch!!.isChecked) {
                speakOut(ttsSwitch!!.text.toString() + ", enabled.")
            } else {
                speakOut(ttsSwitch!!.text.toString() + ", disabled.")
            }

            true
        }

        continueButton?.setOnLongClickListener {
            speakOut(continueButton!!.text.toString())
            true
        }

        updateColours()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButtons", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

        constraintLayout!!.setBackgroundColor(applicationBackgroundColour)

        continueButton!!.setBackgroundColor(menuButtonsColour)

        welcomeText!!.setTextColor(menuTextColour)
        ttsInfo!!.setTextColor(menuTextColour)
        continueButton!!.setTextColor(menuTextColour)
        ttsSwitch!!.setTextColor(menuTextColour)
    }
}
