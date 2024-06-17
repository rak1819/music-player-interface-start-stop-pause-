package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private  var mediaPlayer: MediaPlayer?=null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playbutton  = findViewById<FloatingActionButton>(R.id.play)
        val stopbutton = findViewById<FloatingActionButton>(R.id.stop)
        val pausebutton = findViewById<FloatingActionButton>(R.id.pause)
        val pre = findViewById<TextView>(R.id.textView)
        val pos = findViewById<TextView>(R.id.textView2)
        seekBar = findViewById(R.id.seekBar)
        handler = Handler(Looper.getMainLooper())
        playbutton.setOnClickListener {
            if(mediaPlayer==null){
                mediaPlayer = MediaPlayer.create(this,R.raw.clapsound)
                initializeseekbar()
            }
            mediaPlayer?.start()

        }
        stopbutton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
            pre.text =""
            pos.text=""
        }
        pausebutton.setOnClickListener {
            mediaPlayer?.pause()

        }

    }
    private fun initializeseekbar(){
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser)mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        seekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable,1000)
            val pre = findViewById<TextView>(R.id.textView)
            val pos = findViewById<TextView>(R.id.textView2)
            val play = mediaPlayer!!.currentPosition/1000
            pre.text = "$play sec"
            val dur = mediaPlayer!!.duration/1000
            val duetime = dur-play
            pos.text = "$duetime sec"
        }
        handler.postDelayed(runnable,1000)
    }
}