package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import java.util.*

/**
 * Scene for adjusting some settings.
 */
class Settings : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var backButton: MaterialButton? = null
    private var colourButton: MaterialButton? = null
    private var settingsTitle: MaterialTextView? = null
    private var musicVolumeText: MaterialTextView? = null
    private var soundFXVolumeText: MaterialTextView? = null
    private var musicVolumeSlider: Slider? = null
    private var soundFXVolumeSlider: Slider? = null
    private var ttsSwitch: SwitchMaterial? = null
    private var musicVolume = 0.5f
    private var soundFXVolume = 0.5f
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeechBool = false
    private var textToSpeech: TextToSpeech? = null
    private var buttonClick: MediaPlayer? = null

    /**
     * Sets up layout and listeners etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        constraintLayout = findViewById(R.id.constraintLayout)
        settingsTitle = findViewById(R.id.settingsTitle)
        musicVolumeText = findViewById(R.id.musicVolumeText)
        soundFXVolumeText = findViewById(R.id.soundFXVolumeText)
        backButton = findViewById(R.id.backButton)
        colourButton = findViewById(R.id.colourButton)
        musicVolumeSlider = findViewById(R.id.musicVolumeSlider)
        soundFXVolumeSlider = findViewById(R.id.soundFXVolumeSlider)
        ttsSwitch = findViewById(R.id.ttsSwitch)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        updateColours()

        musicVolume = preferences.getFloat("musicVolume", 0.5f)

        musicVolumeSlider?.value = musicVolume

        musicVolumeSlider?.addOnChangeListener { _, value, _ ->
            val editor = preferences!!.edit()
            editor.putFloat("musicVolume", value)
            editor.apply()

            val intent = Intent(this, BackgroundMusicService::class.java)

            intent.action = "CHANGE_VOLUME"

            startService(intent)
        }

        musicVolumeSlider?.setLabelFormatter { value -> "${(value * 100f).toInt()}%" }

        soundFXVolumeSlider?.value = soundFXVolume

        soundFXVolumeSlider?.addOnChangeListener { _, value, _ ->
            val editor = preferences!!.edit()
            editor.putFloat("soundFXVolume", value)
            editor.apply()

            soundFXVolume = value
        }

        soundFXVolumeSlider?.setLabelFormatter { value -> "${(value * 100f).toInt()}%" }

        textToSpeechBool = preferences.getBoolean("textToSpeech", true)

        ttsSwitch?.isChecked = textToSpeechBool

        ttsSwitch?.setOnCheckedChangeListener { _, _ ->
            val editor = preferences!!.edit()
            editor.putBoolean("textToSpeech", ttsSwitch!!.isChecked)
            editor.apply()
        }

        backButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

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
            buttonClick?.start()

            textToSpeech?.stop()

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

        colourButton?.setOnLongClickListener {
            speakOut(colourButton?.text.toString())
            true
        }
    }

    /**
     * When app is resumed.
     */
    override fun onResume() {
        super.onResume()

        val intent = Intent(this, BackgroundMusicService::class.java)

        intent.action = "RESUME"

        startService(intent)
    }

    /**
     * When app is minimised.
     */
    override fun onPause() {
        super.onPause()

        val intent = Intent(this, BackgroundMusicService::class.java)

        intent.action = "PAUSE"

        startService(intent)
    }

    /**
     * Sets up Text-to-Speech engine.
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    /**
     * When back button pressed on device.
     */
    override fun onBackPressed() {
        backButton?.performClick()
    }

    /**
     * Read aloud given message if enabled.
     */
    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Update colours of elements in layout.
     */
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
    }
}
