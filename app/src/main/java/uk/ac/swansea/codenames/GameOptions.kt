package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.gridlayout.widget.GridLayout
import java.io.File
import java.io.IOException
import java.util.*

class GameOptions : AppCompatActivity() {
    // TODO: Redesign layout.
    // TODO: Check for duplicate custom words

    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: GridLayout? = null
    private var gameOptionsTitle: TextView? = null
    private var gameOptionsSubtitle: TextView? = null
    private var squaresInUse: TextView? = null
    private var bombSquareCount: TextView? = null
    private var neutralSquareCount: TextView? = null
    private var teamASquaresCount: TextView? = null
    private var teamBSquaresCount: TextView? = null
    private var messageBoxText: TextView? = null
    private var customWordText1: EditText? = null
    private var customWordText2: EditText? = null
    private var customWordText3: EditText? = null
    private var customWordText4: EditText? = null
    private var customWordText5: EditText? = null
    private var customWordText6: EditText? = null
    private var customWordText7: EditText? = null
    private var customWordText8: EditText? = null
    private var customWordText9: EditText? = null
    private var customWordText10: EditText? = null
    private var backButton: Button? = null
    private var saveButton: Button? = null
    private var yesButton: Button? = null
    private var noButton: Button? = null
    private var okButton: Button? = null
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
    private var addBombsButton: ImageView? = null
    private var subtractBombsButton: ImageView? = null
    private var addNeutralsButton: ImageView? = null
    private var subtractNeutralsButton: ImageView? = null
    private var teamASqrInc: ImageView? = null
    private var teamASqrDec: ImageView? = null
    private var teamBSqrInc: ImageView? = null
    private var teamBSqrDec: ImageView? = null
    private var startingTeamButton: ToggleButton? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var bombSquares = 1
    private var neutralSquares = 7
    private var teamASquares = 9
    private var teamBSquares = 8
    private var startingTeam = 1
    private var totalSquaresInUse = 25
    private val customWords = ArrayList<String>()
    private var hasCustomSettings = false
    private var disabled = false
    private var windowOpen = false
    private val MAX_SQUARES = 25

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
        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle)
        saveButton = findViewById(R.id.saveButton)
        constraintLayout = findViewById(R.id.constraintLayout)
        messageBox = findViewById(R.id.messageBox)
        gameOptionsTitle = findViewById(R.id.gameOptionsTitle)
        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle)
        backButton = findViewById(R.id.backButton)

        if (intent.getStringExtra("type") == "local") {
            gameOptionsSubtitle?.text = getString(R.string.local)
        } else if (intent.getStringExtra("type") == "online") {
            gameOptionsSubtitle?.text = getString(R.string.online)
        }

        if (intent.getBooleanExtra("hasCustomSettings", false)) {
            saveButton?.visibility = View.VISIBLE

            bombSquares = intent.getIntExtra("bombSquares", 1)
            bombSquareCount?.text = bombSquares.toString()

            neutralSquares = intent.getIntExtra("neutralSquares", 7)
            neutralSquareCount?.text = neutralSquares.toString()

            teamASquares = intent.getIntExtra("teamASquares", 9)
            teamASquaresCount?.text = teamASquares.toString()

            teamBSquares = intent.getIntExtra("teamBSquares", 8)
            teamBSquaresCount?.text = teamBSquares.toString()

            startingTeamButton?.isChecked = intent.getIntExtra("startingTeam", 1) == 1

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
            if (totalSquaresInUse != MAX_SQUARES) {
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
            if (totalSquaresInUse != MAX_SQUARES) {
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
            if (totalSquaresInUse != MAX_SQUARES) {
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
            if (totalSquaresInUse != MAX_SQUARES) {
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

                messageBoxText?.text = "Are you sure you want to exit without saving your settings?"
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
                    error = s.uppercase(Locale.getDefault()) + " already exists as a default word."
                    break
                }
            }

            if (totalSquaresInUse != MAX_SQUARES) {
                validSave = false
                error = "You do not have the required number of squares assigned."
            }

            if (validSave) {
                if (intent.getStringExtra("type") == "local") {
                    i = Intent(applicationContext, LocalSetup::class.java)

                    i.putExtra("type", "local")

                    startingTeam = if (startingTeamButton?.isChecked == true) {
                        1
                    } else {
                        2
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
                        1
                    } else {
                        2
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
                i.putExtra("startingTeam", intent.getIntExtra("startingTeam", 1))

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
                i.putExtra("startingTeam", intent.getIntExtra("startingTeam", 1))

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
        val text = "$totalSquaresInUse/$MAX_SQUARES"
        squaresInUse?.text = text
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
        addBombsButton?.setColorFilter(menuButtonsColour)
        subtractBombsButton?.setColorFilter(menuButtonsColour)
        addNeutralsButton?.setColorFilter(menuButtonsColour)
        subtractNeutralsButton?.setColorFilter(menuButtonsColour)
        teamASqrInc?.setColorFilter(menuButtonsColour)
        teamASqrDec?.setColorFilter(menuButtonsColour)
        teamBSqrInc?.setColorFilter(menuButtonsColour)
        teamBSqrDec?.setColorFilter(menuButtonsColour)
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
        gameOptionsSubtitle?.setTextColor(menuTextColour)
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