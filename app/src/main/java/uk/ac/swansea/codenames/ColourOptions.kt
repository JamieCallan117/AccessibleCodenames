package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.speech.tts.TextToSpeech
import android.widget.Button
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.util.*

class ColourOptions : AppCompatActivity(), TextToSpeech.OnInitListener {

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
    private var neutralColour = -11731092
    private var unmodifiedColour = -3684409
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null

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

        textToSpeech = TextToSpeech(this, this)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        backButton?.setOnClickListener {
            textToSpeech?.stop()

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
            editor.putInt("neutral", -11731092)
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

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        resetColoursButton?.setOnLongClickListener {
            speakOut(resetColoursButton?.text.toString())
            true
        }

        colourTitle?.setOnLongClickListener {
            speakOut(colourTitle?.text.toString())
            true
        }

        teamAButton?.setOnLongClickListener {
            speakOut(teamAButton?.text.toString())
            true
        }

        teamBButton?.setOnLongClickListener {
            speakOut(teamBButton?.text.toString())
            true
        }

        bombSquareButton?.setOnLongClickListener {
            speakOut(bombSquareButton?.text.toString().replace("\n", ""))
            true
        }

        neutralSquareButton?.setOnLongClickListener {
            speakOut(neutralSquareButton?.text.toString().replace("\n", ""))
            true
        }

        unmodifiedSquareButton?.setOnLongClickListener {
            speakOut(unmodifiedSquareButton?.text.toString().replace("\n", ""))
            true
        }

        menuButtonsButton?.setOnLongClickListener {
            speakOut(menuButtonsButton?.text.toString().replace("\n", ""))
            true
        }

        menuTextButton?.setOnLongClickListener {
            speakOut(menuTextButton?.text.toString().replace("\n", ""))
            true
        }

        applicationBackgroundButton?.setOnLongClickListener {
            speakOut(applicationBackgroundButton?.text.toString().replace("\n", ""))
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

        teamAColour = preferences.getInt("teamA", -16773377)
        teamBColour = preferences.getInt("teamB", -65536)
        bombColour = preferences.getInt("bomb", -14342875)
        neutralColour = preferences.getInt("neutral", -11731092)
        unmodifiedColour = preferences.getInt("unmodified", -3684409)
        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

        val teamAColourNegative = Color.rgb(255 - Color.red(teamAColour), 255 - Color.green(teamAColour), 255 - Color.blue(teamAColour))
        val teamBColourNegative = Color.rgb(255 - Color.red(teamBColour), 255 - Color.green(teamBColour), 255 - Color.blue(teamBColour))
        val bombColourNegative = Color.rgb(255 - Color.red(bombColour), 255 - Color.green(bombColour), 255 - Color.blue(bombColour))
        val neutralColourNegative = Color.rgb(255 - Color.red(neutralColour), 255 - Color.green(neutralColour), 255 - Color.blue(neutralColour))
        val unmodifiedColourNegative = Color.rgb(255 - Color.red(unmodifiedColour), 255 - Color.green(unmodifiedColour), 255 - Color.blue(unmodifiedColour))
        val applicationBackgroundColourNegative = Color.rgb(255 - Color.red(applicationBackgroundColour), 255 - Color.green(applicationBackgroundColour), 255 - Color.blue(applicationBackgroundColour))
        val menuButtonsColourNegative = Color.rgb(255 - Color.red(menuButtonsColour), 255 - Color.green(menuButtonsColour), 255 - Color.blue(menuButtonsColour))
        val menuTextColourNegative = Color.rgb(255 - Color.red(menuTextColour), 255 - Color.green(menuTextColour), 255 - Color.blue(menuTextColour))

        teamAButton?.setTextColor(teamAColourNegative)
        teamAButton?.setBackgroundColor(teamAColour)

        teamBButton?.setTextColor(teamBColourNegative)
        teamBButton?.setBackgroundColor(teamBColour)

        bombSquareButton?.setTextColor(bombColourNegative)
        bombSquareButton?.setBackgroundColor(bombColour)

        neutralSquareButton?.setTextColor(neutralColourNegative)
        neutralSquareButton?.setBackgroundColor(neutralColour)

        unmodifiedSquareButton?.setTextColor(unmodifiedColourNegative)
        unmodifiedSquareButton?.setBackgroundColor(unmodifiedColour)

        applicationBackgroundButton?.setTextColor(applicationBackgroundColourNegative)
        applicationBackgroundButton?.setBackgroundColor(applicationBackgroundColour)
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        menuButtonsButton?.setTextColor(menuButtonsColourNegative)
        menuButtonsButton?.setBackgroundColor(menuButtonsColour)
        backButton?.setBackgroundColor(menuButtonsColour)
        resetColoursButton?.setBackgroundColor(menuButtonsColour)

        menuTextButton?.setTextColor(menuTextColourNegative)
        menuTextButton?.setBackgroundColor(menuTextColour)
        colourTitle?.setTextColor(menuTextColour)
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