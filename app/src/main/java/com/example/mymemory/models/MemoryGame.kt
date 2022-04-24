package com.example.mymemory.models

import BoardSize
import com.example.mymemory.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize : BoardSize){
    val cards: List<MemoryCard>
    var numPairsFoundException = 0

    init {
        // for n card memory game we need n/2 images
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it)
        }
    }
}
