package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import java.io.IOException
import kotlin.system.exitProcess

class StartScreen : AppCompatActivity() {
    // TODO: Redesign layout.
    // TODO: On first launch, ask about TTS settings.
    // TODO: Add an exit button.
    // TODO: Properly implement TTS.

    private var quitBox: GridLayout? = null
    private var constraintLayout: ConstraintLayout? = null
    private var playButton: Button? = null
    private var settingsButton: Button? = null
    private var yesButton: Button? = null
    private var noButton: Button? = null
    private var quitText: TextView? = null
    private var startScreenTitle: TextView? = null
    private var preferencesFile = "assets/preferences.txt"
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var exiting = false
    private var preferences = arrayOfNulls<String>(14)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        quitBox = findViewById(R.id.quitBox)
        playButton = findViewById(R.id.playButton)
        settingsButton = findViewById(R.id.settingsButton)
        constraintLayout = findViewById(R.id.saveButton)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)
        quitText = findViewById(R.id.quitText)
        startScreenTitle = findViewById(R.id.startScreenTitle)

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

        playButton!!.setOnClickListener {
            val i = Intent(this, MainMenu::class.java)
            startActivity(i)
        }

        settingsButton!!.setOnClickListener {
            val i = Intent(this, Settings::class.java)
            i.putExtra("from", "start_screen")
            startActivity(i)
        }

        yesButton!!.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }

        noButton!!.setOnClickListener {
            quitBox!!.visibility = View.INVISIBLE
            playButton!!.visibility = View.VISIBLE
            settingsButton!!.visibility = View.VISIBLE
            exiting = false
        }

        //This is how TTS will work. Pressing the button quickly performs it's default action. Holding it
        //with a return true in this listener, will cause it to run the code in here, and not run the code
        //for a regular click.
        //For actual TTS it won't print out the word, it'll obviously read it aloud
        playButton!!.setOnLongClickListener {
            println(playButton?.text)
            true
        }
        settingsButton!!.setOnLongClickListener {
            println(settingsButton?.text)
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

    override fun onBackPressed() {
        if (exiting) {
            quitBox!!.visibility = View.INVISIBLE
            playButton!!.visibility = View.VISIBLE
            settingsButton!!.visibility = View.VISIBLE
            exiting = false
        } else {
            quitBox!!.visibility = View.VISIBLE
            playButton!!.visibility = View.INVISIBLE
            settingsButton!!.visibility = View.INVISIBLE
            exiting = true
        }
    }

    fun updateColours() {
        applicationBackgroundColour = preferences[5]?.toInt()!!
        menuButtonsColour = preferences[6]?.toInt()!!
        menuTextColour = preferences[7]?.toInt()!!

        constraintLayout!!.setBackgroundColor(applicationBackgroundColour)
        quitBox!!.setBackgroundColor(applicationBackgroundColour)
        playButton!!.setBackgroundColor(menuButtonsColour)
        settingsButton!!.setBackgroundColor(menuButtonsColour)
        yesButton!!.setBackgroundColor(menuButtonsColour)
        noButton!!.setBackgroundColor(menuButtonsColour)
        quitText!!.setTextColor(menuTextColour)
        startScreenTitle!!.setTextColor(menuTextColour)
        playButton!!.setTextColor(menuTextColour)
        settingsButton!!.setTextColor(menuTextColour)
    }
}