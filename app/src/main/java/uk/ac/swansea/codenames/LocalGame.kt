package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.gridlayout.widget.GridLayout
import java.io.IOException
import java.util.*

class LocalGame : AppCompatActivity() {
    // TODO: Redesign layout.
    // TODO: Redo the game operations visibility to use GONE
    // TODO: Implement TTS

    private var gamePhase: LocalPhase? = null
    private var bombSquaresCount = 1
    private var neutralSquaresCount = 7
    private var teamASquaresCount = 9
    private var teamBSquaresCount = 8
    private var startingTeam = 1
    private var messageBoxOpen = false
    private var ttsOpen = false
    private var customWords: ArrayList<String>? = null
    private var bombWords: ArrayList<String?>? = null
    private var neutralWords: ArrayList<String?>? = null
    private var teamAWords: ArrayList<String?>? = null
    private var teamBWords: ArrayList<String?>? = null
    private val words = arrayOfNulls<String>(25)
    private var wordButtons = arrayOfNulls<WordButton>(25)
    private var exitButton: Button? = null
    private var confirmButton: Button? = null
    private var turnAction: Button? = null
    private var textToSpeechButton: Button? = null
    private var viewPreviousHints: Button? = null
    private var hidePreviousHints: Button? = null
    private var yesMessage: Button? = null
    private var noMessage: Button? = null
    private var ttsAll: Button? = null
    private var ttsA: Button? = null
    private var ttsB: Button? = null
    private var ttsNeutral: Button? = null
    private var ttsBomb: Button? = null
    private var ttsUnclicked: Button? = null
    private var ttsClicked: Button? = null
    private var closeTts: Button? = null
    private var teamACount: TextView? = null
    private var teamBCount: TextView? = null
    private var teamCounterLine: TextView? = null
    private var messageText: TextView? = null
    private var hintText: TextView? = null
    private var editHint: EditText? = null
    private var messageBox: GridLayout? = null
    private var ttsBox: GridLayout? = null
    private var constraintLayout: ConstraintLayout? = null
    private var gameOperationsLinear: LinearLayout? = null
    private var previousHints: LinearLayout? = null
    private var squareOne: WordButton? = null
    private var squareTwo: WordButton? = null
    private var squareThree: WordButton? = null
    private var squareFour: WordButton? = null
    private var squareFive: WordButton? = null
    private var squareSix: WordButton? = null
    private var squareSeven: WordButton? = null
    private var squareEight: WordButton? = null
    private var squareNine: WordButton? = null
    private var squareTen: WordButton? = null
    private var squareEleven: WordButton? = null
    private var squareTwelve: WordButton? = null
    private var squareThirteen: WordButton? = null
    private var squareFourteen: WordButton? = null
    private var squareFifteen: WordButton? = null
    private var squareSixteen: WordButton? = null
    private var squareSeventeen: WordButton? = null
    private var squareEighteen: WordButton? = null
    private var squareNineteen: WordButton? = null
    private var squareTwenty: WordButton? = null
    private var squareTwentyOne: WordButton? = null
    private var squareTwentyTwo: WordButton? = null
    private var squareTwentyThree: WordButton? = null
    private var squareTwentyFour: WordButton? = null
    private var squareTwentyFive: WordButton? = null
    private var hintNumber: Spinner? = null
    private var previousHintsScroll: ScrollView? = null
    private var gameOperations: ScrollView? = null
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
        setContentView(R.layout.local_game)

