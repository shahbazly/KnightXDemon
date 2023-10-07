package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.widget.ImageView
import java.util.logging.Handler

class Player(
    override val avatar: ImageView,
    override val name: String,
    override var attack: Int,
    override var defense: Int,
    override var health: Int,
    override val damage: Int
): Creature() {

    init {
        setCreatureState(CreatureState.IDLE)
    }

    override fun setCreatureState(newState: CreatureState) = when (newState) {
        CreatureState.IDLE -> {
            avatar.setBackgroundResource(R.drawable.knight_idle_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.start()
        }
        CreatureState.ATTACK -> {
            avatar.setBackgroundResource(R.drawable.knight_attack_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.callback =
                object : AnimationDrawableCallback(creatureAnimation, avatar) {
                    override fun onAnimationComplete() {
                        creatureAnimation.stop()
                        avatar.setBackgroundResource(R.drawable.knight_idle_animation)
                        val anim = avatar.background as AnimationDrawable
                        anim.start()
                    }
                }
            creatureAnimation.start()
        }
        CreatureState.DEFEND -> {
            avatar.setBackgroundResource(R.drawable.knight_defend_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.callback =
                object : AnimationDrawableCallback(creatureAnimation, avatar) {
                    override fun onAnimationComplete() {
                        creatureAnimation.stop()
                        avatar.setBackgroundResource(R.drawable.knight_idle_animation)
                        val anim = avatar.background as AnimationDrawable
                        anim.start()
                    }
                }
            creatureAnimation.start()
        }
        CreatureState.DIE -> {
            avatar.setBackgroundResource(R.drawable.knight_death_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.start()
        }
        CreatureState.TAKE_HIT -> {
            avatar.setBackgroundResource(R.drawable.knight_take_hit_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.callback =
                object : AnimationDrawableCallback(creatureAnimation, avatar) {
                    override fun onAnimationComplete() {
                        creatureAnimation.stop()
                        avatar.setBackgroundResource(R.drawable.knight_idle_animation)
                        val anim = avatar.background as AnimationDrawable
                        anim.start()
                    }
                }
            creatureAnimation.start()
        }
    }

    override fun heal() {
        TODO("Not yet implemented")
    }
}