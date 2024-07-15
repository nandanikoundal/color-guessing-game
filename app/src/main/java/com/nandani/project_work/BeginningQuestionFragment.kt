package com.nandani.project_work

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nandani.project_work.adapters.Adaptor_class
import com.nandani.project_work.databinding.FragmentBegginerQue1Binding
import com.nandani.project_work.room.QuizQuestion
import com.nandani.project_work.room.UserAttempts


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BeginningQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BeginningQuestionFragment : Fragment(), OptionClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var arrayList: ArrayList<QuizQuestion> = ArrayList()
    var currentIndex = 0
    var correctAnswers = 0
    var totalAttempted = 0
    lateinit var binding: FragmentBegginerQue1Binding
    lateinit var adapter: Adaptor_class
    lateinit var mainActivity: Third_screen
    lateinit var countDownTimer: CountDownTimer
    var type: Int = 0
    var time: Long = 60000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            type = it.getInt("type")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBegginerQue1Binding.inflate(layoutInflater)
        mainActivity = activity as Third_screen


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Adaptor_class(this)
        adapter.setHasStableIds(true)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireActivity(), 2)
        getQuestions()

        binding.btnConfirm.setOnClickListener {
            if (adapter.clickedOption == -1) {
                Toast.makeText(
                    mainActivity,
                    resources.getString(R.string.select_option),
                    Toast.LENGTH_LONG
                ).show()
            } else if (adapter.clickedOption == arrayList[currentIndex].correctIndex) {
                totalAttempted++
                ShowAnswerCorrectInCorrect(true)
                correctAnswers++
            } else if (adapter.clickedOption != arrayList[currentIndex].correctIndex) {
                totalAttempted++
                ShowAnswerCorrectInCorrect(false)
            } else {
            }

//            if (binding.recyclerview.checkedrecyclerviewId == -1){
//                return@setOnClickListener
//            }
//
//            if()

        }
        binding.fabbtn.setOnClickListener {
            val dialog = AlertDialog.Builder(mainActivity)
            dialog.setTitle("Alert")
            dialog.setMessage("You will lose your Progress. Do you still want to Continue?")
            dialog.setCancelable(true)
            dialog.setPositiveButton("Yes") { _, _ ->
                countDownTimer.cancel()
                mainActivity.navController.popBackStack(R.id.activity_nav_xml,true)
                mainActivity.navController.navigate(R.id.home2)
            }
            dialog.setNegativeButton("No") { _, _ ->

            }
            dialog.show()
        }

        starTimer()
    }

    fun starTimer(){
        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.countdown.setText("" + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                showCompleteQuiz()

            }
        }.start()
    }

    private fun ShowAnswerCorrectInCorrect(isCorrect: Boolean) {
        var message = resources.getString(R.string.incorrect_answer)
        if (isCorrect)
            message = resources.getString(R.string.correct_answer)

        AlertDialog.Builder(mainActivity).apply {
            setTitle(resources.getString(R.string.answer))
            setCancelable(false)
            setMessage(message)
            setPositiveButton(resources.getString(R.string.next)) { _, _ ->
                if (currentIndex < arrayList.size - 1) {
                    currentIndex++
                    setQuizQuestion()
                } else {

                    showCompleteQuiz()
                }
            }.show()
        }

    }

    private fun showCompleteQuiz() {
        countDownTimer.cancel()
        var yourScore =
            mainActivity.resources.getString(R.string.your_total_score_is) + correctAnswers
        AlertDialog.Builder(mainActivity).apply {
            setTitle(resources.getString(R.string.congratulations))
            setMessage(yourScore)
            setCancelable(false)
            setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                class saveScore: AsyncTask<Void, Void, Void>(){
                    override fun doInBackground(vararg p0: Void?): Void? {
                        var attempts = UserAttempts()
                        attempts.attemptedDate = SimpleDateFormat("yyyy-MM-dd").parse(SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time))
                        attempts.score = correctAnswers
                        attempts.totalQuestions  = arrayList.size
                        attempts.totalAttempted  = totalAttempted
                        attempts.quizType  = type
                        mainActivity.quizDatabase.quizDao().insertUserAttempt(attempts)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        mainActivity.navController.popBackStack(R.id.activity_nav_xml,true)
                        mainActivity.navController.navigate(R.id.levelSelect)
                    }
                }
                saveScore().execute()

            }.show()
        }

    }

    private fun getQuestions() {
        class getQues : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                System.out.println(" type $type")
                arrayList.addAll(mainActivity.quizDatabase.quizDao().getQuizQuestion(type))
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                if (arrayList.isNotEmpty())
                    setQuizQuestion()
            }
        }
        getQues().execute()


    }

    private fun setQuizQuestion() {
        System.out.println("arrayList ${arrayList.size}")
        binding.tv.setText( arrayList[currentIndex].question)

        adapter.addOptions(arrayList[currentIndex].options ?: ArrayList())
        binding.questionCount.setText("Question: ${currentIndex + 1}/${arrayList.size}")
        binding.scoreView1.setText(correctAnswers.toString())

    }

    override fun ClickedOption(position: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.updatePosition(position)

        }, 50)
    }
}