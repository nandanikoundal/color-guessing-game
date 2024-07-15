package com.nandani.project_work

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.nandani.project_work.databinding.CustomLayoutprofileBinding
import com.nandani.project_work.databinding.FragmentProfileBinding
import com.nandani.project_work.room.UserAttempts

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentProfileBinding
    lateinit var profileActivity: Third_screen
    lateinit var imageUri: Uri
    var btmap: Bitmap? = null
    lateinit var dialogBinding: CustomLayoutprofileBinding

    var getPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
                getImage.launch("image/*")
            } else {
                Toast.makeText(requireContext(), "Not Granted", Toast.LENGTH_SHORT).show()
            }
        }

    var getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        System.out.println("it $it")
        it?.let {
            imageUri = it
            btmap = MediaStore.Images.Media.getBitmap(profileActivity.contentResolver, it)
            dialogBinding.imgprofile.setImageBitmap(btmap)
        }

    }
//    var getMulPermission =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
//
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        profileActivity = activity as Third_screen
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setProfile()
        binding.btnedit.setOnClickListener {
            dialogBinding = CustomLayoutprofileBinding.inflate(layoutInflater)
            var dialog = Dialog(profileActivity)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.fabbtn.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getImage.launch("image/*")
                } else
                    getPermission.launch(READ_EXTERNAL_STORAGE)
            }
            dialogBinding.etName.setText(binding.etname.text.toString())
            dialogBinding.etAge.setText(binding.etage.text.toString())
            dialogBinding.etDOB.setText(binding.etdob.text.toString())
            if (btmap != null) {
                dialogBinding.imgprofile.setImageBitmap(btmap)
            }

            dialogBinding.btnOk.setOnClickListener {
                if (dialogBinding.etName.text.toString().isNullOrEmpty()) {
                    dialogBinding.etName.setError("Enter Full Name")
                    return@setOnClickListener
                } else if (dialogBinding.etAge.text.toString().isNullOrEmpty()) {
                    dialogBinding.etAge.setError("Enter Your Age")
                    return@setOnClickListener
                } else if (dialogBinding.etDOB.text.toString().isNullOrEmpty()) {
                    dialogBinding.etDOB.setError("Enter Your Date Of Birth")
                    return@setOnClickListener
                } else {
                    if (btmap != null) {
                        profileActivity.editor.putString(
                            profileActivity.resources.getString(R.string.prefs_image),
                            profileActivity.encodeTobase64(btmap!!)
                        )
                    }
                    profileActivity.editor.putString(
                        profileActivity.resources.getString(R.string.prefs_name),
                        dialogBinding.etName.text.toString()
                    )
                    profileActivity.editor.putString(
                        profileActivity.resources.getString(R.string.prefs_age),
                        dialogBinding.etAge.text.toString()
                    )

                    profileActivity.editor.putString(
                        profileActivity.resources.getString(R.string.prefs_dob),
                        dialogBinding.etDOB.text.toString()
                    )
                    profileActivity.editor.commit()
                    dialog.dismiss()
                    setProfile()
                }
            }
            dialog.show()
        }
        return binding.root
    }

    private fun setProfile() {
        binding.etname.setText(
            profileActivity.sharedPreferences.getString(
                resources.getString(R.string.prefs_name),
                ""
            ) ?: ""
        )
        binding.etage.setText(
            profileActivity.sharedPreferences.getString(
                resources.getString(R.string.prefs_age),
                ""
            ) ?: ""
        )
        binding.etdob.setText(
            profileActivity.sharedPreferences.getString(
                resources.getString(R.string.prefs_dob),
                ""
            ) ?: ""
        )
        if (profileActivity.sharedPreferences.contains(resources.getString(R.string.prefs_image))) {
            btmap = profileActivity.decodeBase64(
                profileActivity.sharedPreferences.getString(
                    resources.getString(R.string.prefs_image), ""
                )
            )
            binding.imgprofile.setImageBitmap(btmap)
        }
        binding.btnlogout.setOnClickListener {
            System.out.println("Button is clicked")
            val dialog = AlertDialog.Builder(profileActivity)
            dialog.setTitle("Alert")
            dialog.setMessage("You will lose your Progress. Do you still want to Continue?")
            dialog.setCancelable(true)
            dialog.setPositiveButton("Yes") { _, _ ->
                Toast.makeText(
                    profileActivity,
                    resources.getString(R.string.logout),
                    Toast.LENGTH_LONG
                )
                    .show()
                profileActivity.editor.clear()
                profileActivity.editor.apply()

                class deleteScore : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg p0: Void?): Void? {

                        profileActivity.quizDatabase.quizDao().deleteScores()
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        var intent = Intent(profileActivity, Activity2::class.java)
                        startActivity(intent)
                        profileActivity.finish()
                    }
                }
                deleteScore().execute()}
                dialog.setNegativeButton("No") { _, _ ->

                }
                dialog.show()


        }
    }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment Profile.
             */

            fun newInstance(param1: String, param2: String) =
                Profile().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    }



