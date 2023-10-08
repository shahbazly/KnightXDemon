package dev.shahbazly.headsxhands

import android.graphics.Color
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
    private lateinit var knightHealButton: Button

    private lateinit var knightHealthBar: ProgressBar
    private lateinit var knightHealingthBar: ProgressBar
    private lateinit var demonAHealthBar: ProgressBar

    private lateinit var dicesContainer: FlexboxLayout

    private lateinit var knight: Player
    private lateinit var demon: Monster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        knightImage = findViewById(R.id.knightImage)
        demonImage = findViewById(R.id.demonImage)

        dicesContainer = findViewById(R.id.dicesFlexboxLayout)

        restartButton = findViewById(R.id.restartButton)
        gameResultText = findViewById(R.id.gameResultTextView)

        demonAttackButton = findViewById(R.id.demonAttackButton)
        knightAttackButton = findViewById(R.id.knightAttackButton)
        knightHealButton =findViewById(R.id.knightHealButton)

        knightHealthBar = findViewById(R.id.playerHealthProgressBar)
        knightHealingthBar = findViewById(R.id.playerHealProgressBar)
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
            attack = 28,
            defense = 25,
            health = 100,
            damage = 15..20,
            creatureAnimationManager = CreatureAnimationManager(
                avatar = demonImage,
                healthBar = demonAHealthBar
            )
        )
        knight = Player(
            name = "Knight",
            attack = 26,
            defense = 21,
            health = 100,
            damage = 15..20,
            creatureAnimationManager = CreatureAnimationManager(
                avatar = knightImage,
                healthBar = knightHealthBar,
                healingBar = knightHealingthBar)
        )
        val diceManager = DiceManager(this, dicesContainer)

        demonNameText.text = demon.name
        demonAttackButton.text = "Attack ${knight.name}"

        knightNameText.text = knight.name
        knightAttackButton.text = "Attack ${demon.name}"

        demonAttackButton.setOnClickListener {
            demon.attack(knight, diceManager)

            demonAttackButton.isClickable = false
            knightAttackButton.isClickable = knight.isAlive()

            if (!knight.isAlive()) showResult(demon)
        }

        knightAttackButton.setOnClickListener {
            knight.attack(demon, diceManager)

            knightAttackButton.isClickable = false
            demonAttackButton.isClickable = demon.isAlive()

            if (!demon.isAlive()) showResult(knight)
        }
        knightHealButton.setOnClickListener {
            knightHealButton.text = knight.heal()
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
}