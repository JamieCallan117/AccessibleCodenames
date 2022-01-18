package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import java.util.*

class Settings : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var constraintLayout: ConstraintLayout? = null
    private var backButton: MaterialButton? = null
    private var colourButton: MaterialButton? = null
    private var settingsTitle: MaterialTextView? = null
    private var musicVolumeText: MaterialTextView? = null
    private var soundFXVolumeText: MaterialTextView? = null
    private var musicVolumeSlider: Slider? = null
    private var soundFXVolumeSlider: Slider? = null
    private var vibrationSwitch: SwitchMaterial? = null
    private var ttsSwitch: SwitchMaterial? = null
    private var musicVolume = 0
    private var soundFXVolume = 0
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var vibration = false
    private var textToSpeechBool = false
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        constraintLayout = findViewById(R.id.saveButton)
        settingsTitle = findViewById(R.id.settingsTitle)
        musicVolumeText = findViewById(R.id.musicVolumeText)
        soundFXVolumeText = findViewById(R.id.soundFXVolumeText)
        backButton = findViewById(R.id.backButton)
        colourButton = findViewById(R.id.colourButton)
        musicVolumeSlider = findViewById(R.id.musicVolumeSlider)
        soundFXVolumeSlider = findViewById(R.id.soundFXVolumeSlider)
        vibrationSwitch = findViewById(R.id.vibrationSwitch)
        ttsSwitch = findViewById(R.id.ttsSwitch)

        textToSpeech = TextToSpeech(this, this)

        updateColours()

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        musicVolume = preferences.getInt("musicVolume", 100)

        musicVolumeSlider?.value = musicVolume.toFloat()

        musicVolumeSlider?.addOnChangeListener { _, value, _ ->
            val editor = preferences!!.edit()
            editor.putInt("musicVolume", value.toInt())
            editor.apply()
        }

        musicVolumeSlider?.setLabelFormatter { value -> "${value.toInt()}%" }

        soundFXVolume = preferences.getInt("soundFXVolume", 100)

        soundFXVolumeSlider?.value = soundFXVolume.toFloat()

        soundFXVolumeSlider?.addOnChangeListener { _, value, _ ->
            val editor = preferences!!.edit()
            editor.putInt("soundFXVolume", value.toInt())
            editor.apply()
        }

        soundFXVolumeSlider?.setLabelFormatter { value -> "${value.toInt()}%" }

        vibration = preferences.getBoolean("vibration", true)

        vibrationSwitch?.isChecked = vibration

        vibrationSwitch?.setOnCheckedChangeListener { _, _ ->
            val editor = preferences!!.edit()
            editor.putBoolean("vibration", vibrationSwitch!!.isChecked)
            editor.apply()
        }

        textToSpeechBool = preferences.getBoolean("textToSpeech", true)

        ttsSwitch?.isChecked = textToSpeechBool

        ttsSwitch?.setOnCheckedChangeListener { _, _ ->
            val editor = preferences!!.edit()
            editor.putBoolean("textToSpeech", ttsSwitch!!.isChecked)
            editor.apply()
        }

        backButton?.setOnClickListener {
            val i: Intent

            if (intent.getStringExtra("from") == "start_screen") {
                i = Intent(applicationContext, StartScreen::class.java)
                startActivity(i)
            } else if (intent.getStringExtra("from") == "main_menu") {
                i = Intent(applicationContext, MainMenu::class.java)
                startActivity(i)
            }
        }

        colourButton?.setOnClickListener {
            val i = Intent(applicationContext, ColourOptions::class.java)

            if (intent.getStringExtra("from") == "start_screen") {
                i.putExtra("from", "start_screen")
            } else if (intent.getStringExtra("from") == "main_menu") {
                i.putExtra("from", "main_menu")
            }

            startActivity(i)
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        settingsTitle?.setOnLongClickListener {
            speakOut(settingsTitle?.text.toString())
            true
        }

        musicVolumeText?.setOnLongClickListener {
            speakOut(musicVolumeText?.text.toString() + ", ${musicVolumeSlider?.value?.toInt().toString()}%")
            true
        }

        soundFXVolumeText?.setOnLongClickListener {
            speakOut(soundFXVolumeText?.text.toString() + ", ${soundFXVolumeSlider?.value?.toInt().toString()}%")
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

        vibrationSwitch?.setOnLongClickListener {
            if (vibrationSwitch!!.isChecked) {
                speakOut(vibrationSwitch!!.text.toString() + ", enabled.")
            } else {
                speakOut(vibrationSwitch!!.text.toString() + ", disabled.")
            }

            true
        }

        colourButton?.setOnLongClickListener {
            speakOut(colourButton?.text.toString())
            true
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButtons", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        colourButton?.setBackgroundColor(menuButtonsColour)

        settingsTitle?.setTextColor(menuTextColour)
        musicVolumeText?.setTextColor(menuTextColour)
        soundFXVolumeText?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        colourButton?.setTextColor(menuTextColour)
        ttsSwitch?.setTextColor(menuTextColour)
        vibrationSwitch?.setTextColor(menuTextColour)
    }
}
