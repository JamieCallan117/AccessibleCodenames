package uk.ac.swansea.codenames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*

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

        textToSpeech = TextToSpeech(this, this)

        helpText?.setText(R.string.teams_help)

        updateColours()

        backButton?.setOnClickListener {
            textToSpeech?.stop()

            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        teamsHelp?.setOnClickListener {
            textToSpeech?.stop()

            helpText?.setText(R.string.teams_help)
        }

        spymasterHelp?.setOnClickListener {
            textToSpeech?.stop()

            helpText?.setText(R.string.spymaster_help)
        }

        hintsHelp?.setOnClickListener {
            textToSpeech?.stop()

            helpText?.setText(R.string.hints_help)
        }

        guessHelp?.setOnClickListener {
            textToSpeech?.stop()

            helpText?.setText(R.string.guessing_help)
        }

        squaresHelp?.setOnClickListener {
            textToSpeech?.stop()

            helpText?.setText(R.string.squares_help)
        }

        playingHelp?.setOnClickListener {
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