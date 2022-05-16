package com.example.e_exam

import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.e_exam.fragment.SignUpFragment
import com.example.e_exam.fragment.SplashScreenFragment

@BindingAdapter("animation")
fun bindSplashScreenAnimation(logo: ImageView, splashScreenFragment: SplashScreenFragment?){
    splashScreenFragment.let{
        splashScreenFragment!!.navigate()
        val animeFade = loadAnimation(splashScreenFragment.context,R.anim.faded_animation)
        logo.startAnimation(animeFade)
    }

}

@BindingAdapter("adapter")
fun bindSpinnerData(spinner : AutoCompleteTextView,signUpFragment: SignUpFragment?){
    signUpFragment.let {
        val departments = signUpFragment!!.viewModel.departments.value ?: listOf()
        val adapter = ArrayAdapter(
            signUpFragment.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            departments
        )
        spinner.setAdapter(adapter)
    }
}

class BindingAdapter {
}