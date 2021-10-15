package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.widget.SeekBar.OnSeekBarChangeListener
import android.content.Intent
import android.widget.*

class Settings : AppCompatActivity() {
    // TODO: Redesign layout.

    private var constraintLayout: ConstraintLayout? = null
    private var backButton: Button? = null
    private var colourButton: Button? = null
    private var settingsTitle: TextView? = null
    private var musicVolumeText: TextView? = null
    private var soundFXVolumeText: TextView? = null
    private var vibrationText: TextView? = null
    private var contrastText: TextView? = null
    private var textToSpeechText: TextView? = null
    private var musicVolumeSeek: SeekBar? = null
    private var soundFXVolumeSeek: SeekBar? = null
    private var vibrationCheck: CheckBox? = null
    private var contrastCheck: CheckBox? = null
    private var textToSpeechCheck: CheckBox? = null
    private var musicVolume = 0
    private var soundFXVolume = 0
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var vibration = false
    private var contrast = false
    private var textToSpeech = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        constraintLayout = findViewById(R.id.saveButton)
        settingsTitle = findViewById(R.id.settingsTitle)
        musicVolumeText = findViewById(R.id.musicVolumeText)
        soundFXVolumeText = findViewById(R.id.soundFXVolumeText)
        vibrationText = findViewById(R.id.vibrationText)
        contrastText = findViewById(R.id.contrastText)
        textToSpeechText = findViewById(R.id.textToSpeechText)
        backButton = findViewById(R.id.backButton)
        colourButton = findViewById(R.id.colourButton)
        musicVolumeSeek = findViewById(R.id.musicVolumeSeek)
        soundFXVolumeSeek = findViewById(R.id.soundFXVolumeSeek)
        vibrationCheck = findViewById(R.id.vibrationCheck)
        contrastCheck = findViewById(R.id.contrastCheck)
        textToSpeechCheck = findViewById(R.id.textToSpeechCheck)

        updateColours()

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        musicVolume = preferences.getInt("musicVolume", 100)

        musicVolumeSeek?.progress = musicVolume

        musicVolumeSeek?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val editor = preferences!!.edit()
                editor.putInt("musicVolume", progress)
                editor.apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        soundFXVolume = preferences.getInt("soundFXVolume", 100)

        soundFXVolumeSeek?.progress = soundFXVolume

        soundFXVolumeSeek?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val editor = preferences!!.edit()
                editor.putInt("soundFXVolume", progress)
                editor.apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        vibration = preferences.getBoolean("vibration", true)

        vibrationCheck?.isChecked = vibration

        vibrationCheck?.setOnCheckedChangeListener { _, _ ->
            val editor = preferences!!.edit()
            editor.putBoolean("vibration", vibrationCheck!!.isChecked)
            editor.apply()
        }

        contrast = preferences.getBoolean("contrast", false)

        contrastCheck?.isChecked = contrast

        contrastCheck?.setOnCheckedChangeListener { _, _ ->
            val editor = preferences!!.edit()
            editor.putBoolean("contrast", contrastCheck!!.isChecked)
            editor.apply()
        }

        textToSpeech = preferences.getBoolean("textToSpeech", true)

        textToSpeechCheck?.isChecked = textToSpeech

        textToSpeechCheck?.setOnCheckedChangeListener { _, _ ->
            val editor = preferences!!.edit()
            editor.putBoolean("textToSpeech", textToSpeechCheck!!.isChecked)
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
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackgroundColour", -10921639)
        menuButtonsColour = preferences.getInt("menuButtonsColour", -8164501)
        menuTextColour = preferences.getInt("menuTextColour", -1)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        colourButton?.setBackgroundColor(menuButtonsColour)

        settingsTitle?.setTextColor(menuTextColour)
        musicVolumeText?.setTextColor(menuTextColour)
        soundFXVolumeText?.setTextColor(menuTextColour)
        vibrationText?.setTextColor(menuTextColour)
        contrastText?.setTextColor(menuTextColour)
        textToSpeechText?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        colourButton?.setTextColor(menuTextColour)
    }
}