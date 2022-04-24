package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.util.*

class ColourOptions : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var teamAButton: MaterialButton? = null
    private var teamBButton: MaterialButton? = null
    private var bombSquareButton: MaterialButton? = null
    private var neutralSquareButton: MaterialButton? = null
    private var unmodifiedSquareButton: MaterialButton? = null
    private var applicationBackgroundButton: MaterialButton? = null
    private var menuButtonsButton: MaterialButton? = null
    private var menuTextButton: MaterialButton? = null
    private var backButton: MaterialButton? = null
    private var optionsButton: MaterialButton? = null
    private var closeOptionsButton: MaterialButton? = null
    private var saveButton: MaterialButton? = null
    private var loadButton: MaterialButton? = null
    private var resetButton: MaterialButton? = null
    private var slotOne: MaterialButton? = null
    private var slotTwo: MaterialButton? = null
    private var slotThree: MaterialButton? = null
    private var colourTitle: MaterialTextView? = null
    private var constraintLayout: ConstraintLayout? = null
    private var optionsBox: ConstraintLayout? = null
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
    private var buttonClick: MediaPlayer? = null
    private var optionsBoxOpen = false
    private var saving = false
    private var loading = false

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
        optionsButton = findViewById(R.id.optionsButton)
        colourTitle = findViewById(R.id.colourTitle)
        constraintLayout = findViewById(R.id.constraintLayout)
        optionsBox = findViewById(R.id.optionsBox)
        closeOptionsButton = findViewById(R.id.closeOptionsButton)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)
        resetButton = findViewById(R.id.resetButton)
        slotOne = findViewById(R.id.slotOne)
        slotTwo = findViewById(R.id.slotTwo)
        slotThree = findViewById(R.id.slotThree)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        backButton?.setOnClickListener {
            textToSpeech?.stop()

            buttonClick?.start()

            val i = Intent(applicationContext, Settings::class.java)

            if (intent.getStringExtra("from") == "start_screen") {
                i.putExtra("from", "start_screen")
            } else if (intent.getStringExtra("from") == "main_menu") {
                i.putExtra("from", "main_menu")
            }

            startActivity(i)
        }

        optionsButton?.setOnClickListener {
            buttonClick?.start()

            optionsBoxOpen = true

            toggleOptionsBox()

            optionsBox?.visibility = View.VISIBLE


        }

        saveButton?.setOnClickListener {
            buttonClick?.start()

            saving = true
            loading = false

            saveButton?.alpha = 1.0f
            loadButton?.alpha = 0.5f
            resetButton?.alpha = 0.5f
        }

        loadButton?.setOnClickListener {
            buttonClick?.start()

            loading = true
            saving = false

            loadButton?.alpha = 1.0f
            saveButton?.alpha = 0.5f
            resetButton?.alpha = 0.5f
        }

        resetButton?.setOnClickListener {
            buttonClick?.start()

            saveButton?.alpha = 1.0f
            resetButton?.alpha = 1.0f
            loadButton?.alpha = 1.0f

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

        closeOptionsButton?.setOnClickListener {
            buttonClick?.start()

            optionsBoxOpen = false

            toggleOptionsBox()

            optionsBox?.visibility = View.GONE

            saveButton?.alpha = 1.0f
            resetButton?.alpha = 1.0f
            loadButton?.alpha = 1.0f
        }

        slotOne?.setOnClickListener {
            buttonClick?.start()

            if (saving) {
                teamAColour = preferences.getInt("teamA", -16773377)
                teamBColour = preferences.getInt("teamB", -65536)
                bombColour = preferences.getInt("bomb", -14342875)
                neutralColour = preferences.getInt("neutral", -11731092)
                unmodifiedColour = preferences.getInt("unmodified", -3684409)
                applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
                menuButtonsColour = preferences.getInt("menuButton", -8164501)
                menuTextColour = preferences.getInt("menuText", -1)

                val editor = preferences!!.edit()
                editor.putInt("teamA1", teamAColour)
                editor.putInt("teamB1", teamBColour)
                editor.putInt("bomb1", bombColour)
                editor.putInt("neutral1", neutralColour)
                editor.putInt("unmodified1", unmodifiedColour)
                editor.putInt("applicationBackground1", applicationBackgroundColour)
                editor.putInt("menuButton1", menuButtonsColour)
                editor.putInt("menuText1", menuTextColour)
                editor.apply()
            } else if (loading) {
                teamAColour = preferences.getInt("teamA1", -16773377)
                teamBColour = preferences.getInt("teamB1", -65536)
                bombColour = preferences.getInt("bomb1", -14342875)
                neutralColour = preferences.getInt("neutral1", -11731092)
                unmodifiedColour = preferences.getInt("unmodified1", -3684409)
                applicationBackgroundColour = preferences.getInt("applicationBackground1", -10921639)
                menuButtonsColour = preferences.getInt("menuButton1", -8164501)
                menuTextColour = preferences.getInt("menuText1", -1)

                val editor = preferences!!.edit()
                editor.putInt("teamA", teamAColour)
                editor.putInt("teamB", teamBColour)
                editor.putInt("bomb", bombColour)
                editor.putInt("neutral", neutralColour)
                editor.putInt("unmodified", unmodifiedColour)
                editor.putInt("applicationBackground", applicationBackgroundColour)
                editor.putInt("menuButton", menuButtonsColour)
                editor.putInt("menuText", menuTextColour)
                editor.apply()

                updateColours()
            }

            saveButton?.alpha = 1.0f
            loadButton?.alpha = 1.0f
            resetButton?.alpha = 1.0f

            saving = false
            loading = false
        }

        slotTwo?.setOnClickListener {
            buttonClick?.start()

            if (saving) {
                teamAColour = preferences.getInt("teamA", -16773377)
                teamBColour = preferences.getInt("teamB", -65536)
                bombColour = preferences.getInt("bomb", -14342875)
                neutralColour = preferences.getInt("neutral", -11731092)
                unmodifiedColour = preferences.getInt("unmodified", -3684409)
                applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
                menuButtonsColour = preferences.getInt("menuButton", -8164501)
                menuTextColour = preferences.getInt("menuText", -1)

                val editor = preferences!!.edit()
                editor.putInt("teamA2", teamAColour)
                editor.putInt("teamB2", teamBColour)
                editor.putInt("bomb2", bombColour)
                editor.putInt("neutral2", neutralColour)
                editor.putInt("unmodified2", unmodifiedColour)
                editor.putInt("applicationBackground2", applicationBackgroundColour)
                editor.putInt("menuButton2", menuButtonsColour)
                editor.putInt("menuText2", menuTextColour)
                editor.apply()
            } else if (loading) {
                teamAColour = preferences.getInt("teamA2", -16773377)
                teamBColour = preferences.getInt("teamB2", -65536)
                bombColour = preferences.getInt("bomb2", -14342875)
                neutralColour = preferences.getInt("neutral2", -11731092)
                unmodifiedColour = preferences.getInt("unmodified2", -3684409)
                applicationBackgroundColour = preferences.getInt("applicationBackground2", -10921639)
                menuButtonsColour = preferences.getInt("menuButton2", -8164501)
                menuTextColour = preferences.getInt("menuText2", -1)

                val editor = preferences!!.edit()
                editor.putInt("teamA", teamAColour)
                editor.putInt("teamB", teamBColour)
                editor.putInt("bomb", bombColour)
                editor.putInt("neutral", neutralColour)
                editor.putInt("unmodified", unmodifiedColour)
                editor.putInt("applicationBackground", applicationBackgroundColour)
                editor.putInt("menuButton", menuButtonsColour)
                editor.putInt("menuText", menuTextColour)
                editor.apply()

                updateColours()
            }

            saveButton?.alpha = 1.0f
            loadButton?.alpha = 1.0f
            resetButton?.alpha = 1.0f

            saving = false
            loading = false
        }

        slotThree?.setOnClickListener {
            buttonClick?.start()

            if (saving) {
                teamAColour = preferences.getInt("teamA", -16773377)
                teamBColour = preferences.getInt("teamB", -65536)
                bombColour = preferences.getInt("bomb", -14342875)
                neutralColour = preferences.getInt("neutral", -11731092)
                unmodifiedColour = preferences.getInt("unmodified", -3684409)
                applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
                menuButtonsColour = preferences.getInt("menuButton", -8164501)
                menuTextColour = preferences.getInt("menuText", -1)

                val editor = preferences!!.edit()
                editor.putInt("teamA3", teamAColour)
                editor.putInt("teamB3", teamBColour)
                editor.putInt("bomb3", bombColour)
                editor.putInt("neutral3", neutralColour)
                editor.putInt("unmodified3", unmodifiedColour)
                editor.putInt("applicationBackground3", applicationBackgroundColour)
                editor.putInt("menuButton3", menuButtonsColour)
                editor.putInt("menuText3", menuTextColour)
                editor.apply()
            } else if (loading) {
                teamAColour = preferences.getInt("teamA3", -16773377)
                teamBColour = preferences.getInt("teamB3", -65536)
                bombColour = preferences.getInt("bomb3", -14342875)
                neutralColour = preferences.getInt("neutral3", -11731092)
                unmodifiedColour = preferences.getInt("unmodified3", -3684409)
                applicationBackgroundColour = preferences.getInt("applicationBackground3", -10921639)
                menuButtonsColour = preferences.getInt("menuButton3", -8164501)
                menuTextColour = preferences.getInt("menuText3", -1)

                val editor = preferences!!.edit()
                editor.putInt("teamA", teamAColour)
                editor.putInt("teamB", teamBColour)
                editor.putInt("bomb", bombColour)
                editor.putInt("neutral", neutralColour)
                editor.putInt("unmodified", unmodifiedColour)
                editor.putInt("applicationBackground", applicationBackgroundColour)
                editor.putInt("menuButton", menuButtonsColour)
                editor.putInt("menuText", menuTextColour)
                editor.apply()

                updateColours()
            }

            saveButton?.alpha = 1.0f
            loadButton?.alpha = 1.0f
            resetButton?.alpha = 1.0f

            saving = false
            loading = false
        }

        teamAButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("teamA")
        }

        teamBButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("teamB")
        }

        bombSquareButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("bomb")
        }

        neutralSquareButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("neutral")
        }

        unmodifiedSquareButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("unmodified")
        }

        applicationBackgroundButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("applicationBackground")
        }

        menuButtonsButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("menuButton")
        }

        menuTextButton?.setOnClickListener {
            buttonClick?.start()
            openColourPicker("menuText")
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        optionsButton?.setOnLongClickListener {
            speakOut(optionsButton?.text.toString())
            true
        }

        closeOptionsButton?.setOnLongClickListener {
            speakOut(closeOptionsButton?.text.toString())
            true
        }

        saveButton?.setOnLongClickListener {
            speakOut(saveButton?.text.toString())
            true
        }

        loadButton?.setOnLongClickListener {
            speakOut(loadButton?.text.toString())
            true
        }

        resetButton?.setOnLongClickListener {
            speakOut(resetButton?.text.toString())
            true
        }

        slotOne?.setOnLongClickListener {
            speakOut(slotOne?.text.toString())
            true
        }

        slotTwo?.setOnLongClickListener {
            speakOut(slotTwo?.text.toString())
            true
        }

        slotThree?.setOnLongClickListener {
            speakOut(slotThree?.text.toString())
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

    override fun onResume() {
        super.onResume()

        val intent = Intent(this, BackgroundMusicService::class.java)

        intent.action = "RESUME"

        startService(intent)
    }

    override fun onPause() {
        super.onPause()

        val intent = Intent(this, BackgroundMusicService::class.java)

        intent.action = "PAUSE"

        startService(intent)
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

    private fun toggleOptionsBox() {
        backButton?.isEnabled = !optionsBoxOpen
        optionsButton?.isEnabled = !optionsBoxOpen
        teamAButton?.isEnabled = !optionsBoxOpen
        teamBButton?.isEnabled = !optionsBoxOpen
        bombSquareButton?.isEnabled = !optionsBoxOpen
        neutralSquareButton?.isEnabled = !optionsBoxOpen
        unmodifiedSquareButton?.isEnabled = !optionsBoxOpen
        applicationBackgroundButton?.isEnabled = !optionsBoxOpen
        menuButtonsButton?.isEnabled = !optionsBoxOpen
        menuTextButton?.isEnabled = !optionsBoxOpen
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
        optionsBox?.setBackgroundColor(applicationBackgroundColour)

        menuButtonsButton?.setTextColor(menuButtonsColourNegative)
        menuButtonsButton?.setBackgroundColor(menuButtonsColour)
        backButton?.setBackgroundColor(menuButtonsColour)
        optionsButton?.setBackgroundColor(menuButtonsColour)
        closeOptionsButton?.setBackgroundColor(menuButtonsColour)
        saveButton?.setBackgroundColor(menuButtonsColour)
        loadButton?.setBackgroundColor(menuButtonsColour)
        resetButton?.setBackgroundColor(menuButtonsColour)
        slotOne?.setBackgroundColor(menuButtonsColour)
        slotTwo?.setBackgroundColor(menuButtonsColour)
        slotThree?.setBackgroundColor(menuButtonsColour)

        menuTextButton?.setTextColor(menuTextColourNegative)
        menuTextButton?.setBackgroundColor(menuTextColour)
        colourTitle?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        optionsButton?.setTextColor(menuTextColour)
        closeOptionsButton?.setTextColor(menuTextColour)
        saveButton?.setTextColor(menuTextColour)
        loadButton?.setTextColor(menuTextColour)
        resetButton?.setTextColor(menuTextColour)
        slotOne?.setTextColor(menuTextColour)
        slotTwo?.setTextColor(menuTextColour)
        slotThree?.setTextColor(menuTextColour)
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