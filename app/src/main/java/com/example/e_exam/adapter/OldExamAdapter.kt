package com.example.e_exam.adapter

import android.app.ProgressDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.OldExamActivity
import com.example.e_exam.R
import com.example.e_exam.databinding.OldExamCardViewBinding
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.viewOldAnswers.Answer
import com.example.e_exam.network.viewOldExams.Exams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OldExamAdapter(private val oldExams: List<Exams>, val token: String) :
    RecyclerView.Adapter<OldExamAdapter.ViewHolder>() {

    companion object {
        var answers: List<Answer> = listOf()
    }

    class ViewHolder(val binding: OldExamCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(oldExam: Exams) {
            binding.oldExam = oldExam
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OldExamCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oldExam = oldExams[position]
        holder.bind(oldExam)
        holder.binding.card.setOnClickListener {
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                try {
                    val dialog = ProgressDialog(holder.binding.root.context)
                    dialog.setTitle("Waiting....")
                    dialog.setCancelable(false)
                    dialog.setIcon(R.drawable.logo)
                    dialog.setMessage("Getting data please wait\uD83D\uDE0A")
                    dialog.show()
                    val viewOldAnswerRespond =
                        ExamApi.retrofitService.viewOldAnswers(token, oldExam.examId.toInt())
                    answers = viewOldAnswerRespond.exams!!
                    Log.d("TAG", "onBindViewHolder: " + answers)
                    val intent = Intent(holder.binding.root.context, OldExamActivity::class.java)
                    dialog.hide()
                    holder.binding.root.context.startActivity(intent)

                } catch (ex: Exception) {
                    Log.d("TAG", "onBindViewHolder: " + ex.message)
                }
                coroutineScope.cancel()
            }
        }
    }

    override fun getItemCount(): Int {
        return oldExams.size
    }
}