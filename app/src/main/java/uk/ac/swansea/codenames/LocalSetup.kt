package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*

class LocalSetup : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var customWordsLinear: LinearLayout? = null
    private var localSetupTitle: MaterialTextView? = null
    private var localSetupSubtitle: MaterialTextView? = null
    private var bombSquaresText: MaterialTextView? = null
    private var neutralSquaresText: MaterialTextView? = null
    private var teamASquaresText: MaterialTextView? = null
    private var teamBSquaresText: MaterialTextView? = null
    private var startingTeamText: MaterialTextView? = null
    private var customWordsText: MaterialTextView? = null
    private var backButton: MaterialButton? = null
    private var gameOptionsButton: MaterialButton? = null
    private var playButton: MaterialButton? = null
    private val customWordTexts = arrayOfNulls<TextView>(10)
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.local_setup)

        constraintLayout = findViewById(R.id.constraintLayout)
        customWordsLinear = findViewById(R.id.customWordsLinear)
        localSetupTitle = findViewById(R.id.localSetupTitle)
        localSetupSubtitle = findViewById(R.id.localSetupSubtitle)
        bombSquaresText = findViewById(R.id.bombSquaresText)
        neutralSquaresText = findViewById(R.id.neutralSquaresText)
        teamASquaresText = findViewById(R.id.teamASquaresText)
        teamBSquaresText = findViewById(R.id.teamBSquaresText)
        startingTeamText = findViewById(R.id.startingTeamText)
        customWordsText = findViewById(R.id.customWordsText)
        backButton = findViewById(R.id.backButton)
        gameOptionsButton = findViewById(R.id.gameOptionsButton)
        playButton = findViewById(R.id.playButton)

        textToSpeech = TextToSpeech(this, this)

        bombSquaresText?.text = getString(R.string.bomb_squares, intent.getIntExtra("bombSquares", 1))
        neutralSquaresText?.text = getString(R.string.neutral_squares, intent.getIntExtra("neutralSquares", 7))
        teamASquaresText?.text = getString(R.string.team_a_squares, intent.getIntExtra("teamASquares", 9))
        teamBSquaresText?.text = getString(R.string.team_b_squares, intent.getIntExtra("teamBSquares", 8))

        val startingTeam = if (intent.getStringExtra("startingTeam") == "B") {
            "B"
        } else {
            "A"
        }

        startingTeamText?.text = getString(R.string.starting_team, startingTeam)

        if (intent.getStringArrayListExtra("customWords") != null && intent.getStringArrayListExtra("customWords")!!.isNotEmpty()) {
            customWordsText?.visibility = View.VISIBLE

            var counter = 0

            while (counter < intent.getStringArrayListExtra("customWords")!!.size) {
                val newCustomWord = MaterialTextView(this)

                newCustomWord.text = intent.getStringArrayListExtra("customWords")!![counter]

                newCustomWord.setOnLongClickListener {
                    speakOut(newCustomWord.text.toString())
                    true
                }

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.bottomMargin = 16

                newCustomWord.layoutParams = params

                val typeface = ResourcesCompat.getFont(this, R.font.general_font)

                newCustomWord.typeface = typeface
                newCustomWord.textSize = 17f

                customWordTexts[counter] = newCustomWord
                customWordsLinear?.addView(newCustomWord)

                counter++
            }
        }

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        gameOptionsButton?.setOnClickListener {
            val i = Intent(applicationContext, GameOptions::class.java)

            i.putExtra("type", "local")

            if (intent.getBooleanExtra("hasCustomSettings", false)) {
                i.putExtra("hasCustomSettings", true)
                i.putExtra("bombSquares", intent.getIntExtra("bombSquares", 1))
                i.putExtra("neutralSquares", intent.getIntExtra("neutralSquares", 7))
                i.putExtra("teamASquares", intent.getIntExtra("teamASquares", 9))
                i.putExtra("teamBSquares", intent.getIntExtra("teamBSquares", 8))
                i.putExtra("startingTeam", intent.getStringExtra("startingTeam"))
                i.putStringArrayListExtra("customWords", intent.getStringArrayListExtra("customWords"))
            }

            startActivity(i)
        }

        playButton?.setOnClickListener {
            val i = Intent(applicationContext, LocalGame::class.java)

            if (intent.getBooleanExtra("hasCustomSettings", false)) {
                i.putExtra("hasCustomSettings", true)
                i.putExtra("bombSquares", intent.getIntExtra("bombSquares", 1))
                i.putExtra("neutralSquares", intent.getIntExtra("neutralSquares", 7))
                i.putExtra("teamASquares", intent.getIntExtra("teamASquares", 9))
                i.putExtra("teamBSquares", intent.getIntExtra("teamBSquares", 8))
                i.putExtra("startingTeam", intent.getStringExtra("startingTeam"))
                i.putStringArrayListExtra("customWords", intent.getStringArrayListExtra("customWords"))
            }

            startActivity(i)
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        localSetupTitle?.setOnLongClickListener {
            speakOut(localSetupTitle?.text.toString() + ", " + localSetupSubtitle?.text.toString())
            true
        }

        localSetupSubtitle?.setOnLongClickListener {
            speakOut(localSetupTitle?.text.toString() + ", " + localSetupSubtitle?.text.toString())
            true
        }

        gameOptionsButton?.setOnLongClickListener {
            speakOut(gameOptionsButton?.text.toString())
            true
        }

        playButton?.setOnLongClickListener {
            speakOut(playButton?.text.toString())
            true
        }

        bombSquaresText?.setOnLongClickListener {
            speakOut(bombSquaresText?.text.toString())
            true
        }

        neutralSquaresText?.setOnLongClickListener {
            speakOut(neutralSquaresText?.text.toString())
            true
        }

        teamASquaresText?.setOnLongClickListener {
            speakOut(teamASquaresText?.text.toString().replace(" A ", " Ay "))
            true
        }

        teamBSquaresText?.setOnLongClickListener {
            speakOut(teamBSquaresText?.text.toString())
            true
        }

        startingTeamText?.setOnLongClickListener {
            speakOut(startingTeamText?.text.toString())
            true
        }

        customWordsText?.setOnLongClickListener {
            var customWordTTS = customWordsText?.text.toString()

            for (word in customWordsLinear!!.children) {
                word as MaterialTextView
                customWordTTS += "\n${word.text}"
            }

            speakOut(customWordTTS)

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

    private fun speakOut(message: String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        gameOptionsButton?.setBackgroundColor(menuButtonsColour)
        playButton?.setBackgroundColor(menuButtonsColour)

        localSetupTitle?.setTextColor(menuTextColour)
        localSetupSubtitle?.setTextColor(menuTextColour)
        bombSquaresText!!.setTextColor(menuTextColour)
        neutralSquaresText!!.setTextColor(menuTextColour)
        teamASquaresText!!.setTextColor(menuTextColour)
        teamBSquaresText!!.setTextColor(menuTextColour)
        startingTeamText!!.setTextColor(menuTextColour)
        customWordsText!!.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        gameOptionsButton?.setTextColor(menuTextColour)
        playButton?.setTextColor(menuTextColour)

        for (t in customWordTexts) {
            t?.setTextColor(menuTextColour)
        }
    }
}