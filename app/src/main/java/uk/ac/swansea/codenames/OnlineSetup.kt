package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.widget.EditText
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.util.*

class OnlineSetup : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: GridLayout? = null
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

        textToSpeech = TextToSpeech(this, this)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val username = preferences.getString("username", "")

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

            Handler(Looper.getMainLooper()).postDelayed({
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

        backButton?.setOnClickListener {
            val editor = preferences!!.edit()

            editor.putString("username", usernameEdit?.text.toString())
            editor.apply()

            val i = Intent(this, MainMenu::class.java)
            startActivity(i)
        }

        createGameButton?.setOnClickListener {
            val editor = preferences!!.edit()

            editor.putString("username", usernameEdit?.text.toString())
            editor.apply()

            val i = Intent(this, CreateGame::class.java)
            i.putExtra("hasCustomSettings", false)
            startActivity(i)
        }

        joinGameButton?.setOnClickListener {
            val editor = preferences!!.edit()

            editor.putString("username", usernameEdit?.text.toString())
            editor.apply()

            val i = Intent(this, JoinGame::class.java)
            startActivity(i)
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
            speakOut(usernameText?.text.toString())
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
        if (!connectingBoxOpen) {
            backButton?.performClick()
        }
    }

    private fun speakOut(message : String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

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