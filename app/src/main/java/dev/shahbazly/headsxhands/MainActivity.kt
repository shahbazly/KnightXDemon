package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.FlexboxLayout

class MainActivity : AppCompatActivity() {

    private lateinit var knightImage: ImageView
    private lateinit var demonImage: ImageView

    private lateinit var knightDiceContainer: FlexboxLayout
    private lateinit var demonDiceContainer: FlexboxLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        knightImage = findViewById(R.id.knightImage)
        demonImage = findViewById(R.id.demonImage)

        knightDiceContainer = findViewById(R.id.playerDicesLayout)
        demonDiceContainer = findViewById(R.id.monsterDicesLayout)

        val knight = Player(name = "Knight", avatar = knightImage, attack = 20, defense = 16, health = 100, damage = 15..20)
        val demon = Monster(name = "Demon", avatar = demonImage, attack = 15, defense = 15, health = 100, damage = 10..15)

        val demonAttackButton = findViewById<Button>(R.id.demonAttackButton)
        val knightAttackButton = findViewById<Button>(R.id.knightAttackButton)

        demonAttackButton.setOnClickListener {
            demonDiceContainer.removeAllViews()

            val dice = demon.attack(knight)
            animateDices(demonDiceContainer, dice)

            Log.e("demon", "demon dices: $dice")
            demonAttackButton.isClickable = false
            knightAttackButton.isClickable = knight.isAlive()
        }

        knightAttackButton.setOnClickListener {
            knightDiceContainer.removeAllViews()

            val dice = knight.attack(demon)
            animateDices(knightDiceContainer, dice)

            Log.e("knight", "knight dices: $dice")
            knightAttackButton.isClickable = false
            demonAttackButton.isClickable = demon.isAlive()
        }

    }

    private fun animateDices(container: FlexboxLayout, diceResults: List<Int>) {
        diceResults.forEach { diceResult ->
            val diceImage = ImageView(this)

            val layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,FlexboxLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(48,48,48,48)
            diceImage.layoutParams = layoutParams

            diceImage.scaleX = 2.5f
            diceImage.scaleY = 2.5f
            diceImage.setImageResource(
                when(diceResult) {
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