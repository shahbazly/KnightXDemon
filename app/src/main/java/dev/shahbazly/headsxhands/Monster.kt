package dev.shahbazly.headsxhands

class Monster(
    name: String,
    attack: Int,
    defense: Int,
    health: Int,
    damage: IntRange,
    private val creatureAnimationManager: CreatureAnimationManager
) : Creature(name, attack, defense, health, damage) {

    init {
        creatureAnimationManager.setHealth(health)
        creatureAnimationManager.setIdleDrawableRes(R.drawable.demon_idle_animation)

        setCreatureState(CreatureState.IDLE)
    }

    override fun setCreatureState(state: CreatureState) = when (state) {
        CreatureState.IDLE -> {
            creatureAnimationManager.playAnimation(
                CreatureState.IDLE,
                R.drawable.demon_idle_animation
            )
        }

        CreatureState.ATTACK -> {
            creatureAnimationManager.playSingleAnimation(
                CreatureState.ATTACK,
                R.drawable.demon_attack_animation
            )
        }

        CreatureState.DEFEND -> {
            creatureAnimationManager.playSingleAnimation(
                CreatureState.DEFEND,
                R.drawable.demon_take_hit_animation
            ) {
                creatureAnimationManager.updateHealthBar(getHealth())
            }
        }

        CreatureState.TAKE_HIT -> {
            creatureAnimationManager.playSingleAnimation(
                CreatureState.TAKE_HIT,
                R.drawable.demon_take_hit_animation
            ) {
                creatureAnimationManager.updateHealthBar(getHealth())
            }
        }

        CreatureState.DIE -> {
            creatureAnimationManager.updateHealthBar(getHealth())
            creatureAnimationManager.playAnimation(
                CreatureState.DIE,
                R.drawable.demon_death_animation
            )
        }
    }
}