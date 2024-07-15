package com.nandani.project_work

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nandani.project_work.room.QuizDatabase
import java.io.ByteArrayOutputStream


class Third_screen : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var quizDatabase: QuizDatabase
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)
        sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        quizDatabase = QuizDatabase.getDatabase(this)
        navController =findNavController( R.id.navHost)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.playQuizFragment, R.id.scoreboard, R.id.home2, R.id.profile))
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView?.setOnItemSelectedListener() { menuItemSelected ->
            when (menuItemSelected.itemId) {
                R.id.home2 -> navController.navigate(R.id.home2)
                R.id.profile -> navController.navigate(R.id.profile)
                R.id.scoreboard -> navController.navigate(R.id.scoreboard)
            }
            return@setOnItemSelectedListener true

        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home2 -> {
                    bottomNavigationView.visibility=View.VISIBLE
                }
                R.id.profile -> bottomNavigationView.visibility=View.VISIBLE

                R.id.scoreboard -> bottomNavigationView.visibility=View.VISIBLE
                else -> bottomNavigationView.visibility=View.GONE
            }
        }
    }

    fun getDrawable(context: Context, name: String?): Int {
        return context.resources.getIdentifier(
            name,
            "drawable", context.packageName
        )
    }

    //use this method to convert the selected image to bitmap to save in database
    fun encodeTobase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        return imageEncoded
    }

    //use this method to convert the saved string to bitmap
    fun decodeBase64(input: String?): Bitmap? {
        val decodedByte: ByteArray = Base64.decode(input, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }

}