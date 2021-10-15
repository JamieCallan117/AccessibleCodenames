package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import java.io.File
import java.io.IOException

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
    private var onlineImage: ImageView? = null
    private var localImage: ImageView? = null
    private var settingsImage: ImageView? = null
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
        onlineImage = findViewById(R.id.onlineImage)
        localImage = findViewById(R.id.localImage)
        settingsImage = findViewById(R.id.settingsImage)

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
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackgroundColour", -10921639)
        menuButtonsColour = preferences.getInt("menuButtonsColour", -8164501)
        menuTextColour = preferences.getInt("menuTextColour", -1)
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        playOnline?.setBackgroundColor(menuButtonsColour)
        playLocal?.setBackgroundColor(menuButtonsColour)
        settingsButton?.setBackgroundColor(menuButtonsColour)
        onlineImage?.setColorFilter(menuButtonsColour)
        localImage?.setColorFilter(menuButtonsColour)
        settingsImage?.setColorFilter(menuButtonsColour)

        mainMenuTitle?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        playOnline?.setTextColor(menuTextColour)
        playLocal?.setTextColor(menuTextColour)
        settingsButton?.setTextColor(menuTextColour)
    }
}