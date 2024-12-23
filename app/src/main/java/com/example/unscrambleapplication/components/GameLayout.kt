package com.example.unscrambleapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscrambleapplication.R
import com.example.unscrambleapplication.data.maxWordCount
import com.example.unscrambleapplication.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    currentScrambleWord: String,
    keyboardOptions: KeyboardOptions,
    currentWordCount: Int,
    isError: Boolean,
    gameViewModel: GameViewModel
) {
    val minutes = gameViewModel.secondsElapsed / 60
    val seconds = gameViewModel.secondsElapsed % 60

    LaunchedEffect(gameViewModel.isTimerRunning) {
        while (gameViewModel.isTimerRunning) {
            delay(1000L)
            gameViewModel.updateTimer()
        }
    }

    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.word_count, currentWordCount, maxWordCount),
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 8.dp)
                    .width(92.dp)
                    .height(32.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = modifier.weight(1f))

            Text(
                text = "Time: $minutes:${seconds.toString().padStart(2, '0')}",
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 8.dp)
                    .width(100.dp)
                    .height(32.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentScrambleWord,
                fontSize = 36.sp,
            )
            Text(
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(top = 16.dp, bottom = 20.dp)
            )
            OutlinedTextField(
                value = value,
                onValueChange = onValueChanged,
                label = {
                    Text(
                        text = if (isError) stringResource(R.string.guess_wrong)
                        else stringResource(R.string.label)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = keyboardOptions,
                isError = isError
            )
        }
    }
}