        constraintLayout = findViewById(R.id.constraintLayout)
        exitButton = findViewById(R.id.exitButton)
        messageBox = findViewById(R.id.messageBox)
        ttsBox = findViewById(R.id.ttsBox)
        messageText = findViewById(R.id.messageText)
        confirmButton = findViewById(R.id.confirmButton)
        yesMessage = findViewById(R.id.yesMessage)
        noMessage = findViewById(R.id.noMessage)
        ttsAll = findViewById(R.id.ttsAll)
        ttsA = findViewById(R.id.ttsA)
        ttsB = findViewById(R.id.ttsB)
        ttsNeutral = findViewById(R.id.ttsNeutral)
        ttsBomb = findViewById(R.id.ttsBomb)
        ttsUnclicked = findViewById(R.id.ttsUnclicked)
        ttsClicked = findViewById(R.id.ttsClicked)
        closeTts = findViewById(R.id.closeTts)
        teamACount = findViewById(R.id.teamACount)
        teamBCount = findViewById(R.id.teamBCount)
        teamCounterLine = findViewById(R.id.teamCounterLine)
        hintText = findViewById(R.id.hintText)
        editHint = findViewById(R.id.editHint)
        hintNumber = findViewById(R.id.hintNumber)
        turnAction = findViewById(R.id.turnAction)
        textToSpeechButton = findViewById(R.id.textToSpeechButton)
        viewPreviousHints = findViewById(R.id.viewPreviousHints)
        gameOperations = findViewById(R.id.gameOperations)
        gameOperationsLinear = findViewById(R.id.gameOperationsLinear)
        previousHintsScroll = findViewById(R.id.previousHintsScroll)
        previousHints = findViewById(R.id.previousHints)
        hidePreviousHints = findViewById(R.id.hidePreviousHints)

        gamePhase = LocalPhase.START

        if (intent.getBooleanExtra("hasCustomSettings", false)) {
            bombSquaresCount = intent.getIntExtra("bombSquares", 1)
            neutralSquaresCount = intent.getIntExtra("neutralSquares", 7)
            teamASquaresCount = intent.getIntExtra("teamASquares", 9)
            teamBSquaresCount = intent.getIntExtra("teamBSquares", 8)
            startingTeam = intent.getIntExtra("startingTeam", 1)
            customWords = intent.getStringArrayListExtra("customWords")
        }

        bombWords = ArrayList(bombSquaresCount)
        neutralWords = ArrayList(neutralSquaresCount)
        teamAWords = ArrayList(teamASquaresCount)
        teamBWords = ArrayList(teamBSquaresCount)

        if (startingTeam == 1) {
            gamePhase = LocalPhase.TEAM_A_INTERMISSION
            teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
        } else {
            gamePhase = LocalPhase.TEAM_B_INTERMISSION
            teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
        }

        teamACount?.text = teamASquaresCount.toString()
        teamBCount?.text = teamBSquaresCount.toString()

        squareOne = findViewById(R.id.squareOne)
        squareTwo = findViewById(R.id.squareTwo)
        squareThree = findViewById(R.id.squareThree)
        squareFour = findViewById(R.id.squareFour)
        squareFive = findViewById(R.id.squareFive)
        squareSix = findViewById(R.id.squareSix)
        squareSeven = findViewById(R.id.squareSeven)
        squareEight = findViewById(R.id.squareEight)
        squareNine = findViewById(R.id.squareNine)
        squareTen = findViewById(R.id.squareTen)
        squareEleven = findViewById(R.id.squareEleven)
        squareTwelve = findViewById(R.id.squareTwelve)
        squareThirteen = findViewById(R.id.squareThirteen)
        squareFourteen = findViewById(R.id.squareFourteen)
        squareFifteen = findViewById(R.id.squareFifteen)
        squareSixteen = findViewById(R.id.squareSixteen)
        squareSeventeen = findViewById(R.id.squareSeventeen)
        squareEighteen = findViewById(R.id.squareEighteen)
        squareNineteen = findViewById(R.id.squareNineteen)
        squareTwenty = findViewById(R.id.squareTwenty)
        squareTwentyOne = findViewById(R.id.squareTwentyOne)
        squareTwentyTwo = findViewById(R.id.squareTwentyTwo)
        squareTwentyThree = findViewById(R.id.squareTwentyThree)
        squareTwentyFour = findViewById(R.id.squareTwentyFour)
        squareTwentyFive = findViewById(R.id.squareTwentyFive)

        wordButtons = arrayOf(squareOne, squareTwo, squareThree, squareFour, squareFive,
                squareSix, squareSeven, squareEight, squareNine, squareTen, squareEleven,
                squareTwelve, squareThirteen, squareFourteen, squareFifteen, squareSixteen,
                squareSeventeen, squareEighteen, squareNineteen, squareTwenty, squareTwentyOne,
                squareTwentyTwo, squareTwentyThree, squareTwentyFour, squareTwentyFive)

