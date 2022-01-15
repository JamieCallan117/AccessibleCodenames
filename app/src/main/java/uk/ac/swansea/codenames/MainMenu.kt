package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageView
import java.util.*

class MainMenu : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var constraintLayout: ConstraintLayout? = null
    private var mainMenuTitle: TextView? = null
    private var backButton: Button? = null
    private var howToPlayButton: Button? = null
    private var playOnline: Button? = null
    private var playLocal: Button? = null
    private var settingsButton: Button? = null
    private var onlineImage: ImageView? = null
    private var localImage: ImageView? = null
    private var settingsImage: ImageView? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        constraintLayout = findViewById(R.id.saveButton)
        mainMenuTitle = findViewById(R.id.mainMenuTitle)
        backButton = findViewById(R.id.backButton)
        howToPlayButton = findViewById(R.id.howToPlayButton)
        playOnline = findViewById(R.id.playOnline)
        playLocal = findViewById(R.id.playLocal)
        settingsButton = findViewById(R.id.settingsButton)
        onlineImage = findViewById(R.id.onlineImage)
        localImage = findViewById(R.id.localImage)
        settingsImage = findViewById(R.id.settingsImage)

        textToSpeech = TextToSpeech(this, this)

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, StartScreen::class.java)
            startActivity(i)
        }

        howToPlayButton?.setOnClickListener {
            val i = Intent(applicationContext, HowToPlay::class.java)
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
            speakOut(mainMenuTitle?.text.toString())
            true
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        howToPlayButton?.setOnLongClickListener {
            speakOut(howToPlayButton?.text.toString().replace("\n", ""))
            true
        }

        playOnline?.setOnLongClickListener {
            speakOut(playOnline?.text.toString())
            true
        }

        playLocal?.setOnLongClickListener {
            speakOut(playLocal?.text.toString())
            true
        }

        settingsButton?.setOnLongClickListener {
            speakOut(settingsButton?.text.toString())
            true
        }

        updateColours()
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

        applicationBackgroundColour = preferences.getInt("applicationBackgroundColour", -10921639)
        menuButtonsColour = preferences.getInt("menuButtonsColour", -8164501)
        menuTextColour = preferences.getInt("menuTextColour", -1)
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        howToPlayButton?.setBackgroundColor(menuButtonsColour)
        playOnline?.setBackgroundColor(menuButtonsColour)
        playLocal?.setBackgroundColor(menuButtonsColour)
        settingsButton?.setBackgroundColor(menuButtonsColour)
        onlineImage?.setColorFilter(menuButtonsColour)
        localImage?.setColorFilter(menuButtonsColour)
        settingsImage?.setColorFilter(menuButtonsColour)

        mainMenuTitle?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        howToPlayButton?.setTextColor(menuTextColour)
        playOnline?.setTextColor(menuTextColour)
        playLocal?.setTextColor(menuTextColour)
        settingsButton?.setTextColor(menuTextColour)
    }
}