package com.example.e_exam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.databinding.OldAnswerCardBinding
import com.example.e_exam.network.viewOldAnswers.Answer

class OldAnswerAdapter(private val oldAnswers: List<Answer>) :
    RecyclerView.Adapter<OldAnswerAdapter.ViewHolder>() {
    class ViewHolder(val binding: OldAnswerCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answer: Answer) {
            binding.answer = answer
            if(answer.isCorrect())
                binding.wrongRb.visibility = View.GONE
            else
                binding.wrongRb.visibility = View.VISIBLE
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OldAnswerCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = oldAnswers[position]
        holder.bind(answer)
    }

    override fun getItemCount(): Int {
        return oldAnswers.size
    }
}