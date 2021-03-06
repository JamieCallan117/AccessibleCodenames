package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*

/**
 * The main menu is what navigates to most other places.
 */
class MainMenu : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: ConstraintLayout? = null
    private var mainMenuTitle: MaterialTextView? = null
    private var messageText: MaterialTextView? = null
    private var backButton: MaterialButton? = null
    private var howToPlayButton: MaterialButton? = null
    private var playOnline: MaterialButton? = null
    private var playLocal: MaterialButton? = null
    private var settingsButton: MaterialButton? = null
    private var okButton: MaterialButton? = null
    private var onlineImage: ImageView? = null
    private var localImage: ImageView? = null
    private var settingsImage: ImageView? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null
    private var buttonClick: MediaPlayer? = null

    /**
     * Sets up layout and listeners etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        constraintLayout = findViewById(R.id.constraintLayout)
        messageBox = findViewById(R.id.messageBox)
        mainMenuTitle = findViewById(R.id.mainMenuTitle)
        messageText = findViewById(R.id.messageText)
        backButton = findViewById(R.id.backButton)
        howToPlayButton = findViewById(R.id.howToPlayButton)
        playOnline = findViewById(R.id.playOnline)
        playLocal = findViewById(R.id.playLocal)
        settingsButton = findViewById(R.id.settingsButton)
        okButton = findViewById(R.id.okButton)
        onlineImage = findViewById(R.id.onlineImage)
        localImage = findViewById(R.id.localImage)
        settingsImage = findViewById(R.id.settingsImage)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        if (intent.getStringExtra("onlineClose") != null) {
            messageBox?.visibility = View.VISIBLE

            messageText?.text = intent.getStringExtra("onlineClose")

            backButton?.isEnabled = false
            howToPlayButton?.isEnabled = false
            playOnline?.isEnabled = false
            playLocal?.isEnabled = false
            settingsButton?.isEnabled = false

            speakOut(messageText?.text.toString())
        }

        if (intent.getBooleanExtra("startMusic", false)) {
            val musicIntent = Intent(this, BackgroundMusicService::class.java)

            musicIntent.action = "START_MENU"

            stopService(musicIntent)

            startService(musicIntent)
        }

        okButton?.setOnClickListener {
            buttonClick?.start()

            messageBox?.visibility = View.INVISIBLE

            backButton?.isEnabled = true
            howToPlayButton?.isEnabled = true
            playOnline?.isEnabled = true
            playLocal?.isEnabled = true
            settingsButton?.isEnabled = true
        }

        backButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, StartScreen::class.java)
            startActivity(i)
        }

        howToPlayButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, HowToPlay::class.java)
            startActivity(i)
        }

        settingsButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(this, Settings::class.java)

            i.putExtra("from", "main_menu")
            startActivity(i)
        }
        
        playLocal?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, LocalSetup::class.java)
            startActivity(i)
        }
        
        playOnline?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, OnlineSetup::class.java)
            startActivity(i)
        }

        mainMenuTitle?.setOnLongClickListener {
            speakOut(mainMenuTitle?.text.toString())
            true
        }

        messageText?.setOnLongClickListener {
            speakOut(messageText?.text.toString())
            true
        }

        okButton?.setOnLongClickListener {
            speakOut(okButton?.text.toString())
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

    /**
     * When app is reopened.
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
     * When device's back button is clicked.
     */
    override fun onBackPressed() {
        backButton?.performClick()
    }

    /**
     * Reads aloud given message.
     */
    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Updates colours of elements in layout.
     */
    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButtons", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        messageBox?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        okButton?.setBackgroundColor(menuButtonsColour)
        howToPlayButton?.setBackgroundColor(menuButtonsColour)
        playOnline?.setBackgroundColor(menuButtonsColour)
        playLocal?.setBackgroundColor(menuButtonsColour)
        settingsButton?.setBackgroundColor(menuButtonsColour)
        onlineImage?.setColorFilter(menuButtonsColour)
        localImage?.setColorFilter(menuButtonsColour)
        settingsImage?.setColorFilter(menuButtonsColour)

        mainMenuTitle?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        okButton?.setTextColor(menuTextColour)
        howToPlayButton?.setTextColor(menuTextColour)
        playOnline?.setTextColor(menuTextColour)
        playLocal?.setTextColor(menuTextColour)
        settingsButton?.setTextColor(menuTextColour)
    }
}