        toggleMessageBox(true, 0)

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
        val MAX_WORDS = 25
        var defaultWordsNeeded = MAX_WORDS

        if (customWords != null) {
            for (i in customWords?.indices!!) {
                words[i] = customWords!![i]
                defaultWordsNeeded--
            }
        }

        val list = ArrayList<Int?>()

        for (i in defaultWords.indices) {
            list.add(i)
        }

        list.shuffle()

        for (i in 0 until defaultWordsNeeded) {
            words[i + (MAX_WORDS - defaultWordsNeeded)] = defaultWords[list[i]!!]
        }

        Collections.shuffle(Arrays.asList(*words))

        for (i in 0 until MAX_WORDS) {
            wordButtons[i]?.text = words[i]
        }

        list.clear()

        for (i in 0 until MAX_WORDS) {
            list.add(i)
        }

        list.shuffle()

        var counter = 0

        for (i in 0 until bombSquaresCount) {
            bombWords?.add(words[list[counter]!!])
            counter++
        }

        for (i in 0 until neutralSquaresCount) {
            neutralWords?.add(words[list[counter]!!])
            counter++
        }

        for (i in 0 until teamASquaresCount) {
            teamAWords?.add(words[list[counter]!!])
            counter++
        }

        for (i in 0 until teamBSquaresCount) {
            teamBWords?.add(words[list[counter]!!])
            counter++
        }

        squareOne?.setOnClickListener { wordButtonPress(squareOne) }
        squareTwo?.setOnClickListener { wordButtonPress(squareTwo) }
        squareThree?.setOnClickListener { wordButtonPress(squareThree) }
        squareFour?.setOnClickListener { wordButtonPress(squareFour) }
        squareFive?.setOnClickListener { wordButtonPress(squareFive) }
        squareSix?.setOnClickListener { wordButtonPress(squareSix) }
        squareSeven?.setOnClickListener { wordButtonPress(squareSeven) }
        squareEight?.setOnClickListener { wordButtonPress(squareEight) }
        squareNine?.setOnClickListener { wordButtonPress(squareNine) }
        squareTen?.setOnClickListener { wordButtonPress(squareTen) }
        squareEleven?.setOnClickListener { wordButtonPress(squareEleven) }
        squareTwelve?.setOnClickListener { wordButtonPress(squareTwelve) }
        squareThirteen?.setOnClickListener { wordButtonPress(squareThirteen) }
        squareFourteen?.setOnClickListener { wordButtonPress(squareFourteen) }
        squareFifteen?.setOnClickListener { wordButtonPress(squareFifteen) }
        squareSixteen?.setOnClickListener { wordButtonPress(squareSixteen) }
        squareSeventeen?.setOnClickListener { wordButtonPress(squareSeventeen) }
        squareEighteen?.setOnClickListener { wordButtonPress(squareEighteen) }
        squareNineteen?.setOnClickListener { wordButtonPress(squareNineteen) }
        squareTwenty?.setOnClickListener { wordButtonPress(squareTwenty) }
        squareTwentyOne?.setOnClickListener { wordButtonPress(squareTwentyOne) }
        squareTwentyTwo?.setOnClickListener { wordButtonPress(squareTwentyTwo) }
        squareTwentyThree?.setOnClickListener { wordButtonPress(squareTwentyThree) }
        squareTwentyFour?.setOnClickListener { wordButtonPress(squareTwentyFour) }
        squareTwentyFive?.setOnClickListener { wordButtonPress(squareTwentyFive) }

        confirmButton?.setOnClickListener {
            if (gamePhase != LocalPhase.TEAM_A_WIN && gamePhase != LocalPhase.TEAM_B_WIN) {
                when (gamePhase) {
                    LocalPhase.TEAM_A_INTERMISSION -> gamePhase = LocalPhase.TEAM_A_SPY
                    LocalPhase.TEAM_B_INTERMISSION -> gamePhase = LocalPhase.TEAM_B_SPY
                }

                hintText?.text = ""
                turnAction?.setText(R.string.give_hint)
                editHint?.visibility = View.VISIBLE
                hintNumber?.visibility = View.VISIBLE
                editHint?.setText("")

                toggleMessageBox(false, 0)
                updateSpinner()
            } else {
                teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                hintText?.text = ""
                gameOperationsLinear?.removeView(editHint)
                gameOperationsLinear?.removeView(hintNumber)
                gameOperationsLinear?.removeView(turnAction)

                toggleMessageBox(false, 0)
            }

            updateColours()
        }

