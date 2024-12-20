package com.example.unscrambleapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscrambleapplication.data.allWords
import com.example.unscrambleapplication.model.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    fun updateUserGuess(newUserGuess: String) {
        userGuess = newUserGuess
    }

    private var usedWords = mutableSetOf<String>()

    init {
        selectRandomWord()
    }

    private fun selectRandomWord() {
        val word = allWords.subtract(usedWords).randomOrNull() ?: return
        usedWords.add(word)
        updateScrambledWord(word)
    }

     private fun updateScrambledWord(word: String) {
        val shuffledWord = generateShuffledWord(word)

        _uiState.update { currentState ->
            currentState.copy(
                currentScrambleWord = shuffledWord,
                currentWordCount = currentState.currentWordCount + 1,
                originalWord = word,
                isError = false
            )
        }
    }

     private fun generateShuffledWord(word: String): String {
        return word.toList() // kelimeyi harflere ayırma
            .shuffled() // ayırılanları karıştırma
            .joinToString("") // karıştırılanı birleştirme
            .takeIf { it != word } ?: generateShuffledWord(word) // orjinal kelime ile aynıysa tekrar karıştırma
    }
}