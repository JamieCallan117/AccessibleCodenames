package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

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

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

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
            val editor = preferences!!.edit()
            editor.putInt("teamA", -16773377)
            editor.putInt("teamB", -65536)
            editor.putInt("bomb", -14342875)
            editor.putInt("neutral", -908)
            editor.putInt("unmodified", -3684409)
            editor.putInt("applicationBackground", -10921639)
            editor.putInt("menuButton", -8164501)
            editor.putInt("menuText", -1)
            editor.apply()

            updateColours()
        }

        teamAButton?.setOnClickListener { openColourPicker("teamA") }
        teamBButton?.setOnClickListener { openColourPicker("teamB") }
        bombSquareButton?.setOnClickListener { openColourPicker("bomb") }
        neutralSquareButton?.setOnClickListener { openColourPicker("neutral") }
        unmodifiedSquareButton?.setOnClickListener { openColourPicker("unmodified") }
        applicationBackgroundButton?.setOnClickListener { openColourPicker("applicationBackground") }
        menuButtonsButton?.setOnClickListener { openColourPicker("menuButton") }
        menuTextButton?.setOnClickListener { openColourPicker("menuText") }

        updateColours()
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        teamAColour = preferences.getInt("teamA", -16773377)
        teamBColour = preferences.getInt("teamB", -65536)
        bombColour = preferences.getInt("bomb", -14342875)
        neutralColour = preferences.getInt("neutral", -908)
        unmodifiedColour = preferences.getInt("unmodified", -3684409)
        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

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
                val preferences = applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
                val editor = preferences!!.edit()
                defaultColour = color

                when (colourToChange) {
                    "teamA" -> editor.putInt("teamA", defaultColour)
                    "teamB" -> editor.putInt("teamB", defaultColour)
                    "bomb" -> editor.putInt("bomb", defaultColour)
                    "neutral" -> editor.putInt("neutral", defaultColour)
                    "unmodified" -> editor.putInt("unmodified", defaultColour)
                    "applicationBackground" -> editor.putInt("applicationBackground", defaultColour)
                    "menuButton" -> editor.putInt("menuButton", defaultColour)
                    "menuText" -> editor.putInt("menuText", defaultColour)
                }

                editor.apply()

                updateColours()
            }
        })

        ambilWarnaDialog.show()
    }
}