        turnAction?.setOnClickListener {
            var validHint = true

            when (gamePhase) {
                LocalPhase.TEAM_A -> {
                    gamePhase = LocalPhase.TEAM_B_INTERMISSION
                    teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                    teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                    messageText?.setText(R.string.spymaster_confirmation)
                    confirmButton?.setText(R.string.confirm)

                    toggleMessageBox(true, 0)
                }

                LocalPhase.TEAM_B -> {
                    gamePhase = LocalPhase.TEAM_A_INTERMISSION
                    teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                    teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                    messageText?.setText(R.string.spymaster_confirmation)
                    confirmButton?.setText(R.string.confirm)

                    toggleMessageBox(true, 0)
                }

                LocalPhase.TEAM_A_SPY -> {
                    if (editHint?.text.toString() == "") {
                        validHint = false
                    } else if (editHint?.text.toString().contains(" ")) {
                        validHint = false
                    }

                    for (wb in wordButtons) {
                        if (editHint?.text.toString().uppercase(Locale.getDefault()).contains(wb?.text.toString().uppercase(Locale.getDefault()))) {
                            validHint = false
                        }
                    }

                    if (validHint) {
                        turnAction?.setText(R.string.end_turn)

                        gamePhase = LocalPhase.TEAM_A

                        val hint = editHint?.text.toString() + ": " + hintNumber?.selectedItem.toString()

                        hintText?.text = hint
                        editHint?.visibility = View.INVISIBLE
                        hintNumber?.visibility = View.INVISIBLE

                        addHint(hint)
                    } else {
                        messageText?.setText(R.string.invalid_hint)
                        confirmButton?.setText(R.string.ok)

                        toggleMessageBox(true, 0)
                    }
                }

                LocalPhase.TEAM_B_SPY -> {
                    if (editHint?.text.toString() == "") {
                        validHint = false
                    } else if (editHint?.text.toString().contains(" ")) {
                        validHint = false
                    }

                    for (wb in wordButtons) {
                        if (editHint?.text.toString().uppercase(Locale.getDefault()).contains(wb?.text.toString().uppercase(Locale.getDefault()))) {
                            validHint = false
                        }
                    }

                    if (validHint) {
                        turnAction?.setText(R.string.end_turn)

                        gamePhase = LocalPhase.TEAM_B

                        val hint = editHint?.text.toString() + ": " + hintNumber?.selectedItem.toString()

                        hintText?.text = hint
                        editHint?.visibility = View.INVISIBLE
                        hintNumber?.visibility = View.INVISIBLE

                        addHint(hint)
                    } else {
                        messageText?.setText(R.string.invalid_hint)
                        confirmButton?.setText(R.string.ok)

                        toggleMessageBox(true, 0)
                    }
                }
            }

            updateColours()
        }

        noMessage?.setOnClickListener { toggleMessageBox(false, 1) }

        textToSpeechButton?.setOnClickListener { toggleTtsBox(true) }

        ttsAll?.setOnClickListener {
            for (wb in wordButtons) {
                //Uncomment if needing a delay between tts.
//                try {
//                    TimeUnit.MILLISECONDS.sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                println(wb?.text.toString())
            }

            toggleTtsBox(false)
        }

        ttsA?.setOnClickListener {
            for (wb in wordButtons) {
                if (teamAWords?.contains(wb?.text.toString()) == true) {
                    if (wb != null) {
                        println(wb.text.toString())
                    }
                }
            }

            toggleTtsBox(false)
        }

        ttsB?.setOnClickListener {
            for (wb in wordButtons) {
                if (teamBWords?.contains(wb?.text.toString()) == true) {
                    if (wb != null) {
                        println(wb.text.toString())
                    }
                }
            }

            toggleTtsBox(false)
        }

        ttsNeutral?.setOnClickListener {
            for (wb in wordButtons) {
                if (neutralWords?.contains(wb?.text.toString()) == true) {
                    if (wb != null) {
                        println(wb.text.toString())
                    }
                }
            }

            toggleTtsBox(false)
        }

