package com.example.e_exam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_exam.databinding.OptionRadioButtonBinding
import com.example.e_exam.network.mcqOptions.Option

class RadioButtonAdapter( private val options: List<Option>) :
    RecyclerView.Adapter<RadioButtonAdapter.RadioButtonViewHolder>() {
    class RadioButtonViewHolder(val binding: OptionRadioButtonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioButtonViewHolder {
        return RadioButtonViewHolder(OptionRadioButtonBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RadioButtonViewHolder, position: Int) {
        val item = options[position]
        holder.binding.option.text = item.answer
    }

    override fun getItemCount(): Int {
        return options.size
    }
}