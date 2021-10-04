package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.widget.EditText
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import java.io.File
import java.io.IOException

class OnlineSetup : AppCompatActivity() {
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: GridLayout? = null
    private var gameSetupTitle: TextView? = null
    private var gameSetupSubtitle: TextView? = null
    private var messageText: TextView? = null
    private var usernameText: TextView? = null
    private var usernameEdit: EditText? = null
    private var backButton: Button? = null
    private var joinGameButton: Button? = null
    private var createGameButton: Button? = null
    private var okButton: Button? = null
    private var preferencesFile = "preferences.txt"
    private var preferences = arrayOfNulls<String>(14)
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var connectingBoxOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_setup)

        constraintLayout = findViewById(R.id.constraintLayout)
        gameSetupTitle = findViewById(R.id.gameSetupTitle)
        gameSetupSubtitle = findViewById(R.id.gameSetupSubtitle)
        usernameText = findViewById(R.id.usernameText)
        usernameEdit = findViewById(R.id.usernameEdit)
        backButton = findViewById(R.id.backButton)
        joinGameButton = findViewById(R.id.joinGameButton)
        createGameButton = findViewById(R.id.createGameButton)
        messageBox = findViewById(R.id.messageBox)
        messageText = findViewById(R.id.messageText)
        okButton = findViewById(R.id.okButton)

        var tempPref = ""

        try {
            val input = assets.open("preferences")
            val size = input.available()
            val buffer = ByteArray(size)

            input.read(buffer)
            input.close()

            tempPref = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val username = preferences[8]

        usernameEdit?.setText(username)

        SocketConnection.socket.disconnect()
        SocketConnection.socket.connect()

        if (!SocketConnection.socket.connected()) {
            messageBox?.visibility = View.VISIBLE
            messageText?.setText(R.string.connecting_server)
            okButton?.visibility = View.INVISIBLE
            backButton?.isEnabled = false
            joinGameButton?.isEnabled = false
            createGameButton?.isEnabled = false
            usernameEdit?.isEnabled = false
            
            val handler = Handler()
            
            handler.postDelayed({
                messageBox?.visibility = View.INVISIBLE
                
                if (!SocketConnection.socket.connected()) {
                    messageText?.setText(R.string.unable_server)
                    messageBox?.visibility = View.VISIBLE
                    okButton?.visibility = View.VISIBLE
                } else {
                    connectingBoxOpen = false
                    backButton?.isEnabled = true
                    joinGameButton?.isEnabled = true
                    createGameButton?.isEnabled = true
                    usernameEdit?.isEnabled = true
                }
            }, 3000)
        }
        
        okButton?.setOnClickListener {
            val i = Intent(baseContext, MainMenu::class.java)
            startActivity(i)
        }

        updateColours()
    }

    override fun onBackPressed() {
        if (!connectingBoxOpen) {
            backButton(window.decorView)
        }
    }

    fun backButton(view: View) {
        updatePreferencesFile()

        val i = Intent(view.context, MainMenu::class.java)
        startActivity(i)
    }

    fun createGame(view: View) {
        updatePreferencesFile()

        val i = Intent(view.context, CreateGame::class.java)
        i.putExtra("hasCustomSettings", false)
        startActivity(i)
    }

    fun joinGame(view: View) {
        updatePreferencesFile()

        val i = Intent(view.context, JoinGame::class.java)
        startActivity(i)
    }

    private fun updatePreferencesFile() {
        val preferences = File(preferencesFile).useLines { it.toList() }

        val teamAColour = preferences[0].toInt()
        val teamBColour = preferences[1].toInt()
        val bombColour = preferences[2].toInt()
        val neutralColour = preferences[3].toInt()
        val unmodifiedColour = preferences[4].toInt()
        var username = preferences[8]
        val musicVolume = preferences[9]
        val soundFxVolume = preferences[10]
        val vibration = preferences[11]
        val contrast = preferences[12]
        val textToSpeech = preferences[13]

        applicationBackgroundColour = preferences[5].toInt()
        menuButtonsColour = preferences[6].toInt()
        menuTextColour = preferences[7].toInt()

        username = username.trimEnd()

        val prefStr = "$teamAColour\n$teamBColour\n$bombColour\n$neutralColour\n$unmodifiedColour" +
                "\n$applicationBackgroundColour\n$menuButtonsColour\n$menuTextColour\n$username" +
                "\n$musicVolume\n$soundFxVolume\n$vibration\n$contrast\n$textToSpeech"

        File(preferencesFile).writeText(prefStr)
    }

    fun updateColours() {
        val regex = "[^A-Za-z0-9 ]".toRegex()

        var applicationBackgroundColourStr = preferences[5]?.let { regex.replace(it, "") }
        var menuButtonsColourStr = preferences[6]?.let { regex.replace(it, "") }
        var menuTextColourStr = preferences[7]?.let { regex.replace(it, "") }

        applicationBackgroundColourStr = "-$applicationBackgroundColourStr"
        menuButtonsColourStr = "-$menuButtonsColourStr"
        menuTextColourStr = "-$menuTextColourStr"

        applicationBackgroundColour = applicationBackgroundColourStr.toInt()
        menuButtonsColour = menuButtonsColourStr.toInt()!!
        menuTextColour = menuTextColourStr.toInt()!!

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        messageBox?.setBackgroundColor(applicationBackgroundColour)
        backButton?.setBackgroundColor(menuButtonsColour)
        joinGameButton?.setBackgroundColor(menuButtonsColour)
        createGameButton?.setBackgroundColor(menuButtonsColour)
        okButton?.setBackgroundColor(menuButtonsColour)
        gameSetupTitle?.setTextColor(menuTextColour)
        gameSetupSubtitle?.setTextColor(menuTextColour)
        usernameText?.setTextColor(menuTextColour)
        usernameEdit?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        joinGameButton?.setTextColor(menuTextColour)
        createGameButton?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        okButton?.setTextColor(menuTextColour)
    }
}