        ttsBomb?.setOnClickListener {
            for (wb in wordButtons) {
                if (bombWords?.contains(wb?.text.toString()) == true) {
                    if (wb != null) {
                        println(wb.text.toString())
                    }
                }
            }

            toggleTtsBox(false)
        }

        ttsUnclicked?.setOnClickListener {
            for (wb in wordButtons) {
                if (!wb?.hasBeenClicked()!!) {
                    println(wb.text.toString())
                }
            }

            toggleTtsBox(false)
        }

        ttsClicked?.setOnClickListener {
            for (wb in wordButtons) {
                if (wb?.hasBeenClicked() == true) {
                    println(wb.text.toString())
                }
            }

            toggleTtsBox(false)
        }

        closeTts?.setOnClickListener { toggleTtsBox(false) }

        viewPreviousHints?.setOnClickListener {
            previousHintsScroll?.visibility = View.VISIBLE
            gameOperations?.visibility = View.INVISIBLE
        }

        hidePreviousHints?.setOnClickListener {
            previousHintsScroll?.visibility = View.INVISIBLE
            gameOperations?.visibility = View.VISIBLE
        }

        exitButton?.setOnClickListener {
            if (gamePhase == LocalPhase.TEAM_A_WIN || gamePhase == LocalPhase.TEAM_B_WIN) {
                val i = Intent(applicationContext, MainMenu::class.java)

                startActivity(i)
            } else {
                messageText?.setText(R.string.exit_confirm)

                toggleMessageBox(true, 1)
            }
        }

        yesMessage?.setOnClickListener {
            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        updateColours()
    }

    override fun onBackPressed() {
        if (!messageBoxOpen) {
            exitButton?.performClick()
        }
    }

    private fun wordButtonPress(buttonPressed: WordButton?) {
        if (!buttonPressed?.hasBeenClicked()!! && gamePhase != LocalPhase.TEAM_A_SPY && gamePhase != LocalPhase.TEAM_B_SPY && gamePhase != LocalPhase.TEAM_A_INTERMISSION && gamePhase != LocalPhase.TEAM_B_INTERMISSION && gamePhase != LocalPhase.TEAM_A_WIN && gamePhase != LocalPhase.TEAM_B_WIN) {
            buttonPressed.setHasBeenClicked(true)

            var buttonType = ""

            for (s in bombWords!!) {
                if (buttonPressed.text.toString() == s) {
                    buttonType = "bomb"
                }
            }

            if (buttonType == "") {
                for (s in neutralWords!!) {
                    if (buttonPressed.text.toString() == s) {
                        buttonType = "neutral"
                    }
                }
            }

            if (buttonType == "") {
                for (s in teamAWords!!) {
                    if (buttonPressed.text.toString() == s) {
                        buttonType = "teamA"
                    }
                }
            }

            if (buttonType == "") {
                for (s in teamBWords!!) {
                    if (buttonPressed.text.toString() == s) {
                        buttonType = "teamB"
                    }
                }
            }

            updateColours()

            when (buttonType) {
                "bomb" -> {
                    gamePhase = if (gamePhase == LocalPhase.TEAM_A) {
                        LocalPhase.TEAM_B_WIN
                    } else {
                        LocalPhase.TEAM_A_WIN
                    }

                    teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                    teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!

                    gameWin()
                }

                "neutral" -> {
                    if (gamePhase == LocalPhase.TEAM_A) {
                        gamePhase = LocalPhase.TEAM_B_INTERMISSION
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                    } else {
                        gamePhase = LocalPhase.TEAM_A_INTERMISSION
                        teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                    }

                    messageText?.setText(R.string.spymaster_confirmation)
                    confirmButton?.setText(R.string.confirm)

                    toggleMessageBox(true, 0)
                }

                "teamA" -> {
                    if (gamePhase == LocalPhase.TEAM_B) {
                        gamePhase = LocalPhase.TEAM_A_INTERMISSION
                        teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                        messageText?.setText(R.string.spymaster_confirmation)
                        confirmButton?.setText(R.string.confirm)

                        toggleMessageBox(true, 0)
                    }
                    teamASquaresCount--
                    teamACount?.text = teamASquaresCount.toString()

                    if (teamASquaresCount == 0) {
                        gamePhase = LocalPhase.TEAM_A_WIN

                        gameWin()
                    }
                }

                "teamB" -> {
                    if (gamePhase == LocalPhase.TEAM_A) {
                        gamePhase = LocalPhase.TEAM_B_INTERMISSION
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                        messageText?.setText(R.string.spymaster_confirmation)
                        confirmButton?.setText(R.string.confirm)

                        toggleMessageBox(true, 0)
                    }

                    teamBSquaresCount--
                    teamBCount?.text = teamBSquaresCount.toString()

                    if (teamBSquaresCount == 0) {
                        gamePhase = LocalPhase.TEAM_B_WIN

                        gameWin()
                    }
                }
            }
        }
    }

