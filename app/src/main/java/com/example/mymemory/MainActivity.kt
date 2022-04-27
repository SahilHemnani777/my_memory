package com.example.mymemory

import BoardSize
import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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

    private var boardSize: BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot= findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.recyclerView)
        movesTextView= findViewById(R.id.movesTextView)
        pairsTextView= findViewById(R.id.pairsTextView)

        setUpBoard()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                if(memoryGame.getNumMoves() > 0 && !memoryGame.haveWonGame()){
                    showAlertDialogue("Quit your current game?", null, View.OnClickListener {
                        setUpBoard()
                    })
                }else{
                    setUpBoard()
                }
                return true
            }
            R.id.new_size -> {
                showNewSizeDialogue()
                return true
            }
        }
        return true
    }

    private fun showNewSizeDialogue() {
        val boardViewSize = LayoutInflater.from(this).inflate(R.layout.dialouge_board_size, null)
        val radioGroupSize = boardViewSize.findViewById<RadioGroup>(R.id.radioGrp)
        // before opening the dialogue we should select the board size which is selected
        when(boardSize) {
            BoardSize.EASY -> radioGroupSize.check(R.id.radioBtnEasy)
            BoardSize.MEDIUM -> radioGroupSize.check(R.id.radioBtnMedium)
            BoardSize.HARD -> radioGroupSize.check(R.id.radioBtnHard)
        }

        showAlertDialogue("Choose New Size", boardViewSize, View.OnClickListener {
            // set the new value for the board size
            boardSize = when (radioGroupSize.checkedRadioButtonId) {
                R.id.radioBtnEasy -> BoardSize.EASY
                R.id.radioBtnMedium -> BoardSize.MEDIUM
                else -> BoardSize.HARD
            }
            // take care of redrawing the whole board
            setUpBoard()
        })
    }

    private fun setUpBoard(){
        when(boardSize){
            BoardSize.EASY -> {
                pairsTextView.text = "Pairs: 0 / 4"
                movesTextView.text ="Easy: 4 X 2"
            }
            BoardSize.MEDIUM -> {
                pairsTextView.text = "Pairs: 0 / 9"
                movesTextView.text ="Easy: 6 X 3"
            }
            BoardSize.HARD -> {
                pairsTextView.text = "Pairs: 0 / 12"
                movesTextView.text ="Easy: 6 X 4"
            }
        }
        pairsTextView.setTextColor(ContextCompat.getColor(this, R.color.color_progress_none))
        memoryGame = MemoryGame(boardSize)

        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object : MemoryBoardAdapter.CardClickListener{
            override fun onCardClick(position: Int){
                updateGameWIthFlip(position)
            }

        })
        rvBoard.hasFixedSize()
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun showAlertDialogue(title: String, view: View?, positiveClickListener : View.OnClickListener) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok") { _, _ ->
                    positiveClickListener.onClick(null)
                }.show()
    }
}