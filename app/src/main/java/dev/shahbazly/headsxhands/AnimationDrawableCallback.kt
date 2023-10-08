package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable

abstract class AnimationDrawableCallback(
    animationDrawable: AnimationDrawable,
    private val wrappedCallback: Drawable.Callback?
) : Drawable.Callback {

    private val lastFrame: Drawable = animationDrawable.getFrame(animationDrawable.numberOfFrames - 1)
    private var isCallbackTriggered = false

    override fun invalidateDrawable(who: Drawable) {
        wrappedCallback?.invalidateDrawable(who)

        if (!isCallbackTriggered && lastFrame == who.current) {
            isCallbackTriggered = true
            onAnimationComplete()
        }
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, whenMillis: Long) {
        wrappedCallback?.scheduleDrawable(who, what, whenMillis)
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        wrappedCallback?.unscheduleDrawable(who, what)
    }

    abstract fun onAnimationComplete()
}