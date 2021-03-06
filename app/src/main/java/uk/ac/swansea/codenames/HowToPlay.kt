package uk.ac.swansea.codenames

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*

/**
 * Displays the rules and how to play the game.
 */
class HowToPlay : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var backButton: MaterialButton? = null
    private var teamsHelp: MaterialButton? = null
    private var spymasterHelp: MaterialButton? = null
    private var hintsHelp: MaterialButton? = null
    private var guessHelp: MaterialButton? = null
    private var squaresHelp: MaterialButton? = null
    private var playingHelp: MaterialButton? = null
    private var howToPlayTitle: MaterialTextView? = null
    private var helpText: MaterialTextView? = null
    private var constraintLayout: ConstraintLayout? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null
    private var buttonClick: MediaPlayer? = null

    /**
     * Sets up layout and all listeners etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.how_to_play)

        backButton = findViewById(R.id.backButton)
        teamsHelp = findViewById(R.id.teamsHelp)
        spymasterHelp = findViewById(R.id.spymasterHelp)
        hintsHelp = findViewById(R.id.hintsHelp)
        guessHelp = findViewById(R.id.guessHelp)
        squaresHelp = findViewById(R.id.squaresHelp)
        playingHelp = findViewById(R.id.playingHelp)
        constraintLayout = findViewById(R.id.constraintLayout)
        howToPlayTitle = findViewById(R.id.howToPlayTitle)
        helpText = findViewById(R.id.helpText)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        helpText?.setText(R.string.teams_help)

        updateColours()

        backButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        teamsHelp?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            helpText?.setText(R.string.teams_help)
        }

        spymasterHelp?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            helpText?.setText(R.string.spymaster_help)
        }

        hintsHelp?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            helpText?.setText(R.string.hints_help)
        }

        guessHelp?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            helpText?.setText(R.string.guessing_help)
        }

        squaresHelp?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            helpText?.setText(R.string.squares_help)
        }

        playingHelp?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            helpText?.setText(R.string.playing_help)
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        teamsHelp?.setOnLongClickListener {
            speakOut(teamsHelp?.text.toString())
            true
        }

        spymasterHelp?.setOnLongClickListener {
            speakOut(spymasterHelp?.text.toString())
            true
        }

        hintsHelp?.setOnLongClickListener {
            speakOut(hintsHelp?.text.toString())
            true
        }

        guessHelp?.setOnLongClickListener {
            speakOut(guessHelp?.text.toString())
            true
        }

        squaresHelp?.setOnLongClickListener {
            speakOut(squaresHelp?.text.toString())
            true
        }

        playingHelp?.setOnLongClickListener {
            speakOut(playingHelp?.text.toString())
            true
        }

        helpText?.setOnLongClickListener {
            speakOut(helpText?.text.toString())
            true
        }

        howToPlayTitle?.setOnLongClickListener {
            speakOut(howToPlayTitle?.text.toString())
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
     * When device back button pressed.
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
     * Updates colours of elements in view.
     */
    private fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButtons", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        teamsHelp?.setBackgroundColor(menuButtonsColour)
        spymasterHelp?.setBackgroundColor(menuButtonsColour)
        hintsHelp?.setBackgroundColor(menuButtonsColour)
        guessHelp?.setBackgroundColor(menuButtonsColour)
        squaresHelp?.setBackgroundColor(menuButtonsColour)
        playingHelp?.setBackgroundColor(menuButtonsColour)

        teamsHelp?.setTextColor(menuTextColour)
        spymasterHelp?.setTextColor(menuTextColour)
        hintsHelp?.setTextColor(menuTextColour)
        guessHelp?.setTextColor(menuTextColour)
        squaresHelp?.setTextColor(menuTextColour)
        playingHelp?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        howToPlayTitle?.setTextColor(menuTextColour)
        helpText?.setTextColor(menuTextColour)
    }
}