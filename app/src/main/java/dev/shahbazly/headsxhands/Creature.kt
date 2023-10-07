package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import dev.shahbazly.headsxhands.CreatureState.ATTACK
import dev.shahbazly.headsxhands.CreatureState.DEFEND
import dev.shahbazly.headsxhands.CreatureState.DIE
import dev.shahbazly.headsxhands.CreatureState.TAKE_HIT
import kotlin.random.Random

abstract class Creature {
    abstract val name: String
    abstract val avatar: ImageView
    abstract var attack: Int
    abstract var defense: Int
    abstract var health: Int
    abstract val damage: Int

    protected abstract fun setCreatureState(newState: CreatureState)

    fun attack(target: Creature) {
        val attackModifier = attack - target.defense + 1
        val diceRolls = List(attackModifier.coerceAtLeast(1)) { Random.nextInt(1, 7) }
        val successfulAttack = diceRolls.any { it in 5..6 }

        setCreatureState(ATTACK)

        if (successfulAttack) {
            val damage = Random.nextInt(damage)
            Log.e(name,"$name attack dmg: $damage")
            target.takeDamage(damage)
        } else {
            Log.e(name,"$name attack dmg: 0")

            target.takeDamage(0)
        }
    }

    private fun takeDamage(damage: Int) {
        if (damage > 0) {
            health -= damage
            if (health <= 0) {
                setCreatureState(DIE)
                health = 0
            } else {
                setCreatureState(TAKE_HIT)
            }
        } else {
            setCreatureState(DEFEND)
        }
        Log.e(name,"$name health: $health")
    }

    abstract fun heal()

    fun isAlive(): Boolean {
        return health > 0
    }

    fun printHealth() {
        Log.e(name,"health: $health")
    }
}