package com.example.sea_fight

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private val boardSize = 10
    private val shipSizes = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    val LIGHT_BLUE = Color.rgb(173, 216, 230)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gridLayout = findViewById(R.id.gridLayout)
        setupBoard()
    }

    private fun setupBoard() {
        gridLayout.columnCount = boardSize
        gridLayout.rowCount = boardSize

        val board = Array(boardSize) { IntArray(boardSize) }

        for (size in shipSizes) {
            var isPlaced = false
            while (!isPlaced) {
                val orientation = (0..1).random() // 0 for horizontal, 1 for vertical
                val row = (0 until boardSize).random()
                val col = (0 until boardSize).random()

                if (canPlaceShip(board, row, col, size, orientation)) {
                    placeShip(board, row, col, size, orientation)
                    isPlaced = true
                }
            }
        }

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val textView = TextView(this)
                textView.textSize = 18f
                textView.gravity = Gravity.CENTER
                textView.setBackgroundColor(if (board[i][j] == 1) Color.BLUE else LIGHT_BLUE)
                val params = GridLayout.LayoutParams()
                params.width = 100
                params.height = 100
                params.setMargins(2, 2, 2, 2)
                params.rowSpec = GridLayout.spec(i)
                params.columnSpec = GridLayout.spec(j)
                textView.layoutParams = params
                gridLayout.addView(textView)
            }
        }
    }

    private fun canPlaceShip(
        board: Array<IntArray>,
        row: Int,
        col: Int,
        size: Int,
        orientation: Int
    ): Boolean {
        if (orientation == 0 && col + size > boardSize) return false
        if (orientation == 1 && row + size > boardSize) return false

        for (i in -1..size) {
            for (j in -1..1) {
                val r = row + i
                val c = col + j

                if (r in 0 until boardSize && c in 0 until boardSize) {
                    if (board.getOrNull(r)?.getOrNull(c) == 1) return false
                }
            }
        }
        return true
    }

    private fun placeShip(board: Array<IntArray>, row: Int, col: Int, size: Int, orientation: Int) {
        for (i in 0 until size) {
            if (orientation == 0) board[row][col + i] = 1
            if (orientation == 1) board[row + i][col] = 1
        }
    }

    companion object {
        val LIGHT_BLUE = Color.rgb(173, 216, 230)
    }
}