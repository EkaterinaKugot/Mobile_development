package com.example.memorina

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val numberOfPairs = 8 // Количество пар карт
    private lateinit var cardViews: MutableList<ImageView>
    private var currentCard: ImageView? = null
    private var foundPairs = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardViews = mutableListOf()
        for (i in 0 until numberOfPairs * 2) {
            val cardId = resources.getIdentifier("card_$i", "id", packageName)
            val card = findViewById<ImageView>(cardId)
            card.tag = i % numberOfPairs
            card.setOnClickListener { onCardClicked(card) }
            cardViews.add(card)
        }
        newGame()
    }

    fun resetGame(v: View) {
        newGame()
    }

    private fun newGame() {
        currentCard = null
        foundPairs = 0
        randomCards()
        closeAllCards()
    }

    private fun randomCards() {
        val tags = MutableList(numberOfPairs * 2) { it % numberOfPairs }
        tags.shuffle()
        cardViews.forEachIndexed { index, card ->
            card.tag = tags[index]
        }
    }

    private fun closeAllCards() {
        cardViews.forEach { card ->
            card.setImageResource(R.drawable.card_back)
            card.isClickable = true
        }
    }

    private fun onCardClicked(card: ImageView) {
        if (card == currentCard || card.drawable == getCardBackDrawable()) {
            return
        }
        card.setImageResource(getImageForCard(card.tag as Int))
        if (currentCard == null) {
            currentCard = card
        } else {
            val tag1 = currentCard!!.tag as Int
            val tag2 = card.tag as Int
            if (tag1 == tag2) {
                foundPairs++
                currentCard!!.isClickable = false
                card.isClickable = false
                currentCard = null

                if (foundPairs == numberOfPairs) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Вы выйграли!", Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(700)
                    currentCard!!.setImageResource(R.drawable.card_back)
                    card.setImageResource(R.drawable.card_back)
                    currentCard = null
                }
            }
        }
    }

    private fun getImageForCard(tag: Int): Int {
        return when (tag) {
            0 -> R.drawable.card_0
            1 -> R.drawable.card_1
            2 -> R.drawable.card_2
            3 -> R.drawable.card_3
            4 -> R.drawable.card_4
            5 -> R.drawable.card_5
            6 -> R.drawable.card_6
            7 -> R.drawable.card_7
            else -> R.drawable.ic_launcher_foreground
        }
    }

    private fun getCardBackDrawable(): Drawable {
        return resources.getDrawable(R.drawable.card_back, null)
    }


}