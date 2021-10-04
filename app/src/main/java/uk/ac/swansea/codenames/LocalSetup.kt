package uk.ac.swansea.codenames

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import java.io.File

class LocalSetup : AppCompatActivity() {
    private var constraintLayout: ConstraintLayout? = null
    private var localSetupTitle: TextView? = null
    private var localSetupSubtitle: TextView? = null
    private var backButton: Button? = null
    private var gameOptionsButton: Button? = null
    private var playGameButton: Button? = null
    private var preferencesFile = "preferences.txt"
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
        val colours = File(preferencesFile).useLines { it.toList() }

        applicationBackgroundColour = colours[5].toInt()
        menuButtonsColour = colours[6].toInt()
        menuTextColour = colours[7].toInt()
        
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