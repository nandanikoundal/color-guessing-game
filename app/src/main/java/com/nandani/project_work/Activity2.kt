package com.nandani.project_work

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class Activity2 : AppCompatActivity() {
    lateinit var Heading: TextView
    lateinit var etage: EditText
    lateinit var etname: EditText
    lateinit var btnregister: Button
    lateinit var fabbtn: FloatingActionButton
    lateinit var imgprofile: CircleImageView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var btmap: Bitmap? = null
    var getPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
            isGranted ->
        if (isGranted) {
            getImage.launch("image/*")

        } else {
            Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show()

        }
    }

    var getImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        System.out.println("it $it")
        btmap = MediaStore.Images.Media.getBitmap(contentResolver, it)

        imgprofile.setImageBitmap(btmap)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        Heading = findViewById(R.id.Heading)
        etname = findViewById(R.id.etname)
        etage = findViewById(R.id.etage)
        btnregister = findViewById(R.id.btnregister)
        fabbtn = findViewById(R.id.fabbtn)
        imgprofile = findViewById(R.id.imgprofile)
        sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        fabbtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getImage.launch("image/*")
                Toast.makeText(this, " Granted ", Toast.LENGTH_SHORT).show()
            } else
                getPermission.launch(READ_EXTERNAL_STORAGE)
        }
        btnregister.setOnClickListener {

            System.out.println("Button is clicked")
            var name = etname.text.toString()

            System.out.println("name $name")
            if (name.isNullOrEmpty()) {
                etname.error = resources.getString(R.string.please_enter_name)
                etname.requestFocus()

                var age = etage.text.toString()

                System.out.println("age $age")
                if (age.isNullOrEmpty()) {
                    etage.error = resources.getString(R.string.please_enter_age)
                    etname.requestFocus()
                }
            } else {
                editor.putString(resources.getString(R.string.prefs_name), name)
                if(btmap != null) {
                    editor.putString(resources.getString(R.string.prefs_age), "age")
                    if (btmap != null) {
                        editor.putString(resources.getString(R.string.prefs_image),
                            encodeTobase64(btmap!!)
                        )
                    }
                }
                editor.apply()
                editor.commit()
                Toast.makeText(this, resources.getString(R.string.GetStart), Toast.LENGTH_LONG)
                    .show()
                var intent = Intent(this, Third_screen::class.java)
                startActivity(intent)
            }



            }

        }

    //use this method to convert the selected image to bitmap to save in database
    fun encodeTobase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        return imageEncoded
    }
    }




