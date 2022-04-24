package com.example.mymemory.models

data class MemoryCard (
    val identifier: Int,
    var isfacedUp: Boolean = false,
    var isMatched: Boolean = false,
)