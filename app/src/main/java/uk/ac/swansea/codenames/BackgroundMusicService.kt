package uk.ac.swansea.codenames

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import kotlin.properties.Delegates

/**
 * This service allows for music to be played in the
 */
class BackgroundMusicService : Service() {
    private lateinit var player: MediaPlayer
    private var length: Int = 0
    override fun onBind(arg0: Intent): IBinder? {

        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val musicVolume = preferences.getFloat("musicVolume", 0.5f)

        if (intent.action != null && intent.action.equals("START_MENU")) {
            player = MediaPlayer.create(applicationContext, R.raw.bgmusic)
            player.isLooping = true
            player.setVolume(musicVolume, musicVolume)

            player.start()
        } else if (intent.action != null && intent.action.equals("START_GAME")) {
            player = MediaPlayer.create(applicationContext, R.raw.gamemusic)
            player.isLooping = true
            player.setVolume(musicVolume, musicVolume)

            player.start()
        } else if (intent.action != null && intent.action.equals("CHANGE_VOLUME")) {
            player.setVolume(musicVolume, musicVolume)
        } else if (intent.action != null && intent.action.equals("PAUSE")) {
            player.pause()
            length = player.currentPosition
        } else if (intent.action != null && intent.action.equals("RESUME")) {
            player.seekTo(length)
            player.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        player.stop()
        player.release()
    }

    override fun onLowMemory() {

    }
}