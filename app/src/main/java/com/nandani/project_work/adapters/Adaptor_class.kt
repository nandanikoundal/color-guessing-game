package com.nandani.project_work.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.nandani.project_work.OptionClickInterface
import com.nandani.project_work.R

class Adaptor_class(var optionClickInterface: OptionClickInterface) :
    RecyclerView.Adapter<Adaptor_class.ViewHolder>() {
    var clickedOption = -1
    var quizQOptions: List<String> = mutableListOf()

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var checkedBox: RadioButton = view.findViewById(R.id.checkBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_options, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        System.out.println("in on bind view holder clickedOption $clickedOption position $position")
        holder.checkedBox.isChecked = position == clickedOption
        holder.checkedBox.setText(quizQOptions[position])
        holder.checkedBox.setOnClickListener {
            optionClickInterface.ClickedOption(position)
        }
    }

    fun updatePosition(position: Int) {
        val oldPosition = clickedOption
        clickedOption = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(position)
//         notifyDataSetChanged()
    }

    fun addOptions(quizQOptions: List<String>) {
        this.quizQOptions = quizQOptions
        clickedOption = -1
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return quizQOptions.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
