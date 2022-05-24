package com.example.e_exam.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.databinding.QuestionCardViewBinding
import com.example.e_exam.network.ExamApi
import com.example.e_exam.network.viewExam.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionAdapter(private val questions: List<Question>, private val token: String) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    companion object {
        var answers = mutableListOf<String>()
    }
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

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.binding.questionTv.text = question.mcq.questionName
        answers.add(null.toString())
        CoroutineScope(Dispatchers.Main).launch {
            //try {
                val getMcqOptionsRespond =
                    ExamApi.retrofitService.getMcqOptions(question.mcqId.toInt(), token)
                val num = getMcqOptionsRespond.options!!.size
                val radioButtons : MutableList<RadioButton> = mutableListOf()

                repeat (num) {
                    radioButtons.add(RadioButton(holder.binding.layout.context))
                }

                for (i in 0 until num){
                    radioButtons[i].text = getMcqOptionsRespond.options[i].answer
                    radioButtons[i].id = i
                    radioButtons[i].layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
                val radioGroup = RadioGroup(holder.binding.layout.context)
                val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                radioGroup.layoutParams = params
            for (i in 0 until num){
                radioGroup.addView(radioButtons[i])
            }
            holder.binding.layout.addView(radioGroup)

            radioGroup.setOnCheckedChangeListener { _, i ->
                answers[position] = radioButtons[i].text.toString()
                Log.d("TAG", "getAnswer: $answers")
            }
           /* } catch (ex: Exception) {
                Log.d("TAG", "onBindViewHolder: " + ex.message)
            }*/
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }
    fun getAnswer(): MutableList<String> {
        return answers
    }
}