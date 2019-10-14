package com.example.dietapp.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field


class NonSwipeableViewPager: ViewPager {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    init {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val scroller: Field = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, NonSwipeableScroller(context)) // context == null?
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false

    override fun onTouchEvent(ev: MotionEvent?) = false

    class NonSwipeableScroller(context: Context): Scroller(context){
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 350)
        }
    }
}