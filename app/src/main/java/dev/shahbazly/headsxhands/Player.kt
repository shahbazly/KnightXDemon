package dev.shahbazly.headsxhands

class Player(
    name: String,
    attack: Int,
    defense: Int,
    health: Int,
    damage: IntRange,
    private val creatureAnimationManager: CreatureAnimationManager
) : Creature(name, attack, defense, health, damage) {

    init {
        creatureAnimationManager.restartCreature(health)
        creatureAnimationManager.setIdleDrawableRes(R.drawable.knight_idle_animation)

        setCreatureState(CreatureState.IDLE)
    }

    override fun setCreatureState(newState: CreatureState) = when (newState) {
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
                creatureAnimationManager.updateHealth(getHealth())
            }
        }

        CreatureState.DIE -> {
            creatureAnimationManager.updateHealth(getHealth())
            creatureAnimationManager.playAnimation(R.drawable.knight_death_animation)
        }
    }

    override fun heal() {
        TODO("Not yet implemented")
    }
}