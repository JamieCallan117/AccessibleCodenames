package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.widget.SeekBar.OnSeekBarChangeListener
import android.content.Intent
import android.widget.*
import java.io.File
import java.io.IOException

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
    private var preferences = arrayOfNulls<String>(14)
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

        var tempPref = ""

        try {
            val input = assets.open("preferences")
            val size = input.available()
            val buffer = ByteArray(size)

            input.read(buffer)
            input.close()

            tempPref = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        preferences = tempPref.split("\n").toTypedArray()

        updateColours()

        val regex = "[^A-Za-z0-9 ]".toRegex()

        var musicVolumeStr = preferences[9]?.let { regex.replace(it, "") }
        var soundFxVolumeStr = preferences[10]?.let { regex.replace(it, "") }

        if (musicVolumeStr != null) {
            musicVolume = musicVolumeStr.toInt()
        }

        musicVolumeSeek?.progress = musicVolume

        musicVolumeSeek?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updatePreferencesFile()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        if (soundFxVolumeStr != null) {
            soundFXVolume = soundFxVolumeStr.toInt()
        }

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
        val regex = "[^A-Za-z0-9 ]".toRegex()

        var teamAColoursStr = preferences[0]?.let { regex.replace(it, "") }
        var teamBColoursStr = preferences[1]?.let { regex.replace(it, "") }
        var bombColoursStr = preferences[2]?.let { regex.replace(it, "") }
        var neutralColoursStr = preferences[3]?.let { regex.replace(it, "") }
        var unmodifiedColourStr = preferences[4]?.let { regex.replace(it, "") }
        var applicationBackgroundColourStr = preferences[5]?.let { regex.replace(it, "") }
        var menuButtonsColourStr = preferences[6]?.let { regex.replace(it, "") }
        var menuTextColourStr = preferences[7]?.let { regex.replace(it, "") }

        teamAColoursStr = "-$teamAColoursStr"
        teamBColoursStr = "-$teamBColoursStr"
        bombColoursStr = "-$bombColoursStr"
        neutralColoursStr = "-$neutralColoursStr"
        unmodifiedColourStr = "-$unmodifiedColourStr"
        applicationBackgroundColourStr = "-$applicationBackgroundColourStr"
        menuButtonsColourStr = "-$menuButtonsColourStr"
        menuTextColourStr = "-$menuTextColourStr"

        val teamAColour = teamAColoursStr.toInt()
        val teamBColour = teamBColoursStr.toInt()
        val bombColour = bombColoursStr.toInt()
        val neutralColour = neutralColoursStr.toInt()
        val unmodifiedColour = unmodifiedColourStr.toInt()
        val username = preferences[8]
        val musicVolume = musicVolumeSeek?.progress.toString()
        val soundFxVolume = soundFXVolumeSeek?.progress.toString()
        val vibration = vibrationCheck?.isChecked.toString()
        val contrast = contrastCheck?.isChecked.toString()
        val textToSpeech = textToSpeechCheck?.isChecked.toString()

        applicationBackgroundColour = applicationBackgroundColourStr.toInt()
        menuButtonsColour = menuButtonsColourStr.toInt()
        menuTextColour = menuTextColourStr.toInt()

        val prefStr = "$teamAColour\n$teamBColour\n$bombColour\n$neutralColour\n$unmodifiedColour" +
                "\n$applicationBackgroundColour\n$menuButtonsColour\n$menuTextColour\n$username" +
                "\n$musicVolume\n$soundFxVolume\n$vibration\n$contrast\n$textToSpeech"

        File(preferencesFile).writeText(prefStr)
    }

    fun updateColours() {
        val regex = "[^A-Za-z0-9 ]".toRegex()

        var applicationBackgroundColourStr = preferences[5]?.let { regex.replace(it, "") }
        var menuButtonsColourStr = preferences[6]?.let { regex.replace(it, "") }
        var menuTextColourStr = preferences[7]?.let { regex.replace(it, "") }

        applicationBackgroundColourStr = "-$applicationBackgroundColourStr"
        menuButtonsColourStr = "-$menuButtonsColourStr"
        menuTextColourStr = "-$menuTextColourStr"

        applicationBackgroundColour = applicationBackgroundColourStr.toInt()
        menuButtonsColour = menuButtonsColourStr.toInt()!!
        menuTextColour = menuTextColourStr.toInt()!!

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