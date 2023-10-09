package dev.shahbazly.headsxhands

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private lateinit var knightAttackButton: Button
    private lateinit var knightHealButton: Button

    private lateinit var knightHealthBar: ProgressBar
    private lateinit var knightHealingthBar: ProgressBar
    private lateinit var demonHealthBar: ProgressBar

    private lateinit var dicesContainer: FlexboxLayout

    private lateinit var knight: Player
    private lateinit var demon: Monster
    private lateinit var diceManager: DiceManager

    private val monsterDelayBeforeAttack = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        startGame()

        restartButton.setOnClickListener {
            startGame()
        }
    }

    private fun initViews() {
        knightImage = findViewById(R.id.knightImage)
        demonImage = findViewById(R.id.demonImage)

        dicesContainer = findViewById(R.id.dicesFlexboxLayout)

        restartButton = findViewById(R.id.restartButton)
        gameResultText = findViewById(R.id.gameResultTextView)

        knightAttackButton = findViewById(R.id.knightAttackButton)
        knightHealButton = findViewById(R.id.knightHealButton)

        knightHealthBar = findViewById(R.id.playerHealthProgressBar)
        knightHealingthBar = findViewById(R.id.playerHealProgressBar)
        demonHealthBar = findViewById(R.id.monsterHealthProgressBar)

        knightNameText = findViewById(R.id.playerNameTextView)
        demonNameText = findViewById(R.id.monsterNameTextView)
    }

    private fun startGame() {
        restartButton.visibility = View.GONE
        gameResultText.visibility = View.GONE

        initPlayers()
        initKnightControls()
    }

    private fun initKnightControls() {
        knightAttackButton.isEnabled = true
        knightHealButton.isEnabled = knight.isHealAvailable()

        knightAttackButton.setOnClickListener {
            knight.attack(demon, diceManager)
            knightAttackButton.isEnabled = false
            knightHealButton.isEnabled = false
        }

        knightHealButton.setOnClickListener {
            knight.heal()
            knightHealButton.isEnabled = knight.isHealAvailable()
        }
    }

    private fun initPlayers() {
        val demonAnimationManager = CreatureAnimationManager(
            avatar = demonImage,
            healthBar = demonHealthBar
        )

        val knightAnimationManager = CreatureAnimationManager(
            avatar = knightImage,
            healthBar = knightHealthBar,
            healingBar = knightHealingthBar
        )

        demonAnimationManager.setAnimationListener(object : CreatureAnimationListener {
            override fun onDeathAnimationFinished() {
                showResult()
            }

            override fun onAttackAnimationFinished() {
                if (knight.isAlive()) {
                    knightAttackButton.isEnabled = true
                    knightHealButton.isEnabled = knight.isHealAvailable()
                } else {
                    showResult()
                }
            }
        })

        knightAnimationManager.setAnimationListener(object : CreatureAnimationListener {
            override fun onDeathAnimationFinished() {
                showResult()
            }

            override fun onAttackAnimationFinished() {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (demon.isAlive())
                        demon.attack(knight, diceManager)
                    else
                        showResult()
                }, monsterDelayBeforeAttack)
            }
        })

        demon = Monster(
            name = "Demon",
            attack = 28,
            defense = 23,
            health = 100,
            damage = 15..28,
            creatureAnimationManager = demonAnimationManager
        )

        knight = Player(
            name = "Knight",
            attack = 26,
            defense = 22,
            health = 100,
            damage = 1..26,
            creatureAnimationManager = knightAnimationManager
        )

        diceManager = DiceManager(this, dicesContainer)

        demonNameText.text = demon.name
        knightNameText.text = knight.name
    }

    private fun showResult() {
        restartButton.visibility = View.VISIBLE
        gameResultText.visibility = View.VISIBLE

        if (knight.isAlive()) {
            val typeface = ResourcesCompat.getFont(baseContext, R.font.cloister)
            val color = Color.parseColor("#3042CC")

            gameResultText.setTextColor(color)
            restartButton.typeface = typeface
            restartButton.text = "Restart"
            gameResultText.typeface = typeface
            gameResultText.text = "${knight.name} wins"
        } else {
            val typeface = ResourcesCompat.getFont(baseContext, R.font.darkmode)
            val color = Color.parseColor("#F15156");

            gameResultText.setTextColor(color)
            restartButton.typeface = typeface
            restartButton.text = "RESTART"
            gameResultText.typeface = typeface
            gameResultText.text = "${demon.name.uppercase()} WINS"
        }
    }
}