package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import android.widget.ProgressBar

class Monster(
    name: String,
    private val avatar: ImageView,
    private val healthBar: ProgressBar,
    attack: Int,
    defense: Int,
    health: Int,
    damage: IntRange
) : Creature(name, attack, defense, health, damage) {

    init {
        healthBar.max = health
        healthBar.progress = health

        setCreatureState(CreatureState.IDLE)
    }
    override fun setCreatureState(newState: CreatureState) = when (newState) {
        CreatureState.IDLE -> {
            avatar.setBackgroundResource(R.drawable.demon_idle_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.start()
        }
        CreatureState.ATTACK -> {
            avatar.setBackgroundResource(R.drawable.demon_attack_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.callback =
                object : AnimationDrawableCallback(creatureAnimation, avatar) {
                    override fun onAnimationComplete() {
                        creatureAnimation.stop()
                        avatar.setBackgroundResource(R.drawable.demon_idle_animation)
                        val anim = avatar.background as AnimationDrawable
                        anim.start()
                    }
                }
            creatureAnimation.start()
        }
        CreatureState.DEFEND, CreatureState.TAKE_HIT -> {
            avatar.setBackgroundResource(R.drawable.demon_take_hit_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.callback =
                object : AnimationDrawableCallback(creatureAnimation, avatar) {
                    override fun onAnimationComplete() {
                        healthBar.progress = getHealth()
                        creatureAnimation.stop()
                        avatar.setBackgroundResource(R.drawable.demon_idle_animation)
                        val anim = avatar.background as AnimationDrawable
                        anim.start()
                    }
                }
            creatureAnimation.start()
        }
        CreatureState.DIE -> {
            healthBar.progress = getHealth()
            avatar.setBackgroundResource(R.drawable.demon_death_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.start()
        }
    }

    override fun heal() {
        TODO("Not yet implemented")
    }
}