package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.widget.SeekBar.OnSeekBarChangeListener
import android.content.Intent
import android.widget.*
import java.io.File

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
    private var preferencesFile = "preferences.txt"
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

        val preferences = File(preferencesFile).useLines { it.toList() }

        musicVolume = preferences[9].toInt()

        musicVolumeSeek?.progress = musicVolume

        musicVolumeSeek?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updatePreferencesFile()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        soundFXVolume = preferences[10].toInt()

        soundFXVolumeSeek?.progress = soundFXVolume

        soundFXVolumeSeek?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updatePreferencesFile()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        vibration = preferences[11].toBoolean()

        vibrationCheck?.isChecked = vibration

        vibrationCheck?.setOnCheckedChangeListener { _, _ ->
            updatePreferencesFile()
        }


        contrast = preferences[12].toBoolean()

        contrastCheck?.isChecked = contrast

        contrastCheck?.setOnCheckedChangeListener { _, _ ->
            updatePreferencesFile()
        }

        textToSpeech = preferences[13].toBoolean()

        textToSpeechCheck?.isChecked = textToSpeech

        textToSpeechCheck?.setOnCheckedChangeListener { _, _ ->
            updatePreferencesFile()
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

    private fun updatePreferencesFile() {
        val preferences = File(preferencesFile).useLines { it.toList() }
        val teamAColour = preferences[0].toInt()
        val teamBColour = preferences[1].toInt()
        val bombColour = preferences[2].toInt()
        val neutralColour = preferences[3].toInt()
        val unmodifiedColour = preferences[4].toInt()
        val username = preferences[8]
        val musicVolume = musicVolumeSeek?.progress.toString()
        val soundFxVolume = soundFXVolumeSeek?.progress.toString()
        val vibration = vibrationCheck?.isChecked.toString()
        val contrast = contrastCheck?.isChecked.toString()
        val textToSpeech = textToSpeechCheck?.isChecked.toString()

        applicationBackgroundColour = preferences[5].toInt()
        menuButtonsColour = preferences[6].toInt()
        menuTextColour = preferences[7].toInt()

        val prefStr = "$teamAColour\n$teamBColour\n$bombColour\n$neutralColour\n$unmodifiedColour" +
                "\n$applicationBackgroundColour\n$menuButtonsColour\n$menuTextColour\n$username" +
                "\n$musicVolume\n$soundFxVolume\n$vibration\n$contrast\n$textToSpeech"

        File(preferencesFile).writeText(prefStr)
    }

    fun updateColours() {
        val colours = File(preferencesFile).useLines { it.toList() }

        applicationBackgroundColour = colours[5].toInt()
        menuButtonsColour = colours[6].toInt()
        menuTextColour = colours[7].toInt()

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