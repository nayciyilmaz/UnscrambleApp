package com.example.unscrambleapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unscrambleapplication.R
import com.example.unscrambleapplication.data.maxWordCount

@Composable
fun GameButtons(
    modifier: Modifier = Modifier,
    skipWords: () -> Unit,
    resetGuess: () -> Unit,
    submit: () -> Unit,
    reshuffle: () -> Unit,
    currentWordCount: Int,
    gameOver: (Boolean) -> Unit
) {
    Column(modifier.padding(12.dp)) {
        Button(
            onClick = { reshuffle() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = stringResource(R.string.reshuffle))
        }
        Button(
            onClick = { submit() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(text = stringResource(R.string.submit))
        }
        Button(
            onClick = {
                if (currentWordCount == maxWordCount) {
                    gameOver(true)
                } else {
                    skipWords()
                    resetGuess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            if (currentWordCount == maxWordCount) {
                Text(text = stringResource(R.string.game_over))
            } else {
                Text(text = stringResource(R.string.skip))
            }
        }
    }
}