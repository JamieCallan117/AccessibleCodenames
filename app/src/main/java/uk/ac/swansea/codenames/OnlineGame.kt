package uk.ac.swansea.codenames

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class OnlineGame : AppCompatActivity(), TextToSpeech.OnInitListener {

    // TODO: Proper win screen.
    // TODO: Disable chat for spymasters
    // TODO: A few TTS fixes. (\n on some buttons, probably some "Team Ay"'s needed and chat messages/individual team members need TTS

    private var outline: ConstraintLayout? = null
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: ConstraintLayout? = null
    private var chooseTeamBox: ConstraintLayout? = null
    private var viewTeamsBox: ConstraintLayout? = null
    private var ttsBox: ConstraintLayout? = null
    private var chatWindow: ConstraintLayout? = null
    private var gameOperations: ScrollView? = null
    private var teamALinear: LinearLayout? = null
    private var teamBLinear: LinearLayout? = null
    private var scoreLinear: LinearLayout? = null
    private var chatLinear: LinearLayout? = null
    private var yesButton: MaterialButton? = null
    private var okButton: MaterialButton? = null
    private var noButton: MaterialButton? = null
    private var teamAButton: MaterialButton? = null
    private var teamBButton: MaterialButton? = null
    private var closeTeamsBox: MaterialButton? = null
    private var ttsAll: MaterialButton? = null
    private var ttsA: MaterialButton? = null
    private var ttsB: MaterialButton? = null
    private var ttsNeutral: MaterialButton? = null
    private var ttsBomb: MaterialButton? = null
    private var ttsUnmodified: MaterialButton? = null
    private var ttsClicked: MaterialButton? = null
    private var closeTTS: MaterialButton? = null
    private var exitButton: MaterialButton? = null
    private var startGame: MaterialButton? = null
    private var requestSpymaster: MaterialButton? = null
    private var changeTeam: MaterialButton? = null
    private var turnAction: MaterialButton? = null
    private var ttsButton: MaterialButton? = null
    private var viewTeams: MaterialButton? = null
    private var chatButton: MaterialButton? = null
    private var gameOpToggleButton: MaterialButton? = null
    private var closeChat: MaterialButton? = null
    private var sendChat: MaterialButton? = null
    private var loadingText: MaterialTextView? = null
    private var messageText: MaterialTextView? = null
    private var chooseTeamText: MaterialTextView? = null
    private var teamAHeader: MaterialTextView? = null
    private var teamBHeader: MaterialTextView? = null
    private var teamACount: MaterialTextView? = null
    private var teamCounterLine: MaterialTextView? = null
    private var teamBCount: MaterialTextView? = null
    private var hintText: MaterialTextView? = null
    private var editHint: TextInputEditText? = null
    private var chatEdit: TextInputEditText? = null
    private var hintNumber: Spinner? = null
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
    private var gamePhase = OnlinePhase.START

    private var teamAUsers = ArrayList<Player>()
    private var teamBUsers = ArrayList<Player>()
    private var allWords = ArrayList<String>()
    private var bombWords = ArrayList<String>()
    private var neutralWords = ArrayList<String>()
    private var teamAWords = ArrayList<String>()
    private var teamBWords = ArrayList<String>()
    private var wordButtons = arrayOfNulls<WordButton>(25)

    private var player: Player? = null
    private var teamASpymaster: Player? = null
    private var teamBSpymaster: Player? = null

    private var startingTeam = "A"
    private var roomName: String? = null

    private var maxTurns = 0
    private var teamAWordCount = 0
    private var teamBWordCount = 0
    private var wordCounter = 0
    private var teamAColour = -16773377
    private var teamBColour = -65536
    private var bombColour = -14342875
    private var neutralColour = -11731092
    private var unmodifiedColour = -3684409
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1

    private var windowOpen = true
    private var gameOpOpen = true

    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_game)

        outline = findViewById(R.id.outline)
        constraintLayout = findViewById(R.id.constraintLayout)
        messageBox = findViewById(R.id.messageBox)
        chooseTeamBox = findViewById(R.id.chooseTeamBox)
        viewTeamsBox = findViewById(R.id.viewTeamsBox)
        ttsBox = findViewById(R.id.ttsBox)
        chatWindow = findViewById(R.id.chatWindow)
        gameOperations = findViewById(R.id.gameOperations)
        teamALinear = findViewById(R.id.teamALinear)
        teamBLinear = findViewById(R.id.teamBLinear)
        scoreLinear = findViewById(R.id.scoreLinear)
        chatLinear = findViewById(R.id.chatLinear)
        yesButton = findViewById(R.id.yesButton)
        okButton = findViewById(R.id.okButton)
        noButton = findViewById(R.id.noButton)
        teamAButton = findViewById(R.id.teamAButton)
        teamBButton = findViewById(R.id.teamBButton)
        closeTeamsBox = findViewById(R.id.closeTeamsBox)
        ttsAll = findViewById(R.id.ttsAll)
        ttsA = findViewById(R.id.ttsA)
        ttsB = findViewById(R.id.ttsB)
        ttsNeutral = findViewById(R.id.ttsNeutral)
        ttsBomb = findViewById(R.id.ttsBomb)
        ttsUnmodified = findViewById(R.id.ttsUnmodified)
        ttsClicked = findViewById(R.id.ttsClicked)
        closeTTS = findViewById(R.id.closeTTS)
        exitButton = findViewById(R.id.exitButton)
        startGame = findViewById(R.id.startGame)
        requestSpymaster = findViewById(R.id.requestSpymaster)
        changeTeam = findViewById(R.id.changeTeam)
        turnAction = findViewById(R.id.turnAction)
        ttsButton = findViewById(R.id.ttsButton)
        viewTeams = findViewById(R.id.viewTeams)
        chatButton = findViewById(R.id.chatButton)
        gameOpToggleButton = findViewById(R.id.gameOpToggleButton)
        closeChat = findViewById(R.id.closeChat)
        sendChat = findViewById(R.id.sendChat)
        loadingText = findViewById(R.id.loadingText)
        messageText = findViewById(R.id.messageText)
        chooseTeamText = findViewById(R.id.chooseTeamText)
        teamAHeader = findViewById(R.id.teamAHeader)
        teamBHeader = findViewById(R.id.teamBHeader)
        teamACount = findViewById(R.id.teamACount)
        teamCounterLine = findViewById(R.id.teamCounterLine)
        teamBCount = findViewById(R.id.teamBCount)
        hintText = findViewById(R.id.hintText)
        editHint = findViewById(R.id.editHint)
        chatEdit = findViewById(R.id.chatEdit)
        hintNumber = findViewById(R.id.hintNumber)
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

        textToSpeech = TextToSpeech(this, this)

        wordButtons = arrayOf(
            squareOne, squareTwo, squareThree, squareFour, squareFive,
            squareSix, squareSeven, squareEight, squareNine, squareTen, squareEleven,
            squareTwelve, squareThirteen, squareFourteen, squareFifteen, squareSixteen,
            squareSeventeen, squareEighteen, squareNineteen, squareTwenty, squareTwentyOne,
            squareTwentyTwo, squareTwentyThree, squareTwentyFour, squareTwentyFive
        )

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val username = if (preferences.getString("username", "").isNullOrEmpty()) {
            UUID.randomUUID().toString().replace("-", "").take(10)
        } else {
            preferences.getString("username", "").toString()
        }

        if (!preferences.getBoolean("textToSpeech", true)) {
            ttsButton?.visibility = View.GONE
        }

        player = Player(username)

        player?.isHost = intent.getBooleanExtra("isHost", false)
        roomName = intent.getStringExtra("roomName")

        Handler(Looper.getMainLooper()).postDelayed({
            SocketConnection.socket.emit("getGameDetails", roomName)
        }, 2000)

        if (!player?.isHost!!) {
            startGame?.visibility = View.GONE
        } else {
            exitButton?.text = getString(R.string.end_game)
        }

        yesButton?.setOnClickListener {
            SocketConnection.socket.emit("leaveRoom")

            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        noButton?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            messageBox?.visibility = View.GONE
            yesButton?.visibility = View.INVISIBLE
            noButton?.visibility = View.INVISIBLE
            okButton?.visibility = View.INVISIBLE
        }

        okButton?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            messageBox?.visibility = View.GONE
            yesButton?.visibility = View.INVISIBLE
            noButton?.visibility = View.INVISIBLE
            okButton?.visibility = View.INVISIBLE
        }

        teamAButton?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            chooseTeamBox?.visibility = View.GONE

            SocketConnection.socket.emit("chooseTeam", player?.nickname, "A", roomName)

            if (teamASpymaster != null) {
                requestSpymaster?.visibility = View.GONE
            }
        }

        teamBButton?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            chooseTeamBox?.visibility = View.GONE

            SocketConnection.socket.emit("chooseTeam", player?.nickname, "B", roomName)

            if (teamBSpymaster != null) {
                requestSpymaster?.visibility = View.GONE
            }
        }

        closeTeamsBox?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            viewTeamsBox?.visibility = View.GONE
        }

        exitButton?.setOnClickListener {
            windowOpen = true

            toggleWindow()

            messageBox?.visibility = View.VISIBLE
            yesButton?.visibility = View.VISIBLE
            noButton?.visibility = View.VISIBLE
            okButton?.visibility = View.INVISIBLE

            if (player?.isHost!!) {
                messageText?.text = getString(R.string.exit_online_host)
            } else {
                messageText?.text = getString(R.string.exit_online)
            }
        }

        startGame?.setOnClickListener {
            SocketConnection.socket.emit("startGame")
        }

        requestSpymaster?.setOnClickListener {
            SocketConnection.socket.emit(
                "requestSpymaster",
                player?.nickname,
                roomName,
                player?.team
            )
        }

        changeTeam?.setOnClickListener {
            if (player?.team == "A") {
                SocketConnection.socket.emit("chooseTeam", player?.nickname, "B", roomName)
            } else {
                SocketConnection.socket.emit("chooseTeam", player?.nickname, "A", roomName)
            }
        }

        turnAction?.setOnClickListener {
            if (gamePhase == OnlinePhase.TEAM_A_SPY || gamePhase == OnlinePhase.TEAM_B_SPY) {
                var validHint = true

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
                    if (editHint?.text.toString().uppercase(Locale.getDefault())
                            .contains(wb?.text.toString().uppercase(Locale.getDefault()))
                    ) {
                        validHint = false
                    }

                    if (wb?.text.toString().uppercase(Locale.getDefault())
                            .contains(editHint?.text.toString().uppercase(Locale.getDefault()))
                    ) {
                        validHint = false
                    }
                }

                if (validHint) {
                    val hint =
                        editHint?.text.toString() + ": " + hintNumber?.selectedItem.toString()

                    editHint?.visibility = View.GONE
                    hintNumber?.visibility = View.GONE
                    turnAction?.visibility = View.GONE

                    SocketConnection.socket.emit("hint", hint, roomName)
                } else {
                    windowOpen = true

                    toggleWindow()

                    messageText?.setText(R.string.invalid_hint)

                    messageBox?.visibility = View.VISIBLE
                    okButton?.visibility = View.VISIBLE
                    yesButton?.visibility = View.INVISIBLE
                    noButton?.visibility = View.INVISIBLE
                }
            } else {
                if (wordCounter == 0) {
                    windowOpen = true

                    toggleWindow()

                    messageText?.text = getString(R.string.minimum_turn)

                    messageBox?.visibility = View.VISIBLE
                    okButton?.visibility = View.VISIBLE
                    yesButton?.visibility = View.INVISIBLE
                    noButton?.visibility = View.INVISIBLE
                } else {
                    SocketConnection.socket.emit("endTurn", roomName)
                }
            }
        }

        ttsButton?.setOnClickListener {
            windowOpen = true

            toggleWindow()

            ttsBox?.visibility = View.VISIBLE
        }

        viewTeams?.setOnClickListener {
            windowOpen = true

            toggleWindow()

            viewTeamsBox?.visibility = View.VISIBLE

            updateTeamsBox()
        }

        chatButton?.setOnClickListener {
            constraintLayout?.visibility = View.GONE
            chatWindow?.visibility = View.VISIBLE
        }

        gameOpToggleButton?.setOnClickListener {
            val wrapSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            gameOperations?.measure(wrapSpec, wrapSpec)

            val gameOpWidth = (gameOperations?.measuredWidth?.times(-1.0f))

            if (gameOpOpen) {
                gameOpWidth?.let { it1 ->
                    gameOperations?.animate()?.translationX(it1)?.duration = 500
                }
                gameOpWidth?.let { it2 ->
                    gameOpToggleButton?.animate()?.translationX(it2)?.duration = 500
                }

                gameOpToggleButton?.text = getString(R.string.open)

                gameOpOpen = false
            } else {
                gameOperations?.animate()?.translationX(0.0f)?.duration = 500
                gameOpToggleButton?.animate()?.translationX(0.0f)?.duration = 500

                gameOpToggleButton?.text = getString(R.string.close)

                gameOpOpen = true
            }
        }

        for (wb in wordButtons) {
            wb?.setOnClickListener {
                if (gamePhase === OnlinePhase.TEAM_A && player?.team == "A" && !player?.isSpymaster!! && !wb.hasBeenClicked()) {
                    SocketConnection.socket.emit(
                        "wordButton",
                        wb.text.toString(),
                        player?.nickname,
                        roomName
                    )
                } else if (gamePhase === OnlinePhase.TEAM_B && player?.team == "B" && !player?.isSpymaster!! && !wb.hasBeenClicked()) {
                    SocketConnection.socket.emit(
                        "wordButton",
                        wb.text.toString(),
                        player?.nickname,
                        roomName
                    )
                }
            }
        }

        closeChat?.setOnClickListener {
            chatWindow?.visibility = View.GONE
            constraintLayout?.visibility = View.VISIBLE
        }

        sendChat?.setOnClickListener {
            val message = chatEdit?.text.toString()

            if (message != "") {
                SocketConnection.socket.emit(
                    "chat",
                    player?.nickname,
                    player?.team,
                    message.trim(),
                    roomName
                )
                chatEdit?.setText("")
            }
        }

        ttsAll?.setOnClickListener {
            var message = ""

            for (wb in wordButtons) {
                message += wb?.text.toString() + "\n"
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        ttsA?.setOnClickListener {
            var message = ""

            if (player?.isSpymaster == true && gamePhase != OnlinePhase.START) {
                for (wb in wordButtons) {
                    if (teamAWords.contains(wb?.text.toString())) {
                        message += wb?.text.toString() + "\n"
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (teamAWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true) {
                        message += wb.text.toString() + "\n"
                    }
                }
            }

            if (message == "") {
                message = getString(R.string.none_found)
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        ttsB?.setOnClickListener {
            var message = ""

            if (player?.isSpymaster == true && gamePhase != OnlinePhase.START) {
                for (wb in wordButtons) {
                    if (teamBWords.contains(wb?.text.toString())) {
                        message += wb?.text.toString() + "\n"
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (teamBWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true) {
                        message += wb.text.toString() + "\n"
                    }
                }
            }

            if (message == "") {
                message = getString(R.string.none_found)
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        ttsNeutral?.setOnClickListener {
            var message = ""

            if (player?.isSpymaster == true && gamePhase != OnlinePhase.START) {
                for (wb in wordButtons) {
                    if (neutralWords.contains(wb?.text.toString())) {
                        message += wb?.text.toString() + "\n"
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (neutralWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true) {
                        message += wb.text.toString() + "\n"
                    }
                }
            }

            if (message == "") {
                message = getString(R.string.none_found)
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        ttsBomb?.setOnClickListener {
            var message = ""

            if (player?.isSpymaster == true && gamePhase != OnlinePhase.START) {
                for (wb in wordButtons) {
                    if (bombWords.contains(wb?.text.toString())) {
                        message += wb?.text.toString() + "\n"
                    }
                }
            } else {
                for (wb in wordButtons) {
                    if (bombWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true) {
                        message += wb.text.toString() + "\n"
                    }
                }
            }

            if (message == "") {
                message = getString(R.string.none_found)
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        ttsUnmodified?.setOnClickListener {
            var message = ""

            for (wb in wordButtons) {
                if (wb?.hasBeenClicked() == false) {
                    message += wb.text.toString() + "\n"
                }
            }

            if (message == "") {
                message = getString(R.string.none_found)
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        ttsClicked?.setOnClickListener {
            var message = ""

            for (wb in wordButtons) {
                if (wb?.hasBeenClicked() == true) {
                    message += wb.text.toString() + "\n"
                }
            }

            if (message == "") {
                message = getString(R.string.none_found)
            }

            ttsBox?.visibility = View.GONE

            windowOpen = false

            toggleWindow()

            speakOut(message)
        }

        closeTTS?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            ttsBox?.visibility = View.GONE
        }

        loadingText?.setOnLongClickListener {
            speakOut(loadingText?.text.toString())
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

        okButton?.setOnLongClickListener {
            speakOut(okButton?.text.toString())
            true
        }

        noButton?.setOnLongClickListener {
            speakOut(noButton?.text.toString())
            true
        }

        chooseTeamText?.setOnLongClickListener {
            speakOut(chooseTeamText?.text.toString())
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

        closeTeamsBox?.setOnLongClickListener {
            speakOut(closeTeamsBox?.text.toString())
            true
        }

        teamAHeader?.setOnLongClickListener {
            var message = ""

            message += "Team Ay members. \n"

            for (i in 0 until teamALinear?.childCount!!) {
                val member = teamALinear?.getChildAt(i) as MaterialTextView

                message += member.text.toString() + "\n"
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

                message += member.text.toString() + "\n"
            }

            if (message == "Team B members. \n") {
                message += getString(R.string.none_found)
            }

            speakOut(message)
            true
        }

        ttsAll?.setOnLongClickListener {
            speakOut(ttsAll?.text.toString())
            true
        }

        ttsA?.setOnLongClickListener {
            speakOut(ttsA?.text.toString())
            true
        }

        ttsB?.setOnLongClickListener {
            speakOut(ttsB?.text.toString())
            true
        }

        ttsNeutral?.setOnLongClickListener {
            speakOut(ttsNeutral?.text.toString())
            true
        }

        ttsBomb?.setOnLongClickListener {
            speakOut(ttsBomb?.text.toString())
            true
        }

        ttsUnmodified?.setOnLongClickListener {
            speakOut(ttsUnmodified?.text.toString())
            true
        }

        ttsClicked?.setOnLongClickListener {
            speakOut(ttsClicked?.text.toString())
            true
        }

        closeTTS?.setOnLongClickListener {
            speakOut(closeTTS?.text.toString())
            true
        }

        exitButton?.setOnLongClickListener {
            speakOut(exitButton?.text.toString())
            true
        }

        scoreLinear?.setOnLongClickListener {
            var message = getString(R.string.remaining_words_a, teamAWordCount).replace(" A ", " Ay ")

            message += "\n" + getString(R.string.remaining_words_b, teamBWordCount)

            speakOut(message)
            true
        }

        startGame?.setOnLongClickListener {
            speakOut(startGame?.text.toString())
            true
        }

        requestSpymaster?.setOnLongClickListener {
            speakOut(requestSpymaster?.text.toString())
            true
        }

        changeTeam?.setOnLongClickListener {
            speakOut(changeTeam?.text.toString())
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
            speakOut(ttsButton?.text.toString())
            true
        }

        viewTeams?.setOnLongClickListener {
            speakOut(viewTeams?.text.toString())
            true
        }

        chatButton?.setOnLongClickListener {
            speakOut(chatButton?.text.toString())
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

        for (wb in wordButtons) {
            wb?.setOnLongClickListener {
                speakOut(wb.text.toString())
                true
            }
        }

        closeChat?.setOnLongClickListener {
            speakOut(closeChat?.text.toString())
            true
        }

        sendChat?.setOnLongClickListener {
            speakOut(sendChat?.text.toString())
            true
        }

        SocketConnection.socket.on("startFail") { args ->
            runOnUiThread {
                val reason = args[0] as String
                windowOpen = true

                toggleWindow()

                messageText?.text = reason

                messageBox?.visibility = View.VISIBLE
                yesButton?.visibility = View.INVISIBLE
                okButton?.visibility = View.VISIBLE
                noButton?.visibility = View.INVISIBLE
            }
        }

        SocketConnection.socket.on("startSuccess") {
            runOnUiThread {
                startGame?.visibility = View.GONE
                changeTeam?.visibility = View.GONE

                gamePhase = if (startingTeam == "A") {
                    OnlinePhase.TEAM_A_SPY
                } else {
                    OnlinePhase.TEAM_B_SPY
                }

                if (player?.isSpymaster == true) {
                    turnAction?.setText(R.string.give_hint)
                } else {
                    turnAction?.setText(R.string.end_turn)
                }

                if (player?.isSpymaster == true && player?.team == "A" && gamePhase === OnlinePhase.TEAM_A_SPY) {
                    editHint?.visibility = View.VISIBLE
                    hintNumber?.visibility = View.VISIBLE
                    turnAction?.visibility = View.VISIBLE
                }

                if (player?.isSpymaster == true && player?.team == "B" && gamePhase === OnlinePhase.TEAM_B_SPY) {
                    editHint?.visibility = View.VISIBLE
                    hintNumber?.visibility = View.VISIBLE
                    turnAction?.visibility = View.VISIBLE
                }

                updateSpinner()
                updateWordColours()
            }
        }

        SocketConnection.socket.on("hostQuit") {
            val i = Intent(this, MainMenu::class.java)
            i.putExtra("onlineClose", "hostQuit")
            startActivity(i)
        }

        SocketConnection.socket.on("playerQuit") { args ->
            val usernameQuit = args[0] as String

            for (p in teamAUsers) {
                if (p.nickname == usernameQuit) {
                    teamAUsers.remove(p)
                    break
                }
            }

            for (p in teamBUsers) {
                if (p.nickname == usernameQuit) {
                    teamBUsers.remove(p)
                    break
                }
            }

            runOnUiThread {
                updateTeamsBox()
            }
        }

        SocketConnection.socket.on("spymasterQuitA") {
            if (gamePhase == OnlinePhase.START) {
                teamASpymaster = null

                runOnUiThread {
                    if (player?.team == "A") {
                        requestSpymaster?.visibility = View.VISIBLE
                    }
                }
            } else {
                val i = Intent(this, MainMenu::class.java)
                i.putExtra("onlineClose", "spymasterQuit")
                startActivity(i)
            }
        }

        SocketConnection.socket.on("spymasterQuitB") {
            if (gamePhase == OnlinePhase.START) {
                teamBSpymaster = null

                runOnUiThread {
                    if (player?.team == "B") {
                        requestSpymaster?.visibility = View.VISIBLE
                    }
                }
            } else {
                val i = Intent(this, MainMenu::class.java)
                i.putExtra("onlineClose", "spymasterQuit")
                startActivity(i)
            }
        }

        SocketConnection.socket.on("gameDetails") { args ->
            try {
                allWords = jsonArrayToStrings(args[0] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            try {
                teamAUsers = jsonArrayToPlayers(args[1] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            try {
                teamBUsers = jsonArrayToPlayers(args[2] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            teamASpymaster = if (args[3] != null) {
                Player((args[3] as String))
            } else {
                null
            }

            teamBSpymaster = if (args[4] != null) {
                Player((args[4] as String))
            } else {
                null
            }

            try {
                bombWords = jsonArrayToStrings(args[5] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            try {
                neutralWords = jsonArrayToStrings(args[6] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            try {
                teamAWords = jsonArrayToStrings(args[7] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            try {
                teamBWords = jsonArrayToStrings(args[8] as JSONArray)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            startingTeam = args[9] as String

            runOnUiThread {
                for (i in wordButtons.indices) {
                    wordButtons[i]?.text = allWords[i]
                }

                if (startingTeam == "A") {
                    teamAColour = preferences.getInt("teamA", -16773377)

                    outline?.setBackgroundColor(teamAColour)

                    teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                } else {
                    teamBColour = preferences.getInt("teamB", -65536)

                    outline?.setBackgroundColor(teamBColour)

                    teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                }

                teamAWordCount = teamAWords.size
                teamBWordCount = teamBWords.size
                teamACount?.text = teamAWordCount.toString()
                teamBCount?.text = teamBWordCount.toString()
                loadingText?.visibility = View.GONE
                chooseTeamBox?.visibility = View.VISIBLE
            }
        }

        SocketConnection.socket.on("teamASpymaster") { args ->
            val newUser = args[0] as String

            for (p in teamAUsers) {
                if (p.nickname == newUser) {
                    p.isSpymaster = true
                    teamASpymaster = p
                }
            }

            runOnUiThread {
                if (player?.nickname == newUser) {
                    player?.isSpymaster = true
                    changeTeam?.visibility = View.GONE
                }

                if (player?.team == "A") {
                    requestSpymaster?.visibility = View.GONE
                }

                updateTeamsBox()
            }
        }

        SocketConnection.socket.on("teamBSpymaster") { args ->
            val newUser = args[0] as String

            for (p in teamBUsers) {
                if (p.nickname == newUser) {
                    p.isSpymaster = true
                    teamBSpymaster = p
                }
            }

            runOnUiThread {
                if (player?.nickname == newUser) {
                    player?.isSpymaster = true
                    changeTeam?.visibility = View.GONE
                }

                if (player?.team == "B") {
                    requestSpymaster?.visibility = View.GONE
                }

                updateTeamsBox()
            }
        }

        SocketConnection.socket.on("spymasterFail") {
            windowOpen = true

            toggleWindow()

            messageText?.text = getString(R.string.existing_spymaster)

            messageBox?.visibility = View.VISIBLE
            yesButton?.visibility = View.INVISIBLE
            okButton?.visibility = View.VISIBLE
            noButton?.visibility = View.INVISIBLE
        }

        SocketConnection.socket.on("teamChange") { args ->
            val playerName = args[0] as String
            val team = args[1] as String
            val newPlayer = Player(playerName, team)

            for (p in teamAUsers) {
                if (p.nickname == playerName) {
                    teamAUsers.remove(p)
                    break
                }
            }

            for (p in teamBUsers) {
                if (p.nickname == playerName) {
                    teamBUsers.remove(p)
                    break
                }
            }

            if (team == "A") {
                teamAUsers.add(newPlayer)
            } else {
                teamBUsers.add(newPlayer)
            }

            if (newPlayer.nickname == player?.nickname) {
                player?.team = team
            }

            if (team == "A" && teamASpymaster != null) {
                requestSpymaster?.visibility = View.GONE
            } else if (team == "B" && teamBSpymaster != null) {
                requestSpymaster?.visibility = View.GONE
            }

            updateTeamsBox()
        }

        SocketConnection.socket.on("wordButton") { args ->
            runOnUiThread {
                val word = args[0] as String
                val user = args[1] as String
                var typeClicked: String? = null
                var index = 0

                wordCounter++

                for (i in wordButtons.indices) {
                    if (wordButtons[i]?.text.toString() == word) {
                        index = i
                        break
                    }
                }

                val buttonClicked = wordButtons[index]

                for (s in teamAWords) {
                    if (s == word) {
                        typeClicked = "A"
                        break
                    }
                }

                if (typeClicked == null) {
                    for (s in teamBWords) {
                        if (s == word) {
                            typeClicked = "B"
                            break
                        }
                    }
                }

                if (typeClicked == null) {
                    for (s in neutralWords) {
                        if (s == word) {
                            typeClicked = "Neutral"
                            break
                        }
                    }
                }

                if (typeClicked == null) {
                    for (s in bombWords) {
                        if (s == word) {
                            typeClicked = "Bomb"
                            break
                        }
                    }
                }

                when (typeClicked) {
                    "A" -> {
                        buttonClicked?.setHasBeenClicked(true)
                        if (gamePhase === OnlinePhase.TEAM_B) {
                            gamePhase = OnlinePhase.TEAM_A_SPY
                            hintText?.visibility = View.GONE

                            if (player?.isSpymaster == true && player?.team == "A") {
                                hintText?.visibility = View.GONE
                                editHint?.visibility = View.VISIBLE
                                hintNumber?.visibility = View.VISIBLE
                                turnAction?.visibility = View.VISIBLE
                            }
                        }

                        teamAWordCount--
                        teamACount?.text = teamAWordCount.toString()

                        updateWordColours()
                    }

                    "B" -> {
                        buttonClicked?.setHasBeenClicked(true)
                        if (gamePhase === OnlinePhase.TEAM_A) {
                            gamePhase = OnlinePhase.TEAM_B_SPY
                            hintText?.visibility = View.GONE

                            if (player?.isSpymaster == true && player?.team == "B") {
                                hintText?.visibility = View.GONE
                                editHint?.visibility = View.VISIBLE
                                hintNumber?.visibility = View.VISIBLE
                                turnAction?.visibility = View.VISIBLE
                            }
                        }

                        teamBWordCount--
                        teamBCount?.text = teamBWordCount.toString()

                        updateWordColours()
                    }

                    "Neutral" -> {
                        buttonClicked?.setHasBeenClicked(true)

                        if (gamePhase === OnlinePhase.TEAM_A) {
                            gamePhase = OnlinePhase.TEAM_B_SPY
                            hintText?.visibility = View.GONE

                            if (player?.isSpymaster == true && player?.team == "B") {
                                editHint?.visibility = View.VISIBLE
                                hintNumber?.visibility = View.VISIBLE
                                turnAction?.visibility = View.VISIBLE
                            }
                        } else {
                            gamePhase = OnlinePhase.TEAM_A_SPY
                            hintText?.visibility = View.GONE

                            if (player?.isSpymaster == true && player?.team == "A") {
                                editHint?.visibility = View.VISIBLE
                                hintNumber?.visibility = View.VISIBLE
                                turnAction?.visibility = View.VISIBLE
                            }
                        }

                        updateWordColours()
                    }

                    "Bomb" -> {
                        buttonClicked?.setHasBeenClicked(true)

                        gamePhase = if (gamePhase === OnlinePhase.TEAM_A) {
                            OnlinePhase.TEAM_B_WIN
                        } else {
                            OnlinePhase.TEAM_A_WIN
                        }

                        updateWordColours()
                    }

                    null -> {
                        val i = Intent(applicationContext, MainMenu::class.java)
                        i.putExtra("onlineClose", "Unknown error occurred")
                        startActivity(i)
                    }
                }

                teamAColour = preferences.getInt("teamA", -16773377)
                teamBColour = preferences.getInt("teamB", -65536)


                if (gamePhase === OnlinePhase.TEAM_A) {
                    val newWord = MaterialTextView(this)

                    newWord.text = getString(R.string.chat_word, user, word)
                    newWord.setTextColor(teamAColour)
                    newWord.textSize = 30f

                    chatLinear?.addView(newWord)
                } else {
                    val newWord = MaterialTextView(this)

                    newWord.text = getString(R.string.chat_word, user, word)
                    newWord.setTextColor(teamBColour)
                    newWord.textSize = 30f

                    chatLinear?.addView(newWord)
                }

                if (wordCounter == maxTurns) {
                    if (player?.isHost == true) {
                        SocketConnection.socket.emit("endTurn", roomName)
                    }
                }
            }
        }

        SocketConnection.socket.on("endTurn") {
            gamePhase = if (gamePhase === OnlinePhase.TEAM_A) {
                OnlinePhase.TEAM_B_SPY
            } else {
                OnlinePhase.TEAM_A_SPY
            }

            runOnUiThread {
                hintText?.visibility = View.GONE
                
                if (player?.isSpymaster == true && player?.team == "A" && gamePhase === OnlinePhase.TEAM_A_SPY) {
                    editHint?.visibility = View.VISIBLE
                    hintNumber?.visibility = View.VISIBLE
                    turnAction?.visibility = View.VISIBLE
                } else if (!player?.isSpymaster!! && player?.team == "A" && gamePhase === OnlinePhase.TEAM_B_SPY) {
                    turnAction?.visibility = View.GONE
                }

                if (player?.isSpymaster == true && player?.team == "B" && gamePhase === OnlinePhase.TEAM_B_SPY) {
                    editHint?.visibility = View.VISIBLE
                    hintNumber?.visibility = View.VISIBLE
                    turnAction?.visibility = View.VISIBLE
                } else if (!player?.isSpymaster!! && player?.team == "B" && gamePhase === OnlinePhase.TEAM_A_SPY) {
                    turnAction?.visibility = View.GONE
                }

                if (gamePhase == OnlinePhase.TEAM_A_SPY) {
                    teamAColour = preferences.getInt("teamA", -16773377)

                    outline?.setBackgroundColor(teamAColour)

                    teamACount?.paintFlags = teamACount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                    teamBCount?.paintFlags = teamBCount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                } else {
                    teamBColour = preferences.getInt("teamB", -65536)

                    outline?.setBackgroundColor(teamBColour)

                    teamBCount?.paintFlags = teamBCount?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)!!
                    teamACount?.paintFlags = teamACount?.paintFlags?.and(Paint.UNDERLINE_TEXT_FLAG.inv())!!
                }

                updateSpinner()
            }
        }

        SocketConnection.socket.on("hint") { args ->
            runOnUiThread {
                val hint = args[0] as String
                val temp = hint.split(": ")
                val num = temp[1]

                wordCounter = 0

                maxTurns = if (num == "Infinite") {
                    24
                } else {
                    num.toInt() + 1
                }

                teamAColour = preferences.getInt("teamA", -16773377)
                teamBColour = preferences.getInt("teamB", -65536)


                if (gamePhase === OnlinePhase.TEAM_A_SPY) {
                    gamePhase = OnlinePhase.TEAM_A

                    val newHint = MaterialTextView(this)

                    newHint.text = getString(R.string.chat_hint, hint)
                    newHint.setTextColor(teamAColour)
                    newHint.textSize = 30f

                    chatLinear?.addView(newHint)
                } else {
                    gamePhase = OnlinePhase.TEAM_B

                    val newHint = MaterialTextView(this)

                    newHint.text = getString(R.string.chat_hint, hint)
                    newHint.setTextColor(teamBColour)
                    newHint.textSize = 30f

                    chatLinear?.addView(newHint)
                }

                hintText?.text = hint
                hintText?.visibility = View.VISIBLE

                if (player?.isSpymaster == true) {
                    editHint?.visibility = View.GONE
                    hintNumber?.visibility = View.GONE
                    turnAction?.visibility = View.GONE
                } else {
                    if (gamePhase === OnlinePhase.TEAM_A && player?.team == "A") {
                        turnAction?.visibility = View.VISIBLE
                    } else if (gamePhase === OnlinePhase.TEAM_B && player?.team == "B") {
                        turnAction?.visibility = View.VISIBLE
                    }
                }
            }
        }

        SocketConnection.socket.on("teamAChat") { args ->
            val usernameChat = args[0] as String
            val message = args[1] as String

            teamAColour = preferences.getInt("teamA", -16773377)
            menuTextColour = preferences.getInt("menuTextColour", -1)

            val newMessage = MaterialTextView(this)

            val usernameColour = "<font color=$teamAColour>$usernameChat</font>"
            val messageColour = "<font color=$menuTextColour>$message</font>"

            newMessage.text = HtmlCompat.fromHtml("$usernameColour: $messageColour", HtmlCompat.FROM_HTML_MODE_LEGACY)
            newMessage.textSize = 32f

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.bottomMargin = 8

            newMessage.layoutParams = params

            runOnUiThread {
                chatLinear?.addView(newMessage)
            }
        }

        SocketConnection.socket.on("teamBChat") { args ->
            val usernameChat = args[0] as String
            val message = args[1] as String

            teamBColour = preferences.getInt("teamB", -65536)
            menuTextColour = preferences.getInt("menuTextColour", -1)

            val newMessage = MaterialTextView(this)

            val usernameColour = "<font color=$teamBColour>$usernameChat</font>"
            val messageColour = "<font color=$menuTextColour>$message</font>"

            newMessage.text = HtmlCompat.fromHtml("$usernameColour: $messageColour", HtmlCompat.FROM_HTML_MODE_LEGACY)
            newMessage.textSize = 32f

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.bottomMargin = 8

            newMessage.layoutParams = params

            runOnUiThread {
                chatLinear?.addView(newMessage)
            }
        }

        updateColours()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    override fun onBackPressed() {
        exitButton?.performClick()
    }

    private fun updateTeamsBox() {
        val preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)

        menuTextColour = preferences.getInt("menuTextColour", -1)

        teamALinear?.removeAllViews()
        teamBLinear?.removeAllViews()

        for (p in teamAUsers) {
            val newPlayer = MaterialTextView(this)

            if (p.isSpymaster) {
                newPlayer.text = getString(R.string.spymaster_name, p.nickname)
            } else {
                newPlayer.text = p.nickname
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.gravity = Gravity.CENTER

            newPlayer.layoutParams = params

            newPlayer.setTextColor(menuTextColour)
            newPlayer.textSize = 30f
            teamALinear?.addView(newPlayer)
        }

        for (p in teamBUsers) {
            val newPlayer = MaterialTextView(this)

            if (p.isSpymaster) {
                newPlayer.text = getString(R.string.spymaster_name, p.nickname)
            } else {
                newPlayer.text = p.nickname
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.gravity = Gravity.CENTER

            newPlayer.layoutParams = params

            newPlayer.setTextColor(menuTextColour)
            newPlayer.textSize = 30f
            teamBLinear?.addView(newPlayer)
        }
    }

    private fun toggleWindow() {
        exitButton?.isEnabled = !windowOpen
        startGame?.isEnabled = !windowOpen
        requestSpymaster?.isEnabled = !windowOpen
        changeTeam?.isEnabled = !windowOpen
        editHint?.isEnabled = !windowOpen
        hintNumber?.isEnabled = !windowOpen
        turnAction?.isEnabled = !windowOpen
        ttsButton?.isEnabled = !windowOpen
        viewTeams?.isEnabled = !windowOpen
        chatButton?.isEnabled = !windowOpen
        gameOpToggleButton?.isEnabled = !windowOpen
        closeChat?.isEnabled = !windowOpen
        chatEdit?.isEnabled = !windowOpen
        sendChat?.isEnabled = !windowOpen

        for (wb in wordButtons) {
            wb?.isEnabled = !windowOpen
        }
    }

    @Throws(JSONException::class)
    private fun jsonArrayToPlayers(jsonArray: JSONArray?): ArrayList<Player> {
        val players = ArrayList<Player>()

        if (jsonArray != null) {
            val len = jsonArray.length()
            for (i in 0 until len) {
                players.add(Player(jsonArray.getString(i)))
            }
        }

        return players
    }

    @Throws(JSONException::class)
    private fun jsonArrayToStrings(jsonArray: JSONArray?): ArrayList<String> {
        val words = ArrayList<String>()

        if (jsonArray != null) {
            val len = jsonArray.length()
            for (i in 0 until len) {
                words.add(jsonArray.getString(i))
            }
        }

        return words
    }

    private fun speakOut(message: String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun updateSpinner() {
        val spinnerArray: MutableList<String> = ArrayList()

        if (gamePhase == OnlinePhase.TEAM_A_SPY) {
            for (i in 1 until teamAWordCount + 1) {
                spinnerArray.add(i.toString())
            }
        } else {
            for (i in 1 until teamBWordCount + 1) {
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

    private fun updateColours() {
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
        teamAButton?.setBackgroundColor(teamAColour)
        teamAHeader?.setTextColor(teamAColour)
        ttsA?.setBackgroundColor(teamAColour)

        teamBCount?.setTextColor(teamBColour)
        teamBButton?.setBackgroundColor(teamBColour)
        teamBHeader?.setTextColor(teamBColour)
        ttsB?.setBackgroundColor(teamBColour)

        ttsBomb?.setBackgroundColor(bombColour)

        ttsNeutral?.setBackgroundColor(neutralColour)

        ttsUnmodified?.setBackgroundColor(unmodifiedColour)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        chatWindow?.setBackgroundColor(applicationBackgroundColour)
        messageBox?.setBackgroundColor(applicationBackgroundColour)
        ttsBox?.setBackgroundColor(applicationBackgroundColour)
        loadingText?.setBackgroundColor(applicationBackgroundColour)
        chooseTeamBox?.setBackgroundColor(applicationBackgroundColour)
        viewTeamsBox?.setBackgroundColor(applicationBackgroundColour)
        gameOperations?.setBackgroundColor(applicationBackgroundColour)

        exitButton?.setBackgroundColor(menuButtonsColour)
        startGame?.setBackgroundColor(menuButtonsColour)
        requestSpymaster?.setBackgroundColor(menuButtonsColour)
        changeTeam?.setBackgroundColor(menuButtonsColour)
        turnAction?.setBackgroundColor(menuButtonsColour)
        ttsButton?.setBackgroundColor(menuButtonsColour)
        viewTeams?.setBackgroundColor(menuButtonsColour)
        chatButton?.setBackgroundColor(menuButtonsColour)
        gameOpToggleButton?.setBackgroundColor(menuButtonsColour)
        closeChat?.setBackgroundColor(menuButtonsColour)
        sendChat?.setBackgroundColor(menuButtonsColour)
        closeTeamsBox?.setBackgroundColor(menuButtonsColour)
        closeTTS?.setBackgroundColor(menuButtonsColour)
        ttsAll?.setBackgroundColor(menuButtonsColour)
        ttsClicked?.setBackgroundColor(menuButtonsColour)
        yesButton?.setBackgroundColor(menuButtonsColour)
        okButton?.setBackgroundColor(menuButtonsColour)
        noButton?.setBackgroundColor(menuButtonsColour)

        for (wb in wordButtons) {
            wb?.setBackgroundColor(menuButtonsColour)
        }

        loadingText?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        yesButton?.setTextColor(menuTextColour)
        okButton?.setTextColor(menuTextColour)
        noButton?.setTextColor(menuTextColour)
        chooseTeamText?.setTextColor(menuTextColour)
        teamAButton?.setTextColor(menuTextColour)
        teamBButton?.setTextColor(menuTextColour)
        closeTeamsBox?.setTextColor(menuTextColour)
        ttsAll?.setTextColor(menuTextColour)
        ttsA?.setTextColor(menuTextColour)
        ttsB?.setTextColor(menuTextColour)
        ttsNeutral?.setTextColor(menuTextColour)
        ttsBomb?.setTextColor(menuTextColour)
        ttsUnmodified?.setTextColor(menuTextColour)
        ttsClicked?.setTextColor(menuTextColour)
        closeTTS?.setTextColor(menuTextColour)
        exitButton?.setTextColor(menuTextColour)
        teamCounterLine?.setTextColor(menuTextColour)
        startGame?.setTextColor(menuTextColour)
        requestSpymaster?.setTextColor(menuTextColour)
        changeTeam?.setTextColor(menuTextColour)
        editHint?.setTextColor(menuTextColour)
        turnAction?.setTextColor(menuTextColour)
        ttsButton?.setTextColor(menuTextColour)
        viewTeams?.setTextColor(menuTextColour)
        chatButton?.setTextColor(menuTextColour)
        gameOpToggleButton?.setTextColor(menuTextColour)
        closeChat?.setTextColor(menuTextColour)
        chatEdit?.setTextColor(menuTextColour)
        sendChat?.setTextColor(menuTextColour)

        for (wb in wordButtons) {
            wb?.setTextColor(menuTextColour)
        }

        updateWordColours()
    }

    private fun updateWordColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        teamAColour = preferences.getInt("teamA", -16773377)
        teamBColour = preferences.getInt("teamB", -65536)
        bombColour = preferences.getInt("bomb", -14342875)
        neutralColour = preferences.getInt("neutral", -11731092)
        unmodifiedColour = preferences.getInt("unmodified", -3684409)

        if (player?.isSpymaster == true) {
            for (wb in wordButtons) {
                when {
                    teamAWords.contains(wb?.text.toString()) -> {
                        wb?.setBackgroundColor(teamAColour)
                    }

                    teamBWords.contains(wb?.text.toString()) -> {
                        wb?.setBackgroundColor(teamBColour)
                    }

                    neutralWords.contains(wb?.text.toString()) -> {
                        wb?.setBackgroundColor(neutralColour)
                    }

                    bombWords.contains(wb?.text.toString()) -> {
                        wb?.setBackgroundColor(bombColour)
                    }
                }
            }
        } else {
            for (wb in wordButtons) {
                when {
                    teamAWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true -> {
                        wb.setBackgroundColor(teamAColour)
                    }

                    teamBWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true -> {
                        wb.setBackgroundColor(teamBColour)
                    }

                    neutralWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true -> {
                        wb.setBackgroundColor(neutralColour)
                    }

                    bombWords.contains(wb?.text.toString()) && wb?.hasBeenClicked() == true -> {
                        wb.setBackgroundColor(bombColour)
                    }
                }
            }
        }

        for (wb in wordButtons) {
            if (wb?.hasBeenClicked() == true) {
                wb.background.alpha = 128
            }
        }
    }
}
