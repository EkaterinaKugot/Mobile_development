package com.example.music_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.media.MediaPlayer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private val handler = Handler()
    private lateinit var playPauseButton: Button
    private lateinit var currentPositionTextView: TextView
    private lateinit var totalDurationTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        mediaPlayer = MediaPlayer()
        val assetFileDescriptor = assets.openFd("music.mp3")
        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        assetFileDescriptor.close()
        mediaPlayer.prepare()

        currentPositionTextView = findViewById(R.id.currentPositionTextView)
        totalDurationTextView = findViewById(R.id.totalDurationTextView)
        progressBar = findViewById(R.id.progressBar)

        playPauseButton = findViewById(R.id.playPauseButton)
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playPauseButton.text = "Play"
            } else {
                mediaPlayer.start()
                playPauseButton.text = "Pause"
            }
        }

        handler.postDelayed(updateProgress, 1000)
    }

    private val updateProgress = object : Runnable {
        override fun run() {
            val currentTime = mediaPlayer.currentPosition
            val duration = mediaPlayer.duration
            val elapsedTime = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong())
                )
            )
            val totalTime = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
                )
            )
            currentPositionTextView.text = elapsedTime
            totalDurationTextView.text = totalTime

            progressBar.max = duration
            progressBar.progress = currentTime

            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateProgress)
    }
}