package com.example.e_exam.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.databinding.QuestionCardViewBinding
import com.example.e_exam.network.ExamApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionAdapter(private val questionsId: List<Int>, private val token: String) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    companion object {
        var answers = mutableListOf<String>()
    }

    class QuestionViewHolder(val binding: QuestionCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(questionId: Int, token: String) {
            answers.add(null.toString())
            CoroutineScope(Dispatchers.Main).launch {
                //try {
                    val getMcqOptionsRespond =
                        ExamApi.retrofitService.getMcqOptions(questionId, token)
                    val num = getMcqOptionsRespond.options!!.size
                    val radioButtons: MutableList<AppCompatRadioButton> = mutableListOf()
                binding.questionTv.text = getMcqOptionsRespond.options[adapterPosition].mcq.questionName
                    repeat(num) {
                        radioButtons.add(AppCompatRadioButton(binding.layout.context))
                    }

                    for (i in 0 until num) {
                        radioButtons[i].text = getMcqOptionsRespond.options[i].answer
                        radioButtons[i].id = i
                        radioButtons[i].layoutParams = RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }
                    val radioGroup = RadioGroup(binding.layout.context)
                    val params = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    radioGroup.layoutParams = params
                    for (i in 0 until num) {
                        radioGroup.addView(radioButtons[i])
                    }
                    binding.layout.addView(radioGroup)

                    radioGroup.setOnCheckedChangeListener { _, i ->
                        answers[adapterPosition] = radioButtons[i].text.toString()
                        Log.d("TAG", "getAnswer: $answers")
                    }
                /*} catch (ex: Exception) {
                    Log.d("TAG", "onBindViewHolder: " + ex.message)
                }*/
            }
        }
    }

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
        val questionId = questionsId[position]
        holder.bind(questionId, token)
    }


    override fun getItemCount(): Int {
        return questionsId.size
    }

    fun getAnswer(): MutableList<String> {
        return answers
    }
}