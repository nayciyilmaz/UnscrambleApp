package com.example.unscrambleapplication.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscrambleapplication.R
import com.example.unscrambleapplication.components.GameButtons
import com.example.unscrambleapplication.components.GameLayout
import com.example.unscrambleapplication.components.GameStatus
import com.example.unscrambleapplication.viewmodel.GameViewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val uiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .padding(top = 70.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.unscramble),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
        GameLayout(
            value = gameViewModel.userGuess,
            onValueChanged = { gameViewModel.updateUserGuess(it) },
            currentScrambleWord = uiState.currentScrambleWord,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            currentWordCount = uiState.currentWordCount,
            isError = uiState.isError,
            gameViewModel = gameViewModel
        )
        GameButtons(
            skipWords = { gameViewModel.selectRandomWord() },
            submit = { gameViewModel.checkUserGuess() },
            resetGuess = { gameViewModel.resetGuess() },
            reshuffle = { gameViewModel.reshuffleCurrentWord() },
            currentWordCount = uiState.currentWordCount,
            gameOver = { gameViewModel.gameOver() }
        )
        GameStatus(
            totalScore = uiState.totalScore
        )
        ShowDialog(
            showDialog = gameViewModel.showDialog,
            totalScore = uiState.totalScore,
            resetGame = { gameViewModel.resetGame() },
            secondsElapsed = uiState.secondsElapsed
        )
    }
}

@Composable
fun ShowDialog(
    showDialog: Boolean,
    totalScore: Int,
    resetGame: () -> Unit,
    secondsElapsed: Int
) {
    val activity = (LocalContext.current as Activity)

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = stringResource(R.string.congratulations)) },
            text = {
                Column {
                    Text("Total Score: $totalScore")
                    Text(
                        "Time: ${secondsElapsed / 60}:${
                            (secondsElapsed % 60).toString()
                                .padStart(2, '0')
                        }"
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    activity.finish()
                    resetGame()
                }) {
                    Text(stringResource(R.string.exit))
                }
            },
            dismissButton = {
                TextButton(onClick = { resetGame() }) {
                    Text(stringResource(R.string.play_again))
                }
            }
        )
    }
}