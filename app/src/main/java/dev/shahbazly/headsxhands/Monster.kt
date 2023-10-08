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
        creatureAnimationManager.restartCreature(health)
        creatureAnimationManager.setIdleDrawableRes(R.drawable.demon_idle_animation)

        setCreatureState(CreatureState.IDLE)
    }

    override fun setCreatureState(state: CreatureState) = when (state) {
        CreatureState.IDLE -> {
            creatureAnimationManager.playAnimation(R.drawable.demon_idle_animation)
        }

        CreatureState.ATTACK -> {
            creatureAnimationManager.playSingleAnimation(R.drawable.demon_attack_animation)
        }

        CreatureState.DEFEND, CreatureState.TAKE_HIT -> {
            creatureAnimationManager.playSingleAnimation(R.drawable.demon_take_hit_animation) {
                creatureAnimationManager.updateHealth(getHealth())
            }
        }

        CreatureState.DIE -> {
            creatureAnimationManager.updateHealth(getHealth())
            creatureAnimationManager.playAnimation(R.drawable.demon_death_animation)
        }
    }

    override fun heal() {
        TODO("Not yet implemented")
    }
}