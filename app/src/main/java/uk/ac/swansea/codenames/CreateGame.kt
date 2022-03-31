package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import org.json.JSONArray
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.io.IOException
import java.util.*

class CreateGame : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: ConstraintLayout? = null
    private var customWordsLinear: LinearLayout? = null
    private var privateSwitch: SwitchMaterial? = null
    private var roomNameEdit: TextInputEditText? = null
    private var passwordEdit: TextInputEditText? = null
    private val customWordTexts = arrayOfNulls<TextView>(10)
    private var bombSquaresText: MaterialTextView? = null
    private var neutralSquaresText: MaterialTextView? = null
    private var teamASquaresText: MaterialTextView? = null
    private var teamBSquaresText: MaterialTextView? = null
    private var startingTeamText: MaterialTextView? = null
    private var customWordsText: MaterialTextView? = null
    private var createGameTitle: MaterialTextView? = null
    private var messageText: MaterialTextView? = null
    private var okButton: MaterialButton? = null
    private var backButton: MaterialButton? = null
    private var gameOptionsButton: MaterialButton? = null
    private var createButton: MaterialButton? = null
    private var validGame = true
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var textToSpeech: TextToSpeech? = null
    private var messageBoxOpen = false
    private var buttonClick: MediaPlayer? = null

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
        messageBox = findViewById(R.id.messageBox)
        messageText = findViewById(R.id.messageText)
        okButton = findViewById(R.id.okButton)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

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
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, OnlineSetup::class.java)
            startActivity(i)
        }

        gameOptionsButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val i = Intent(applicationContext, GameOptions::class.java)

            i.putExtra("type", "online")

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

        createButton?.setOnClickListener {
            buttonClick?.start()

            validGame = true

            backButton?.isEnabled = false
            gameOptionsButton?.isEnabled = false
            createButton?.isEnabled = false
            roomNameEdit?.isEnabled = false
            passwordEdit?.isEnabled = false
            privateSwitch?.isEnabled = false

            val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

            val username = preferences.getString("username", "")

            val roomName = roomNameEdit?.text.toString()

            if (roomName == "") {
                toggleMessageBox("Room name cannot be empty.")
                validGame = false
            } else if (!roomName.matches("[A-Za-z0-9]+".toRegex())) {
                toggleMessageBox("Room name must be alphanumerical.")
                validGame = false
            }

            val password = passwordEdit?.text.toString()

            if (privateSwitch?.isChecked == true) {
                if (password == "") {
                    toggleMessageBox("Please enter a password.")
                    validGame = false
                } else if (!password.matches("[A-Za-z0-9]+".toRegex())) {
                    toggleMessageBox("Password must be alphanumerical.")
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
            val maxWords = 25
            var defaultWordsNeeded = maxWords
            val gameWords = arrayOfNulls<String>(maxWords)
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
                gameWords[i + (maxWords - defaultWordsNeeded)] = defaultWords[list[i]!!]
            }

            gameWords.shuffle()

            list.clear()

            for (i in 0 until maxWords) {
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

            val jsonGameWords = JSONArray(listOf(*gameWords))
            val jsonBombWords = JSONArray(listOf(*bombWords))
            val jsonNeutralWords = JSONArray(listOf(*neutralWords))
            val jsonTeamAWords = JSONArray(listOf(*teamAWords))
            val jsonTeamBWords = JSONArray(listOf(*teamBWords))

            if (validGame) {
                SocketConnection.socket.emit("createRoom", username, roomName, password, jsonGameWords,
                        jsonBombWords, jsonNeutralWords, jsonTeamAWords, jsonTeamBWords, startingTeam)
            }
            
            Handler(Looper.getMainLooper()).postDelayed({
                if (validGame) {
                    textToSpeech?.stop()

                    val i = Intent(applicationContext, OnlineGame::class.java)
                    i.putExtra("username", username)
                    i.putExtra("roomName", roomName)
                    i.putExtra("isHost", true)
                    startActivity(i)
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

        okButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()
            messageBox?.visibility = View.INVISIBLE
            messageBoxOpen = false

            backButton?.isEnabled = true
            gameOptionsButton?.isEnabled = true
            createButton?.isEnabled = true
            roomNameEdit?.isEnabled = true
            passwordEdit?.isEnabled = true
            privateSwitch?.isEnabled = true
        }

        SocketConnection.socket.on("createFail") { args ->
            toggleMessageBox(args[0] as String)
            validGame = false
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        createGameTitle?.setOnLongClickListener {
            speakOut(createGameTitle?.text.toString())
            true
        }

        gameOptionsButton?.setOnLongClickListener {
            speakOut(gameOptionsButton?.text.toString())
            true
        }

        createButton?.setOnLongClickListener {
            speakOut(createButton?.text.toString())
            true
        }

        privateSwitch?.setOnLongClickListener {
            speakOut(privateSwitch?.text.toString() + ", " + privateSwitch?.isChecked.toString())
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

        messageText?.setOnLongClickListener {
            speakOut(messageText?.text.toString())
            true
        }

        okButton?.setOnLongClickListener {
            speakOut(okButton?.text.toString())
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
        if (!messageBoxOpen) {
            backButton?.performClick()
        }
    }

    private fun toggleMessageBox(message: String) {
        messageBoxOpen = true

        speakOut(messageText?.text.toString())

        messageBox?.visibility = View.VISIBLE

        messageText?.text = message

        backButton?.isEnabled = false
        gameOptionsButton?.isEnabled = false
        createButton?.isEnabled = false
        roomNameEdit?.isEnabled = false
        passwordEdit?.isEnabled = false
        privateSwitch?.isEnabled = false
    }

    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

        constraintLayout!!.setBackgroundColor(applicationBackgroundColour)
        messageBox!!.setBackgroundColor(applicationBackgroundColour)

        backButton!!.setBackgroundColor(menuButtonsColour)
        gameOptionsButton!!.setBackgroundColor(menuButtonsColour)
        createButton!!.setBackgroundColor(menuButtonsColour)
        okButton!!.setBackgroundColor(menuButtonsColour)

        createGameTitle!!.setTextColor(menuTextColour)
        messageText!!.setTextColor(menuTextColour)
        bombSquaresText!!.setTextColor(menuTextColour)
        neutralSquaresText!!.setTextColor(menuTextColour)
        teamASquaresText!!.setTextColor(menuTextColour)
        teamBSquaresText!!.setTextColor(menuTextColour)
        startingTeamText!!.setTextColor(menuTextColour)
        customWordsText!!.setTextColor(menuTextColour)
        backButton!!.setTextColor(menuTextColour)
        gameOptionsButton!!.setTextColor(menuTextColour)
        createButton!!.setTextColor(menuTextColour)
        okButton!!.setTextColor(menuTextColour)
        privateSwitch!!.setTextColor(menuTextColour)

        for (t in customWordTexts) {
            t?.setTextColor(menuTextColour)
        }
    }
}