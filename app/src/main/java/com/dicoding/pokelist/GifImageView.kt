package com.dicoding.pokelist

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class GifImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var animationDrawable: AnimationDrawable? = null

    fun setGifImageResource(resId: Int) {
        setImageResource(resId)
        animationDrawable = drawable as? AnimationDrawable
    }

    fun startAnimation() {
        animationDrawable?.start()
    }

    fun stopAnimation() {
        animationDrawable?.stop()
    }
}