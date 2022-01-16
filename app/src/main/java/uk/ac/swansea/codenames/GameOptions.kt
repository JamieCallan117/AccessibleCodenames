package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.gridlayout.widget.GridLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.io.IOException
import java.util.*

class GameOptions : AppCompatActivity(), TextToSpeech.OnInitListener {
    // TODO: Verify custom words, no duplicates, maximum length, alphabetical etc
    // TODO: Implement TTS

    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: GridLayout? = null
    private var gameOptionsTitle: MaterialTextView? = null
    private var squaresInUse: MaterialTextView? = null
    private var bombSquareCount: MaterialTextView? = null
    private var neutralSquareCount: MaterialTextView? = null
    private var teamASquaresCount: MaterialTextView? = null
    private var teamBSquaresCount: MaterialTextView? = null
    private var messageBoxText: MaterialTextView? = null
    private var customWordText1: TextInputEditText? = null
    private var customWordText2: TextInputEditText? = null
    private var customWordText3: TextInputEditText? = null
    private var customWordText4: TextInputEditText? = null
    private var customWordText5: TextInputEditText? = null
    private var customWordText6: TextInputEditText? = null
    private var customWordText7: TextInputEditText? = null
    private var customWordText8: TextInputEditText? = null
    private var customWordText9: TextInputEditText? = null
    private var customWordText10: TextInputEditText? = null
    private var backButton: MaterialButton? = null
    private var saveButton: MaterialButton? = null
    private var yesButton: MaterialButton? = null
    private var noButton: MaterialButton? = null
    private var okButton: MaterialButton? = null
    private var deleteCustomWord1: ImageButton? = null
    private var deleteCustomWord2: ImageButton? = null
    private var deleteCustomWord3: ImageButton? = null
    private var deleteCustomWord4: ImageButton? = null
    private var deleteCustomWord5: ImageButton? = null
    private var deleteCustomWord6: ImageButton? = null
    private var deleteCustomWord7: ImageButton? = null
    private var deleteCustomWord8: ImageButton? = null
    private var deleteCustomWord9: ImageButton? = null
    private var deleteCustomWord10: ImageButton? = null
    private var addBombsButton: MaterialButton? = null
    private var subtractBombsButton: MaterialButton? = null
    private var addNeutralsButton: MaterialButton? = null
    private var subtractNeutralsButton: MaterialButton? = null
    private var teamASqrInc: MaterialButton? = null
    private var teamASqrDec: MaterialButton? = null
    private var teamBSqrInc: MaterialButton? = null
    private var teamBSqrDec: MaterialButton? = null
    private var startingTeamButton: ToggleButton? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var bombSquares = 1
    private var neutralSquares = 7
    private var teamASquares = 9
    private var teamBSquares = 8
    private var startingTeam = "A"
    private var totalSquaresInUse = 25
    private val customWords = ArrayList<String>()
    private var hasCustomSettings = false
    private var disabled = false
    private var windowOpen = false
    private val maxSquares = 25
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_options)

        messageBox = findViewById(R.id.messageBox)
        messageBoxText = findViewById(R.id.messageBoxText)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)
        okButton = findViewById(R.id.okButton)
        customWordText1 = findViewById(R.id.customWordText1)
        customWordText2 = findViewById(R.id.customWordText2)
        customWordText3 = findViewById(R.id.customWordText3)
        customWordText4 = findViewById(R.id.customWordText4)
        customWordText5 = findViewById(R.id.customWordText5)
        customWordText6 = findViewById(R.id.customWordText6)
        customWordText7 = findViewById(R.id.customWordText7)
        customWordText8 = findViewById(R.id.customWordText8)
        customWordText9 = findViewById(R.id.customWordText9)
        customWordText10 = findViewById(R.id.customWordText10)
        deleteCustomWord1 = findViewById(R.id.deleteCustomWord1)
        deleteCustomWord2 = findViewById(R.id.deleteCustomWord2)
        deleteCustomWord3 = findViewById(R.id.deleteCustomWord3)
        deleteCustomWord4 = findViewById(R.id.deleteCustomWord4)
        deleteCustomWord5 = findViewById(R.id.deleteCustomWord5)
        deleteCustomWord6 = findViewById(R.id.deleteCustomWord6)
        deleteCustomWord7 = findViewById(R.id.deleteCustomWord7)
        deleteCustomWord8 = findViewById(R.id.deleteCustomWord8)
        deleteCustomWord9 = findViewById(R.id.deleteCustomWord9)
        deleteCustomWord10 = findViewById(R.id.deleteCustomWord10)
        addBombsButton = findViewById(R.id.addBombsButton)
        addNeutralsButton = findViewById(R.id.addNeutralsButton)
        subtractBombsButton = findViewById(R.id.subtractBombsButton)
        subtractNeutralsButton = findViewById(R.id.subtractNeutralsButton)
        teamASqrInc = findViewById(R.id.teamASqrInc)
        teamBSqrInc = findViewById(R.id.teamBSqrInc)
        teamASqrDec = findViewById(R.id.teamASqrDec)
        teamBSqrDec = findViewById(R.id.teamBSqrDec)
        bombSquareCount = findViewById(R.id.bombSquareCount)
        neutralSquareCount = findViewById(R.id.neutralSquareCount)
        teamASquaresCount = findViewById(R.id.teamASquaresCount)
        teamBSquaresCount = findViewById(R.id.teamBSquaresCount)
        startingTeamButton = findViewById(R.id.startingTeamButton)
        squaresInUse = findViewById(R.id.squaresInUse)
        saveButton = findViewById(R.id.saveButton)
        constraintLayout = findViewById(R.id.constraintLayout)
        messageBox = findViewById(R.id.messageBox)
        gameOptionsTitle = findViewById(R.id.gameOptionsTitle)
        backButton = findViewById(R.id.backButton)

        textToSpeech = TextToSpeech(this, this)

        updateTotalSquares()

        bombSquares = intent.getIntExtra("bombSquares", 1)
        bombSquareCount?.text = bombSquares.toString()

        neutralSquares = intent.getIntExtra("neutralSquares", 7)
        neutralSquareCount?.text = neutralSquares.toString()

        teamASquares = intent.getIntExtra("teamASquares", 9)
        teamASquaresCount?.text = teamASquares.toString()

        teamBSquares = intent.getIntExtra("teamBSquares", 8)
        teamBSquaresCount?.text = teamBSquares.toString()

        if (intent.getBooleanExtra("hasCustomSettings", false)) {
            saveButton?.visibility = View.VISIBLE

            startingTeamButton?.isChecked = intent.getStringExtra("startingTeam") == "A"

            val customWordTexts = arrayOf(customWordText1, customWordText2, customWordText3,
                    customWordText4, customWordText5, customWordText6, customWordText7,
                    customWordText8, customWordText9, customWordText10)

            if (intent.getStringArrayListExtra("customWords") != null) {
                var counter = 0

                while (counter < intent.getStringArrayListExtra("customWords")!!.size) {
                    customWordTexts[counter]?.setText(intent.getStringArrayListExtra("customWords")!![counter])
                    counter++
                }
            }

            hasCustomSettings = true
        }

        teamASqrInc?.setOnClickListener {
            if (totalSquaresInUse != maxSquares) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse++
                teamASquares++
                teamASquaresCount?.text = teamASquares.toString()

                updateTotalSquares()
            }
        }

        teamASqrDec?.setOnClickListener {
            if (totalSquaresInUse >= 1 && teamASquares >= 2) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse--
                teamASquares--
                teamASquaresCount?.text = teamASquares.toString()

                updateTotalSquares()
            }
        }

        teamBSqrInc?.setOnClickListener {
            if (totalSquaresInUse != maxSquares) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse++
                teamBSquares++
                teamBSquaresCount?.text = teamBSquares.toString()

                updateTotalSquares()
            }
        }

        teamBSqrDec?.setOnClickListener {
            if (totalSquaresInUse >= 1 && teamBSquares >= 2) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse--
                teamBSquares--
                teamBSquaresCount?.text = teamBSquares.toString()

                updateTotalSquares()
            }
        }

        addBombsButton?.setOnClickListener {
            if (totalSquaresInUse != maxSquares) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse++
                bombSquares++
                bombSquareCount?.text = bombSquares.toString()

                updateTotalSquares()
            }
        }

        subtractBombsButton?.setOnClickListener {
            if (totalSquaresInUse >= 1 && bombSquares >= 1) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse--
                bombSquares--
                bombSquareCount?.text = bombSquares.toString()

                updateTotalSquares()
            }
        }

        addNeutralsButton?.setOnClickListener {
            if (totalSquaresInUse != maxSquares) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse++
                neutralSquares++
                neutralSquareCount?.text = neutralSquares.toString()

                updateTotalSquares()
            }
        }
        subtractNeutralsButton?.setOnClickListener {
            if (totalSquaresInUse >= 1 && neutralSquares >= 1) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
                totalSquaresInUse--
                neutralSquares--
                neutralSquareCount?.text = neutralSquares.toString()

                updateTotalSquares()
            }
        }

        startingTeamButton?.setOnCheckedChangeListener { _, _ ->
            saveButton?.visibility = View.VISIBLE
            hasCustomSettings = true
        }

        customWordText1?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText2?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText2?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText3?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText3?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText4?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText4?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText5?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText5?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText6?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText6?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText7?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText7?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText8?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText8?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText9?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText9?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        customWordText10?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && customWordText10?.text.toString() != "") {
                saveButton?.visibility = View.VISIBLE
                hasCustomSettings = true
            }

            false
        }

        deleteCustomWord1?.setOnClickListener { customWordText1?.setText("") }
        deleteCustomWord2?.setOnClickListener { customWordText2?.setText("") }
        deleteCustomWord3?.setOnClickListener { customWordText3?.setText("") }
        deleteCustomWord4?.setOnClickListener { customWordText4?.setText("") }
        deleteCustomWord5?.setOnClickListener { customWordText5?.setText("") }
        deleteCustomWord6?.setOnClickListener { customWordText6?.setText("") }
        deleteCustomWord7?.setOnClickListener { customWordText7?.setText("") }
        deleteCustomWord8?.setOnClickListener { customWordText8?.setText("") }
        deleteCustomWord9?.setOnClickListener { customWordText9?.setText("") }
        deleteCustomWord10?.setOnClickListener { customWordText10?.setText("") }

        backButton?.setOnClickListener {
            if (hasCustomSettings) {
                windowOpen = true
                messageBox?.visibility = View.VISIBLE

                swapEnableStates()

                messageBoxText?.text = getString(R.string.exit_without_save)
                yesButton?.visibility = View.VISIBLE
                noButton?.visibility = View.VISIBLE
                okButton?.visibility = View.INVISIBLE
            } else {
                yesButton?.performClick()
            }
        }

        saveButton?.setOnClickListener {
            val i: Intent
            var error = ""
            var validSave = true

            //Reads words from the text file to store in the defaultWords array
            var wordsFromFile = ""

            try {
                val input = assets.open("gameWords")
                val size = input.available()
                val buffer = ByteArray(size)

                input.read(buffer)
                input.close()

                wordsFromFile = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val temp = wordsFromFile.split(",").toTypedArray()
            val defaultWords = ArrayList(listOf(*temp))
            val customWordsTemp = ArrayList<String>()
            val customWordTextsTemp = arrayOf(customWordText1, customWordText2, customWordText3,
                    customWordText4, customWordText5, customWordText6, customWordText7,
                    customWordText8, customWordText9, customWordText10)

            for (j in customWordTextsTemp.indices) {
                if (customWordTextsTemp[j]?.text.toString() != "") {
                    customWordsTemp.add(customWordTextsTemp[j]?.text.toString())
                }
            }

            for (s in customWordsTemp) {
                if (defaultWords.contains(s.uppercase(Locale.getDefault()))) {
                    validSave = false
                    error = s.uppercase(Locale.getDefault()) + " already exists\nas a default word."
                    break
                }
            }

            if (totalSquaresInUse != maxSquares) {
                validSave = false
                error = "You do not have the\nrequired number of\nsquares assigned."
            }

            if (validSave) {
                if (intent.getStringExtra("type") == "local") {
                    i = Intent(applicationContext, LocalSetup::class.java)

                    i.putExtra("type", "local")

                    startingTeam = if (startingTeamButton?.isChecked == true) {
                        "A"
                    } else {
                        "B"
                    }

                    i.putExtra("hasCustomSettings", true)
                    i.putExtra("bombSquares", bombSquares)
                    i.putExtra("neutralSquares", neutralSquares)
                    i.putExtra("teamASquares", teamASquares)
                    i.putExtra("teamBSquares", teamBSquares)
                    i.putExtra("startingTeam", startingTeam)

                    val customWordTexts = arrayOf(customWordText1, customWordText2, customWordText3,
                            customWordText4, customWordText5, customWordText6, customWordText7,
                            customWordText8, customWordText9, customWordText10)

                    for (customWordText in customWordTexts) {
                        if (customWordText?.text.toString() != "") {
                            customWords.add(customWordText?.text.toString())
                        }
                    }

                    i.putStringArrayListExtra("customWords", customWords)

                    startActivity(i)
                } else if (intent.getStringExtra("type") == "online") {
                    i = Intent(applicationContext, CreateGame::class.java)

                    i.putExtra("type", "online")

                    startingTeam = if (startingTeamButton?.isChecked == true) {
                        "A"
                    } else {
                        "B"
                    }

                    i.putExtra("hasCustomSettings", true)
                    i.putExtra("bombSquares", bombSquares)
                    i.putExtra("neutralSquares", neutralSquares)
                    i.putExtra("teamASquares", teamASquares)
                    i.putExtra("teamBSquares", teamBSquares)
                    i.putExtra("startingTeam", startingTeam)

                    val customWordTexts = arrayOf(customWordText1, customWordText2, customWordText3,
                            customWordText4, customWordText5, customWordText6, customWordText7,
                            customWordText8, customWordText9, customWordText10)

                    for (customWordText in customWordTexts) {
                        if (customWordText?.text.toString() != "") {
                            customWords.add(customWordText?.text.toString())
                        }
                    }

                    i.putStringArrayListExtra("customWords", customWords)
                    i.putExtra("creatingGame", true)

                    startActivity(i)
                }
            } else {
                windowOpen = true
                messageBox?.visibility = View.VISIBLE
                yesButton?.visibility = View.INVISIBLE
                noButton?.visibility = View.INVISIBLE
                okButton?.visibility = View.VISIBLE

                swapEnableStates()

                messageBoxText?.text = error
            }
        }

        okButton?.setOnClickListener {
            messageBox?.visibility = View.INVISIBLE

            swapEnableStates()
        }

        yesButton?.setOnClickListener {
            val i: Intent

            if (intent.getStringExtra("type") == "local") {
                i = Intent(applicationContext, LocalSetup::class.java)

                i.putExtra("hasCustomSettings", hasCustomSettings)
                i.putExtra("type", "local")
                i.putExtra("bombSquares", intent.getIntExtra("bombSquares", 1))
                i.putExtra("neutralSquares", intent.getIntExtra("neutralSquares", 7))
                i.putExtra("teamASquares", intent.getIntExtra("teamASquares", 9))
                i.putExtra("teamBSquares", intent.getIntExtra("teamBSquares", 8))
                i.putExtra("startingTeam", intent.getStringExtra("startingTeam"))

                val tempCustomWords = ArrayList<String>()

                if (intent.getStringArrayListExtra("customWords") != null) {
                    var counter = 0
                    while (counter < intent.getStringArrayListExtra("customWords")?.size!!) {
                        tempCustomWords.add(intent.getStringArrayListExtra("customWords")!![counter])
                        counter++
                    }
                }

                i.putStringArrayListExtra("customWords", tempCustomWords)

                startActivity(i)
            } else if (intent.getStringExtra("type") == "online") {
                i = Intent(applicationContext, CreateGame::class.java)

                i.putExtra("hasCustomSettings", hasCustomSettings)
                i.putExtra("type", "online")
                i.putExtra("bombSquares", intent.getIntExtra("bombSquares", 1))
                i.putExtra("neutralSquares", intent.getIntExtra("neutralSquares", 7))
                i.putExtra("teamASquares", intent.getIntExtra("teamASquares", 9))
                i.putExtra("teamBSquares", intent.getIntExtra("teamBSquares", 8))
                i.putExtra("startingTeam", intent.getStringExtra("startingTeam"))

                val tempCustomWords = ArrayList<String>()

                if (intent.getStringArrayListExtra("customWords") != null) {
                    var counter = 0
                    while (counter < intent.getStringArrayListExtra("customWords")?.size!!) {
                        tempCustomWords.add(intent.getStringArrayListExtra("customWords")!![counter])
                        counter++
                    }
                }

                i.putStringArrayListExtra("customWords", tempCustomWords)

                startActivity(i)
            }
        }

        noButton?.setOnClickListener {
            swapEnableStates()
            windowOpen = false
            messageBox?.visibility = View.INVISIBLE
        }

        updateColours()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    override fun onBackPressed() {
        if (windowOpen) {
            noButton?.performClick()
        } else {
            backButton?.performClick()
        }
    }

    private fun swapEnableStates() {
        backButton?.isEnabled = disabled
        saveButton?.isEnabled = disabled
        addBombsButton?.isEnabled = disabled
        subtractBombsButton?.isEnabled = disabled
        addNeutralsButton?.isEnabled = disabled
        subtractNeutralsButton?.isEnabled = disabled
        teamASqrInc?.isEnabled = disabled
        teamASqrDec?.isEnabled = disabled
        teamBSqrInc?.isEnabled = disabled
        teamBSqrDec?.isEnabled = disabled
        startingTeamButton?.isEnabled = disabled
        customWordText1?.isEnabled = disabled
        customWordText2?.isEnabled = disabled
        customWordText3?.isEnabled = disabled
        customWordText4?.isEnabled = disabled
        customWordText5?.isEnabled = disabled
        customWordText6?.isEnabled = disabled
        customWordText7?.isEnabled = disabled
        customWordText8?.isEnabled = disabled
        customWordText9?.isEnabled = disabled
        customWordText10?.isEnabled = disabled
        deleteCustomWord1?.isEnabled = disabled
        deleteCustomWord2?.isEnabled = disabled
        deleteCustomWord3?.isEnabled = disabled
        deleteCustomWord4?.isEnabled = disabled
        deleteCustomWord5?.isEnabled = disabled
        deleteCustomWord6?.isEnabled = disabled
        deleteCustomWord7?.isEnabled = disabled
        deleteCustomWord8?.isEnabled = disabled
        deleteCustomWord9?.isEnabled = disabled
        deleteCustomWord10?.isEnabled = disabled

        disabled = !disabled
    }

    private fun updateTotalSquares() {
        squaresInUse?.text = getString(R.string.square_counter, totalSquaresInUse)
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
        messageBox?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        saveButton?.setBackgroundColor(menuButtonsColour)
        yesButton?.setBackgroundColor(menuButtonsColour)
        noButton?.setBackgroundColor(menuButtonsColour)
        okButton?.setBackgroundColor(menuButtonsColour)
        addBombsButton?.setBackgroundColor(menuButtonsColour)
        subtractBombsButton?.setBackgroundColor(menuButtonsColour)
        addNeutralsButton?.setBackgroundColor(menuButtonsColour)
        subtractNeutralsButton?.setBackgroundColor(menuButtonsColour)
        teamASqrInc?.setBackgroundColor(menuButtonsColour)
        teamASqrDec?.setBackgroundColor(menuButtonsColour)
        teamBSqrInc?.setBackgroundColor(menuButtonsColour)
        teamBSqrDec?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord1?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord2?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord3?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord4?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord5?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord6?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord7?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord8?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord9?.setBackgroundColor(menuButtonsColour)
        deleteCustomWord10?.setBackgroundColor(menuButtonsColour)
        
        gameOptionsTitle?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        saveButton?.setTextColor(menuTextColour)
        yesButton?.setTextColor(menuTextColour)
        noButton?.setTextColor(menuTextColour)
        okButton?.setTextColor(menuTextColour)
        messageBoxText?.setTextColor(menuTextColour)
        deleteCustomWord1?.setColorFilter(menuTextColour)
        deleteCustomWord2?.setColorFilter(menuTextColour)
        deleteCustomWord3?.setColorFilter(menuTextColour)
        deleteCustomWord4?.setColorFilter(menuTextColour)
        deleteCustomWord5?.setColorFilter(menuTextColour)
        deleteCustomWord6?.setColorFilter(menuTextColour)
        deleteCustomWord7?.setColorFilter(menuTextColour)
        deleteCustomWord8?.setColorFilter(menuTextColour)
        deleteCustomWord9?.setColorFilter(menuTextColour)
        deleteCustomWord10?.setColorFilter(menuTextColour)
    }
}