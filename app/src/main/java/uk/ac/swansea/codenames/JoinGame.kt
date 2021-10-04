package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import android.view.ViewGroup
import android.widget.Button
import java.io.File
import java.io.IOException
import java.util.ArrayList

class JoinGame : AppCompatActivity() {
    // TODO: Redesign layout.

    private var constraintLayout: ConstraintLayout? = null
    private var scrollLinear: LinearLayout? = null
    private var backButton: Button? = null
    private var refreshButton: Button? = null
    private var joinPrivateButton: Button? = null
    private var joinGameTitle: TextView? = null
    private var publicRoomText: TextView? = null
    private var privateRoomText: TextView? = null
    private var roomNameEdit: EditText? = null
    private var passwordEdit: EditText? = null
    private val publicJoinButtons = ArrayList<Button>()
    private var preferencesFile = "preferences.txt"
    private var preferences = arrayOfNulls<String>(14)
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1
    private var username: String? = null
    private var validJoin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_game)

        constraintLayout = findViewById(R.id.constraintLayout)
        scrollLinear = findViewById(R.id.scrollLinear)
        backButton = findViewById(R.id.backButton)
        refreshButton = findViewById(R.id.refreshButton)
        joinGameTitle = findViewById(R.id.joinGameTitle)
        joinPrivateButton = findViewById(R.id.joinPrivateButton)
        publicRoomText = findViewById(R.id.publicRoomText)
        privateRoomText = findViewById(R.id.privateRoomText)
        roomNameEdit = findViewById(R.id.roomNameEdit)
        passwordEdit = findViewById(R.id.passwordEdit)

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

        preferences = tempPref.split("\n").toTypedArray()

        username = preferences[8]

        SocketConnection.socket.emit("getAllRooms")

        joinPrivateButton?.setOnClickListener {
            validJoin = true

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

            val handler = Handler()

            handler.postDelayed({
                if (validJoin) {
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
            scrollLinear?.removeAllViews()
            SocketConnection.socket.emit("getAllRooms")
        }

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, OnlineSetup::class.java)
            startActivity(i)
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

            runOnUiThread {
                for (name in publicRooms) {
                    val roomLinear = LinearLayout(applicationContext)
                    roomLinear.orientation = LinearLayout.HORIZONTAL

                    val colours = File(preferencesFile).useLines { it.toList() }

                    menuButtonsColour = colours[6].toInt()
                    menuTextColour = colours[7].toInt()

                    val joinButton = Button(applicationContext)

                    joinButton.text = name
                    joinButton.setTextColor(menuTextColour)

                    val buttonParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                    joinButton.layoutParams = buttonParams

                    joinButton.setBackgroundColor(menuButtonsColour)

                    joinButton.setOnClickListener {
                        validJoin = true
                        SocketConnection.socket.emit("joinRoom", username, name, "")

                        backButton?.isEnabled = false
                        refreshButton?.isEnabled = false
                        roomNameEdit?.isEnabled = false
                        passwordEdit?.isEnabled = false
                        joinPrivateButton?.isEnabled = false

                        for (b in publicJoinButtons) {
                            b.isEnabled = false
                        }

                        val handler = Handler()

                        handler.postDelayed({
                            if (validJoin) {
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

                    publicJoinButtons.add(joinButton)
                    scrollLinear?.addView(roomLinear)
                    roomLinear.addView(joinButton)
                }
            }
        }

        updateColours()
    }

    override fun onBackPressed() {
        backButton?.performClick()
    }

    private fun toggleMessageBox(message: String) {

    }

    private fun toggleNicknameBox(message: String) {

    }

    private fun updateColours() {
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

        backButton?.setBackgroundColor(menuButtonsColour)
        refreshButton?.setBackgroundColor(menuButtonsColour)
        joinPrivateButton?.setBackgroundColor(menuButtonsColour)

        joinGameTitle?.setTextColor(menuTextColour)
        publicRoomText?.setTextColor(menuTextColour)
        privateRoomText?.setTextColor(menuTextColour)
        roomNameEdit?.setTextColor(menuTextColour)
        passwordEdit?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        refreshButton?.setTextColor(menuTextColour)
        joinPrivateButton?.setTextColor(menuTextColour)
    }
}