package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView

class Monster(
    name: String,
    val avatar: ImageView,
    attack: Int,
    defense: Int,
    health: Int,
    damage: IntRange
) : Creature(name, attack, defense, health, damage) {

    init {
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
                        creatureAnimation.stop()
                        avatar.setBackgroundResource(R.drawable.demon_idle_animation)
                        val anim = avatar.background as AnimationDrawable
                        anim.start()
                    }
                }
            creatureAnimation.start()
        }
        CreatureState.DIE -> {
            avatar.setBackgroundResource(R.drawable.demon_death_animation)
            val creatureAnimation = avatar.background as AnimationDrawable
            creatureAnimation.start()
        }
    }

    override fun heal() {
        TODO("Not yet implemented")
    }
}