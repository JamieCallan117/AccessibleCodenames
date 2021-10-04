package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import java.io.File
import java.io.IOException

class LocalSetup : AppCompatActivity() {
    private var constraintLayout: ConstraintLayout? = null
    private var localSetupTitle: TextView? = null
    private var localSetupSubtitle: TextView? = null
    private var backButton: Button? = null
    private var gameOptionsButton: Button? = null
    private var playGameButton: Button? = null
    private var preferencesFile = "preferences.txt"
    private var preferences = arrayOfNulls<String>(14)
    private var applicationBackgroundColour = -10921639
    private var menuButtonsColour = -8164501
    private var menuTextColour = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.local_setup)

        constraintLayout = findViewById(R.id.constraintLayout)
        localSetupTitle = findViewById(R.id.localSetupTitle)
        localSetupSubtitle = findViewById(R.id.localSetupSubtitle)
        backButton = findViewById(R.id.backButton)
        gameOptionsButton = findViewById(R.id.gameOptionsButton)
        playGameButton = findViewById(R.id.createGameButton)

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

        backButton?.setOnClickListener {
            val i = Intent(applicationContext, MainMenu::class.java)
            startActivity(i)
        }

        gameOptionsButton?.setOnClickListener {
            val i = Intent(applicationContext, GameOptions::class.java)

            i.putExtra("type", "local")

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

        playGameButton?.setOnClickListener {
            val i = Intent(applicationContext, LocalGame::class.java)

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

        backButton?.setOnLongClickListener {
            println(backButton?.text.toString())
            true
        }
        localSetupTitle?.setOnLongClickListener {
            println(localSetupTitle?.text.toString())
            true
        }
        localSetupSubtitle?.setOnLongClickListener {
            println(localSetupSubtitle?.text.toString())
            true
        }
        gameOptionsButton?.setOnLongClickListener {
            println(gameOptionsButton?.text.toString())
            true
        }
        playGameButton?.setOnLongClickListener {
            println(playGameButton?.text.toString())
            true
        }

        updateColours()
    }

    override fun onBackPressed() {
        backButton?.performClick()
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
        backButton?.setBackgroundColor(menuButtonsColour)
        gameOptionsButton?.setBackgroundColor(menuButtonsColour)
        playGameButton?.setBackgroundColor(menuButtonsColour)
        localSetupTitle?.setTextColor(menuTextColour)
        localSetupSubtitle?.setTextColor(menuTextColour)
        backButton?.setTextColor(menuTextColour)
        gameOptionsButton?.setTextColor(menuTextColour)
        playGameButton?.setTextColor(menuTextColour)
    }
}