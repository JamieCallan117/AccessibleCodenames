package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.graphics.Paint
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

/**
 * Games being placed locally on one device.
 */
class LocalGame : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var gamePhase: LocalPhase? = null
    private var bombSquaresCount = 1
    private var neutralSquaresCount = 7
    private var teamASquaresCount = 9
    private var teamBSquaresCount = 8
    private var startingTeam = "A"
    private var messageBoxOpen = false
    private var teamsBoxOpen = false
    private var ttsOpen = false
    private var gameOpOpen = true
    private var customWords: ArrayList<String>? = null
    private var bombWords: ArrayList<String?>? = null
    private var neutralWords: ArrayList<String?>? = null
    private var teamAWords: ArrayList<String?>? = null
    private var teamBWords: ArrayList<String?>? = null
    private val words = arrayOfNulls<String>(25)
    private var wordButtons = arrayOfNulls<WordButton>(25)
    private var exitButton: MaterialButton? = null
    private var confirmButton: MaterialButton? = null
    private var turnAction: MaterialButton? = null
    private var ttsButton: MaterialButton? = null
    private var viewTeams: MaterialButton? = null
    private var closeTeamsBox: MaterialButton? = null
    private var viewPreviousHints: MaterialButton? = null
    private var hidePreviousHints: MaterialButton? = null
    private var yesButton: MaterialButton? = null
    private var noButton: MaterialButton? = null
    private var gameOpToggleButton: MaterialButton? = null
    private var ttsAll: MaterialButton? = null
    private var ttsA: MaterialButton? = null
    private var ttsB: MaterialButton? = null
    private var ttsNeutral: MaterialButton? = null
    private var ttsBomb: MaterialButton? = null
    private var ttsUnmodified: MaterialButton? = null
    private var ttsClicked: MaterialButton? = null
    private var closeTts: MaterialButton? = null
    private var teamACount: MaterialTextView? = null
    private var teamBCount: MaterialTextView? = null
    private var teamAHeader: MaterialTextView? = null
    private var teamBHeader: MaterialTextView? = null
    private var teamCounterLine: MaterialTextView? = null
    private var messageText: MaterialTextView? = null
    private var hintText: MaterialTextView? = null
    private var editHint: TextInputEditText? = null
    private var messageBox: ConstraintLayout? = null
    private var ttsBox: ConstraintLayout? = null
    private var constraintLayout: ConstraintLayout? = null
    private var viewTeamsBox: ConstraintLayout? = null
    private var outline: ConstraintLayout? = null
    private var gameOperationsLinear: LinearLayout? = null
    private var teamALinear: LinearLayout? = null
    private var teamBLinear: LinearLayout? = null
    private var previousHintsLinear: LinearLayout? = null
    private var scoreLinear: LinearLayout? = null
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
    private var neutralColour = -11731092
    private var unmodifiedColour = -3684409
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var wordCounter = 0
    private var maxTurns = 0
    private var textToSpeech: TextToSpeech? = null
    private var buttonClick: MediaPlayer? = null
    private var correctGuess: MediaPlayer? = null
    private var incorrectGuess: MediaPlayer? = null
    private var winSound: MediaPlayer? = null
    private var lossSound: MediaPlayer? = null

    /**
     * Sets up layout and listeners for the game etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.local_game)

        outline = findViewById(R.id.outline)
        constraintLayout = findViewById(R.id.constraintLayout)
        viewTeamsBox = findViewById(R.id.viewTeamsBox)
        exitButton = findViewById(R.id.exitButton)
        messageBox = findViewById(R.id.messageBox)
        ttsBox = findViewById(R.id.ttsBox)
        messageText = findViewById(R.id.messageText)
        confirmButton = findViewById(R.id.confirmButton)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)
        gameOpToggleButton = findViewById(R.id.gameOpToggleButton)
        ttsAll = findViewById(R.id.ttsAll)
        ttsA = findViewById(R.id.ttsA)
        ttsB = findViewById(R.id.ttsB)
        ttsNeutral = findViewById(R.id.ttsNeutral)
        ttsBomb = findViewById(R.id.ttsBomb)
        ttsUnmodified = findViewById(R.id.ttsUnmodified)
        ttsClicked = findViewById(R.id.ttsClicked)
        closeTts = findViewById(R.id.closeTTS)
        teamACount = findViewById(R.id.teamACount)
        teamBCount = findViewById(R.id.teamBCount)
        teamAHeader = findViewById(R.id.teamAHeader)
        teamBHeader = findViewById(R.id.teamBHeader)
        teamCounterLine = findViewById(R.id.teamCounterLine)
        hintText = findViewById(R.id.hintText)
        editHint = findViewById(R.id.editHint)
        hintNumber = findViewById(R.id.hintNumber)
        turnAction = findViewById(R.id.turnAction)
        ttsButton = findViewById(R.id.ttsButton)
        viewTeams = findViewById(R.id.viewTeams)
        closeTeamsBox = findViewById(R.id.closeTeamsBox)
        viewPreviousHints = findViewById(R.id.viewPreviousHints)
        gameOperations = findViewById(R.id.gameOperations)
        teamALinear = findViewById(R.id.teamALinear)
        teamBLinear = findViewById(R.id.teamBLinear)
        gameOperationsLinear = findViewById(R.id.gameOperationsLinear)
        previousHintsScroll = findViewById(R.id.previousHintsScroll)
        previousHintsLinear = findViewById(R.id.previousHintsLinear)
        hidePreviousHints = findViewById(R.id.hidePreviousHints)
        scoreLinear = findViewById(R.id.scoreLinear)

        correctGuess = MediaPlayer.create(this, R.raw.correctguess)
        incorrectGuess = MediaPlayer.create(this, R.raw.incorrectguess)
        winSound = MediaPlayer.create(this, R.raw.gamewin)
        lossSound = MediaPlayer.create(this, R.raw.gameloss)
        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        correctGuess?.setVolume(soundFXVolume, soundFXVolume)
        incorrectGuess?.setVolume(soundFXVolume, soundFXVolume)
        winSound?.setVolume(soundFXVolume, soundFXVolume)
        lossSound?.setVolume(soundFXVolume, soundFXVolume)
        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        val musicIntent = Intent(this, BackgroundMusicService::class.java)

        musicIntent.action = "START_GAME"

        stopService(musicIntent)

        startService(musicIntent)

        if (!preferences.getBoolean("textToSpeech", true)) {
            ttsButton?.visibility = View.GONE
        }

        hintText?.visibility = View.GONE
        viewPreviousHints?.visibility = View.GONE

        gamePhase = LocalPhase.START

        if (intent.getBooleanExtra("hasCustomSettings", false)) {
            bombSquaresCount = intent.getIntExtra("bombSquares", 1)
            neutralSquaresCount = intent.getIntExtra("neutralSquares", 7)
            teamASquaresCount = intent.getIntExtra("teamASquares", 9)
            teamBSquaresCount = intent.getIntExtra("teamBSquares", 8)
            startingTeam = intent.getStringExtra("startingTeam").toString()
            customWords = intent.getStringArrayListExtra("customWords")
        }

        //Sets up team viewer with all members.
        if (intent.getBooleanExtra("hasTeams", false)) {
            val teamASpymaster = MaterialTextView(this)
            val teamBSpymaster = MaterialTextView(this)

            menuTextColour = preferences.getInt("menuTextColour", -1)

            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.gravity = Gravity.CENTER

            teamASpymaster.text = getString(R.string.spymaster_name, intent.getStringExtra("spymasterA"))
            teamBSpymaster.text = getString(R.string.spymaster_name, intent.getStringExtra("spymasterB"))

            teamASpymaster.layoutParams = params
            teamBSpymaster.layoutParams = params

            teamASpymaster.setTextColor(menuTextColour)
            teamBSpymaster.setTextColor(menuTextColour)
            teamASpymaster.textSize = 30f
            teamBSpymaster.textSize = 30f

            runOnUiThread {
                teamALinear?.addView(teamASpymaster)
                teamBLinear?.addView(teamBSpymaster)
            }

            teamASpymaster.setOnLongClickListener {
                speakOut(teamASpymaster.text.toString())
                true
            }

            teamBSpymaster.setOnLongClickListener {
                speakOut(teamBSpymaster.text.toString())
                true
            }

            val teamAMembers = intent.getStringArrayListExtra("teamAMembers")
            val teamBMembers = intent.getStringArrayListExtra("teamBMembers")

            if (teamAMembers != null) {
                for (p in teamAMembers) {
                    val newMember = MaterialTextView(this)

                    newMember.text = p

                    newMember.layoutParams = params

                    newMember.setTextColor(menuTextColour)
                    newMember.textSize = 30f

                    runOnUiThread {
                        teamALinear?.addView(newMember)
                    }

                    newMember.setOnLongClickListener {
                        speakOut(newMember.text.toString())
                        true
                    }
                }
            }

            if (teamBMembers != null) {
                for (p in teamBMembers) {
                    val newMember = MaterialTextView(this)

                    newMember.text = p

                    newMember.layoutParams = params

                    newMember.setTextColor(menuTextColour)
                    newMember.textSize = 30f

                    runOnUiThread {
                        teamBLinear?.addView(newMember)
                    }

                    newMember.setOnLongClickListener {
                        speakOut(newMember.text.toString())
                        true
                    }
                }
            }
        } else {
            viewTeams?.visibility = View.GONE
        }

        //Sets up game.
        bombWords = ArrayList(bombSquaresCount)
        neutralWords = ArrayList(neutralSquaresCount)
        teamAWords = ArrayList(teamASquaresCount)
        teamBWords = ArrayList(teamBSquaresCount)

        if (startingTeam == "B") {
            gamePhase = LocalPhase.TEAM_B_INTERMISSION
            teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!

            teamBColour = preferences.getInt("teamB", -65536)

            outline?.setBackgroundColor(teamBColour)
        } else {
            gamePhase = LocalPhase.TEAM_A_INTERMISSION
            teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!

            teamAColour = preferences.getInt("teamA", -16773377)

            outline?.setBackgroundColor(teamAColour)
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

        messageText?.text = getString(R.string.spymaster_confirmation)

        Handler(Looper.getMainLooper()).postDelayed({
            toggleMessageBox(true, 0)
        }, 1000)

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
        val maxWords = 25
        var defaultWordsNeeded = maxWords

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
            words[i + (maxWords - defaultWordsNeeded)] = defaultWords[list[i]!!]
        }

        words.shuffle()

        for (i in 0 until maxWords) {
            wordButtons[i]?.text = words[i]
        }

        list.clear()

        for (i in 0 until maxWords) {
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

        squareOne?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareOne)
        }

        squareTwo?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwo)
        }

        squareThree?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareThree)
        }

        squareFour?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareFour)
        }

        squareFive?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareFive)
        }

        squareSix?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareSix)
        }

        squareSeven?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareSeven)
        }

        squareEight?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareEight)
        }

        squareNine?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareNine)
        }

        squareTen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTen)
        }

        squareEleven?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareEleven)
        }

        squareTwelve?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwelve)
        }

        squareThirteen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareThirteen)
        }

        squareFourteen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareFourteen)
        }

        squareFifteen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareFifteen)
        }

        squareSixteen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareSixteen)
        }

        squareSeventeen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareSeventeen)
        }

        squareEighteen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareEighteen)
        }

        squareNineteen?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareNineteen)
        }

        squareTwenty?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwenty)
        }

        squareTwentyOne?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwentyOne)
        }

        squareTwentyTwo?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwentyTwo)
        }

        squareTwentyThree?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwentyThree)
        }

        squareTwentyFour?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwentyFour)
        }

        squareTwentyFive?.setOnClickListener {
            buttonClick?.start()
            wordButtonPress(squareTwentyFive)
        }

        squareOne?.setOnLongClickListener {
            speakOut(squareOne?.text.toString())
            true
        }

        squareTwo?.setOnLongClickListener {
            speakOut(squareTwo?.text.toString())
            true
        }

        squareThree?.setOnLongClickListener {
            speakOut(squareThree?.text.toString())
            true
        }

        squareFour?.setOnLongClickListener {
            speakOut(squareFour?.text.toString())
            true
        }

        squareFive?.setOnLongClickListener {
            speakOut(squareFive?.text.toString())
            true
        }

        squareSix?.setOnLongClickListener {
            speakOut(squareSix?.text.toString())
            true
        }

        squareSeven?.setOnLongClickListener {
            speakOut(squareSeven?.text.toString())
            true
        }

        squareEight?.setOnLongClickListener {
            speakOut(squareEight?.text.toString())
            true
        }

        squareNine?.setOnLongClickListener {
            speakOut(squareNine?.text.toString())
            true
        }

        squareTen?.setOnLongClickListener {
            speakOut(squareTen?.text.toString())
            true
        }

        squareEleven?.setOnLongClickListener {
            speakOut(squareEleven?.text.toString())
            true
        }

        squareTwelve?.setOnLongClickListener {
            speakOut(squareTwelve?.text.toString())
            true
        }

        squareThirteen?.setOnLongClickListener {
            speakOut(squareThirteen?.text.toString())
            true
        }

        squareFourteen?.setOnLongClickListener {
            speakOut(squareFourteen?.text.toString())
            true
        }

        squareFifteen?.setOnLongClickListener {
            speakOut(squareFifteen?.text.toString())
            true
        }

        squareSixteen?.setOnLongClickListener {
            speakOut(squareSixteen?.text.toString())
            true
        }

        squareSeventeen?.setOnLongClickListener {
            speakOut(squareSeventeen?.text.toString())
            true
        }

        squareEighteen?.setOnLongClickListener {
            speakOut(squareEighteen?.text.toString())
            true
        }

        squareNineteen?.setOnLongClickListener {
            speakOut(squareNineteen?.text.toString())
            true
        }

        squareTwenty?.setOnLongClickListener {
            speakOut(squareTwenty?.text.toString())
            true
        }

        squareTwentyOne?.setOnLongClickListener {
            speakOut(squareTwentyOne?.text.toString())
            true
        }

        squareTwentyTwo?.setOnLongClickListener {
            speakOut(squareTwentyTwo?.text.toString())
            true
        }

        squareTwentyThree?.setOnLongClickListener {
            speakOut(squareTwentyThree?.text.toString())
            true
        }

        squareTwentyFour?.setOnLongClickListener {
            speakOut(squareTwentyFour?.text.toString())
            true
        }

        squareTwentyFive?.setOnLongClickListener {
            speakOut(squareTwentyFive?.text.toString())
            true
        }

        gameOpToggleButton?.setOnClickListener {
            buttonClick?.start()

            val wrapSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            gameOperations?.measure(wrapSpec, wrapSpec)

            val gameOpWidth = (gameOperations?.measuredWidth?.times(-1.0f))

            if (gameOpOpen) {
                gameOpWidth?.let { it1 -> gameOperations?.animate()?.translationX(it1)?.duration = 500}
                gameOpWidth?.let { it2 -> gameOpToggleButton?.animate()?.translationX(it2)?.duration = 500}

                gameOpToggleButton?.text = getString(R.string.open)

                gameOpOpen = false
            } else {
                gameOperations?.animate()?.translationX(0.0f)?.duration = 500
                gameOpToggleButton?.animate()?.translationX(0.0f)?.duration = 500

                gameOpToggleButton?.text = getString(R.string.close)

                gameOpOpen = true
            }
        }

        confirmButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            if (gamePhase == LocalPhase.TEAM_A || gamePhase == LocalPhase.TEAM_B || gamePhase == LocalPhase.TEAM_A_SPY || gamePhase == LocalPhase.TEAM_B_SPY) {
                toggleMessageBox(false, 0)
            } else if (gamePhase != LocalPhase.TEAM_A_WIN && gamePhase != LocalPhase.TEAM_B_WIN) {
                when (gamePhase) {
                    LocalPhase.TEAM_A_INTERMISSION -> gamePhase = LocalPhase.TEAM_A_SPY
                    LocalPhase.TEAM_B_INTERMISSION -> gamePhase = LocalPhase.TEAM_B_SPY
                    else -> {
                        val i = Intent(this, MainMenu::class.java)
                        i.putExtra("startMusic", true)
                        startActivity(i)
                    }
                }

                hintText?.visibility = View.GONE
                turnAction?.setText(R.string.give_hint)
                editHint?.visibility = View.VISIBLE
                hintNumber?.visibility = View.VISIBLE
                editHint?.setText("")

                toggleMessageBox(false, 0)
                updateSpinner()
            } else {
                teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                hintText?.visibility = View.GONE
                gameOperationsLinear?.removeView(editHint)
                gameOperationsLinear?.removeView(hintNumber)
                gameOperationsLinear?.removeView(turnAction)

                toggleMessageBox(false, 0)
            }

            updateColours()
        }

        //Handles when the turn action button clicked for all different phases.
        turnAction?.setOnClickListener {
            buttonClick?.start()

            var validHint = true

            when (gamePhase) {
                //Ends team A's turn if they've guessed at least once.
                LocalPhase.TEAM_A -> {
                    if (wordCounter == 0) {
                        messageText?.text = getString(R.string.minimum_turn)

                        confirmButton?.text = getString(R.string.ok)

                        toggleMessageBox(true, 0)
                    } else {
                        gamePhase = LocalPhase.TEAM_B_INTERMISSION
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                        messageText?.setText(R.string.spymaster_confirmation)
                        confirmButton?.setText(R.string.confirm)

                        teamBColour = preferences.getInt("teamB", -65536)

                        outline?.setBackgroundColor(teamBColour)

                        toggleMessageBox(true, 0)
                    }
                }

                //Ends team B's turn if guessed at least once.
                LocalPhase.TEAM_B -> {
                    if (wordCounter == 0) {
                        messageText?.text = getString(R.string.minimum_turn)

                        confirmButton?.text = getString(R.string.ok)

                        toggleMessageBox(true, 0)
                    } else {
                        gamePhase = LocalPhase.TEAM_A_INTERMISSION
                        teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                        messageText?.setText(R.string.spymaster_confirmation)
                        confirmButton?.setText(R.string.confirm)

                        teamAColour = preferences.getInt("teamA", -16773377)

                        outline?.setBackgroundColor(teamAColour)

                        toggleMessageBox(true, 0)
                    }
                }

                //Gives team A a hint if valid.
                LocalPhase.TEAM_A_SPY -> {
                    if (editHint?.text.toString() == "") {
                        validHint = false
                    } else if (editHint?.text.toString().trim().contains(" ")) {
                        validHint = false
                    } else if (!editHint?.text.toString().matches("[A-Za-z]+".toRegex())) {
                        validHint = false
                    } else if (editHint?.text.toString().length < 3) {
                        validHint = false
                    }

                    for (wb in wordButtons) {
                        if (editHint?.text.toString().uppercase(Locale.getDefault()).contains(wb?.text.toString().uppercase(Locale.getDefault()))) {
                            validHint = false
                        }

                        if (wb?.text.toString().uppercase(Locale.getDefault()).contains(editHint?.text.toString().uppercase(Locale.getDefault()))) {
                            validHint = false
                        }
                    }

                    if (validHint) {
                        turnAction?.setText(R.string.end_turn)

                        viewPreviousHints?.visibility = View.VISIBLE

                        wordCounter = 0

                        maxTurns = if (hintNumber?.selectedItem.toString() == "Infinite") {
                            24
                        } else {
                            hintNumber?.selectedItem.toString().toInt() + 1
                        }

                        gamePhase = LocalPhase.TEAM_A

                        val hint = editHint?.text.toString() + ": " + hintNumber?.selectedItem.toString()

                        hintText?.text = hint
                        hintText?.visibility = View.VISIBLE
                        editHint?.visibility = View.GONE
                        hintNumber?.visibility = View.GONE

                        addHint(hint)
                    } else {
                        messageText?.setText(R.string.invalid_hint)
                        confirmButton?.setText(R.string.ok)

                        toggleMessageBox(true, 0)
                    }
                }

                //Gives team B a hint if valid.
                LocalPhase.TEAM_B_SPY -> {
                    if (editHint?.text.toString() == "") {
                        validHint = false
                    } else if (editHint?.text.toString().trim().contains(" ")) {
                        validHint = false
                    } else if (!editHint?.text.toString().matches("[A-Za-z]+".toRegex())) {
                        validHint = false
                    } else if (editHint?.text.toString().length < 3) {
                        validHint = false
                    }

                    for (wb in wordButtons) {
                        if (editHint?.text.toString().uppercase(Locale.getDefault()).contains(wb?.text.toString().uppercase(Locale.getDefault()))) {
                            validHint = false
                        }

                        if (wb?.text.toString().uppercase(Locale.getDefault()).contains(editHint?.text.toString().uppercase(Locale.getDefault()))) {
                            validHint = false
                        }
                    }

                    if (validHint) {
                        turnAction?.setText(R.string.end_turn)

                        viewPreviousHints?.visibility = View.VISIBLE

                        wordCounter = 0

                        maxTurns = if (hintNumber?.selectedItem.toString() == "Infinite") {
                            24
                        } else {
                            hintNumber?.selectedItem.toString().toInt() + 1
                        }

                        gamePhase = LocalPhase.TEAM_B

                        val hint = editHint?.text.toString() + ": " + hintNumber?.selectedItem.toString()

                        hintText?.text = hint
                        hintText?.visibility = View.VISIBLE
                        editHint?.visibility = View.GONE
                        hintNumber?.visibility = View.GONE

                        addHint(hint)
                    } else {
                        messageText?.setText(R.string.invalid_hint)
                        confirmButton?.setText(R.string.ok)

                        toggleMessageBox(true, 0)
                    }
                }
                //If somehow in a different phase, return to main menu as something gone wrong.
                else -> {
                    val i = Intent(this, MainMenu::class.java)
                    i.putExtra("startMusic", true)
                    startActivity(i)
                }
            }

            updateColours()
        }

        noButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()
            toggleMessageBox(false, 1)
        }

        ttsButton?.setOnClickListener {
            buttonClick?.start()

            toggleTtsBox(true)
        }

        viewTeams?.setOnClickListener {
            buttonClick?.start()

            teamsBoxOpen = true

            toggleTeamsBox()

            viewTeamsBox?.visibility = View.VISIBLE
        }

        closeTeamsBox?.setOnClickListener {
            buttonClick?.start()

            teamsBoxOpen = false

            toggleTeamsBox()

            viewTeamsBox?.visibility = View.INVISIBLE
        }

        ttsAll?.setOnClickListener {
            buttonClick?.start()

            var message = ""

            for (wb in wordButtons) {
                message += wb?.text.toString() + "\n"
            }

            speakOut(message)

            toggleTtsBox(false)
        }

        ttsA?.setOnClickListener {
            buttonClick?.start()

            var message = ""

            if (gamePhase != LocalPhase.TEAM_A && gamePhase != LocalPhase.TEAM_B) {
                for (wb in wordButtons) {
                    if (teamAWords?.contains(wb?.text.toString()) == true) {
                        if (wb != null) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (wb?.hasBeenClicked() == true) {
                        if (teamAWords?.contains(wb.text.toString()) == true) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            }

            if (message == "") {
                speakOut(getString(R.string.none_found))
            } else {
                speakOut(message)
            }

            toggleTtsBox(false)
        }

        ttsB?.setOnClickListener {
            buttonClick?.start()

            var message = ""

            if (gamePhase != LocalPhase.TEAM_A && gamePhase != LocalPhase.TEAM_B) {
                for (wb in wordButtons) {
                    if (teamBWords?.contains(wb?.text.toString()) == true) {
                        if (wb != null) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (wb?.hasBeenClicked() == true) {
                        if (teamBWords?.contains(wb.text.toString()) == true) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            }

            if (message == "") {
                speakOut(getString(R.string.none_found))
            } else {
                speakOut(message)
            }

            toggleTtsBox(false)
        }

        ttsNeutral?.setOnClickListener {
            buttonClick?.start()

            var message = ""
            if (gamePhase != LocalPhase.TEAM_A && gamePhase != LocalPhase.TEAM_B) {
                for (wb in wordButtons) {
                    if (neutralWords?.contains(wb?.text.toString()) == true) {
                        if (wb != null) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (wb?.hasBeenClicked() == true) {
                        if (neutralWords?.contains(wb.text.toString()) == true) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            }

            if (message == "") {
                speakOut(getString(R.string.none_found))
            } else {
                speakOut(message)
            }

            toggleTtsBox(false)
        }

        ttsBomb?.setOnClickListener {
            buttonClick?.start()

            var message = ""

            if (gamePhase != LocalPhase.TEAM_A && gamePhase != LocalPhase.TEAM_B) {
                for (wb in wordButtons) {
                    if (bombWords?.contains(wb?.text.toString()) == true) {
                        if (wb != null) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (wb?.hasBeenClicked() == true) {
                        if (bombWords?.contains(wb.text.toString()) == true) {
                            message += wb.text.toString() + "\n"
                        }
                    }
                }
            }

            if (message == "") {
                speakOut(getString(R.string.none_found))
            } else {
                speakOut(message)
            }

            toggleTtsBox(false)
        }

        ttsUnmodified?.setOnClickListener {
            buttonClick?.start()

            var message = ""

            for (wb in wordButtons) {
                if (!wb?.hasBeenClicked()!!) {
                    message += wb.text.toString() + "\n"
                }
            }

            speakOut(message)

            toggleTtsBox(false)
        }

        ttsClicked?.setOnClickListener {
            buttonClick?.start()

            var message = ""

            for (wb in wordButtons) {
                if (wb?.hasBeenClicked() == true) {
                    message += wb.text.toString() + "\n"
                }
            }

            if (message == "") {
                speakOut(getString(R.string.none_found))
            } else {
                speakOut(message)
            }

            toggleTtsBox(false)
        }

        closeTts?.setOnClickListener {
            buttonClick?.start()
            toggleTtsBox(false)
        }

        viewPreviousHints?.setOnClickListener {
            buttonClick?.start()

            previousHintsScroll?.visibility = View.VISIBLE
            gameOpToggleButton?.visibility = View.INVISIBLE
            gameOperations?.visibility = View.INVISIBLE
        }

        hidePreviousHints?.setOnClickListener {
            buttonClick?.start()

            previousHintsScroll?.visibility = View.INVISIBLE
            gameOperations?.visibility = View.VISIBLE
            gameOpToggleButton?.visibility = View.VISIBLE
        }

        exitButton?.setOnClickListener {
            buttonClick?.start()

            if (gamePhase == LocalPhase.TEAM_A_WIN || gamePhase == LocalPhase.TEAM_B_WIN) {
                val i = Intent(applicationContext, MainMenu::class.java)
                i.putExtra("startMusic", true)
                startActivity(i)
            } else {
                messageText?.setText(R.string.exit_confirm)

                toggleMessageBox(true, 1)
            }
        }

        yesButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, MainMenu::class.java)
            i.putExtra("startMusic", true)
            startActivity(i)
        }

        exitButton?.setOnLongClickListener {
            speakOut(exitButton?.text.toString())
            true
        }

        scoreLinear?.setOnLongClickListener {
            var message = getString(R.string.remaining_words_a, teamASquaresCount).replace(" A ", " Ay ")

            message += "\n" + getString(R.string.remaining_words_b, teamBSquaresCount)

            speakOut(message)
            true
        }

        hintText?.setOnLongClickListener {
            speakOut(hintText?.text.toString())
            true
        }

        hintNumber?.setOnLongClickListener {
            speakOut(hintNumber?.selectedItem.toString())
            true
        }

        turnAction?.setOnLongClickListener {
            speakOut(turnAction?.text.toString())
            true
        }

        ttsButton?.setOnLongClickListener {
            speakOut(getString(R.string.text_to_speech))
            true
        }

        viewTeams?.setOnLongClickListener {
            speakOut(viewTeams?.text.toString())
            true
        }

        closeTeamsBox?.setOnLongClickListener {
            speakOut(closeTeamsBox?.text.toString())
            true
        }

        viewPreviousHints?.setOnLongClickListener {
            speakOut(viewPreviousHints?.text.toString().replace("\n", ""))
            true
        }

        hidePreviousHints?.setOnLongClickListener {
            speakOut(hidePreviousHints?.text.toString())
            true
        }

        messageText?.setOnLongClickListener {
            speakOut(messageText?.text.toString())
            true
        }

        yesButton?.setOnLongClickListener {
            speakOut(yesButton?.text.toString())
            true
        }

        confirmButton?.setOnLongClickListener {
            speakOut(confirmButton?.text.toString())
            true
        }

        noButton?.setOnLongClickListener {
            speakOut(noButton?.text.toString())
            true
        }

        gameOpToggleButton?.setOnLongClickListener {
            if (gameOpOpen) {
                speakOut(getString(R.string.close_game_op))
            } else {
                speakOut(getString(R.string.open_game_op))
            }

            true
        }

        ttsAll?.setOnLongClickListener {
            speakOut(ttsAll?.text.toString().replace("\n", ""))
            true
        }

        ttsA?.setOnLongClickListener {
            speakOut(ttsA?.text.toString().replace("\n", "").replace(" A ", " Ay "))
            true
        }

        ttsB?.setOnLongClickListener {
            speakOut(ttsB?.text.toString().replace("\n", ""))
            true
        }

        ttsNeutral?.setOnLongClickListener {
            speakOut(ttsNeutral?.text.toString().replace("\n", ""))
            true
        }

        ttsBomb?.setOnLongClickListener {
            speakOut(ttsBomb?.text.toString().replace("\n", ""))
            true
        }

        ttsUnmodified?.setOnLongClickListener {
            speakOut(ttsUnmodified?.text.toString().replace("\n", ""))
            true
        }

        ttsClicked?.setOnLongClickListener {
            speakOut(ttsClicked?.text.toString().replace("\n", ""))
            true
        }

        closeTts?.setOnLongClickListener {
            speakOut(closeTts?.text.toString())
            true
        }

        teamAHeader?.setOnLongClickListener {
            var message = ""

            message += "Team Ay members. \n"

            for (i in 0 until teamALinear?.childCount!!) {
                val member = teamALinear?.getChildAt(i) as MaterialTextView
                var memberStr = member.text.toString()

                val regex = Regex("[^A-Za-z0-9]")
                memberStr = regex.replace(memberStr, "")

                message += memberStr + "\n"
            }

            if (message == "Team Ay members. \n") {
                message += getString(R.string.none_found)
            }

            speakOut(message)
            true
        }

        teamBHeader?.setOnLongClickListener {
            var message = ""

            message += "Team B members. \n"

            for (i in 0 until teamBLinear?.childCount!!) {
                val member = teamBLinear?.getChildAt(i) as MaterialTextView
                var memberStr = member.text.toString()
                val regex = Regex("[^A-Za-z0-9]")
                memberStr = regex.replace(memberStr, "")

                message += memberStr + "\n"
            }

            if (message == "Team B members. \n") {
                message += getString(R.string.none_found)
            }

            speakOut(message)
            true
        }

        updateColours()
    }

    /**
     * When app is resumed.
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
     * Sets up Text-to-Speech engine.
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    /**
     * When device back button pressed.
     */
    override fun onBackPressed() {
        if (!messageBoxOpen) {
            exitButton?.performClick()
        }
    }

    /**
     * When a word button has been pushed.
     */
    private fun wordButtonPress(buttonPressed: WordButton?) {
        //Ensure it hasn't been clicked and it's the guessing phase
        if (!buttonPressed?.hasBeenClicked()!! && gamePhase != LocalPhase.TEAM_A_SPY && gamePhase != LocalPhase.TEAM_B_SPY && gamePhase != LocalPhase.TEAM_A_INTERMISSION && gamePhase != LocalPhase.TEAM_B_INTERMISSION && gamePhase != LocalPhase.TEAM_A_WIN && gamePhase != LocalPhase.TEAM_B_WIN) {
            buttonPressed.setHasBeenClicked(true)

            wordCounter++

            var buttonType = ""

            //Figure out what type of word was clicked.
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

            //Handle based on what phase it is and what type of word was clicked.
            when (buttonType) {
                //Bomb ends the game with opposing team victory.
                "bomb" -> {
                    gamePhase = if (gamePhase == LocalPhase.TEAM_A) {
                        LocalPhase.TEAM_B_WIN
                    } else {
                        LocalPhase.TEAM_A_WIN
                    }

                    teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                    teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!

                    lossSound?.start()

                    Handler(Looper.getMainLooper()).postDelayed({
                        gameWin()
                    }, 1000)
                }

                //Neutral ends current turn.
                "neutral" -> {
                    incorrectGuess?.start()

                    if (gamePhase == LocalPhase.TEAM_A) {
                        gamePhase = LocalPhase.TEAM_B_INTERMISSION
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!

                        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

                        teamBColour = preferences.getInt("teamB", -65536)

                        outline?.setBackgroundColor(teamBColour)
                    } else {
                        gamePhase = LocalPhase.TEAM_A_INTERMISSION
                        teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!

                        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

                        teamAColour = preferences.getInt("teamA", -16773377)

                        outline?.setBackgroundColor(teamAColour)
                    }

                    messageText?.setText(R.string.spymaster_confirmation)
                    confirmButton?.setText(R.string.confirm)

                    Handler(Looper.getMainLooper()).postDelayed({
                        toggleMessageBox(true, 0)
                    }, 1000)
                }

                //Ends turn if it's team b, otherwise continue playing unless reached maximum number of guesses.
                "teamA" -> {
                    if (gamePhase == LocalPhase.TEAM_B) {
                        incorrectGuess?.start()

                        gamePhase = LocalPhase.TEAM_A_INTERMISSION
                        teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                        messageText?.setText(R.string.spymaster_confirmation)
                        confirmButton?.setText(R.string.confirm)

                        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

                        teamAColour = preferences.getInt("teamA", -16773377)

                        outline?.setBackgroundColor(teamAColour)

                        Handler(Looper.getMainLooper()).postDelayed({
                            toggleMessageBox(true, 0)
                        }, 1000)
                    } else {
                        correctGuess?.start()
                    }

                    teamASquaresCount--
                    teamACount?.text = teamASquaresCount.toString()

                    if (teamASquaresCount == 0) {
                        gamePhase = LocalPhase.TEAM_A_WIN

                        winSound?.start()

                        Handler(Looper.getMainLooper()).postDelayed({
                            gameWin()
                        }, 1000)
                    } else if (maxTurns == wordCounter) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            gamePhase = LocalPhase.TEAM_B_INTERMISSION
                            teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                            teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                            messageText?.setText(R.string.spymaster_confirmation)
                            confirmButton?.setText(R.string.confirm)

                            val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

                            teamBColour = preferences.getInt("teamB", -65536)

                            outline?.setBackgroundColor(teamBColour)

                            toggleMessageBox(true, 0)
                        }, 1000)
                    }
                }

                //Ends turn if it's team a, otherwise continue playing unless reached maximum number of guesses.
                "teamB" -> {
                    if (gamePhase == LocalPhase.TEAM_A) {
                        incorrectGuess?.start()

                        gamePhase = LocalPhase.TEAM_B_INTERMISSION
                        teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                        teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                        messageText?.setText(R.string.spymaster_confirmation)
                        confirmButton?.setText(R.string.confirm)

                        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

                        teamBColour = preferences.getInt("teamB", -65536)

                        outline?.setBackgroundColor(teamBColour)

                        Handler(Looper.getMainLooper()).postDelayed({
                            toggleMessageBox(true, 0)
                        }, 1000)
                    } else {
                        correctGuess?.start()
                    }

                    teamBSquaresCount--
                    teamBCount?.text = teamBSquaresCount.toString()

                    if (teamBSquaresCount == 0) {
                        gamePhase = LocalPhase.TEAM_B_WIN

                        winSound?.start()

                        Handler(Looper.getMainLooper()).postDelayed({
                            gameWin()
                        }, 1000)
                    } else if (maxTurns == wordCounter) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            gamePhase = LocalPhase.TEAM_A_INTERMISSION
                            teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                            teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                            messageText?.setText(R.string.spymaster_confirmation)
                            confirmButton?.setText(R.string.confirm)

                            val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

                            teamAColour = preferences.getInt("teamA", -16773377)

                            outline?.setBackgroundColor(teamAColour)

                            toggleMessageBox(true, 0)
                        }, 1000)
                    }
                }
            }
        }
    }

    /**
     * When the game is won display win message.
     */
    private fun gameWin() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        teamAColour = preferences.getInt("teamA", -16773377)
        teamBColour = preferences.getInt("teamB", -65536)

        outline?.setBackgroundColor(teamAColour)

        if (gamePhase == LocalPhase.TEAM_A_WIN) {
            messageText?.setText(R.string.team_a_win)

            outline?.setBackgroundColor(teamAColour)
        } else {
            messageText?.setText(R.string.team_b_win)

            outline?.setBackgroundColor(teamBColour)
        }

        turnAction?.visibility = View.GONE

        confirmButton?.setText(R.string.ok)
        
        toggleMessageBox(true, 0)
    }

    /**
     * Disable other elements whilst message box visible.
     */
    private fun toggleMessageBox(enabled: Boolean, type: Int) {
        messageBoxOpen = enabled

        if (enabled) {
            messageBox?.visibility = View.VISIBLE

            speakOut(messageText?.text.toString().replace("\n", ""))
        } else {
            messageBox?.visibility = View.INVISIBLE
        }

        for (wb in wordButtons) {
            wb?.isEnabled = !enabled
        }

        gameOpToggleButton?.isEnabled = !enabled
        exitButton?.isEnabled = !enabled
        editHint?.isEnabled = !enabled
        hintNumber?.isEnabled = !enabled
        turnAction?.isEnabled = !enabled
        ttsButton?.isEnabled = !enabled
        viewTeams?.isEnabled = !enabled
        viewPreviousHints?.isEnabled = !enabled

        if (type == 0) {
            confirmButton?.visibility = View.VISIBLE
            yesButton?.visibility = View.INVISIBLE
            noButton?.visibility = View.INVISIBLE
        } else {
            confirmButton?.visibility = View.INVISIBLE
            yesButton?.visibility = View.VISIBLE
            noButton?.visibility = View.VISIBLE
        }
    }

    /**
     * Disable other elements whilst Text-to-Speech box visible.
     */
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
        ttsButton?.isEnabled = !enabled
        viewTeams?.isEnabled = !enabled
        viewPreviousHints?.isEnabled = !enabled
    }

    /**
     * Disable other elements whilst teams box visible.
     */
    private fun toggleTeamsBox() {
        for (wb in wordButtons) {
            wb?.isEnabled = !teamsBoxOpen
        }

        gameOpToggleButton?.isEnabled = !teamsBoxOpen
        exitButton?.isEnabled = !teamsBoxOpen
        editHint?.isEnabled = !teamsBoxOpen
        hintNumber?.isEnabled = !teamsBoxOpen
        turnAction?.isEnabled = !teamsBoxOpen
        ttsButton?.isEnabled = !teamsBoxOpen
        viewTeams?.isEnabled = !teamsBoxOpen
        viewPreviousHints?.isEnabled = !teamsBoxOpen
    }

    /**
     * Add a hint to the previous hints ScrollView.
     */
    private fun addHint(hint: String?) {
        val newHint = MaterialTextView(this)

        newHint.text = hint
        previousHintsLinear?.addView(newHint)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        teamAColour = preferences.getInt("teamA", -16773377)
        teamBColour = preferences.getInt("teamB", -65536)

        if (gamePhase == LocalPhase.TEAM_A) {
            newHint.setTextColor(teamAColour)

            newHint.setOnLongClickListener {
                speakOut(getString(R.string.team_a_hint, newHint.text.toString()).replace(" A ", " Ay "))
                true
            }

            speakOut(getString(R.string.team_a_new_hint, newHint.text.toString()).replace(" A ", " Ay "))
        } else {
            newHint.setTextColor(teamBColour)

            newHint.setOnLongClickListener {
                speakOut(getString(R.string.team_b_hint, newHint.text.toString()))
                true
            }

            speakOut(getString(R.string.team_b_new_hint, newHint.text.toString()))
        }

        newHint.textSize = 20f

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.bottomMargin = 8
        params.gravity = Gravity.CENTER

        newHint.layoutParams = params


    }

    /**
     * Update the spinner for how many hints you can give.
     */
    private fun updateSpinner() {
        val spinnerArray: MutableList<String> = ArrayList()

        if (gamePhase == LocalPhase.TEAM_A_SPY) {
            for (i in 1 until teamASquaresCount + 1) {
                spinnerArray.add(i.toString())
            }
        } else {
            for (i in 1 until teamBSquaresCount + 1) {
                spinnerArray.add(i.toString())
            }
        }

        spinnerArray.add("0")
        spinnerArray.add("Infinite")

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, spinnerArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hintNumber?.adapter = adapter
    }

    /**
     * Read aloud given message.
     */
    private fun speakOut(message: String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Updates colours of elements.
     */
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

        teamACount?.setTextColor(teamAColour)
        ttsA?.setBackgroundColor(teamAColour)

        teamBCount?.setTextColor(teamBColour)
        ttsB?.setBackgroundColor(teamBColour)

        ttsBomb?.setBackgroundColor(bombColour)

        ttsNeutral?.setBackgroundColor(neutralColour)

        ttsUnmodified?.setBackgroundColor(unmodifiedColour)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        gameOperations?.setBackgroundColor(applicationBackgroundColour)
        previousHintsScroll?.setBackgroundColor(applicationBackgroundColour)
        messageBox?.setBackgroundColor(applicationBackgroundColour)
        ttsBox?.setBackgroundColor(applicationBackgroundColour)
        viewTeamsBox?.setBackgroundColor(applicationBackgroundColour)

        exitButton?.setBackgroundColor(menuButtonsColour)
        confirmButton?.setBackgroundColor(menuButtonsColour)
        yesButton?.setBackgroundColor(menuButtonsColour)
        noButton?.setBackgroundColor(menuButtonsColour)
        gameOpToggleButton?.setBackgroundColor(menuButtonsColour)
        turnAction?.setBackgroundColor(menuButtonsColour)
        ttsButton?.setBackgroundColor(menuButtonsColour)
        viewPreviousHints?.setBackgroundColor(menuButtonsColour)
        hidePreviousHints?.setBackgroundColor(menuButtonsColour)
        ttsAll?.setBackgroundColor(menuButtonsColour)
        ttsClicked?.setBackgroundColor(menuButtonsColour)
        closeTts?.setBackgroundColor(menuButtonsColour)
        viewTeams?.setBackgroundColor(menuButtonsColour)
        closeTeamsBox?.setBackgroundColor(menuButtonsColour)

        exitButton?.setTextColor(menuTextColour)
        confirmButton?.setTextColor(menuTextColour)
        yesButton?.setTextColor(menuTextColour)
        noButton?.setTextColor(menuTextColour)
        gameOpToggleButton?.setTextColor(menuTextColour)
        teamCounterLine?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        turnAction?.setTextColor(menuTextColour)
        ttsButton?.setTextColor(menuTextColour)
        viewPreviousHints?.setTextColor(menuTextColour)
        hidePreviousHints?.setTextColor(menuTextColour)
        viewTeams?.setTextColor(menuTextColour)
        closeTeamsBox?.setTextColor(menuTextColour)

        teamAHeader?.setTextColor(teamAColour)
        teamBHeader?.setTextColor(teamBColour)

        //Update colours of word buttons depending on the current phase.
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
            else -> {
                val i = Intent(this, MainMenu::class.java)
                i.putExtra("startMusic", true)
                startActivity(i)
            }
        }
    }
}
