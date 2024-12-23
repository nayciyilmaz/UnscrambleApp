![Logo](https://github.com/nayciyilmaz/UnscrambleApp/blob/main/proje1.png?raw=true)
![Logo](https://github.com/nayciyilmaz/UnscrambleApp/blob/main/proje2.png?raw=true)

## Features

Game Logic: Manages the flow of the game, including selecting random words, shuffling the words, checking the user's guess, and updating the score.
Timer: Tracks the elapsed time and updates the UI accordingly.
State Management: Uses StateFlow for managing the game's UI state and ensuring UI updates are performed in response to state changes.
Game Over Handling: Ends the game either after a set number of words or if the user chooses to finish early, showing the final score and elapsed time in a dialog.
Reset Game: Provides functionality to reset the game, clearing used words and starting a new round.
User Input: Captures and processes the user's guesses, providing feedback on correctness.

## Project Structure

GameViewModel: Contains the main game logic and handles interactions with the model. It manages the state of the game, including word selection, score tracking, timer updates, and user input validation.
GameUiState: Holds the UI state of the game, including the current scrambled word, score, timer, and flags for errors and correct guesses.
StateFlow: Ensures the game's UI is updated in response to changes in the game state, such as user guesses and game over status.

## Key Functions

selectRandomWord(): Selects a random word from a predefined list and updates the game state.
generateShuffledWord(): Shuffles the letters of the selected word to create a new scrambled word.
checkUserGuess(): Validates the user's guess, updates the score, and determines if the guess is correct.
reshuffleCurrentWord(): Reshuffles the current word when the user requests it.
gameOver(): Handles the end of the game, displaying a dialog with the final score and time.
resetGame(): Resets the game to its initial state, clearing used words and setting the score and timer back to zero.
updateTimer(): Updates the timer every second if the game is running.

## Technologies Used

Kotlin: Primary language for Android development.
Jetpack Compose: UI toolkit for building native Android UIs in a declarative way.
StateFlow: For managing and observing state changes in a reactive way.
ViewModel: A lifecycle-aware component used to manage UI-related data.
