package dev.shahbazly.headsxhands

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var knightImage: ImageView
    private lateinit var demonImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        knightImage = findViewById(R.id.knightImage)
        demonImage = findViewById(R.id.demonImage)

        val knight = Player(name = "Knight", avatar = knightImage, attack = 100, defense = 10, health = 10, damage = 15)
        val demon = Monster(name = "Demon", avatar = demonImage, attack = 150, defense = 16, health = 100, damage = 20)

        val demonAttackButton = findViewById<Button>(R.id.demonAttackButton)
        val knightAttackButton = findViewById<Button>(R.id.knightAttackButton)

        demonAttackButton.setOnClickListener {
            demon.attack(knight)
            demonAttackButton.isClickable = false
            knightAttackButton.isClickable = knight.isAlive()
        }

        knightAttackButton.setOnClickListener {
            knight.attack(demon)
            knightAttackButton.isClickable = false
            demonAttackButton.isClickable = demon.isAlive()
        }

    }
}