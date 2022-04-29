package uk.ac.swansea.codenames

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.LinearLayout
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import android.widget.Button
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.util.*

/**
 * Scene for joining online games.
 */
class JoinGame : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var constraintLayout: ConstraintLayout? = null
    private var messageBox: ConstraintLayout? = null
    private var scrollLinear: LinearLayout? = null
    private var backButton: MaterialButton? = null
    private var refreshButton: MaterialButton? = null
    private var joinPrivateButton: MaterialButton? = null
    private var okButton: MaterialButton? = null
    private var joinGameTitle: MaterialTextView? = null
    private var publicRoomText: MaterialTextView? = null
    private var privateRoomText: MaterialTextView? = null
    private var messageText: MaterialTextView? = null
    private var nicknameEdit: TextInputEditText? = null
    private var roomNameEdit: TextInputEditText? = null
    private var passwordEdit: TextInputEditText? = null
    private val publicJoinButtons = ArrayList<Button>()
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var username: String? = null
    private var validJoin = false
    private var messageBoxOpen = false
    private var textToSpeech: TextToSpeech? = null
    private var buttonClick: MediaPlayer? = null

    /**
     * Sets up layout and listeners etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_game)

        constraintLayout = findViewById(R.id.constraintLayout)
        messageBox = findViewById(R.id.messageBox)
        scrollLinear = findViewById(R.id.scrollLinear)
        backButton = findViewById(R.id.backButton)
        refreshButton = findViewById(R.id.refreshButton)
        joinGameTitle = findViewById(R.id.joinGameTitle)
        joinPrivateButton = findViewById(R.id.joinPrivateButton)
        publicRoomText = findViewById(R.id.publicRoomText)
        privateRoomText = findViewById(R.id.privateRoomText)
        nicknameEdit = findViewById(R.id.nicknameEdit)
        roomNameEdit = findViewById(R.id.roomNameEdit)
        passwordEdit = findViewById(R.id.passwordEdit)
        okButton = findViewById(R.id.okButton)
        messageText = findViewById(R.id.messageText)

        buttonClick = MediaPlayer.create(this, R.raw.buttonclick)

        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val soundFXVolume = preferences.getFloat("soundFXVolume", 0.5f)

        buttonClick?.setVolume(soundFXVolume, soundFXVolume)

        textToSpeech = TextToSpeech(this, this)

        username = preferences.getString("username", "")

        nicknameEdit?.setText(username)

        SocketConnection.socket.emit("getAllRooms")

        joinPrivateButton?.setOnClickListener {
            buttonClick?.start()

            validJoin = true

            SocketConnection.socket.close()
            SocketConnection.socket.open()

            SocketConnection.socket.emit("joinRoom", username, roomNameEdit?.text.toString(),
                    passwordEdit?.text.toString())

            backButton?.isEnabled = false
            refreshButton?.isEnabled = false
            roomNameEdit?.isEnabled = false
            passwordEdit?.isEnabled = false
            joinPrivateButton?.isEnabled = false

            for (b in publicJoinButtons) {
                b.isEnabled = false
            }

            Handler(Looper.getMainLooper()).postDelayed({
                if (validJoin) {
                    textToSpeech?.stop()

                    val i = Intent(applicationContext, OnlineGame::class.java)
                    i.putExtra("username", username)
                    i.putExtra("roomName", roomNameEdit?.text.toString())
                    i.putExtra("isHost", false)

                    startActivity(i)
                } else {
                    backButton?.isEnabled = true
                    refreshButton?.isEnabled = true
                    roomNameEdit?.isEnabled = true
                    passwordEdit?.isEnabled = true
                    joinPrivateButton?.isEnabled = true
                }
            }, 3000)
        }

        refreshButton?.setOnClickListener {
            buttonClick?.start()

            scrollLinear?.removeAllViews()
            SocketConnection.socket.emit("getAllRooms")
        }

        backButton?.setOnClickListener {
            buttonClick?.start()

            if (!messageBoxOpen) {
                textToSpeech?.stop()

                val i = Intent(applicationContext, OnlineSetup::class.java)
                startActivity(i)
            }
        }

        okButton?.setOnClickListener {
            buttonClick?.start()

            textToSpeech?.stop()

            messageBoxOpen = false

            messageBox?.visibility = View.INVISIBLE

            for (b in publicJoinButtons) {
                b.isEnabled = true
            }

            val editor = preferences.edit()

            editor.putString("username", nicknameEdit?.text.toString())

            editor.apply()

            username = preferences.getString("username", "")
        }

        SocketConnection.socket.on("joinFail") { args ->
            toggleMessageBox(args[0] as String)
            validJoin = false
        }

        SocketConnection.socket.on("joinFailNick") { args ->
            toggleNicknameBox(args[0] as String)
            validJoin = false
        }

        SocketConnection.socket.on("allRooms") { args ->
            publicJoinButtons.clear()

            val data = args[0] as JSONObject

            if (data.length() > 0) {
                val allRoomNames = ArrayList<String>()
                val publicRooms = ArrayList<String>()
                val jArray = data.names() as JSONArray

                for (i in 0 until jArray.length()) {
                    try {
                        allRoomNames.add(jArray.getString(i))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                for (s in allRoomNames) {
                    try {
                        val j = data[s] as JSONObject
                        val temp = j.toString()
                        val parts = temp.split(",").toTypedArray()

                        parts[0] = parts[0].replace("{\"roomName\":", "")
                        parts[1] = parts[1].replace("\"password\":", "")
                        parts[3] = parts[3].replace("\"closed\":", "")
                        parts[3] = parts[3].replace("}", "")

                        val roomName = parts[0].replace("\"", "")
                        val password = parts[1].replace("\"", "")
                        val closed = parts[3].toBoolean()

                        if (password == "" && !closed) {
                            publicRooms.add(roomName)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                //Add buttons for every public game found.
                runOnUiThread {
                    for (name in publicRooms) {
                        val roomLinear = LinearLayout(applicationContext)
                        roomLinear.orientation = LinearLayout.HORIZONTAL

                        menuButtonsColour = preferences.getInt("menuButton", -8164501)
                        menuTextColour = preferences.getInt("menuText", -1)

                        val joinButton = Button(applicationContext)
                        joinButton.text = name
                        joinButton.setTextColor(menuTextColour)
                        joinButton.setBackgroundColor(menuButtonsColour)
                        joinButton.isSoundEffectsEnabled = false

                        val buttonParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                        buttonParams.bottomMargin = 8

                        joinButton.layoutParams = buttonParams

                        joinButton.setOnClickListener {
                            buttonClick?.start()

                            validJoin = true

                            SocketConnection.socket.close()
                            SocketConnection.socket.open()

                            SocketConnection.socket.emit("joinRoom", username, name, "")

                            backButton?.isEnabled = false
                            refreshButton?.isEnabled = false
                            roomNameEdit?.isEnabled = false
                            passwordEdit?.isEnabled = false
                            joinPrivateButton?.isEnabled = false

                            for (b in publicJoinButtons) {
                                b.isEnabled = false
                            }

                            Handler(Looper.getMainLooper()).postDelayed({
                                if (validJoin) {
                                    textToSpeech?.stop()

                                    val i = Intent(applicationContext, OnlineGame::class.java)
                                    i.putExtra("username", username)
                                    i.putExtra("roomName", name)
                                    i.putExtra("isHost", false)

                                    startActivity(i)
                                } else {
                                    backButton?.isEnabled = true
                                    refreshButton?.isEnabled = true
                                    roomNameEdit?.isEnabled = true
                                    passwordEdit?.isEnabled = true
                                    joinPrivateButton?.isEnabled = true
                                }
                            }, 3000)
                        }

                        joinButton.setOnLongClickListener {
                            speakOut(joinButton.text.toString())
                            true
                        }

                        publicJoinButtons.add(joinButton)
                        scrollLinear?.addView(roomLinear)
                        roomLinear.addView(joinButton)
                    }
                }
            }
        }

        backButton?.setOnLongClickListener {
            speakOut(backButton?.text.toString())
            true
        }

        joinGameTitle?.setOnLongClickListener {
            speakOut(joinGameTitle?.text.toString())
            true
        }

        refreshButton?.setOnLongClickListener {
            speakOut(refreshButton?.text.toString())
            true
        }

        publicRoomText?.setOnLongClickListener {
            speakOut(publicRoomText?.text.toString())
            true
        }

        privateRoomText?.setOnLongClickListener {
            speakOut(privateRoomText?.text.toString())
            true
        }

        joinPrivateButton?.setOnLongClickListener {
            speakOut(joinPrivateButton?.text.toString())
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
     * When app is reopened after being minimised.
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
     * When device's back button pressed.
     */
    override fun onBackPressed() {
        backButton?.performClick()
    }

    /**
     * Toggles the message box being opened.
     */
    private fun toggleMessageBox(message: String) {
        messageBoxOpen = true

        runOnUiThread {
            speakOut(messageText?.text.toString())

            messageBox?.visibility = View.VISIBLE
            messageText?.text = message
            nicknameEdit?.visibility = View.GONE

            for (b in publicJoinButtons) {
                b.isEnabled = false
            }
        }
    }

    /**
     * Toggles the nickname box being opened.
     */
    private fun toggleNicknameBox(message: String) {
        messageBoxOpen = true

        runOnUiThread {
            speakOut(messageText?.text.toString())

            messageBox?.visibility = View.VISIBLE
            messageText?.text = message
            nicknameEdit?.visibility = View.VISIBLE

            for (b in publicJoinButtons) {
                b.isEnabled = false
            }
        }
    }

    /**
     * Reads aloud the given text.
     */
    private fun speakOut(message: String) {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        if (preferences.getBoolean("textToSpeech", true)) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    /**
     * Updates colours of elements in the layout.
     */
    private fun updateColours() {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        applicationBackgroundColour = preferences.getInt("applicationBackground", -10921639)
        menuButtonsColour = preferences.getInt("menuButton", -8164501)
        menuTextColour = preferences.getInt("menuText", -1)

        constraintLayout?.setBackgroundColor(applicationBackgroundColour)
        messageBox?.setBackgroundColor(applicationBackgroundColour)

        backButton?.setBackgroundColor(menuButtonsColour)
        refreshButton?.setBackgroundColor(menuButtonsColour)
        joinPrivateButton?.setBackgroundColor(menuButtonsColour)
        okButton?.setBackgroundColor(menuButtonsColour)

        joinGameTitle?.setTextColor(menuTextColour)
        publicRoomText?.setTextColor(menuTextColour)
        privateRoomText?.setTextColor(menuTextColour)
        messageText?.setTextColor(menuTextColour)
        roomNameEdit?.setTextColor(menuTextColour)
        passwordEdit?.setTextColor(menuTextColour)
        nicknameEdit?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        refreshButton?.setTextColor(menuTextColour)
        joinPrivateButton?.setTextColor(menuTextColour)
        okButton?.setTextColor(menuTextColour)
    }
}