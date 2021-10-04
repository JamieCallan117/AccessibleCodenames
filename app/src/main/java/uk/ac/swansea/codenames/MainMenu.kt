package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import java.io.File

class MainMenu : AppCompatActivity() {
    // TODO: Redesign layout.
    // TODO: Properly implement TTS.
    // TODO: Add how to play section.

    private var constraintLayout: ConstraintLayout? = null
    private var mainMenuTitle: TextView? = null
    private var backButton: Button? = null
    private var playOnline: Button? = null
    private var playLocal: Button? = null
    private var settingsButton: Button? = null
    private var preferencesFile = "preferences.txt"
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        constraintLayout = findViewById(R.id.saveButton)
        mainMenuTitle = findViewById(R.id.mainMenuTitle)
        backButton = findViewById(R.id.backButton)
        playOnline = findViewById(R.id.playOnline)
        playLocal = findViewById(R.id.playLocal)
        settingsButton = findViewById(R.id.settingsButton)

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, StartScreen::class.java)
            startActivity(i)
        }

        settingsButton?.setOnClickListener {
            val i = Intent(this, Settings::class.java)

            i.putExtra("from", "main_menu")
            startActivity(i)
        }
        
        playLocal?.setOnClickListener {
            val i = Intent(applicationContext, LocalSetup::class.java)
            startActivity(i)
        }
        
        playOnline?.setOnClickListener {
            val i = Intent(applicationContext, OnlineSetup::class.java)
            startActivity(i)
        }

        mainMenuTitle?.setOnLongClickListener {
            println(mainMenuTitle?.text)
            true
        }
        backButton?.setOnLongClickListener {
            println(backButton?.text)
            true
        }
        playOnline?.setOnLongClickListener {
            println(playOnline?.text)
            true
        }
        playLocal?.setOnLongClickListener {
            println(playLocal?.text)
            true
        }
        settingsButton?.setOnLongClickListener {
            println(settingsButton?.text)
            true
        }

        updateColours()
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    fun updateColours() {
        val colours = File(preferencesFile).useLines { it.toList() }

        applicationBackgroundColour = colours[5].toInt()
        menuButtonsColour = colours[6].toInt()
        menuTextColour = colours[7].toInt()
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        backButton?.setBackgroundColor(menuButtonsColour)
        playOnline?.setBackgroundColor(menuButtonsColour)
        playLocal?.setBackgroundColor(menuButtonsColour)
        settingsButton?.setBackgroundColor(menuButtonsColour)
        mainMenuTitle?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        playOnline?.setTextColor(menuTextColour)
        playLocal?.setTextColor(menuTextColour)
        settingsButton?.setTextColor(menuTextColour)
    }
}