package dev.shahbazly.headsxhands

import dev.shahbazly.headsxhands.CreatureState.ATTACK
import dev.shahbazly.headsxhands.CreatureState.DEFEND
import dev.shahbazly.headsxhands.CreatureState.DIE
import dev.shahbazly.headsxhands.CreatureState.TAKE_HIT
import kotlin.random.Random
import kotlin.random.nextInt

abstract class Creature(
    val name: String,
    private var attack: Int,
    private var defense: Int,
    private var health: Int,
    private val damage: IntRange
) {
    init {
        require(attack in 1..30) { "Атака должна быть в диапазоне от 1 до 30" }
        require(defense in 1..30) { "Защита должна быть в диапазоне от 1 до 30" }
        require(health >= 0) { "Здоровье не может быть отрицательным" }
        require(damage.first > 0 && damage.last <= attack) { "Диапазон урона должен быть больше 0 и меньше либо равен атаке" }
    }

    protected abstract fun setCreatureState(newState: CreatureState)

    fun attack(target: Creature): List<Int> {
        require(target != this) { "Существо не может атаковать самого себя" }
        val attackModifier = attack - target.defense + 1
        val diceRolls = List(attackModifier.coerceAtLeast(1)) { Random.nextInt(1, 7) }
        val successfulAttack = diceRolls.any { it in 5..6 }

        setCreatureState(ATTACK)

        if (successfulAttack) {
            val damage = Random.nextInt(damage)
            target.takeDamage(damage)
        } else {
            target.takeDamage(0)
        }

        return diceRolls
    }

    private fun takeDamage(damage: Int) {
        if (damage > 0) {
            health -= damage
            if (health <= 0) {
                health = 0
                setCreatureState(DIE)
            } else {
                setCreatureState(TAKE_HIT)
            }
        } else {
            setCreatureState(DEFEND)
        }
    }

    abstract fun heal()

    fun isAlive(): Boolean {
        return health > 0
    }

    fun getHealth() = this.health
}