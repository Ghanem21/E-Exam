package com.example.e_exam.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.ExamActivity
import com.example.e_exam.R
import com.example.e_exam.databinding.SubjectCardViewBinding
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.studentSubject.Subject
import com.example.e_exam.network.viewExam.Question
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.System.exit
import java.text.SimpleDateFormat
import java.util.*

class SubjectAdapter(private val subjects: LiveData<List<Subject>>, private val token: String) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {


    class SubjectViewHolder(val binding: SubjectCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var examId = -1

        companion object {
            private val startExamTime = mutableListOf<Long>()
            val endExamTime = mutableListOf<Long>()
        }

        fun bind(subject: Subject, token: String) {
            binding.subject = subject
            startExamTime.add(0L)
            endExamTime.add(0L)
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val checkExistExamRespond =
                        ExamApi.retrofitService.checkExistExam(subject.id, token)
                    if (checkExistExamRespond.exams != null) {
                        examId = checkExistExamRespond.exams[0].id

                        startExamTime[adapterPosition] =
                            checkExistExamRespond.exams[0].startTime.toLong() * 1000
                        endExamTime[adapterPosition] =
                            checkExistExamRespond.exams[0].endTime.toLong() * 1000
                        val currentTime = System.currentTimeMillis()
                        if (startExamTime[adapterPosition] > currentTime) {
                            val examTime = getDateFromMilliseconds(startExamTime[adapterPosition])
                            binding.exam = examTime
                        } else if (endExamTime[adapterPosition] > currentTime) {
                            binding.exam = "Running Now"
                        } else {
                            binding.exam = "No Exam"
                        }
                    } else {
                        binding.exam = "No Exam"
                    }
                } catch (ex: Exception) {
                    Log.d("TAG", "bind: failed" + ex.message)
                }
            }
            binding.executePendingBindings()
        }

        private fun getDateFromMilliseconds(millis: Long): String {
            val dateFormat = "dd/MM/yyyy\n HH:mm"
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            val calendar = Calendar.getInstance()

            calendar.timeInMillis = millis
            return formatter.format(calendar.time)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubjectViewHolder {
        return SubjectViewHolder(
            SubjectCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects.value!![position]
        holder.bind(subject, token)
        holder.binding.card.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            var questions: List<Question>
            if (currentTime < SubjectViewHolder.endExamTime[position]) {
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    try {
                        val end = async {
                            return@async ExamApi.retrofitService.getExamQuestion(
                                holder.examId,
                                subject.id,
                                token
                            )
                        }

                        val examQuestionRespond = end.await()
                            if (examQuestionRespond.status) {
                                questions = examQuestionRespond.questions!!
                                val intent =
                                    Intent(holder.binding.root.context, ExamActivity::class.java)
                                val bundle = Bundle()
                                val list = mutableListOf<Int>()
                                for (question in questions)
                                    list.add(question.mcqId.toInt())
                                bundle.putIntegerArrayList("question", list as ArrayList<Int>)
                                bundle.putString("token",token)
                                bundle.putInt("examId",holder.examId)
                                bundle.putLong("endExamTime",SubjectViewHolder.endExamTime[position])
                                intent.putExtras(bundle)
                                holder.binding.root.context.startActivity(intent)
                                val activity = holder.binding.root.context as Activity
                                activity.finish()
                            } else
                            {
                                MaterialAlertDialogBuilder(holder.binding.root.context)
                                    .setTitle("Permission Denied\uD83D\uDED1✋")
                                    .setMessage(examQuestionRespond.msg)
                                    .setCancelable(false)
                                    .setIcon(R.drawable.logo)
                                    .setPositiveButton("OK") { _, _ ->
                                    }
                                    .show()
                            }
                        } catch (ex: Exception) {
                        Log.d("TAG", "getStudentSubject: " + ex.message)
                    }

                }
            }else{
                MaterialAlertDialogBuilder(holder.binding.root.context)
                    .setTitle("No Exam available\uD83D\uDE14")
                    .setMessage("Wait us soon,we will provide you with more exam\uD83D\uDE0A")
                    .setCancelable(false)
                    .setIcon(R.drawable.logo)
                    .setPositiveButton("OK") { _, _ ->
                    }
                    .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return subjects.value!!.size
    }
}