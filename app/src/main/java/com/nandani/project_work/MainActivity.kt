package com.nandani.project_work

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.nandani.project_work.databinding.ActivityMainBinding
import com.nandani.project_work.room.QuizDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var quizDatabase: QuizDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        quizDatabase = QuizDatabase.getDatabase(this)
        Handler(Looper.getMainLooper()).postDelayed({
                if(sharedPreferences.contains(resources.getString(R.string.prefs_name))) {
                    var intent = Intent(this, Third_screen::class.java)
                    startActivity(intent)
                    this.finish()
                }else{
                    var intent = Intent(this, Activity2::class.java)
                    startActivity(intent)
                    this.finish()
                }
            },2000)
        binding.pBar.max=10
        val cp=1
        ObjectAnimator.ofInt(binding.pBar,"progress",cp).setDuration(2000).start()
        }
    }