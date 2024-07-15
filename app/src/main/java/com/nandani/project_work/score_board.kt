package com.nandani.project_work

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandani.project_work.adapters.ScoreAdapter
import com.nandani.project_work.databinding.FragmentScoreBoardBinding
import com.nandani.project_work.room.UserAttempts

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [score_board.newInstance] factory method to
 * create an instance of this fragment.
 */
class score_board : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentScoreBoardBinding
    var scoreList : ArrayList<UserAttempts> = ArrayList()
    lateinit var scoreAdapter: ScoreAdapter
    lateinit var mainActivity : Third_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mainActivity = activity as Third_screen

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScoreBoardBinding.inflate(layoutInflater)
        scoreAdapter = ScoreAdapter(scoreList)
        scoreAdapter.setHasStableIds(true)
        binding.rvScore.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvScore.adapter = scoreAdapter
        binding.tvName.setText(mainActivity.sharedPreferences.getString("name",""))
        if(mainActivity.sharedPreferences.contains(resources.getString(R.string.prefs_image))){
            var btmap = mainActivity.decodeBase64(mainActivity.sharedPreferences.getString(resources.getString(R.string.prefs_image),""))
            binding.ivProfile.setImageBitmap(btmap)
        }
        getScores()
        return  binding.root
    }

    private fun getScores() {
        class getScore: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                scoreList.addAll(mainActivity.quizDatabase.quizDao().getUserAttempts())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                scoreAdapter.notifyDataSetChanged()
            }
        }
        getScore().execute()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment score_board.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            score_board().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}