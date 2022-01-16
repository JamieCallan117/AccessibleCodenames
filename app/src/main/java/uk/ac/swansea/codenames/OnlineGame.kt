package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import org.json.JSONArray
import org.json.JSONException
import android.text.Html
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.gridlayout.widget.GridLayout
import java.util.ArrayList
import kotlin.Throws

//Anything that runs something like toggleMessageBox() might need to be in a runOnUiThread
class OnlineGame : AppCompatActivity() {
    private var player: Player? = null
    private var roomName: String? = null
    private var teamAUsers = ArrayList<Player>()
    private var teamBUsers = ArrayList<Player>()
    private var teamASpymaster: Player? = null
    private var teamBSpymaster: Player? = null
    private var allWords = ArrayList<String>()
    private var bombWords = ArrayList<String>()
    private var neutralWords = ArrayList<String>()
    private var teamAWords = ArrayList<String>()
    private var teamBWords = ArrayList<String>()
    private var startingTeam = "A"
    private var teamAWordCount = 0
    private var teamBWordCount = 0
    private var teamAColour = -16773377
    private var teamBColour = -65536
    private var bombColour = -14342875
    private var neutralColour = -908
    private var unmodifiedColour = -3684409
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var teamsBoxOpen = false
    private var chatWindowOpen = false
    private val wordButtonsEnabled = false
    private var validHint = false
    private var gamePhase = OnlinePhase.START
    private var constraintLayout: ConstraintLayout? = null
    private var wordButtons = arrayOfNulls<WordButton>(25)
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
    private var exitButton: Button? = null
    private var startGame: Button? = null
    private var requestSpymaster: Button? = null
    private var changeTeamButton: Button? = null
    private var turnAction: Button? = null
    private var textToSpeechButton: Button? = null
    private var viewTeams: Button? = null
    private var teamAButton: Button? = null
    private var teamBButton: Button? = null
    private var chatButton: Button? = null
    private var closeTeamsBox: Button? = null
    private var sendChatButton: Button? = null
    private var closeChatButton: Button? = null
    private var gameOperationsScroll: ScrollView? = null
    private var chatScroll: ScrollView? = null
    private var gameOperationsLinear: LinearLayout? = null
    private var chatLinear: LinearLayout? = null
    private var teamALinear: LinearLayout? = null
    private var teamBLinear: LinearLayout? = null
    private var teamACount: TextView? = null
    private var teamBCount: TextView? = null
    private var teamAHeader: TextView? = null
    private var teamBHeader: TextView? = null
    private var teamCounterLine: TextView? = null
    private var hintText: TextView? = null
    private var messageText: TextView? = null
    private var loadingText: TextView? = null
    private var editHint: EditText? = null
    private var chatEdit: EditText? = null
    private var hintNumber: Spinner? = null
    private var chooseTeamBox: GridLayout? = null
    private var viewTeamsBox: GridLayout? = null
    private var loadingBox: GridLayout? = null
    private var teamSeparator: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_game)

        constraintLayout = findViewById(R.id.constraintLayout)
        exitButton = findViewById(R.id.exitButton)
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
        startGame = findViewById(R.id.startGame)
        requestSpymaster = findViewById(R.id.requestSpymaster)
        changeTeamButton = findViewById(R.id.changeTeamButton)
        turnAction = findViewById(R.id.turnAction)
        textToSpeechButton = findViewById(R.id.textToSpeechButton)
        teamAButton = findViewById(R.id.teamAButton)
        teamBButton = findViewById(R.id.teamBButton)
        viewTeams = findViewById(R.id.viewTeams)
        chatButton = findViewById(R.id.chatButton)
        gameOperationsScroll = findViewById(R.id.gameOperationsScroll)
        gameOperationsLinear = findViewById(R.id.gameOperationsLinear)
        hintText = findViewById(R.id.hintText)
        editHint = findViewById(R.id.editHint)
        hintNumber = findViewById(R.id.hintNumber)
        teamACount = findViewById(R.id.teamACount)
        teamBCount = findViewById(R.id.teamBCount)
        teamCounterLine = findViewById(R.id.teamCounterLine)
        chooseTeamBox = findViewById(R.id.chooseTeamBox)
        viewTeamsBox = findViewById(R.id.viewTeamsBox)
        teamAHeader = findViewById(R.id.teamAHeader)
        teamBHeader = findViewById(R.id.teamBHeader)
        teamALinear = findViewById(R.id.teamALinear)
        teamBLinear = findViewById(R.id.teamBLinear)
        closeTeamsBox = findViewById(R.id.closeTeamsBox)
        teamSeparator = findViewById(R.id.teamSeparator)
        closeChatButton = findViewById(R.id.closeChatButton)
        sendChatButton = findViewById(R.id.sendChatButton)
        chatScroll = findViewById(R.id.chatScroll)
        chatLinear = findViewById(R.id.chatLinear)
        chatEdit = findViewById(R.id.chatEdit)
        loadingBox = findViewById(R.id.loadingBox)
        messageText = findViewById(R.id.messageText)
        loadingText = findViewById(R.id.loadingText)

        wordButtons = arrayOf(squareOne, squareTwo, squareThree, squareFour, squareFive,
                squareSix, squareSeven, squareEight, squareNine, squareTen, squareEleven,
                squareTwelve, squareThirteen, squareFourteen, squareFifteen, squareSixteen,
                squareSeventeen, squareEighteen, squareNineteen, squareTwenty, squareTwentyOne,
                squareTwentyTwo, squareTwentyThree, squareTwentyFour, squareTwentyFive)


        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val username = preferences.getString("username", "")

        player = username?.let { Player(it) }

        player?.isHost = intent.getBooleanExtra("isHost", false)
        roomName = intent.getStringExtra("roomName")

        val handler = Handler()

        handler.postDelayed({ SocketConnection.socket.emit("getGameDetails", roomName) }, 2000)

        startGame?.isEnabled = false
        requestSpymaster?.isEnabled = false
        changeTeamButton?.isEnabled = false
        chatButton?.isEnabled = false
        viewTeams?.isEnabled = false

        hintText?.visibility = View.GONE
        editHint?.visibility = View.GONE
        hintNumber?.visibility = View.GONE
        turnAction?.visibility = View.GONE
        textToSpeechButton?.visibility = View.GONE

        if (!player?.isHost!!) {
            gameOperationsLinear?.removeView(startGame)
        }

        //toggleWordButtons();

        startGame?.setOnClickListener { SocketConnection.socket.emit("startGame") }

        teamAButton?.setOnClickListener {
            SocketConnection.socket.emit("chooseTeam", player?.nickname, "A", roomName)
            constraintLayout?.removeView(chooseTeamBox)

            startGame?.isEnabled = true
            requestSpymaster?.isEnabled = true
            changeTeamButton?.isEnabled = true
            chatButton?.isEnabled = true
            viewTeams?.isEnabled = true
        }

        teamBButton?.setOnClickListener { v: View? ->
            SocketConnection.socket.emit("chooseTeam", player?.nickname, "B", roomName)
            constraintLayout?.removeView(chooseTeamBox)

            startGame?.isEnabled = true
            requestSpymaster?.isEnabled = true
            changeTeamButton?.isEnabled = true
            chatButton?.isEnabled = true
            viewTeams?.isEnabled = true
        }

        requestSpymaster?.setOnClickListener { SocketConnection.socket.emit("requestSpymaster", player?.nickname, roomName, player?.team) }

        changeTeamButton?.setOnClickListener {
            for (p in teamAUsers) {
                if (p.nickname == player?.nickname) {
                    SocketConnection.socket.emit("chooseTeam", player?.nickname, "B", roomName)
                    break
                }
            }

            for (p in teamBUsers) {
                if (p.nickname == player?.nickname) {
                    SocketConnection.socket.emit("chooseTeam", player?.nickname, "A", roomName)
                    break
                }
            }
        }

        closeTeamsBox?.setOnClickListener { toggleTeamsBox() }
        viewTeams?.setOnClickListener { toggleTeamsBox() }
        chatButton?.setOnClickListener { toggleChatWindow() }
        closeChatButton?.setOnClickListener { toggleChatWindow() }

        sendChatButton?.setOnClickListener {
            val message = chatEdit?.text.toString()

            if (message != "") {
                SocketConnection.socket.emit("chat", player?.nickname, player?.team, message, roomName)
                chatEdit?.setText("")
            }
        }

        turnAction?.setOnClickListener {
            if (gamePhase === OnlinePhase.TEAM_A_SPY || gamePhase === OnlinePhase.TEAM_B_SPY) {
                validHint = true

                if (editHint?.text.toString() == "") {
                    validHint = false
                } else if (!editHint?.text.toString().matches("[A-Za-z]+".toRegex())) {
                    validHint = false
                }

                //Maybe change to the hint can't be a substring of any of the game words
                for (s in allWords) {
                    if (editHint?.text.toString().equals(s, ignoreCase = true)) {
                        validHint = false
                    }
                }

                if (validHint) {
                    val hint = editHint?.text.toString() + ": " + hintNumber?.selectedItem.toString()
                    editHint?.setText("")
                    SocketConnection.socket.emit("hint", hint, roomName)
                }
            } else {
                SocketConnection.socket.emit("endTurn", roomName)
            }
        }

        for (wb in wordButtons) {
            wb?.setOnClickListener {
                if (gamePhase === OnlinePhase.TEAM_A && player?.team == "A" && !player?.isSpymaster!! && !wb.hasBeenClicked()) {
                    SocketConnection.socket.emit("wordButton", wb.text.toString(), player?.nickname, roomName)
                } else if (gamePhase === OnlinePhase.TEAM_B && player?.team == "B" && !player?.isSpymaster!! && !wb.hasBeenClicked()) {
                    SocketConnection.socket.emit("wordButton", wb.text.toString(), player?.nickname, roomName)
                }
            }
        }

        exitButton?.setOnClickListener {
            SocketConnection.socket.emit("leaveRoom")

            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
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

            for (i in wordButtons.indices) {
                wordButtons[i]?.text = allWords[i]
            }

            runOnUiThread {
                teamAWordCount = teamAWords.size
                teamBWordCount = teamBWords.size
                teamACount?.text = teamAWordCount.toString()
                teamBCount?.text = teamBWordCount.toString()
                loadingBox?.visibility = View.GONE
                chooseTeamBox?.visibility = View.VISIBLE
            }
        }

        SocketConnection.socket.on("startSuccess") {
            runOnUiThread {
                startGame?.visibility = View.GONE
                changeTeamButton?.visibility = View.GONE
                textToSpeechButton?.visibility = View.VISIBLE

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
                updateColours()
            }
        }

        SocketConnection.socket.on("startFail") {
            runOnUiThread {
                if (player?.isHost!!) {
                    toggleMessageBox()
                }
            }
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

            runOnUiThread {
                if (teamsBoxOpen) {
                    teamsBoxOpen = false
                    toggleTeamsBox()
                }
            }
        }

        SocketConnection.socket.on("teamASpymaster") { args ->
            val newUser = args[0] as String

            teamASpymaster = Player(newUser, "A")

            for (p in teamAUsers) {
                if (p.nickname == newUser) {
                    p.isSpymaster = true
                }
            }

            runOnUiThread {
                if (player?.nickname == newUser) {
                    player?.isSpymaster = true
                    changeTeamButton?.visibility = View.GONE
                }

                if (player?.team == "A") {
                    requestSpymaster?.visibility = View.GONE
                }

                if (teamsBoxOpen) {
                    teamsBoxOpen = false
                    toggleTeamsBox()
                }
            }
        }

        SocketConnection.socket.on("teamBSpymaster") { args ->
            val newUser = args[0] as String

            teamBSpymaster = Player(newUser, "B")

            for (p in teamBUsers) {
                if (p.nickname == newUser) {
                    p.isSpymaster = true
                }
            }

            runOnUiThread {
                if (player?.nickname == newUser) {
                    player?.isSpymaster = true
                    changeTeamButton?.visibility = View.GONE
                }

                if (player?.team == "B") {
                    requestSpymaster?.visibility = View.GONE
                }

                if (teamsBoxOpen) {
                    teamsBoxOpen = false
                    toggleTeamsBox()
                }
            }
        }

        SocketConnection.socket.on("spymasterFail") {
            toggleMessageBox() //"The Spymaster for your team has already been selected"
        }

        SocketConnection.socket.on("hint") { args ->
            runOnUiThread {
                val hint = args[0] as String

                teamAColour = preferences.getInt("teamA", -16773377)
                teamBColour = preferences.getInt("teamB", -65536)


                if (gamePhase === OnlinePhase.TEAM_A_SPY) {
                    gamePhase = OnlinePhase.TEAM_A

                    val newHint = TextView(applicationContext)

                    newHint.text = "Hint: $hint"
                    newHint.setTextColor(teamAColour)
                    newHint.textSize = 18f

                    chatLinear?.addView(newHint)
                } else {
                    gamePhase = OnlinePhase.TEAM_B

                    val newHint = TextView(applicationContext)

                    newHint.text = "Hint: $hint"
                    newHint.setTextColor(teamBColour)
                    newHint.textSize = 18f

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

        SocketConnection.socket.on("endTurn") {
            gamePhase = if (gamePhase === OnlinePhase.TEAM_A) {
                OnlinePhase.TEAM_B_SPY
            } else {
                OnlinePhase.TEAM_A_SPY
            }

            runOnUiThread {
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

                updateSpinner()
            }
        }

        SocketConnection.socket.on("wordButton") { args ->
            runOnUiThread {
                val word = args[0] as String
                val user = args[1] as String
                var typeClicked: String? = null
                var index = 0

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

                        updateColours()
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

                        updateColours()
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

                        updateColours()
                    }

                    "Bomb" -> {
                        buttonClicked?.setHasBeenClicked(true)

                        gamePhase = if (gamePhase === OnlinePhase.TEAM_A) {
                            OnlinePhase.TEAM_B_WIN
                        } else {
                            OnlinePhase.TEAM_A_WIN
                        }

                        updateColours()
                    }
                }
            }
        }

        SocketConnection.socket.on("teamAChat") { args ->
            val usernameChat = args[0] as String
            val message = args[1] as String

            teamAColour = preferences.getInt("teamA", -16773377)
            menuTextColour = preferences.getInt("menuTextColour", -1)

            val usernameColour = "<font color=$teamAColour>$usernameChat</font>"
            val messageColour = "<font color=$menuTextColour>$message</font>"
            val newMessage = TextView(applicationContext)

            newMessage.text = Html.fromHtml("$usernameColour: $messageColour")
            newMessage.textSize = 18f

            runOnUiThread { chatLinear?.addView(newMessage) }
        }

        SocketConnection.socket.on("teamBChat") { args ->
            val usernameChat = args[0] as String
            val message = args[1] as String

            teamBColour = preferences.getInt("teamB", -65536)
            menuTextColour = preferences.getInt("menuTextColour", -1)

            val usernameColour = "<font color=$teamBColour>$usernameChat</font>"
            val messageColour = "<font color=$menuTextColour>$message</font>"
            val newMessage = TextView(applicationContext)

            newMessage.text = Html.fromHtml("$usernameColour: $messageColour")
            newMessage.textSize = 18f

            runOnUiThread { chatLinear?.addView(newMessage) }
        }

        SocketConnection.socket.on("spymasterQuitA") {
            teamASpymaster = null

            if (player?.team == "A") {
                runOnUiThread { requestSpymaster?.visibility = View.VISIBLE }
            }
        }

        SocketConnection.socket.on("spymasterQuitB") {
            teamBSpymaster = null

            if (player?.team == "B") {
                runOnUiThread { requestSpymaster?.visibility = View.VISIBLE }
            }
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
                if (teamsBoxOpen) {
                    teamsBoxOpen = false
                    toggleTeamsBox()
                }
            }
        }

        SocketConnection.socket.on("hostQuit") { //Maybe change to pop up box saying host quit, return to main menu with button
            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        updateColours()
    }

    override fun onBackPressed() {
        exitButton?.performClick()
    }

    private fun toggleChatWindow() {
        if (chatWindowOpen) {
            closeChatButton?.visibility = View.GONE
            chatScroll?.visibility = View.GONE
            chatEdit?.visibility = View.GONE
            sendChatButton?.visibility = View.GONE
            exitButton?.visibility = View.VISIBLE
            chatButton?.visibility = View.VISIBLE
            hintText?.visibility = View.VISIBLE
            viewTeams?.visibility = View.VISIBLE
            textToSpeechButton?.visibility = View.VISIBLE
            teamACount?.visibility = View.VISIBLE
            teamCounterLine?.visibility = View.VISIBLE
            teamBCount?.visibility = View.VISIBLE

            for (wb in wordButtons) {
                wb?.visibility = View.VISIBLE
            }

            if (gamePhase === OnlinePhase.START && player?.isHost == true) {
                startGame?.visibility = View.VISIBLE
            }

            if (gamePhase === OnlinePhase.START) {
                changeTeamButton?.visibility = View.VISIBLE
            }

            if (player?.team == "A" && teamASpymaster == null) {
                requestSpymaster?.visibility = View.VISIBLE
            } else if (player?.team == "B" && teamBSpymaster == null) {
                requestSpymaster?.visibility = View.VISIBLE
            }

            if (gamePhase === OnlinePhase.TEAM_A_SPY && player?.isSpymaster == true && player?.team == "A") {
                editHint?.visibility = View.VISIBLE
                hintNumber?.visibility = View.VISIBLE
            } else if (gamePhase === OnlinePhase.TEAM_B_SPY && player?.isSpymaster == true && player?.team == "B") {
                editHint?.visibility = View.VISIBLE
                hintNumber?.visibility = View.VISIBLE
            }

            if (gamePhase === OnlinePhase.TEAM_A && !player?.isSpymaster!! && player?.team == "A") {
                turnAction?.visibility = View.VISIBLE
            } else if (gamePhase === OnlinePhase.TEAM_B && !player?.isSpymaster!! && player?.team == "B") {
                turnAction?.visibility = View.VISIBLE
            }

            chatWindowOpen = false
        } else {
            chatScroll?.fullScroll(View.FOCUS_DOWN)
            closeChatButton?.visibility = View.VISIBLE
            chatScroll?.visibility = View.VISIBLE
            chatEdit?.visibility = View.VISIBLE
            sendChatButton?.visibility = View.VISIBLE
            exitButton?.visibility = View.GONE
            chatButton?.visibility = View.GONE
            hintText?.visibility = View.GONE
            viewTeams?.visibility = View.GONE
            textToSpeechButton?.visibility = View.GONE
            teamACount?.visibility = View.GONE
            teamCounterLine?.visibility = View.GONE
            teamBCount?.visibility = View.GONE

            for (wb in wordButtons) {
                wb?.visibility = View.GONE
            }

            if (gamePhase === OnlinePhase.START && player?.isHost == true) {
                startGame?.visibility = View.GONE
            }

            if (gamePhase === OnlinePhase.START) {
                changeTeamButton?.visibility = View.GONE
            }

            if (player?.team == "A" && teamASpymaster == null) {
                requestSpymaster?.visibility = View.GONE
            } else if (player?.team == "B" && teamBSpymaster == null) {
                requestSpymaster?.visibility = View.GONE
            }

            if (gamePhase === OnlinePhase.TEAM_A_SPY && player?.isSpymaster == true && player?.team == "A") {
                editHint?.visibility = View.GONE
                hintNumber?.visibility = View.GONE
            } else if (gamePhase === OnlinePhase.TEAM_B_SPY && player?.isSpymaster == true && player?.team == "B") {
                editHint?.visibility = View.GONE
                hintNumber?.visibility = View.GONE
            }

            if (gamePhase === OnlinePhase.TEAM_A && !player?.isSpymaster!! && player?.team == "A") {
                turnAction?.visibility = View.GONE
            } else if (gamePhase === OnlinePhase.TEAM_B && !player?.isSpymaster!! && player?.team == "B") {
                turnAction?.visibility = View.GONE
            }

            chatWindowOpen = true
        }
    }

    //Either take in a string for the displayed message or numbered switch case for each potential message
    private fun toggleMessageBox() {}

    //    private void toggleWordButtons() {
    //        for (WordButton wb : wordButtons) {
    //            wb.setVisibility(View.GONE);
    //        }
    //
    //        wordButtonsEnabled = !wordButtonsEnabled;
    //    }

    private fun toggleTeamsBox() {
        if (!teamsBoxOpen) {
            teamALinear?.removeAllViews()
            teamBLinear?.removeAllViews()
            exitButton?.isEnabled = false
            startGame?.isEnabled = false
            requestSpymaster?.isEnabled = false
            changeTeamButton?.isEnabled = false
            chatButton?.isEnabled = false
            viewTeams?.isEnabled = false
            hintText?.isEnabled = false
            editHint?.isEnabled = false
            hintNumber?.isEnabled = false
            turnAction?.isEnabled = false
            textToSpeechButton?.isEnabled = false
            viewTeamsBox?.visibility = View.VISIBLE
            teamsBoxOpen = true

            val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            val spymasterSymbol = String(Character.toChars(0x1F441))

            menuTextColour = preferences.getInt("menuTextColour", -1)

            for (p in teamAUsers) {
                val newPlayer = TextView(this)

                if (p.isSpymaster) {
                    newPlayer.text = p.nickname + " " + spymasterSymbol
                } else {
                    newPlayer.text = p.nickname
                }

                newPlayer.setTextColor(menuTextColour)
                newPlayer.textSize = 30f
                teamALinear?.addView(newPlayer)
            }

            for (p in teamBUsers) {
                val newPlayer = TextView(this)

                if (p.isSpymaster) {
                    newPlayer.text = p.nickname + " " + spymasterSymbol
                } else {
                    newPlayer.text = p.nickname
                }

                newPlayer.setTextColor(menuTextColour)
                newPlayer.textSize = 30f
                teamBLinear?.addView(newPlayer)
            }
        } else {
            viewTeamsBox?.visibility = View.INVISIBLE
            teamsBoxOpen = false
            exitButton?.isEnabled = true
            startGame?.isEnabled = true
            requestSpymaster?.isEnabled = true
            changeTeamButton?.isEnabled = true
            chatButton?.isEnabled = true
            viewTeams?.isEnabled = true
            hintText?.isEnabled = true
            editHint?.isEnabled = true
            hintNumber?.isEnabled = true
            turnAction?.isEnabled = true
            textToSpeechButton?.isEnabled = true
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

    private fun updateSpinner() {
        val spinnerArray: MutableList<String> = ArrayList()

        spinnerArray.add("∞")

        if (gamePhase === OnlinePhase.TEAM_A_SPY) {
            for (i in 0 until teamAWords.size + 1) {
                spinnerArray.add(i.toString())
            }
        } else {
            for (i in 0 until teamBWords.size + 1) {
                spinnerArray.add(i.toString())
            }
        }

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
        neutralColour = preferences.getInt("neutral", -908)
        unmodifiedColour = preferences.getInt("unmodified", -3684409)
        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)
        
        teamACount?.setTextColor(teamAColour)
        
        if (player?.isSpymaster == true) {
            for (wb in wordButtons) {
                if (teamAWords.contains(wb?.text.toString())) {
                    wb?.setBackgroundColor(teamAColour)
                }
            }
        }
        
        for (wb in wordButtons) {
            if (wb?.hasBeenClicked() == true && teamAWords.contains(wb.text.toString())) {
                wb.setBackgroundColor(teamAColour)
            }
        }
        
        teamBCount?.setTextColor(teamBColour)
        
        if (player?.isSpymaster == true) {
            for (wb in wordButtons) {
                if (teamBWords.contains(wb?.text.toString())) {
                    wb?.setBackgroundColor(teamBColour)
                }
            }
        }
        
        for (wb in wordButtons) {
            if (wb?.hasBeenClicked() == true && teamBWords.contains(wb.text.toString())) {
                wb.setBackgroundColor(teamBColour)
            }
        }
        
        if (player?.isSpymaster == true) {
            for (wb in wordButtons) {
                if (neutralWords.contains(wb?.text.toString())) {
                    wb?.setBackgroundColor(neutralColour)
                }
            }
        }
        
        for (wb in wordButtons) {
            if (wb?.hasBeenClicked() == true && neutralWords.contains(wb.text.toString())) {
                wb.setBackgroundColor(neutralColour)
            }
        }
        
        if (player?.isSpymaster == true) {
            for (wb in wordButtons) {
                if (bombWords.contains(wb?.text.toString())) {
                    wb?.setBackgroundColor(bombColour)
                }
            }
        }
        
        for (wb in wordButtons) {
            if (wb?.hasBeenClicked() == true && bombWords.contains(wb.text.toString())) {
                wb.setBackgroundColor(bombColour)
            }
        }
        
        if (!player?.isSpymaster!!) {
            for (wb in wordButtons) {
                if (!wb?.hasBeenClicked()!!) {
                    wb.setBackgroundColor(unmodifiedColour)
                }
            }
        }
        
        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        chooseTeamBox?.setBackgroundColor(applicationBackgroundColour)
        viewTeamsBox?.setBackgroundColor(applicationBackgroundColour)
        loadingBox?.setBackgroundColor(applicationBackgroundColour)

        exitButton?.setBackgroundColor(menuButtonsColour)
        startGame?.setBackgroundColor(menuButtonsColour)
        requestSpymaster?.setBackgroundColor(menuButtonsColour)
        changeTeamButton?.setBackgroundColor(menuButtonsColour)
        teamAButton?.setBackgroundColor(menuButtonsColour)
        teamBButton?.setBackgroundColor(menuButtonsColour)
        closeTeamsBox?.setBackgroundColor(menuButtonsColour)
        turnAction?.setBackgroundColor(menuButtonsColour)
        textToSpeechButton?.setBackgroundColor(menuButtonsColour)
        viewTeams?.setBackgroundColor(menuButtonsColour)
        chatButton?.setBackgroundColor(menuButtonsColour)
        closeChatButton?.setBackgroundColor(menuButtonsColour)
        sendChatButton?.setBackgroundColor(menuButtonsColour)

        teamCounterLine?.setTextColor(menuTextColour)
        teamAHeader?.setTextColor(menuTextColour)
        teamBHeader?.setTextColor(menuTextColour)
        exitButton?.setTextColor(menuTextColour)
        startGame?.setTextColor(menuTextColour)
        requestSpymaster?.setTextColor(menuTextColour)
        changeTeamButton?.setTextColor(menuTextColour)
        teamAButton?.setTextColor(menuTextColour)
        teamBButton?.setTextColor(menuTextColour)
        closeTeamsBox?.setTextColor(menuTextColour)
        turnAction?.setTextColor(menuTextColour)
        textToSpeechButton?.setTextColor(menuTextColour)
        viewTeams?.setTextColor(menuTextColour)
        chatButton?.setTextColor(menuTextColour)
        closeChatButton?.setTextColor(menuTextColour)
        sendChatButton?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        loadingText?.setTextColor(menuTextColour)
        teamSeparator?.setBackgroundColor(menuTextColour)
        
        for (wb in wordButtons) {
            if (wb?.hasBeenClicked() == true) {
                wb.background.alpha = 128
            }
        }
    }
}