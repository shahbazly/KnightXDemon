package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes

class CreatureAnimationManager(private val avatar: ImageView, private val healthBar: ProgressBar) {
    private var currentAnimation: AnimationDrawable? = null

    @DrawableRes
    private var idleDrawableRes: Int? = null


    fun setIdleDrawableRes(@DrawableRes animationDrawable: Int) {
        idleDrawableRes = animationDrawable
    }

    /**
     * Воспроизводится зацикленная анимация
     */
    fun playAnimation(@DrawableRes animationDrawable: Int, onAnimationComplete: () -> Unit? = {}) {
        currentAnimation?.stop()

        avatar.setBackgroundResource(animationDrawable)
        currentAnimation = avatar.background as AnimationDrawable

        currentAnimation?.callback = object : AnimationDrawableCallback(currentAnimation!!, avatar) {
            override fun onAnimationComplete() {
                onAnimationComplete()
            }
        }
        currentAnimation?.start()
    }

    /**
     * Воспроизводится незацикленная анимация (прим. атаки или обороны)
     * после которой воспроизводится анимация ожидания (idle animation)
     */
    fun playSingleAnimation(
        @DrawableRes animationDrawable: Int,
        onAnimationComplete: () -> Unit? = {}
    ) {
        currentAnimation?.stop()

        avatar.setBackgroundResource(animationDrawable)
        currentAnimation = avatar.background as AnimationDrawable

        currentAnimation?.callback = object : AnimationDrawableCallback(currentAnimation!!, avatar) {
            override fun onAnimationComplete() {
                onAnimationComplete()
                playIdleAnimation()
            }
        }
        currentAnimation?.start()
    }

    fun playIdleAnimation() {
        idleDrawableRes?.let { resource ->
            currentAnimation?.stop()

            avatar.setBackgroundResource(resource)
            currentAnimation = avatar.background as AnimationDrawable

            currentAnimation?.start()
        }
    }

    fun restartCreature(health: Int) {
        healthBar.max = health
        healthBar.progress = health
    }

    fun updateHealth(health: Int) {
        healthBar.progress = health
    }

}