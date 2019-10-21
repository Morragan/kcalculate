package com.example.dietapp.utils

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import kotlin.math.abs


class ProgressBarAnimator(private val progressBar: ProgressBar, fullDuration: Long) : Animation() {
    private val stepDuration = fullDuration / progressBar.max
    private var from = 0
    private var to = 0

    fun setProgress(progress: Int) {
        to = if (progress < 0) 0 else if (progress > progressBar.max) progressBar.max else progress
        from = progressBar.progress
        duration = abs(to - from) * stepDuration
        progressBar.startAnimation(this)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
    }
}