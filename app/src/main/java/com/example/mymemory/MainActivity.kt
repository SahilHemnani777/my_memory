package com.example.mymemory

import BoardSize
import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.models.MemoryGame
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var clRoot : ConstraintLayout
    private lateinit var memoryGame: MemoryGame
    private lateinit var rvBoard : RecyclerView
    private lateinit var movesTextView : TextView
    private lateinit var pairsTextView: TextView

    private val boardSize: BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot= findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.recyclerView)
        movesTextView= findViewById(R.id.movesTextView)
        pairsTextView= findViewById(R.id.pairsTextView)

        memoryGame = MemoryGame(boardSize)

        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object : MemoryBoardAdapter.CardClickListener{
            override fun onCardClick(position: Int) {
                updateGameWIthFlip(position)
            }

        })
        rvBoard.hasFixedSize()
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())

    }

    private fun updateGameWIthFlip(position: Int) {
        if(memoryGame.haveWonGame()){
            // Alert
            Snackbar.make(clRoot, "You Already Won",Snackbar.LENGTH_LONG).show()
            return
        }
        if(memoryGame.isFlippedUp(position)){
            // Alert
            Snackbar.make(clRoot, "Invalid Move",Snackbar.LENGTH_SHORT).show()

            return
        }

        if(memoryGame.flipCard(position)) {
            Log.i("TAG", memoryGame.numPairsFound.toString())
            var color = ArgbEvaluator().evaluate(
                memoryGame.numPairsFound.toFloat() / boardSize.getNumPairs(),
                ContextCompat.getColor(this, R.color.color_progress_none),
                ContextCompat.getColor(this, R.color.color_progress_full)
            ) as Int
            pairsTextView.setTextColor(color)
            pairsTextView.text = "Pairs: ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"
            if (memoryGame.haveWonGame()) {
                Snackbar.make(clRoot, "You Won",Snackbar.LENGTH_LONG).show()
            }
        }
        movesTextView.text = "Moves: ${memoryGame.getNumMoves().toString()}"
        rvBoard.adapter?.notifyDataSetChanged()
    }


}