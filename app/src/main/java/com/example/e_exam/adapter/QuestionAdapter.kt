package com.example.e_exam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.databinding.QuestionCardViewBinding
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.viewExam.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionAdapter(private val questions: List<Question>, private val token: String) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(val binding: QuestionCardViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            QuestionCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.binding.questionTv.text = question.mcq.questionName
        Log.d("TAG", "onBindViewHolder: " + question.mcq.questionName)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val getMcqOptionsRespond =
                    ExamApi.retrofitService.getMcqOptions(question.mcqId.toInt(), token)

                holder.binding.recyclerview.adapter =
                    RadioButtonAdapter(getMcqOptionsRespond.options!!)
            } catch (ex: Exception) {
                Log.d("TAG", "onBindViewHolder: " + ex.message)
            }
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}