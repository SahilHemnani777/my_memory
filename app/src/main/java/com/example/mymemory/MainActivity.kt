package com.example.mymemory

import BoardSize
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.models.MemoryCard
import com.example.mymemory.models.MemoryGame
import com.example.mymemory.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard : RecyclerView
    private lateinit var movesTextView : TextView
    private lateinit var pairsTextView: TextView

    private val boardSize: BoardSize = BoardSize.HARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.recyclerView)
        movesTextView= findViewById(R.id.movesTextView)
        pairsTextView= findViewById(R.id.pairsTextView)

        val memoryGame = MemoryGame(boardSize)

        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object : MemoryBoardAdapter.CardClickListener{
            override fun onCardClick(position: Int) {
                Log.d("TAG", position.toString())
            }

        })
        rvBoard.hasFixedSize()
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())



    }
}