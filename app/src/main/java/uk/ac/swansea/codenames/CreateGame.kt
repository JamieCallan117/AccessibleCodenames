package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import org.json.JSONArray
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import java.io.File
import java.io.IOException
import java.util.*

class CreateGame : AppCompatActivity() {
    private var constraintLayout: ConstraintLayout? = null
    private var customWordsLinear: LinearLayout? = null
    private var privateSwitch: SwitchCompat? = null
    private var roomNameEdit: EditText? = null
    private var passwordEdit: EditText? = null
    private val customWordTexts = arrayOfNulls<TextView>(10)
    private var bombSquaresText: TextView? = null
    private var neutralSquaresText: TextView? = null
    private var teamASquaresText: TextView? = null
    private var teamBSquaresText: TextView? = null
    private var startingTeamText: TextView? = null
    private var customWordsText: TextView? = null
    private var createGameTitle: TextView? = null
    private var backButton: Button? = null
    private var gameOptionsButton: Button? = null
    private var createButton: Button? = null
    private var validGame = true
    private var preferencesFile = "preferences.txt"
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_game)

        constraintLayout = findViewById(R.id.constraintLayout)
        customWordsLinear = findViewById(R.id.customWordsLinear)
        privateSwitch = findViewById(R.id.privateSwitch)
        roomNameEdit = findViewById(R.id.roomNameEdit)
        passwordEdit = findViewById(R.id.passwordEdit)
        bombSquaresText = findViewById(R.id.bombSquaresText)
        neutralSquaresText = findViewById(R.id.neutralSquaresText)
        teamASquaresText = findViewById(R.id.teamASquaresText)
        teamBSquaresText = findViewById(R.id.teamBSquaresText)
        startingTeamText = findViewById(R.id.startingTeamText)
        customWordsText = findViewById(R.id.customWordsText)
        createGameTitle = findViewById(R.id.createGameTitle)
        backButton = findViewById(R.id.backButton)
        gameOptionsButton = findViewById(R.id.gameOptionsButton)
        createButton = findViewById(R.id.createButton)

        if (intent.getBooleanExtra("hasCustomSettings", false)) {
            customWordsText?.visibility = View.VISIBLE

            bombSquaresText?.text = getString(R.string.bomb_squares) + " " + intent.getIntExtra("bombSquares", 1).toString()
            neutralSquaresText?.text = getString(R.string.neutral_squares) + " " + intent.getIntExtra("neutralSquares", 7).toString()
            teamASquaresText?.text = getString(R.string.team_a_squares) + " " + intent.getIntExtra("teamASquares", 9).toString()
            teamBSquaresText?.text = getString(R.string.team_b_squares) + " " + intent.getIntExtra("teamBSquares", 8).toString()

            val startingTeam: String = if (intent.getIntExtra("startingTeam", 1) == 1) {
                "A"
            } else {
                "B"
            }

            startingTeamText?.text = getString(R.string.starting_team) + " " + startingTeam

            if (intent.getStringArrayListExtra("customWords") != null) {
                var counter = 0

                while (counter < intent.getStringArrayListExtra("customWords")!!.size) {
                    val newCustomWord = TextView(this)

                    newCustomWord.text = intent.getStringArrayListExtra("customWords")!![counter]

                    val typeface = ResourcesCompat.getFont(this, R.font.general_font)

                    newCustomWord.typeface = typeface
                    newCustomWord.textSize = 17f

                    customWordTexts[counter] = newCustomWord
                    customWordsLinear?.addView(newCustomWord)

                    counter++
                }
            }
        }

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, OnlineSetup::class.java)
            startActivity(i)
        }

        gameOptionsButton?.setOnClickListener {
            val i = Intent(applicationContext, GameOptions::class.java)

            i.putExtra("type", "online")

            if (intent.getBooleanExtra("hasCustomSettings", false)) {
                i.putExtra("hasCustomSettings", true)
                i.putExtra("bombSquares", intent.getIntExtra("bombSquares", 1))
                i.putExtra("neutralSquares", intent.getIntExtra("neutralSquares", 7))
                i.putExtra("teamASquares", intent.getIntExtra("teamASquares", 9))
                i.putExtra("teamBSquares", intent.getIntExtra("teamBSquares", 8))
                i.putExtra("startingTeam", intent.getIntExtra("startingTeam", 1))
                i.putStringArrayListExtra("customWords", intent.getStringArrayListExtra("customWords"))
            }

            startActivity(i)
        }

        createButton?.setOnClickListener {
            validGame = true

            val preferences = File(preferencesFile).useLines { it.toList() }

            val username = preferences[8]

            if (username == "") {
                toggleNicknameBox()
                validGame = false
            } else if (!username!!.matches("[A-Za-z0-9]+".toRegex())) {
                toggleNicknameBox()
                validGame = false
            }

            val roomName = roomNameEdit?.text.toString()

            if (roomName == "") {
                toggleMessageBox("Room name cannot be empty.", true)
                validGame = false
            } else if (!roomName.matches("[A-Za-z0-9]+".toRegex())) {
                toggleMessageBox("Room name must be alphanumerical.", true)
                validGame = false
            }

            val password = passwordEdit?.text.toString()

            if (privateSwitch?.isChecked == true) {
                if (password == "") {
                    toggleMessageBox("Please enter a password.", true)
                    validGame = false
                } else if (!password.matches("[A-Za-z0-9]+".toRegex())) {
                    toggleMessageBox("Password must be alphanumerical.", true)
                    validGame = false
                }
            }

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
            val gameWords = arrayOfNulls<String>(MAX_WORDS)
            val customWords = intent.getStringArrayListExtra("customWords")

            if (customWords != null) {
                for (i in customWords.indices) {
                    gameWords[i] = customWords[i]
                    defaultWordsNeeded--
                }
            }

            val list = ArrayList<Int?>()

            for (i in defaultWords.indices) {
                list.add(i)
            }

            list.shuffle()

            for (i in 0 until defaultWordsNeeded) {
                gameWords[i + (MAX_WORDS - defaultWordsNeeded)] = defaultWords[list[i]!!]
            }

            Collections.shuffle(Arrays.asList(*gameWords))

            list.clear()

            for (i in 0 until MAX_WORDS) {
                list.add(i)
            }

            list.shuffle()

            var counter = 0
            val bombSquaresCount = intent.getIntExtra("bombSquares", 1)
            val neutralSquaresCount = intent.getIntExtra("neutralSquares", 7)
            val teamASquaresCount = intent.getIntExtra("teamASquares", 9)
            val teamBSquaresCount = intent.getIntExtra("teamBSquares", 8)
            val bombWords = arrayOfNulls<String>(bombSquaresCount)
            val neutralWords = arrayOfNulls<String>(neutralSquaresCount)
            val teamAWords = arrayOfNulls<String>(teamASquaresCount)
            val teamBWords = arrayOfNulls<String>(teamBSquaresCount)

            for (i in 0 until bombSquaresCount) {
                bombWords[i] = gameWords[list[counter]!!]
                counter++
            }

            for (i in 0 until neutralSquaresCount) {
                neutralWords[i] = gameWords[list[counter]!!]
                counter++
            }

            for (i in 0 until teamASquaresCount) {
                teamAWords[i] = gameWords[list[counter]!!]
                counter++
            }

            for (i in 0 until teamBSquaresCount) {
                teamBWords[i] = gameWords[list[counter]!!]
                counter++
            }

            val startingTeam = intent.getIntExtra("startingTeam", 1)
            val jsonGameWords = JSONArray(listOf(*gameWords))
            val jsonBombWords = JSONArray(listOf(*bombWords))
            val jsonNeutralWords = JSONArray(listOf(*neutralWords))
            val jsonTeamAWords = JSONArray(listOf(*teamAWords))
            val jsonTeamBWords = JSONArray(listOf(*teamBWords))

            if (validGame) {
                SocketConnection.socket.emit("createRoom", username, roomName, password, jsonGameWords,
                        jsonBombWords, jsonNeutralWords, jsonTeamAWords, jsonTeamBWords, startingTeam)
            }

            backButton?.isEnabled = false
            gameOptionsButton?.isEnabled = false
            createButton?.isEnabled = false
            roomNameEdit?.isEnabled = false
            passwordEdit?.isEnabled = false
            privateSwitch?.isEnabled = false

            val handler = Handler()

            handler.postDelayed({
                if (validGame) {
                    val i = Intent(applicationContext, OnlineGame::class.java)
                    i.putExtra("username", username)
                    i.putExtra("roomName", roomName)
                    i.putExtra("isHost", true)
                    startActivity(i)
                } else {
                    backButton?.isEnabled = true
                    gameOptionsButton?.isEnabled = true
                    createButton?.isEnabled = true
                    roomNameEdit?.isEnabled = true
                    passwordEdit?.isEnabled = true
                    privateSwitch?.isEnabled = true
                }
            }, 3000)
        }

        privateSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordEdit?.visibility = View.VISIBLE
            } else {
                passwordEdit?.visibility = View.INVISIBLE
            }
            passwordEdit?.setText("")
        }

        SocketConnection.socket.on("createFail") { args ->
            toggleMessageBox(args[0] as String, false)
            validGame = false
        }

        updateColours()
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    private fun toggleMessageBox(message: String, visibleButton: Boolean) {

    }

    private fun toggleNicknameBox() {

    }

    private fun updateColours() {
        val colours = File(preferencesFile).useLines { it.toList() }

        applicationBackgroundColour = colours[5].toInt()
        menuButtonsColour = colours[6].toInt()
        menuTextColour = colours[7].toInt()

        constraintLayout!!.setBackgroundColor(applicationBackgroundColour)
        backButton!!.setBackgroundColor(menuButtonsColour)
        gameOptionsButton!!.setBackgroundColor(menuButtonsColour)
        createButton!!.setBackgroundColor(menuButtonsColour)
        createGameTitle!!.setTextColor(menuTextColour)
        bombSquaresText!!.setTextColor(menuTextColour)
        neutralSquaresText!!.setTextColor(menuTextColour)
        teamASquaresText!!.setTextColor(menuTextColour)
        teamBSquaresText!!.setTextColor(menuTextColour)
        startingTeamText!!.setTextColor(menuTextColour)
        customWordsText!!.setTextColor(menuTextColour)
        backButton!!.setTextColor(menuTextColour)
        gameOptionsButton!!.setTextColor(menuTextColour)
        createButton!!.setTextColor(menuTextColour)
        privateSwitch!!.setTextColor(menuTextColour)

        for (t in customWordTexts) {
            t?.setTextColor(menuTextColour)
        }
    }
}