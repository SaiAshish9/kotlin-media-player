package com.example.kotlinmediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_song.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var songs = ArrayList<Song>()
    var adapter: SongAdapter? = null
    var mp: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSong()
        adapter = SongAdapter(songs)
        list_view.adapter = adapter
        var track = SongTrack()
        track.start()
    }

    fun loadSong() {
        Collections.addAll(
            songs,
            Song("X", "abcd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
            Song("Y", "abcd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
            Song("Z", "abcd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"),
            Song("XY", "abcd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"),
            Song("YZ", "abcd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3"),
            Song("XYZ", "abcd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3")
        )
    }

    inner class SongAdapter : BaseAdapter {

        var songList = ArrayList<Song>()
        constructor(songList: ArrayList<Song>) : super() {
            this.songList = songList
        }
        override fun getCount(): Int {
            return this.songList.size
        }
        override fun getItem(position: Int): Any {
            return this.songList[position]
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.activity_song, null)
            val Song = this.songList[position]
            view.tv.text = Song.Title
            view.tv1.text = Song.AuthorName
            view.play.setOnClickListener(
                View.OnClickListener {
                    println("clicked")
                    if (view.play.text.equals("Stop")) {
                        mp!!.stop()
                        view.play.text = "Play"
                    } else {
                        mp = MediaPlayer()
                        try {
                            mp!!.setDataSource(Song.SongURL)
                            mp!!.prepare()
                            mp!!.start()
                            view.play.text = "Stop"
                            seekBar.max = mp!!.duration

                        } catch (ex: Exception) {
                        }
                    }
                }
            )
            return view
        }
    }

    inner class SongTrack (): Thread() {
        override fun run() {
            while (true) {
                try {
                    Thread.sleep(1000)
                } catch (ex: Exception) { }
                runOnUiThread{
                    if(mp !=null)
                    seekBar.progress=mp!!.currentPosition
                }
            }
        }
    }




}