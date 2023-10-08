package dev.shahbazly.headsxhands

class Player(
    name: String,
    attack: Int,
    defense: Int,
    health: Int,
    damage: IntRange,
    private val creatureAnimationManager: CreatureAnimationManager
) : Creature(name, attack, defense, health, damage) {

    private val maxHealth = health
    private var maxHealings = 4
    private val maxHealingPercentage = 30

    init {
        creatureAnimationManager.restartCreature(health, maxHealings)
        creatureAnimationManager.setIdleDrawableRes(R.drawable.knight_idle_animation)

        setCreatureState(CreatureState.IDLE)
    }

    override fun setCreatureState(state: CreatureState) = when (state) {
        CreatureState.IDLE -> {
            creatureAnimationManager.playAnimation(R.drawable.knight_idle_animation)
        }

        CreatureState.ATTACK -> {
            creatureAnimationManager.playSingleAnimation(R.drawable.knight_attack_animation)
        }

        CreatureState.DEFEND -> {
            creatureAnimationManager.playSingleAnimation(R.drawable.knight_defend_animation)
        }

        CreatureState.TAKE_HIT -> {
            creatureAnimationManager.playSingleAnimation(R.drawable.knight_take_hit_animation) {
                creatureAnimationManager.updateHealthBar(getHealth())
            }
        }

        CreatureState.DIE -> {
            creatureAnimationManager.updateHealthBar(getHealth())
            creatureAnimationManager.playAnimation(R.drawable.knight_death_animation)
        }
    }

    fun heal(): String {
        return if (isAlive() && maxHealings > 0) {
            val playerCurrentHealth = getHealth()
            val maxHealingAmount = (maxHealth * maxHealingPercentage / 100).coerceAtMost(maxHealth)
            val healing = (playerCurrentHealth + maxHealingAmount).coerceAtMost(maxHealth)

            setHealth(healing)

            maxHealings--
            creatureAnimationManager.updateHealingBar(maxHealings)
            creatureAnimationManager.updateHealthBar(healing)

            "Heal $name ($maxHealings)"
        } else {
            "No healings"
        }
    }
}