package com.example.unscrambleapplication.model

data class GameUiState(
    val currentScrambleWord: String = "",
    val currentWordCount: Int = 0,
    val totalScore: Int = 0,
    val isGuessCorrect: Boolean = false,
    val originalWord: String = "",
    val isError: Boolean = false
)
