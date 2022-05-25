package com.example.e_exam

import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import coil.load
import com.example.e_exam.fragment.SplashScreenFragment
import com.example.e_exam.network.levelsAndDepartments.Department
import com.example.e_exam.network.levelsAndDepartments.Level
import com.example.e_exam.viewModels.ExamApiStatus

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
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val url = "https://e-exam.ahmed-projects.me$imgUrl"
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: ExamApiStatus?) {
    when (status) {

        ExamApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        ExamApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        ExamApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

class BindingAdapter {
}