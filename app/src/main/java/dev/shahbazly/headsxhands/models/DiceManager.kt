package dev.shahbazly.headsxhands.models

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import com.google.android.flexbox.FlexboxLayout
import dev.shahbazly.headsxhands.R
import kotlin.random.Random

class DiceManager(private val context: Context, private val container: FlexboxLayout) {

    fun rollDice(creature: Creature, numberOfDice: Int): Boolean {
        cleanUpContainer()
        val diceRolls = List(numberOfDice.coerceAtLeast(1)) {
            rollSingleDice()
        }
        animateDices(creature, diceRolls)

        return diceRolls.any { it in 5..6 }
    }

    private fun cleanUpContainer() {
        container.removeAllViews()
    }

    private fun rollSingleDice(): Int {
        return Random.nextInt(1, 7)
    }

    private fun animateDices(creature: Creature, diceResults: List<Int>) {
        diceResults.forEach { diceResult ->
            val diceImage = ImageView(context)
            if (creature is Player)
                diceImage.setColorFilter(Color.argb(70, 0, 200, 0))
            else
                diceImage.setColorFilter(Color.argb(70, 200, 0, 0))

            val layoutParams = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(48, 48, 48, 48)
            diceImage.layoutParams = layoutParams

            diceImage.scaleX = 2.5f
            diceImage.scaleY = 2.5f
            diceImage.setImageResource(
                when (diceResult) {
                    1 -> R.drawable.dice_animation_1
                    2 -> R.drawable.dice_animation_2
                    3 -> R.drawable.dice_animation_3
                    4 -> R.drawable.dice_animation_4
                    5 -> R.drawable.dice_animation_5
                    6 -> R.drawable.dice_animation_6
                    else -> R.drawable.dice_animation_error
                }
            )
            val animationDrawable = diceImage.drawable as AnimationDrawable
            container.addView(diceImage)
            animationDrawable.start()
        }
    }
}