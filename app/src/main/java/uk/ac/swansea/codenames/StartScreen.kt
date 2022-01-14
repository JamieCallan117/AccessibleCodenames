package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.gridlayout.widget.GridLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*
import kotlin.system.exitProcess

/**
 * Starting screen, what is seen when the app is launched. Can go to the main menu, or the settings page from here.
 */
class StartScreen : AppCompatActivity(), TextToSpeech.OnInitListener {
    // TODO: Properly implement TTS.

    private var quitBox: GridLayout? = null
    private var constraintLayout: ConstraintLayout? = null
    private var playButton: MaterialButton? = null
    private var settingsButton: MaterialButton? = null
    private var exitButton: MaterialButton? = null
    private var yesButton: MaterialButton? = null
    private var noButton: MaterialButton? = null
    private var quitText: MaterialTextView? = null
    private var startScreenTitle: MaterialTextView? = null
    private var playImage: ImageView? = null
    private var settingsImage: ImageView? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var exiting = false

    private var textToSpeech: TextToSpeech? = null

    /**
     * Sets up the layout for the screen.
     * Adds references to each View, sets up listeners and calls the updateColours function.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        quitBox = findViewById(R.id.quitBox)
        playButton = findViewById(R.id.playButton)
        settingsButton = findViewById(R.id.settingsButton)
        exitButton = findViewById(R.id.exitButton)
        constraintLayout = findViewById(R.id.constraintLayout)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)
        quitText = findViewById(R.id.quitText)
        startScreenTitle = findViewById(R.id.startScreenTitle)
        playImage = findViewById(R.id.playImage)
        settingsImage = findViewById(R.id.settingsImage)

        textToSpeech = TextToSpeech(this, this)

        playButton!!.setOnClickListener {
            val i = Intent(this, MainMenu::class.java)
            startActivity(i)
        }

        settingsButton!!.setOnClickListener {
            val i = Intent(this, Settings::class.java)
            i.putExtra("from", "start_screen")
            startActivity(i)
        }

        exitButton!!.setOnClickListener {
            onBackPressed()
        }

        yesButton!!.setOnClickListener {
            //Closes the app.
            finishAffinity()
            exitProcess(0)
        }

        noButton!!.setOnClickListener {
            quitBox!!.visibility = View.INVISIBLE
            playButton!!.visibility = View.VISIBLE
            settingsButton!!.visibility = View.VISIBLE
            exitButton!!.visibility = View.VISIBLE
            exiting = false
        }

        //This is how TTS will work. Pressing the button quickly performs it's default action. Holding it
        //with a return true in this listener, will cause it to run the code in here, and not run the code
        //for a regular click.
        //For actual TTS it won't print out the word, it'll obviously read it aloud
        playButton!!.setOnLongClickListener {
            speakOut(playButton?.text.toString())
            true
        }

        settingsButton!!.setOnLongClickListener {
            println(settingsButton?.text)
            true
        }

        exitButton!!.setOnLongClickListener {
            println(exitButton?.text)
            true
        }

        yesButton!!.setOnLongClickListener {
            println(yesButton?.text)
            true
        }

        noButton!!.setOnLongClickListener {
            println(noButton?.text)
            true
        }

        quitText!!.setOnLongClickListener {
            println(quitText?.text)
            true
        }

        startScreenTitle!!.setOnLongClickListener {
            println(startScreenTitle?.text)
            true
        }

        updateColours()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    /**
     * Opens a box giving the user the choice to close the app
     * completely or not.
     */
    override fun onBackPressed() {
        if (exiting) {
            quitBox!!.visibility = View.INVISIBLE
            playButton!!.visibility = View.VISIBLE
            settingsButton!!.visibility = View.VISIBLE
            exitButton!!.visibility = View.VISIBLE
            playImage!!.visibility = View.VISIBLE
            settingsImage!!.visibility = View.VISIBLE
            exiting = false
        } else {
            quitBox!!.visibility = View.VISIBLE
            playButton!!.visibility = View.INVISIBLE
            settingsButton!!.visibility = View.INVISIBLE
            exitButton!!.visibility = View.INVISIBLE
            playImage!!.visibility = View.INVISIBLE
            settingsImage!!.visibility = View.INVISIBLE
            exiting = true
        }
    }

    private fun speakOut(message : String) {
        textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    /**
     * Applies the chosen colour scheme to the layout.
     */
    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackgroundColour", -10921639)
        menuButtonsColour = preferences.getInt("menuButtonsColour", -8164501)
        menuTextColour = preferences.getInt("menuTextColour", -1)

        constraintLayout!!.setBackgroundColor(applicationBackgroundColour)
        quitBox!!.setBackgroundColor(applicationBackgroundColour)

        playButton!!.setBackgroundColor(menuButtonsColour)
        settingsButton!!.setBackgroundColor(menuButtonsColour)
        exitButton!!.setBackgroundColor(menuButtonsColour)
        yesButton!!.setBackgroundColor(menuButtonsColour)
        noButton!!.setBackgroundColor(menuButtonsColour)
        playImage!!.setColorFilter(menuButtonsColour)
        settingsImage!!.setColorFilter(menuButtonsColour)

        quitText!!.setTextColor(menuTextColour)
        startScreenTitle!!.setTextColor(menuTextColour)
        playButton!!.setTextColor(menuTextColour)
        settingsButton!!.setTextColor(menuTextColour)
        exitButton!!.setTextColor(menuTextColour)
    }
}