    private fun gameWin() {
        if (gamePhase == LocalPhase.TEAM_A_WIN) {
            messageText?.setText(R.string.team_a_win)
        } else {
            messageText?.setText(R.string.team_b_win)
        }
        
        confirmButton?.setText(R.string.ok)
        
        toggleMessageBox(true, 0)
    }

    private fun toggleMessageBox(enabled: Boolean, type: Int) {
        messageBoxOpen = enabled

        if (enabled) {
            messageBox?.visibility = View.VISIBLE
        } else {
            messageBox?.visibility = View.INVISIBLE
        }

        for (wb in wordButtons) {
            wb?.isEnabled = !enabled
        }

        exitButton?.isEnabled = !enabled
        editHint?.isEnabled = !enabled
        hintNumber?.isEnabled = !enabled
        turnAction?.isEnabled = !enabled
        textToSpeechButton?.isEnabled = !enabled
        viewPreviousHints?.isEnabled = !enabled

        if (type == 0) {
            confirmButton?.visibility = View.VISIBLE
            yesMessage?.visibility = View.INVISIBLE
            noMessage?.visibility = View.INVISIBLE
        } else {
            confirmButton?.visibility = View.INVISIBLE
            yesMessage?.visibility = View.VISIBLE
            noMessage?.visibility = View.VISIBLE
        }
    }

    private fun toggleTtsBox(enabled: Boolean) {
        ttsOpen = enabled

        if (enabled) {
            ttsBox?.visibility = View.VISIBLE
        } else {
            ttsBox?.visibility = View.INVISIBLE
        }

        for (wb in wordButtons) {
            wb?.isEnabled = !enabled
        }

        exitButton?.isEnabled = !enabled
        editHint?.isEnabled = !enabled
        hintNumber?.isEnabled = !enabled
        turnAction?.isEnabled = !enabled
        textToSpeechButton?.isEnabled = !enabled
        viewPreviousHints?.isEnabled = !enabled
    }

    private fun addHint(hint: String?) {
        val newHint = TextView(this)

        newHint.text = hint
        previousHints?.addView(newHint)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        teamAColour = preferences.getInt("teamA", -16773377)
        teamBColour = preferences.getInt("teamB", -65536)

        if (gamePhase == LocalPhase.TEAM_A) {
            newHint.setTextColor(teamAColour)
        } else {
            newHint.setTextColor(teamBColour)
        }

        newHint.gravity = Gravity.CENTER
        newHint.textSize = 16f

        //Use this for TTS
        newHint.setOnClickListener { println(newHint.text.toString()) }
    }

