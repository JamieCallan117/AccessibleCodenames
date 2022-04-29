package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.util.*

/**
 * Menu for choosing to create or join an online game.
 */
class OnlineSetup : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: ConstraintLayout? = null
    private var gameSetupTitle: MaterialTextView? = null
    private var gameSetupSubtitle: MaterialTextView? = null
    private var messageText: MaterialTextView? = null
    private var usernameText: MaterialTextView? = null
    private var usernameEdit: TextInputEditText? = null
    private var backButton: MaterialButton? = null
    private var joinGameButton: MaterialButton? = null
    private var createGameButton: MaterialButton? = null
    private var okButton: MaterialButton? = null
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var connectingBoxOpen = true
    private var textToSpeech: TextToSpeech? = null
    private var messageType = 0
    private var buttonClick: MediaPlayer? = null

    /**
     * Sets up layout and listeners etc.
     */
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

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        var username = preferences.getString("username", "")

        usernameEdit?.setText(username)

        SocketConnection.socket.disconnect()
        SocketConnection.socket.connect()

        toggleMessageBox()
        
        okButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            if (messageType == 0) {
                val i = Intent(baseContext, MainMenu::class.java)
                startActivity(i)
            } else if (messageType >= 1) {
                messageBox?.visibility = View.INVISIBLE

                connectingBoxOpen = false
                backButton?.isEnabled = true
                joinGameButton?.isEnabled = true
                createGameButton?.isEnabled = true
                usernameEdit?.isEnabled = true
            }
        }

        backButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            val editor = preferences!!.edit()

            editor.putString("username", usernameEdit?.text.toString().replace(" ", ""))
            editor.apply()

            val i = Intent(this, MainMenu::class.java)
            startActivity(i)
        }

        createGameButton?.setOnClickListener {
            buttonClick?.start()

            username = usernameEdit?.text.toString().replace(" ", "")

            if (username == "") {
                messageType = 1
                toggleMessageBox()
            } else if (!username!!.matches("[A-Za-z0-9]+".toRegex())) {
                messageType = 2
                toggleMessageBox()
            } else {
                textToSpeech?.stop()

                val editor = preferences!!.edit()

                editor.putString("username", usernameEdit?.text.toString())
                editor.apply()

                val i = Intent(this, CreateGame::class.java)
                i.putExtra("hasCustomSettings", false)
                startActivity(i)
            }
        }

        joinGameButton?.setOnClickListener {
            buttonClick?.start()

            username = usernameEdit?.text.toString().replace(" ", "")

            if (username == "") {
                messageType = 1
                toggleMessageBox()
            } else if (!username!!.matches("[A-Za-z0-9]+".toRegex())) {
                messageType = 2
                toggleMessageBox()
            } else {
                textToSpeech?.stop()

                val editor = preferences!!.edit()

                editor.putString("username", usernameEdit?.text.toString())
                editor.apply()

                val i = Intent(this, JoinGame::class.java)
                startActivity(i)
            }
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        gameSetupTitle?.setOnLongClickListener {
            speakOut(gameSetupTitle?.text.toString() + ", " + gameSetupSubtitle?.text.toString())
            true
        }

        gameSetupSubtitle?.setOnLongClickListener {
            speakOut(gameSetupTitle?.text.toString() + ", " + gameSetupSubtitle?.text.toString())
            true
        }

        joinGameButton?.setOnLongClickListener {
            speakOut(joinGameButton?.text.toString())
            true
        }

        createGameButton?.setOnLongClickListener {
            speakOut(createGameButton?.text.toString())
            true
        }

        usernameText?.setOnLongClickListener {
            speakOut(usernameText?.text.toString() + ", " + usernameEdit?.text.toString())
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

    /**
     * When app is reopened.
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
     * When device's back button is pressed.
     */
    override fun onBackPressed() {
        if (!connectingBoxOpen) {
            backButton?.performClick()
        }
    }

    /**
     * Opens message box depending on type.
     */
    private fun toggleMessageBox() {
        if (messageType == 0) {
            if (!SocketConnection.socket.connected()) {
                messageBox?.visibility = View.VISIBLE
                messageText?.setText(R.string.connecting_server)
                okButton?.visibility = View.INVISIBLE
                backButton?.isEnabled = false
                joinGameButton?.isEnabled = false
                createGameButton?.isEnabled = false
                usernameEdit?.isEnabled = false

                Handler(Looper.getMainLooper()).postDelayed({
                    messageBox?.visibility = View.INVISIBLE

                    if (!SocketConnection.socket.connected()) {
                        messageText?.setText(R.string.unable_server)
                        messageBox?.visibility = View.VISIBLE
                        okButton?.visibility = View.VISIBLE

                        speakOut(messageText?.text.toString())
                    } else {
                        connectingBoxOpen = false
                        backButton?.isEnabled = true
                        joinGameButton?.isEnabled = true
                        createGameButton?.isEnabled = true
                        usernameEdit?.isEnabled = true
                    }
                }, 3000)
            }
        } else {
            messageBox?.visibility = View.VISIBLE
            okButton?.visibility = View.VISIBLE

            connectingBoxOpen = true
            backButton?.isEnabled = false
            joinGameButton?.isEnabled = false
            createGameButton?.isEnabled = false
            usernameEdit?.isEnabled = false

            when (messageType) {
                1 -> messageText?.setText(R.string.no_username)
                2 ->messageText?.setText(R.string.alpha_username)
            }

            speakOut(messageText?.text.toString())
        }
    }

    /**
     * Reads aloud given text.
     */
    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Updates colours of elements in layout.
     */
    fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

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