package dev.shahbazly.headsxhands

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var knightAnimation: AnimationDrawable
    private lateinit var demonAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val knightImage = findViewById<ImageView>(R.id.knightImage).apply {
            setBackgroundResource(R.drawable.knight_idle_animation)
            knightAnimation = background as AnimationDrawable
        }
        val demonImage = findViewById<ImageView>(R.id.demonImage).apply {
            setBackgroundResource(R.drawable.demon_idle_animation)
            demonAnimation = background as AnimationDrawable
        }

        startGame()
    }

    private fun startGame() {
        knightAnimation.start()
        demonAnimation.start()
    }
}