package com.nandani.project_work.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nandani.project_work.R
import com.nandani.project_work.room.UserAttempts


class ScoreAdapter(var userAttempts: ArrayList<UserAttempts>) :
    RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvId: TextView = view.findViewById(R.id.tvId)
        var tvLevelType: TextView = view.findViewById(R.id.tvLevelType)
        var tvAttemptedQuestions: TextView = view.findViewById(R.id.tvAttemptedQuestions)
        var tvTotalQuestions: TextView = view.findViewById(R.id.tvTotalQuestions)
        var tvScores: TextView = view.findViewById(R.id.tvScores)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_scores, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId.setText((position + 1).toString())
        when(userAttempts[position].quizType){
            0->{holder.tvLevelType.setText("Beginner") }
            1->{holder.tvLevelType.setText("Intermediate") }
            2->{holder.tvLevelType.setText("Advance") }
        }
        holder.tvAttemptedQuestions.setText(userAttempts[position].totalAttempted.toString())
        holder.tvTotalQuestions.setText(userAttempts[position].totalQuestions.toString())
        holder.tvScores.setText(userAttempts[position].score.toString())
    }

    override fun getItemCount(): Int {
        return userAttempts.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
