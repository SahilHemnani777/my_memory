package com.example.mymemory

import BoardSize
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.mymemory.utils.EXTRA_BOARD_SIZE

class CreateActivty : AppCompatActivity() {
    private lateinit var boardSize: BoardSize
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_activty)
        boardSize = intent.getSerializableExtra(EXTRA_BOARD_SIZE) as BoardSize
        supportActionBar?.title = when (boardSize) {
            BoardSize.EASY -> "Choose Pics (0 / 4)"
            BoardSize.MEDIUM -> "Choose Pics (0 / 9)"
            else -> "Choose Pics (0 /12)"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}