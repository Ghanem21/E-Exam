package com.example.e_exam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.databinding.OldExamCardViewBinding
import com.example.e_exam.network.viewOldExams.Exams

class OldExamAdapter(private val oldExams: List<Exams>) :
    RecyclerView.Adapter<OldExamAdapter.ViewHolder>() {
    class ViewHolder(val binding: OldExamCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(oldExam: Exams) {
            binding.oldExam = oldExam
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OldExamCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oldExam = oldExams[position]
        holder.bind(oldExam)
    }

    override fun getItemCount(): Int {
        return oldExams.size
    }
}