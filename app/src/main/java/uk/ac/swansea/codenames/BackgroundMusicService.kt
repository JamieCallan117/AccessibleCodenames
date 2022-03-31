package uk.ac.swansea.codenames

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class BackgroundMusicService : Service() {
    private lateinit var player: MediaPlayer
    override fun onBind(arg0: Intent): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
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
        }


        return START_STICKY
    }

    override fun onStart(intent: Intent, startId: Int) {
        // TO DO
    }

    fun onUnBind(arg0: Intent): IBinder? {
        // TO DO Auto-generated method
        return null
    }

    fun onStop() {

    }

    fun onPause() {

    }

    override fun onDestroy() {
        player.stop()
        player.release()
    }

    override fun onLowMemory() {

    }

    companion object {
        private val TAG: String? = null
    }
}