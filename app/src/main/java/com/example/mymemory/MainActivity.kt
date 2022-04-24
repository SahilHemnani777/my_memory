package com.example.mymemory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard : RecyclerView
    private lateinit var movesTextView : TextView
    private lateinit var pairsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.recyclerView)
        movesTextView= findViewById(R.id.movesTextView)
        pairsTextView= findViewById(R.id.pairsTextView)

        rvBoard.adapter = MemoryBoardAdapter(this, 8)
        rvBoard.hasFixedSize()
        rvBoard.layoutManager = GridLayoutManager(this, 2)



    }
}