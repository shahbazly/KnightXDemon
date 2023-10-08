package dev.shahbazly.headsxhands

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.flexbox.FlexboxLayout


class MainActivity : AppCompatActivity() {

    private lateinit var gameResultText: TextView
    private lateinit var knightNameText: TextView
    private lateinit var demonNameText: TextView

    private lateinit var knightImage: ImageView
    private lateinit var demonImage: ImageView

    private lateinit var restartButton: Button
    private lateinit var demonAttackButton: Button
    private lateinit var knightAttackButton: Button

    private lateinit var knightHealthBar: ProgressBar
    private lateinit var demonAHealthBar: ProgressBar

    private lateinit var knightDiceContainer: FlexboxLayout
    private lateinit var demonDiceContainer: FlexboxLayout

    private lateinit var knight: Player
    private lateinit var demon: Monster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        knightImage = findViewById(R.id.knightImage)
        demonImage = findViewById(R.id.demonImage)

        knightDiceContainer = findViewById(R.id.playerDicesLayout)
        demonDiceContainer = findViewById(R.id.monsterDicesLayout)

        restartButton = findViewById(R.id.restartButton)
        gameResultText = findViewById(R.id.gameResultTextView)

        demonAttackButton = findViewById(R.id.demonAttackButton)
        knightAttackButton = findViewById(R.id.knightAttackButton)

        knightHealthBar = findViewById(R.id.playerHealthProgressBar)
        demonAHealthBar = findViewById(R.id.monsterHealthProgressBar)

        knightNameText = findViewById(R.id.playerNameTextView)
        demonNameText = findViewById(R.id.monsterNameTextView)


        startGame()

        restartButton.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        restartButton.visibility = View.GONE
        gameResultText.visibility = View.GONE

        demon = Monster(
            name = "Demon",
            avatar = demonImage,
            healthBar = demonAHealthBar,
            attack = 30,
            defense = 30,
            health = 50,
            damage = 15..20
        )
        knight = Player(
            name = "Knight",
            avatar = knightImage,
            healthBar = knightHealthBar,
            attack = 30,
            defense = 30,
            health = 50,
            damage = 15..20
        )

        demonNameText.text = demon.name
        demonAttackButton.text = "Attack ${knight.name}"

        knightNameText.text = knight.name
        knightAttackButton.text = "Attack ${demon.name}"

        demonAttackButton.setOnClickListener {
            demonDiceContainer.removeAllViews()

            val dice = demon.attack(knight)
            animateDices(demonDiceContainer, dice)

            demonAttackButton.isClickable = false
            knightAttackButton.isClickable = knight.isAlive()

            if (!knight.isAlive()) showResult(demon)
        }

        knightAttackButton.setOnClickListener {
            knightDiceContainer.removeAllViews()

            val dice = knight.attack(demon)
            animateDices(knightDiceContainer, dice)

            knightAttackButton.isClickable = false
            demonAttackButton.isClickable = demon.isAlive()

            if (!demon.isAlive()) showResult(knight)
        }
    }

    private fun showResult(winner: Creature) {
        restartButton.visibility = View.VISIBLE
        gameResultText.visibility = View.VISIBLE

        if (winner is Player) {
            val typeface = ResourcesCompat.getFont(baseContext, R.font.cloister)
            val color = Color.parseColor("#3042CC")

            gameResultText.setTextColor(color)
            restartButton.typeface = typeface
            gameResultText.typeface = typeface
            gameResultText.text = "${winner.name} wins"
        } else {
            val typeface = ResourcesCompat.getFont(baseContext, R.font.darkmode)
            val color = Color.parseColor("#F15156");

            gameResultText.setTextColor(color)
            restartButton.typeface = typeface
            restartButton.text = "RESTART"
            gameResultText.typeface = typeface
            gameResultText.text = "${winner.name.uppercase()} WINS"
        }
    }

    private fun animateDices(container: FlexboxLayout, diceResults: List<Int>) {
        diceResults.forEach { diceResult ->
            val diceImage = ImageView(this)

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