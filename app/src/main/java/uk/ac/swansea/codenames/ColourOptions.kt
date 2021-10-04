package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.File

class ColourOptions : AppCompatActivity() {
    // TODO: Redesign layout.
    // TODO: Figure out how to get inverse colours. And then make the button text inverse of the button.
    // TODO: Add confirmation box to the reset button.
    // TODO: Properly implement TTS.

    private var teamAButton: Button? = null
    private var teamBButton: Button? = null
    private var bombSquareButton: Button? = null
    private var neutralSquareButton: Button? = null
    private var unmodifiedSquareButton: Button? = null
    private var applicationBackgroundButton: Button? = null
    private var menuButtonsButton: Button? = null
    private var menuTextButton: Button? = null
    private var backButton: Button? = null
    private var resetColoursButton: Button? = null
    private var colourTitle: TextView? = null
    private var constraintLayout: ConstraintLayout? = null
    private var defaultColour = 0
    private var preferencesFile = "preferences.txt"
    private var preferences = arrayOfNulls<String>(14)
    private var teamADefault = -16773377
    private var teamBDefault = -65536
    private var bombDefault = -14342875
    private var neutralDefault = -908
    private var unmodifiedDefault = -3684409
    private var applicationBackgroundDefault = -10921639
    private var menuButtonsDefault = -8164501
    private var menuTextDefault = -1
    private var teamAColour = -16773377
    private var teamBColour = -65536
    private var bombColour = -14342875
    private var neutralColour = -908
    private var unmodifiedColour = -3684409
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.colour_options)

        teamAButton = findViewById(R.id.teamAButton)
        teamBButton = findViewById(R.id.teamBButton)
        bombSquareButton = findViewById(R.id.bombSquareButton)
        neutralSquareButton = findViewById(R.id.neutralSquareButton)
        unmodifiedSquareButton = findViewById(R.id.unmodifiedSquareButton)
        applicationBackgroundButton = findViewById(R.id.applicationBackgroundButton)
        menuButtonsButton = findViewById(R.id.menuButtonsButton)
        menuTextButton = findViewById(R.id.menuTextButton)
        backButton = findViewById(R.id.backButton)
        resetColoursButton = findViewById(R.id.resetColoursButton)
        colourTitle = findViewById(R.id.colourTitle)
        constraintLayout = findViewById(R.id.constraintLayout)

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, Settings::class.java)

            if (intent.getStringExtra("from") == "start_screen") {
                i.putExtra("from", "start_screen")
            } else if (intent.getStringExtra("from") == "main_menu") {
                i.putExtra("from", "main_menu")
            }

            startActivity(i)
        }

        resetColoursButton?.setOnClickListener {
            val colourStr = "$teamADefault\n$teamBDefault\n$bombDefault\n$neutralDefault\n$unmodifiedDefault\n$applicationBackgroundDefault\n$menuButtonsDefault\n$menuTextDefault"

            File(preferencesFile).writeText(colourStr)

            updateColours()
        }

        updateColours()

        teamAButton?.setOnClickListener { openColourPicker("teamA") }
        teamBButton?.setOnClickListener { openColourPicker("teamB") }
        bombSquareButton?.setOnClickListener { openColourPicker("bomb") }
        neutralSquareButton?.setOnClickListener { openColourPicker("neutral") }
        unmodifiedSquareButton?.setOnClickListener { openColourPicker("unmodified") }
        applicationBackgroundButton?.setOnClickListener { openColourPicker("applicationBackground") }
        menuButtonsButton?.setOnClickListener { openColourPicker("menuButton") }
        menuTextButton?.setOnClickListener { openColourPicker("menuText") }
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    fun updatePreferencesFile() {
        val preferences = File(preferencesFile).useLines { it.toList() }

        val username = preferences[8]
        val musicVolume = preferences[9]
        val soundFxVolume = preferences[10]
        val vibration = preferences[11]
        val contrast = preferences[12]
        val textToSpeech = preferences[13]


        val prefStr = "$teamAColour\n$teamBColour\n$bombColour\n$neutralColour\n$unmodifiedColour" +
                "\n$applicationBackgroundColour\n$menuButtonsColour\n$menuTextColour\n$username" +
                "\n$musicVolume\n$soundFxVolume\n$vibration\n$contrast\n$textToSpeech"

        File(preferencesFile).writeText(prefStr)

        updateColours()
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
        val colours = File(preferencesFile).useLines { it.toList() }

        teamAColour = colours[0].toInt()
        teamBColour = colours[1].toInt()
        bombColour = colours[2].toInt()
        neutralColour = colours[3].toInt()
        unmodifiedColour = colours[4].toInt()
        applicationBackgroundColour = colours[5].toInt()
        menuButtonsColour = colours[6].toInt()
        menuTextColour = colours[7].toInt()

        teamAButton?.setBackgroundColor(teamAColour)
        teamBButton?.setBackgroundColor(teamBColour)
        bombSquareButton?.setBackgroundColor(bombColour)
        neutralSquareButton?.setBackgroundColor(neutralColour)
        unmodifiedSquareButton?.setBackgroundColor(unmodifiedColour)
        applicationBackgroundButton?.setBackgroundColor(applicationBackgroundColour)
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        menuButtonsButton?.setBackgroundColor(menuButtonsColour)
        backButton?.setBackgroundColor(menuButtonsColour)
        resetColoursButton?.setBackgroundColor(menuButtonsColour)
        menuTextButton?.setBackgroundColor(menuTextColour)
        colourTitle?.setBackgroundColor(menuTextColour)
    }

    private fun openColourPicker(colourToChange: String?) {
        val ambilWarnaDialog = AmbilWarnaDialog(this, defaultColour, object : OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}

            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                defaultColour = color

                when (colourToChange) {
                    "teamA" -> teamAColour = defaultColour
                    "teamB" -> teamBColour = defaultColour
                    "bomb" -> bombColour = defaultColour
                    "neutral" -> neutralColour = defaultColour
                    "unmodified" -> unmodifiedColour = defaultColour
                    "applicationBackground" -> applicationBackgroundColour = defaultColour
                    "menuButton" -> menuButtonsColour = defaultColour
                    "menuText" -> menuTextColour = defaultColour
                }

                updatePreferencesFile()
            }
        })

        ambilWarnaDialog.show()
    }
}