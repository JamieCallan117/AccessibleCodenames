package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.util.*
import kotlin.collections.ArrayList

/**
 * Setting up a local game.
 */
class LocalSetup : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var setupTeamsBox: ConstraintLayout? = null
    private var customWordsLinear: LinearLayout? = null
    private var localSetupTitle: MaterialTextView? = null
    private var localSetupSubtitle: MaterialTextView? = null
    private var bombSquaresText: MaterialTextView? = null
    private var neutralSquaresText: MaterialTextView? = null
    private var teamASquaresText: MaterialTextView? = null
    private var teamBSquaresText: MaterialTextView? = null
    private var startingTeamText: MaterialTextView? = null
    private var customWordsText: MaterialTextView? = null
    private var teamsNote: MaterialTextView? = null
    private var backButton: MaterialButton? = null
    private var gameOptionsButton: MaterialButton? = null
    private var playButton: MaterialButton? = null
    private var setupTeams: MaterialButton? = null
    private var closeSetupTeams: MaterialButton? = null
    private var teamASpymasterEdit: TextInputEditText? = null
    private var teamBSpymasterEdit: TextInputEditText? = null
    private var teamAMemberEdit: TextInputEditText? = null
    private var teamBMemberEdit: TextInputEditText? = null
    private val customWordTexts = arrayOfNulls<TextView>(10)
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null
    private var buttonClick: MediaPlayer? = null
    private var teamsWindowOpen = false
    private var teamsAdded = false
    private var spymasterA = ""
    private var spymasterB = ""
    private var teamAMembers = ArrayList<String>()
    private var teamBMembers = ArrayList<String>()

    /**
     * Sets up layout and listeners etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.local_setup)

        constraintLayout = findViewById(R.id.constraintLayout)
        setupTeamsBox = findViewById(R.id.setupTeamsBox)
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
        setupTeams = findViewById(R.id.setupTeams)
        closeSetupTeams = findViewById(R.id.closeSetupTeams)
        teamsNote = findViewById(R.id.teamsNote)
        teamASpymasterEdit = findViewById(R.id.teamASpymasterEdit)
        teamBSpymasterEdit = findViewById(R.id.teamBSpymasterEdit)
        teamAMemberEdit = findViewById(R.id.teamAMemberEdit)
        teamBMemberEdit = findViewById(R.id.teamBMemberEdit)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        //Display settings for the game.
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
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        gameOptionsButton?.setOnClickListener {
            buttonClick?.start()

            val i = Intent(applicationContext, GameOptions::class.java)

            i.putExtra("type", "local")

            if (intent.getBooleanExtra("hasCustomSettings", false)) {
                textToSpeech?.stop()

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

        setupTeams?.setOnClickListener {
            buttonClick?.start()

            teamsWindowOpen = true

            toggleTeamsWindow()

            setupTeamsBox?.visibility = View.VISIBLE
        }

        closeSetupTeams?.setOnClickListener {
            buttonClick?.start()

            teamsWindowOpen = false

            toggleTeamsWindow()

            setupTeamsBox?.visibility = View.GONE

            addTeams()
        }

        playButton?.setOnClickListener {
            buttonClick?.start()

            val i = Intent(applicationContext, LocalGame::class.java)

            if (intent.getBooleanExtra("hasCustomSettings", false)) {
                textToSpeech?.stop()

                i.putExtra("hasCustomSettings", true)
                i.putExtra("bombSquares", intent.getIntExtra("bombSquares", 1))
                i.putExtra("neutralSquares", intent.getIntExtra("neutralSquares", 7))
                i.putExtra("teamASquares", intent.getIntExtra("teamASquares", 9))
                i.putExtra("teamBSquares", intent.getIntExtra("teamBSquares", 8))
                i.putExtra("startingTeam", intent.getStringExtra("startingTeam"))
                i.putStringArrayListExtra("customWords", intent.getStringArrayListExtra("customWords"))
            }

            if (teamsAdded) {
                i.putExtra("hasTeams", true)
                i.putExtra("spymasterA", spymasterA)
                i.putExtra("spymasterB", spymasterB)
                i.putExtra("teamAMembers", teamAMembers)
                i.putExtra("teamBMembers", teamBMembers)
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

    /**
     * When app is resumed after being minimised.
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
     * Sets up Text-to-Speech engine
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    /**
     * When device's back button pressed.
     */
    override fun onBackPressed() {
        backButton?.performClick()
    }

    /**
     * Reads aloud given message.
     */
    private fun speakOut(message: String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Disables elements whilst teams window open.
     */
    private fun toggleTeamsWindow() {
        backButton?.isEnabled = !teamsWindowOpen
        gameOptionsButton?.isEnabled = !teamsWindowOpen
        playButton?.isEnabled = !teamsWindowOpen
        setupTeams?.isEnabled = !teamsWindowOpen
    }

    /**
     * Adds the team spymasters and members ready to be passed to the game scene.
     */
    private fun addTeams() {
        if (teamASpymasterEdit?.text.toString() == "" && teamBSpymasterEdit?.text.toString() == "" && teamAMemberEdit?.text.toString() == "" && teamBMemberEdit?.text.toString() == "") {
            teamsAdded = false
        } else {
            if (teamASpymasterEdit?.text.toString() != "") {
                spymasterA = teamASpymasterEdit?.text.toString()
                teamsAdded = true
            }

            if (teamBSpymasterEdit?.text.toString() != "") {
                spymasterB = teamBSpymasterEdit?.text.toString()
                teamsAdded = true
            }

            if (teamAMemberEdit?.text.toString() != "") {
                teamAMembers = teamAMemberEdit?.text.toString().split(",").toCollection(ArrayList())
                teamsAdded = true
            }

            if (teamBMemberEdit?.text.toString() != "") {
                teamBMembers = teamBMemberEdit?.text.toString().split(",").toCollection(ArrayList())
                teamsAdded = true
            }
        }
    }

    /**
     * Updates colours of elements in layout.
     */
    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        setupTeamsBox?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        gameOptionsButton?.setBackgroundColor(menuButtonsColour)
        playButton?.setBackgroundColor(menuButtonsColour)
        setupTeams?.setBackgroundColor(menuButtonsColour)
        closeSetupTeams?.setBackgroundColor(menuButtonsColour)

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
        setupTeams?.setTextColor(menuTextColour)
        closeSetupTeams?.setTextColor(menuTextColour)
        teamsNote?.setTextColor(menuTextColour)

        for (t in customWordTexts) {
            t?.setTextColor(menuTextColour)
        }
    }
}