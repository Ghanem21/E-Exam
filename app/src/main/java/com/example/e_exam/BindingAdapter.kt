package com.example.e_exam

import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.e_exam.fragment.SplashScreenFragment

@BindingAdapter("animation")
fun bindSplashScreenAnimation(logo: ImageView, splashScreenFragment: SplashScreenFragment?){
    splashScreenFragment.let{
        splashScreenFragment!!.navigate()
        val animeFade = loadAnimation(splashScreenFragment.context,R.anim.faded_animation)
        logo.startAnimation(animeFade)
    }

}
class BindingAdapter {
}