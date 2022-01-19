package uk.ac.swansea.codenames

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.util.*

class OnlineGame : AppCompatActivity(), TextToSpeech.OnInitListener {

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
    private var ttsUnclicked: MaterialButton? = null
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

    private var wordButtons = arrayOfNulls<WordButton>(25)

    private var player: Player? = null

    private var roomName: String? = null

    private var windowOpen = true

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
        ttsUnclicked = findViewById(R.id.ttsUnclicked)
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

        wordButtons = arrayOf(squareOne, squareTwo, squareThree, squareFour, squareFive,
            squareSix, squareSeven, squareEight, squareNine, squareTen, squareEleven,
            squareTwelve, squareThirteen, squareFourteen, squareFifteen, squareSixteen,
            squareSeventeen, squareEighteen, squareNineteen, squareTwenty, squareTwentyOne,
            squareTwentyTwo, squareTwentyThree, squareTwentyFour, squareTwentyFive)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val username = if (preferences.getString("username", "").isNullOrEmpty()) {
            UUID.randomUUID().toString().replace("-", "").take(10)
        } else {
            preferences.getString("username", "").toString()
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

            SocketConnection.socket.emit("chooseTeam", player?.nickname, "A", roomName)
        }

        teamBButton?.setOnClickListener {
            windowOpen = false

            toggleWindow()

            SocketConnection.socket.emit("chooseTeam", player?.nickname, "B", roomName)
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
            SocketConnection.socket.emit("requestSpymaster", player?.nickname, roomName, player?.team)
        }

        changeTeam?.setOnClickListener {
            if (player?.team == "A") {
                SocketConnection.socket.emit("chooseTeam", player?.nickname, "B", roomName)
            } else {
                SocketConnection.socket.emit("chooseTeam", player?.nickname, "A", roomName)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech!!.language = Locale.UK
        }
    }

    override fun onBackPressed() {
        exitButton?.performClick()
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

    private fun speakOut(message: String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun updateColours() {

    }
}