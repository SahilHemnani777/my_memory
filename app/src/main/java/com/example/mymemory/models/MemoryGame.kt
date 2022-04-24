package com.example.mymemory.models

import BoardSize
import com.example.mymemory.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize : BoardSize){
    val cards: List<MemoryCard>

    var numPairsFound = 0

    private var indexOfSingleSelectedCard : Int? = null

    private var numCardFlips = 0;

    init {
        // for n card memory game we need n/2 images
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it)
        }
    }

    fun flipCard(position: Int) : Boolean{
        numCardFlips++
        val card = cards[position]
        // Three cases
        // 0 card flipped over- flip over the selected card
        // 1 card flipped over- flip over the selected card + check if the images match
        // 2 card flipped over- restore cards + flip over the selected card

        // but now 1st and the 3rd case is the same so now it skims down to only two cases and
        // difference is only the card flipped and we have to keep the
        var foundMatch = false
        if(indexOfSingleSelectedCard == null) {
            // either 0 cards or 2 cards previously flipper over
            restoreCards()
            indexOfSingleSelectedCard = position

        }else{
            // 1 card previously flipped over
            foundMatch = checkForMatch(position, indexOfSingleSelectedCard!!)
            indexOfSingleSelectedCard=null
        }
        card.isfacedUp= !card.isfacedUp
        return foundMatch
    }

    private fun checkForMatch(position: Int, indexOfSingleSelectedCard: Int): Boolean {
        if(cards[position].identifier != cards[indexOfSingleSelectedCard].identifier){
            return false
        }
        cards[position].isMatched =true
        cards[indexOfSingleSelectedCard].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched){
                card.isfacedUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound ==  boardSize.getNumPairs()
    }

    fun isFlippedUp(position: Int): Boolean {
        return cards[position].isfacedUp
    }

    fun getNumMoves(): Int {
        return numCardFlips /2
    }
}