    private fun updateSpinner() {
        val spinnerArray: MutableList<String> = ArrayList()

        spinnerArray.add("∞")

        if (gamePhase == LocalPhase.TEAM_A_SPY) {
            for (i in 0 until teamASquaresCount + 1) {
                spinnerArray.add(i.toString())
            }
        } else {
            for (i in 0 until teamBSquaresCount + 1) {
                spinnerArray.add(i.toString())
            }
        }

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, spinnerArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hintNumber?.adapter = adapter
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

        teamACount?.setTextColor(teamAColour)
        ttsA?.setBackgroundColor(teamAColour)

        teamBCount?.setTextColor(teamBColour)
        ttsB?.setBackgroundColor(teamBColour)

        ttsBomb?.setBackgroundColor(bombColour)

        ttsNeutral?.setBackgroundColor(neutralColour)

        ttsUnclicked?.setBackgroundColor(unmodifiedColour)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        messageBox?.setBackgroundColor(applicationBackgroundColour)
        ttsBox?.setBackgroundColor(applicationBackgroundColour)

        exitButton?.setBackgroundColor(menuButtonsColour)
        confirmButton?.setBackgroundColor(menuButtonsColour)
        yesMessage?.setBackgroundColor(menuButtonsColour)
        noMessage?.setBackgroundColor(menuButtonsColour)
        turnAction?.setBackgroundColor(menuButtonsColour)
        textToSpeechButton?.setBackgroundColor(menuButtonsColour)
        viewPreviousHints?.setBackgroundColor(menuButtonsColour)
        hidePreviousHints?.setBackgroundColor(menuButtonsColour)
        ttsAll?.setBackgroundColor(menuButtonsColour)
        ttsClicked?.setBackgroundColor(menuButtonsColour)
        closeTts?.setBackgroundColor(menuButtonsColour)

        exitButton?.setTextColor(menuTextColour)
        confirmButton?.setTextColor(menuTextColour)
        yesMessage?.setTextColor(menuTextColour)
        noMessage?.setTextColor(menuTextColour)
        teamCounterLine?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        turnAction?.setTextColor(menuTextColour)
        textToSpeechButton?.setTextColor(menuTextColour)
        viewPreviousHints?.setTextColor(menuTextColour)
        hidePreviousHints?.setTextColor(menuTextColour)

        when (gamePhase) {
            LocalPhase.START, LocalPhase.TEAM_A_INTERMISSION, LocalPhase.TEAM_B_INTERMISSION -> {
                for (wb in wordButtons) {
                    wb?.setBackgroundColor(menuButtonsColour)
                }
            }

            LocalPhase.TEAM_A, LocalPhase.TEAM_B -> for (wb in wordButtons) {
                if (wb?.hasBeenClicked() == true) {
                    var buttonType = ""

                    for (s in neutralWords!!) {
                        if (wb.text.toString() == s) {
                            buttonType = "neutral"
                        }
                    }

                    if (buttonType == "") {
                        for (s in teamAWords!!) {
                            if (wb.text.toString() == s) {
                                buttonType = "teamA"
                            }
                        }
                    }

                    if (buttonType == "") {
                        for (s in teamBWords!!) {
                            if (wb.text.toString() == s) {
                                buttonType = "teamB"
                            }
                        }
                    }

                    when (buttonType) {
                        "teamA" -> {
                            wb.setBackgroundColor(teamAColour)
                        }
                        "teamB" -> {
                            wb.setBackgroundColor(teamBColour)
                        }
                        "neutral" -> {
                            wb.setBackgroundColor(neutralColour)
                        }
                    }
                } else {
                    wb?.setBackgroundColor(unmodifiedColour)
                }
            }

            LocalPhase.TEAM_A_SPY, LocalPhase.TEAM_B_SPY, LocalPhase.TEAM_A_WIN, LocalPhase.TEAM_B_WIN -> {
                for (wb in wordButtons) {
                    for (s in bombWords!!) {
                        if (wb?.text.toString() == s) {
                            wb?.setBackgroundColor(bombColour)
                        }
                    }
                }

                for (wb in wordButtons) {
                    for (s in neutralWords!!) {
                        if (wb?.text.toString() == s) {
                            wb?.setBackgroundColor(neutralColour)
                        }
                    }
                }

                for (wb in wordButtons) {
                    for (s in teamAWords!!) {
                        if (wb?.text.toString() == s) {
                            wb?.setBackgroundColor(teamAColour)
                        }
                    }
                }

                for (wb in wordButtons) {
                    for (s in teamBWords!!) {
                        if (wb?.text.toString() == s) {
                            wb?.setBackgroundColor(teamBColour)
                        }
                    }
                }

                for (wb in wordButtons) {
                    wb?.setTextColor(menuTextColour)
                }

                for (wb in wordButtons) {
                    if (wb?.hasBeenClicked() == true) {
                        wb.background.alpha = 128
                    }
                }
            }
        }
    }
}