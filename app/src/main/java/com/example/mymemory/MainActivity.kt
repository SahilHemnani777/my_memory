package com.example.mymemory

import BoardSize
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.models.MemoryGame
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var clRoot : ConstraintLayout
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

        val memoryGame = MemoryGame(boardSize)

        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object : MemoryBoardAdapter.CardClickListener{
            override fun onCardClick(position: Int) {

                if(memoryGame.haveWonGame()){
                    // Alert
                    Snackbar.make(clRoot, "You Already Wion",Snackbar.LENGTH_LONG).show()
                    return
                }
                if(memoryGame.isFlippedUp(position)){
                    // Alert
                    Snackbar.make(clRoot, "Invalid Move",Snackbar.LENGTH_LONG).show()

                    return
                }

                memoryGame.flipCard(position)
                rvBoard.adapter?.notifyDataSetChanged()
            }

        })
        rvBoard.hasFixedSize()
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())



    }


}