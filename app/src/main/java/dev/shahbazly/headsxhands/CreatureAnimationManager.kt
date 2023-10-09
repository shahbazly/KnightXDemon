package dev.shahbazly.headsxhands

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes

class CreatureAnimationManager(
    private val avatar: ImageView,
    private val healthBar: ProgressBar,
    private val healingBar: ProgressBar? = null
) {
    private var currentAnimation: AnimationDrawable? = null
    private var creatureAnimationListener: CreatureAnimationListener? = null

    @DrawableRes
    private var idleDrawableRes: Int? = null

    fun setAnimationListener(listener: CreatureAnimationListener) {
        creatureAnimationListener = listener
    }

    /**
     * Воспроизводится зацикленная анимация
     */
    fun playAnimation(
        creatureState: CreatureState,
        @DrawableRes animationDrawable: Int,
        onAnimationComplete: () -> Unit? = {}
    ) {
        if (idleDrawableRes == null && creatureState == CreatureState.IDLE)
            idleDrawableRes = animationDrawable

        currentAnimation?.stop()

        avatar.setBackgroundResource(animationDrawable)
        currentAnimation = avatar.background as AnimationDrawable

        currentAnimation?.callback =
            object : AnimationDrawableCallback(currentAnimation!!, avatar) {
                override fun onAnimationComplete() {
                    if (creatureState == CreatureState.DIE)
                        creatureAnimationListener?.onDeathAnimationFinished()
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
        creatureState: CreatureState,
        @DrawableRes animationDrawable: Int,
        onAnimationComplete: () -> Unit? = {}
    ) {
        currentAnimation?.stop()

        avatar.setBackgroundResource(animationDrawable)
        currentAnimation = avatar.background as AnimationDrawable

        currentAnimation?.callback =
            object : AnimationDrawableCallback(currentAnimation!!, avatar) {
                override fun onAnimationComplete() {
                    if (creatureState == CreatureState.ATTACK)
                        creatureAnimationListener?.onAttackAnimationFinished()
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

    fun setHealth(health: Int, healingsRemain: Int) {
        healthBar.max = health
        healthBar.progress = health

        healingBar?.max = healingsRemain
        healingBar?.progress = healingsRemain
    }

    fun setHealth(health: Int) {
        healthBar.max = health
        healthBar.progress = health
    }

    fun updateHealthBar(health: Int) {
        val animation = ObjectAnimator.ofInt(healthBar, "progress", healthBar.progress, health)
        animation.duration = 200
        animation.setAutoCancel(true)
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    fun updateHealingBar(healingsRemain: Int) {
        healingBar?.let {
            val animation = ObjectAnimator.ofInt(it, "progress", it.progress, healingsRemain)
            animation.duration = 200
            animation.setAutoCancel(true)
            animation.interpolator = DecelerateInterpolator()
            animation.start()
        }
    }

}