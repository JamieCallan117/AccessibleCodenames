package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*
import kotlin.system.exitProcess

/**
 * Starting screen, what is seen when the app is launched. Can go to the main menu, or the settings page from here.
 */
class StartScreen : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var quitBox: ConstraintLayout? = null
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
            textToSpeech?.stop()

            val i = Intent(this, MainMenu::class.java)
            startActivity(i)
        }

        settingsButton!!.setOnClickListener {
            textToSpeech?.stop()

            val i = Intent(this, Settings::class.java)
            i.putExtra("from", "start_screen")
            startActivity(i)
        }

        exitButton!!.setOnClickListener {
            textToSpeech?.stop()

            onBackPressed()
        }

        yesButton!!.setOnClickListener {
            textToSpeech?.stop()

            //Closes the app.
            finishAffinity()
            exitProcess(0)
        }

        noButton!!.setOnClickListener {
            textToSpeech?.stop()

            quitBox!!.visibility = View.INVISIBLE
            playButton!!.visibility = View.VISIBLE
            settingsButton!!.visibility = View.VISIBLE
            exitButton!!.visibility = View.VISIBLE
            playImage!!.visibility = View.VISIBLE
            settingsImage!!.visibility = View.VISIBLE
            startScreenTitle!!.visibility = View.VISIBLE
            exiting = false
        }

        playButton!!.setOnLongClickListener {
            speakOut(playButton?.text.toString())
            true
        }

        settingsButton!!.setOnLongClickListener {
            speakOut(settingsButton?.text.toString())
            true
        }

        exitButton!!.setOnLongClickListener {
            speakOut(exitButton?.text.toString())
            true
        }

        yesButton!!.setOnLongClickListener {
            speakOut(yesButton?.text.toString())
            true
        }

        noButton!!.setOnLongClickListener {
            speakOut(noButton?.text.toString())
            true
        }

        quitText!!.setOnLongClickListener {
            speakOut(quitText?.text.toString().replace("\n",""))
            true
        }

        startScreenTitle!!.setOnLongClickListener {
            speakOut(startScreenTitle?.text.toString())
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
        textToSpeech?.stop()

        if (exiting) {
            quitBox!!.visibility = View.INVISIBLE
            playButton!!.visibility = View.VISIBLE
            settingsButton!!.visibility = View.VISIBLE
            exitButton!!.visibility = View.VISIBLE
            playImage!!.visibility = View.VISIBLE
            settingsImage!!.visibility = View.VISIBLE
            startScreenTitle!!.visibility = View.VISIBLE
            exiting = false
        } else {
            speakOut(quitText?.text.toString().replace("\n",""))

            quitBox!!.visibility = View.VISIBLE
            playButton!!.visibility = View.INVISIBLE
            settingsButton!!.visibility = View.INVISIBLE
            exitButton!!.visibility = View.INVISIBLE
            playImage!!.visibility = View.INVISIBLE
            settingsImage!!.visibility = View.INVISIBLE
            startScreenTitle!!.visibility = View.INVISIBLE
            exiting = true
        }
    }

    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Applies the chosen colour scheme to the layout.
     */
    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButtons", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

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