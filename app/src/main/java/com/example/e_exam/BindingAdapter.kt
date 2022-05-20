package com.example.e_exam

import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.e_exam.fragment.SplashScreenFragment
import com.example.e_exam.network.levelsAndDepartments.Department
import com.example.e_exam.network.levelsAndDepartments.Level

@BindingAdapter("animation")
fun bindSplashScreenAnimation(logo: ImageView, splashScreenFragment: SplashScreenFragment?) {
    splashScreenFragment.let {
        splashScreenFragment!!.navigate()
        val animeFade = loadAnimation(splashScreenFragment.context, R.anim.faded_animation)
        logo.startAnimation(animeFade)
    }

}

@BindingAdapter("adapterDepartment")
fun bindDepartment(
    autoCompleteTextView: AutoCompleteTextView,
    departments: LiveData<List<Department>>?
) {
    departments.let {
        val items: List<Department>? = departments?.value
        if (items != null) {
            autoCompleteTextView.setAdapter(
                ArrayAdapter(
                    autoCompleteTextView.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    items
                )
            )
        }
    }
}


@BindingAdapter("adapterLevel")
fun bindLevel(autoCompleteTextView: AutoCompleteTextView, levels: LiveData<List<Level>>?) {
    levels.let {
        val items: List<Level>? = levels?.value
        if (items != null) {
            autoCompleteTextView.setAdapter(
                ArrayAdapter(
                    autoCompleteTextView.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    items
                )
            )
        }
    }
}

class BindingAdapter {
}