package uk.ac.swansea.codenames

import android.content.Context
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
import io.socket.client.Socket
import io.socket.emitter.Emitter

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
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences!!.edit()

        editor.putString("username", usernameEdit?.text.toString())
        editor.apply()

        val i = Intent(view.context, MainMenu::class.java)
        startActivity(i)
    }

    fun createGame(view: View) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences!!.edit()

        editor.putString("username", usernameEdit?.text.toString())
        editor.apply()

        val i = Intent(view.context, CreateGame::class.java)
        i.putExtra("hasCustomSettings", false)
        startActivity(i)
    }

    fun joinGame(view: View) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences!!.edit()

        editor.putString("username", usernameEdit?.text.toString())
        editor.apply()

        val i = Intent(view.context, JoinGame::class.java)
        startActivity(i)
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