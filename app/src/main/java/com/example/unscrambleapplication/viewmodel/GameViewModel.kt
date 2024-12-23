package com.example.unscrambleapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscrambleapplication.data.allWords
import com.example.unscrambleapplication.data.correctGuessPoints
import com.example.unscrambleapplication.data.incorrectGuessPenalty
import com.example.unscrambleapplication.data.maxWordCount
import com.example.unscrambleapplication.model.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var usedWords = mutableSetOf<String>()

    var isTimerRunning by mutableStateOf(true)
    var secondsElapsed by mutableStateOf(0)

    var showDialog by mutableStateOf(false)
        private set
    var userGuess by mutableStateOf("")
        private set

    fun updateUserGuess(newUserGuess: String) {
        userGuess = newUserGuess
    }

    init {
        selectRandomWord()
    }

    fun selectRandomWord() {
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

    fun reshuffleCurrentWord() {
        val currentWord = _uiState.value.originalWord
        val reshuffledWord = generateShuffledWord(currentWord)

        _uiState.update { currentState ->
            currentState.copy(
                currentScrambleWord = reshuffledWord,
                isError = false
            )
        }
    }

    fun checkUserGuess() {
        if (userGuess.isBlank()) {
            return
        }

        _uiState.update { currentState ->
            if (_uiState.value.originalWord.equals(userGuess, ignoreCase = true)) {
                currentState.copy(
                    totalScore = _uiState.value.totalScore.plus(correctGuessPoints),
                    isGuessCorrect = true,
                    isError = false
                )
            } else {
                currentState.copy(
                    totalScore = _uiState.value.totalScore.minus(incorrectGuessPenalty),
                    isGuessCorrect = false,
                    isError = true
                )
            }
        }
        gameOver(forceGameOver = _uiState.value.currentWordCount == maxWordCount)
        resetGuess()
    }

    fun gameOver(forceGameOver: Boolean = false) {
        if (forceGameOver || _uiState.value.currentWordCount >= maxWordCount) {
            isTimerRunning = false
            showDialog = true
        } else {
            selectRandomWord()
        }
    }

    fun resetGuess() {
        userGuess = ""
    }

    fun updateTimer() {
        if (isTimerRunning) {
            secondsElapsed += 1
            _uiState.update { currentState ->
                currentState.copy(secondsElapsed = secondsElapsed)
            }
        }
    }

    fun resetGame() {
        usedWords.clear()
        showDialog = false
        secondsElapsed = 0
        isTimerRunning = true
        _uiState.update { currentState ->
            currentState.copy(
                currentWordCount = 0,
                totalScore = 0,
                isError = false,
                originalWord = "",
                isGuessCorrect = false,
                secondsElapsed = secondsElapsed
            )
        }
        selectRandomWord()
    }
}